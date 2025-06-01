public class ASTString implements ASTNode {
    private final String value;
    
    public ASTString(String value) {
        // Remove quotes and handle escape sequences
        this.value = processStringLiteral(value);
    }
    
    private String processStringLiteral(String literal) {
        // Remove surrounding quotes
        String content = literal.substring(1, literal.length() - 1);
        
        // Process escape sequences
        return content.replace("\\n", "\n")
                     .replace("\\t", "\t")
                     .replace("\\r", "\r")
                     .replace("\\\\", "\\")
                     .replace("\\\"", "\"");
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