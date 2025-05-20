
public class VClosure implements IValue {
    private final Environment<IValue> env;
    private final String param;
    private final ASTNode body;
    
    public VClosure(Environment<IValue> env, String param, ASTNode body) {
        this.env = env;
        this.param = param;
        this.body = body;
    }
    
    public Environment<IValue> getEnv() {
        return this.env;
    }
    
    public String getParam() {
        return this.param;
    }
    
    public ASTNode getBody() {
        return this.body;
    }
    
    @Override
    public final String toStr() {
        return "<function>";
    }
}