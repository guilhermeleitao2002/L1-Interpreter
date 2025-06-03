import java.util.HashMap;
import java.util.Map;

public class ASTVariant implements ASTNode {
    private final String label;
    private final ASTNode value;
    
    public ASTVariant(String label, ASTNode value) {
        this.label = label;
        this.value = value;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue evalValue = this.value.eval(e);
        return new VVariant(this.label, evalValue);
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        final ASTType valueType = this.value.typecheck(gamma, typeDefs);        
        final Map<String, ASTType> variants = new HashMap<>();
        
        variants.put(this.label, valueType);
        
        return new ASTTUnion(variants);
    }
}