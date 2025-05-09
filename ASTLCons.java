public class ASTLCons implements ASTNode {
    private ASTNode head;
    private ASTNode tail;
    
    public ASTLCons(ASTNode head, ASTNode tail) {
        this.head = head;
        this.tail = tail;
    }
    
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VLazyList(head, tail, e);
    }
}