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
        final Environment<IValue> newEnv = env.beginScope();
        
        // Add union constructors to environment
        for (Map.Entry<String, ASTType> entry : this.typeDefs.entrySet()) {
            final String typeName = entry.getKey();
            final ASTType type = entry.getValue();

            if (type instanceof ASTTUnion unionType)
                for (String variantName : unionType.getVariants().keySet())
                    newEnv.assoc(variantName, new VConstructor(variantName, typeName));
        }
        
        return this.body.eval(newEnv);
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefEnv) throws TypeError {
        final TypeDefEnvironment newTypeDefEnv = typeDefEnv.beginScope();
        final TypeEnvironment newGamma = gamma.beginScope();
        
        for (Map.Entry<String, ASTType> entry : this.typeDefs.entrySet())
            newTypeDefEnv.assoc(entry.getKey(), entry.getValue());
        
        for (Map.Entry<String, ASTType> entry : this.typeDefs.entrySet()) {
            final String typeName = entry.getKey();
            final ASTType type = entry.getValue();

            if (type instanceof ASTTUnion unionType)
                for (Map.Entry<String, ASTType> variant : unionType.getVariants().entrySet()) {
                    final String variantName = variant.getKey();
                    final ASTType variantType = variant.getValue();

                    final ASTType constructorType = new ASTTArrow(variantType, new ASTTId(typeName));
                    newGamma.assoc(variantName, constructorType);
                }
        }
        
        return this.body.typecheck(newGamma, newTypeDefEnv);
    }
}