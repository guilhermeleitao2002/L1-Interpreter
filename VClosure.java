import java.util.List;

public class VClosure implements IValue {
    private final Environment<IValue> env;
    private final List<String> params;
    private final ASTNode body;
    
    public VClosure(Environment<IValue> env, List<String> params, ASTNode body) {
        this.env = env;
        this.params = params;
        this.body = body;
    }
    
    public Environment<IValue> getEnv() {
        return this.env;
    }
    
    public List<String> getParams() {
        return this.params;
    }
    
    public ASTNode getBody() {
        return this.body;
    }
    
    @Override
    public final String toStr() {
        return "<function>";
    }
}