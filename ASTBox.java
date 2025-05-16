public class ASTBox implements ASTNode {
    private final ASTNode expr;
    
    public ASTBox(ASTNode expr) {
        this.expr = expr;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue value = expr.eval(e);
        return new VCell(value);
    }
}