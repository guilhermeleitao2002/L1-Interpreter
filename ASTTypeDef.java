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
        return this.body.eval(env);
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefEnv) throws TypeError {
        final TypeDefEnvironment newTypeDefEnv = typeDefEnv.beginScope();
        
        // Add all type definitions to the environment
        for (Map.Entry<String, ASTType> entry : this.typeDefs.entrySet()) {
            newTypeDefEnv.assoc(entry.getKey(), entry.getValue());
        }
        
        return this.body.typecheck(gamma, newTypeDefEnv);
    }
}