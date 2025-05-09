public class ASTPrint implements ASTNode {
    private final ASTNode expr;
    private final boolean newline;
    
    public ASTPrint(ASTNode expr, boolean newline) {
        this.expr = expr;
        this.newline = newline;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue value = expr.eval(e);
        if (newline) {
            System.out.println(value.toStr());
        } else {
            System.out.print(value.toStr());
        }
        return value;
    }
}