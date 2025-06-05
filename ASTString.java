public class ASTString implements ASTNode {
    private final String value;
    
    public ASTString(String value) {
        // Remove surrounding quotes
        this.value = value.substring(1, value.length() - 1);
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VString(this.value);
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        return new ASTTString();
    }
}