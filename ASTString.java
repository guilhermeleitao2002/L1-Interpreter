public class ASTString implements ASTNode {
    private final String value;
    
    public ASTString(String value) {
        // Remove quotes
        this.value = processStringLiteral(value);
    }
    
    private String processStringLiteral(String literal) {
        // Remove surrounding quotes
        return literal.substring(1, literal.length() - 1);
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