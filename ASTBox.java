public class ASTBox implements ASTNode {
    private ASTNode expr;
    
    public ASTBox(ASTNode expr) {
        this.expr = expr;
    }
    
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue value = expr.eval(e);
        return new VRef(value);
    }
}