public class ASTCaseBranch {
    private final String variable;
    private final ASTNode body;
    
    public ASTCaseBranch(String variable, ASTNode body) {
        this.variable = variable;
        this.body = body;
    }
    
    public String getVariable() {
        return this.variable;
    }
    
    public ASTNode getBody() {
        return this.body;
    }
}