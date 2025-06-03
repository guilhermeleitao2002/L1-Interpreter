import java.util.*;

public class ASTStructLiteral implements ASTNode {
    private final Map<String, ASTNode> fields;
    
    public ASTStructLiteral(Map<String, ASTNode> fields) {
        this.fields = fields;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        Map<String, IValue> evaluatedFields = new HashMap<>();
        
        for (Map.Entry<String, ASTNode> entry : this.fields.entrySet()) {
            evaluatedFields.put(entry.getKey(), entry.getValue().eval(e));
        }
        
        return new VStruct(evaluatedFields);
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        final Map<String, ASTType> fieldTypes = new HashMap<>();
        
        for (Map.Entry<String, ASTNode> entry : this.fields.entrySet())
            fieldTypes.put(entry.getKey(), entry.getValue().typecheck(gamma, typeDefs));
        
        return new ASTTStruct(fieldTypes);
    }
}