public class ASTDeref implements ASTNode {
    private final ASTNode expr;
    
    public ASTDeref(ASTNode expr) {
        this.expr = expr;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue value = expr.eval(e);
        if (!(value instanceof VRef)) {
            throw new InterpreterError("Cannot dereference non-reference value");
        }
        return ((VRef) value).getValue();
    }
}