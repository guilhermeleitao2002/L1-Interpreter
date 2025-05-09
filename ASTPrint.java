public class ASTPrint implements ASTNode {
    private ASTNode expr;
    private boolean newline;
    
    public ASTPrint(ASTNode expr, boolean newline) {
        this.expr = expr;
        this.newline = newline;
    }
    
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue value = expr.eval(e);
        if (newline) {
            System.out.println(value.toStr());
        } else {
            System.out.print(value.toStr());
        }
        return value;
    }
}