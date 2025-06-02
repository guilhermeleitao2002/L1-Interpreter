import java.util.*;

public class ASTTypeDef implements ASTNode {
    private final Map<String, ASTType> typeDefs;
    private final ASTNode body;

    public ASTTypeDef(Map<String, ASTType> typeDefs, ASTNode body) {
        this.typeDefs = typeDefs;
        this.body = body;
    }
    
    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        Environment<IValue> newEnv = env.beginScope();
        
        // Add union constructors to environment
        for (Map.Entry<String, ASTType> entry : this.typeDefs.entrySet()) {
            String typeName = entry.getKey();
            ASTType type = entry.getValue();
            if (type instanceof ASTTUnion unionType) {
                for (String variantName : unionType.getVariants().keySet()) {
                    newEnv.assoc(variantName, new VConstructor(variantName, typeName));
                }
            }
        }
        
        return this.body.eval(newEnv);
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefEnv) throws TypeError {
        final TypeDefEnvironment newTypeDefEnv = typeDefEnv.beginScope();
        final TypeEnvironment newGamma = gamma.beginScope();
        
        // Add all type definitions to the environment
        for (Map.Entry<String, ASTType> entry : this.typeDefs.entrySet()) {
            newTypeDefEnv.assoc(entry.getKey(), entry.getValue());
        }
        
        // Add union constructors to type environment
        for (Map.Entry<String, ASTType> entry : this.typeDefs.entrySet()) {
            String typeName = entry.getKey();
            ASTType type = entry.getValue();
            if (type instanceof ASTTUnion unionType) {
                for (Map.Entry<String, ASTType> variant : unionType.getVariants().entrySet()) {
                    String variantName = variant.getKey();
                    ASTType variantType = variant.getValue();
                    // Constructor has type: VariantType -> UnionType
                    ASTType constructorType = new ASTTArrow(variantType, new ASTTId(typeName));
                    newGamma.assoc(variantName, constructorType);
                }
            }
        }
        
        return this.body.typecheck(newGamma, newTypeDefEnv);
    }
}