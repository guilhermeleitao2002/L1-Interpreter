import java.util.*;

public class Subtyping {
    
    public static boolean isSubtype(ASTType subType, ASTType superType, TypeDefEnvironment typeDefs) throws TypeError {
        if (subType instanceof ASTTFunction aSTTFunction)
            subType = aSTTFunction.toCurriedType();
        if (superType instanceof ASTTFunction aSTTFunction)
            superType = aSTTFunction.toCurriedType();

        // A <: A
        if (typeEquals(subType, superType, typeDefs)) {
            return true;
        }
        
        // Resolve type ids
        final ASTType resolvedSub = resolveType(subType, typeDefs);
        final ASTType resolvedSuper = resolveType(superType, typeDefs);
        
        // C <: A, B <: D => A -> B <: C -> D
        if (resolvedSub instanceof ASTTArrow && resolvedSuper instanceof ASTTArrow) {
            final ASTTArrow subArrow = (ASTTArrow) resolvedSub;
            final ASTTArrow superArrow = (ASTTArrow) resolvedSuper;

            return isSubtype(superArrow.dom, subArrow.dom, typeDefs) && 
                   isSubtype(subArrow.codom, superArrow.codom, typeDefs);
        }
        
        // A <:> B => ref(A) <: ref(B)
        if (resolvedSub instanceof ASTTRef && resolvedSuper instanceof ASTTRef) {
            final ASTTRef subRef = (ASTTRef) resolvedSub;
            final ASTTRef superRef = (ASTTRef) resolvedSuper;

            return typeEquals(subRef.getType(), superRef.getType(), typeDefs);
        }
        
        // A <: B => list(A) <: list(B)
        if (resolvedSub instanceof ASTTList && resolvedSuper instanceof ASTTList) {
            final ASTTList subList = (ASTTList) resolvedSub;
            final ASTTList superList = (ASTTList) resolvedSuper;

            return isSubtype(subList.getElementType(), superList.getElementType(), typeDefs);
        }
        
        // Labeled products
        if (resolvedSub instanceof ASTTStruct && resolvedSuper instanceof ASTTStruct)
            return isStructSubtype((ASTTStruct) resolvedSub, (ASTTStruct) resolvedSuper, typeDefs);
        
        // Labeled sums
        if (resolvedSub instanceof ASTTUnion && resolvedSuper instanceof ASTTUnion)
            return isUnionSubtype((ASTTUnion) resolvedSub, (ASTTUnion) resolvedSuper, typeDefs);
        
        return false;
    }
    
    private static boolean isStructSubtype(ASTTStruct subStruct, ASTTStruct superStruct, TypeDefEnvironment typeDefs) throws TypeError {
        final Map<String, ASTType> subFields = subStruct.getFields();
        final Map<String, ASTType> superFields = superStruct.getFields();
        
        for (Map.Entry<String, ASTType> superField : superFields.entrySet()) {
            final String fieldName = superField.getKey();
            final ASTType superFieldType = superField.getValue();
            
            if (!subFields.containsKey(fieldName))
                return false; // Missing required field
            
            final ASTType subFieldType = subFields.get(fieldName);
            if (!isSubtype(subFieldType, superFieldType, typeDefs))
                return false; // Field type mismatch
        }
        
        return true;
    }
    
    private static boolean isUnionSubtype(ASTTUnion subUnion, ASTTUnion superUnion, TypeDefEnvironment typeDefs) throws TypeError {
        final Map<String, ASTType> subVariants = subUnion.getVariants();
        final Map<String, ASTType> superVariants = superUnion.getVariants();
        
        for (Map.Entry<String, ASTType> subVariant : subVariants.entrySet()) {
            final String variantName = subVariant.getKey();
            final ASTType subVariantType = subVariant.getValue();
            
            if (!superVariants.containsKey(variantName))
                return false; // Extra variant not allowed
            
            final ASTType superVariantType = superVariants.get(variantName);
            if (!isSubtype(subVariantType, superVariantType, typeDefs))
                return false; // Variant type mismatch
        }
        
        return true;
    }
    
    private static boolean typeEquals(ASTType type1, ASTType type2, TypeDefEnvironment typeDefs) throws TypeError {
        final ASTType resolved1 = resolveType(type1, typeDefs);
        final ASTType resolved2 = resolveType(type2, typeDefs);
        
        if (resolved1.getClass() != resolved2.getClass())
            return false;
        
        if (resolved1 instanceof ASTTInt || resolved1 instanceof ASTTBool || 
            resolved1 instanceof ASTTString || resolved1 instanceof ASTTUnit)
            return true;
        
        if (resolved1 instanceof ASTTArrow arrow1) {
            final ASTTArrow arrow2 = (ASTTArrow) resolved2;

            return typeEquals(arrow1.dom, arrow2.dom, typeDefs) && 
                   typeEquals(arrow1.codom, arrow2.codom, typeDefs);
        }
        
        if (resolved1 instanceof ASTTRef ref1) {
            final ASTTRef ref2 = (ASTTRef) resolved2;

            return typeEquals(ref1.getType(), ref2.getType(), typeDefs);
        }
        
        if (resolved1 instanceof ASTTList list1) {
            final ASTTList list2 = (ASTTList) resolved2;

            return typeEquals(list1.getElementType(), list2.getElementType(), typeDefs);
        }
        
        if (resolved1 instanceof ASTTStruct struct1) {
            final ASTTStruct struct2 = (ASTTStruct) resolved2;

            return structTypesEqual(struct1, struct2, typeDefs);
        }
        
        if (resolved1 instanceof ASTTUnion union1) {
            final ASTTUnion union2 = (ASTTUnion) resolved2;
            
            return unionTypesEqual(union1, union2, typeDefs);
        }
        
        return false;
    }
    
    private static boolean structTypesEqual(ASTTStruct struct1, ASTTStruct struct2, TypeDefEnvironment typeDefs) throws TypeError {
        final Map<String, ASTType> fields1 = struct1.getFields();
        final Map<String, ASTType> fields2 = struct2.getFields();
        
        if (fields1.size() != fields2.size())
            return false;
        
        for (Map.Entry<String, ASTType> entry : fields1.entrySet()) {
            final String fieldName = entry.getKey();

            if (!fields2.containsKey(fieldName))
                return false;
            if (!typeEquals(entry.getValue(), fields2.get(fieldName), typeDefs))
                return false;
        }
        
        return true;
    }
    
    private static boolean unionTypesEqual(ASTTUnion union1, ASTTUnion union2, TypeDefEnvironment typeDefs) throws TypeError {
        final Map<String, ASTType> variants1 = union1.getVariants();
        final Map<String, ASTType> variants2 = union2.getVariants();
        
        if (variants1.size() != variants2.size())
            return false;
        
        for (Map.Entry<String, ASTType> entry : variants1.entrySet()) {
            final String variantName = entry.getKey();

            if (!variants2.containsKey(variantName))
                return false;
            if (!typeEquals(entry.getValue(), variants2.get(variantName), typeDefs))
                return false;
        }
        
        return true;
    }
    
    private static ASTType resolveType(ASTType type, TypeDefEnvironment typeDefs) throws TypeError {
        if (type instanceof ASTTId typeId)
            return typeDefs.find(typeId.id);

        return type;
    }
}