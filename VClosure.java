public class VClosure implements IValue {
    private Environment<IValue> env;
    private List<String> params;
    private ASTNode body;
    
    public VClosure(Environment<IValue> env, List<String> params, ASTNode body) {
        this.env = env;
        this.params = params;
        this.body = body;
    }
    
    public Environment<IValue> getEnv() {
        return env;
    }
    
    public List<String> getParams() {
        return params;
    }
    
    public ASTNode getBody() {
        return body;
    }
    
    public String toStr() {
        return "<function>";
    }
}