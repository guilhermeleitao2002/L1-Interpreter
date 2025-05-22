public class ASTCons implements ASTNode {
    private final ASTNode head;
    private final ASTNode tail;
    
    public ASTCons(ASTNode head, ASTNode tail) {
        this.head = head;
        this.tail = tail;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue headValue = this.head.eval(e);
        final IValue tailValue = this.tail.eval(e);
        
        if (!(tailValue instanceof VList))
            throw new InterpreterError("List Cons tail must be a list");
        
        return new VList(headValue, tailValue);
    }
}