public class ASTLCons implements ASTNode {
    private final ASTNode head;
    private final ASTNode tail;
    
    public ASTLCons(ASTNode head, ASTNode tail) {
        this.head = head;
        this.tail = tail;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        // For lazy cons, we don't evaluate the expressions yet
        // Instead, we store the unevaluated expressions and the environment
        return new VLazyList(e, head, tail);
    }
}