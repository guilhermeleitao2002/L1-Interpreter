public class ASTCons implements ASTNode {
    private ASTNode head;
    private ASTNode tail;
    
    public ASTCons(ASTNode head, ASTNode tail) {
        this.head = head;
        this.tail = tail;
    }
    
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue headValue = head.eval(e);
        IValue tailValue = tail.eval(e);
        
        if (!(tailValue instanceof VList)) {
            throw new InterpreterError("Cons tail must be a list");
        }
        
        return new VList(headValue, tailValue);
    }
}