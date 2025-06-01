import java.util.*;

public class Subtyping {    
    public static boolean isSubtype(ASTType subType, ASTType superType, TypeDefEnvironment typeDefs) throws TypeError {
        // Reflexivity: A <: A
        if (typeEquals(subType, superType, typeDefs)) {
            return true;
        }
        
        // Resolve type identifiers
        ASTType resolvedSub = resolveType(subType, typeDefs);
        ASTType resolvedSuper = resolveType(superType, typeDefs);
        
        // Function types: C <: A, B <: D => A -> B <: C -> D (contravariant in argument, covariant in result)
        if (resolvedSub instanceof ASTTArrow && resolvedSuper instanceof ASTTArrow) {
            ASTTArrow subArrow = (ASTTArrow) resolvedSub;
            ASTTArrow superArrow = (ASTTArrow) resolvedSuper;
            return isSubtype(superArrow.dom, subArrow.dom, typeDefs) && 
                   isSubtype(subArrow.codom, superArrow.codom, typeDefs);
        }
        
        // Reference types: A <:> B => ref(A) <: ref(B) (invariant)
        if (resolvedSub instanceof ASTTRef && resolvedSuper instanceof ASTTRef) {
            ASTTRef subRef = (ASTTRef) resolvedSub;
            ASTTRef superRef = (ASTTRef) resolvedSuper;
            return typeEquals(subRef.getType(), superRef.getType(), typeDefs);
        }
        
        // List types: A <: B => list(A) <: list(B) (covariant)
        if (resolvedSub instanceof ASTTList && resolvedSuper instanceof ASTTList) {
            ASTTList subList = (ASTTList) resolvedSub;
            ASTTList superList = (ASTTList) resolvedSuper;
            return isSubtype(subList.getElementType(), superList.getElementType(), typeDefs);
        }
        
        // Labeled products: width and depth subtyping
        if (resolvedSub instanceof ASTTStruct && resolvedSuper instanceof ASTTStruct) {
            return isStructSubtype((ASTTStruct) resolvedSub, (ASTTStruct) resolvedSuper, typeDefs);
        }
        
        // Labeled sums: depth subtyping only  
        if (resolvedSub instanceof ASTTUnion && resolvedSuper instanceof ASTTUnion) {
            return isUnionSubtype((ASTTUnion) resolvedSub, (ASTTUnion) resolvedSuper, typeDefs);
        }
        
        return false;
    }
    
    private static boolean isStructSubtype(ASTTStruct subStruct, ASTTStruct superStruct, TypeDefEnvironment typeDefs) throws TypeError {
        // Width subtyping: subtype can have more fields
        // Depth subtyping: corresponding fields must be subtypes
        Map<String, ASTType> subFields = subStruct.getFields();
        Map<String, ASTType> superFields = superStruct.getFields();
        
        for (Map.Entry<String, ASTType> superField : superFields.entrySet()) {
            String fieldName = superField.getKey();
            ASTType superFieldType = superField.getValue();
            
            if (!subFields.containsKey(fieldName)) {
                return false; // Missing required field
            }
            
            ASTType subFieldType = subFields.get(fieldName);
            if (!isSubtype(subFieldType, superFieldType, typeDefs)) {
                return false; // Field type mismatch
            }
        }
        
        return true;
    }
    
    private static boolean isUnionSubtype(ASTTUnion subUnion, ASTTUnion superUnion, TypeDefEnvironment typeDefs) throws TypeError {
        // For unions: subtype must have subset of variants with subtype fields
        Map<String, ASTType> subVariants = subUnion.getVariants();
        Map<String, ASTType> superVariants = superUnion.getVariants();
        
        for (Map.Entry<String, ASTType> subVariant : subVariants.entrySet()) {
            String variantName = subVariant.getKey();
            ASTType subVariantType = subVariant.getValue();
            
            if (!superVariants.containsKey(variantName)) {
                return false; // Extra variant not allowed
            }
            
            ASTType superVariantType = superVariants.get(variantName);
            if (!isSubtype(subVariantType, superVariantType, typeDefs)) {
                return false; // Variant type mismatch
            }
        }
        
        return true;
    }
    
    private static boolean typeEquals(ASTType type1, ASTType type2, TypeDefEnvironment typeDefs) throws TypeError {
        ASTType resolved1 = resolveType(type1, typeDefs);
        ASTType resolved2 = resolveType(type2, typeDefs);
        
        if (resolved1.getClass() != resolved2.getClass()) {
            return false;
        }
        
        if (resolved1 instanceof ASTTInt || resolved1 instanceof ASTTBool || 
            resolved1 instanceof ASTTString || resolved1 instanceof ASTTUnit) {
            return true;
        }
        
        if (resolved1 instanceof ASTTArrow arrow1) {
            ASTTArrow arrow2 = (ASTTArrow) resolved2;
            return typeEquals(arrow1.dom, arrow2.dom, typeDefs) && 
                   typeEquals(arrow1.codom, arrow2.codom, typeDefs);
        }
        
        // Add more type equality checks as needed
        
        return false;
    }
    
    private static ASTType resolveType(ASTType type, TypeDefEnvironment typeDefs) throws TypeError {
        if (type instanceof ASTTId typeId) {
            return typeDefs.find(typeId.id);
        }
        return type;
    }
}