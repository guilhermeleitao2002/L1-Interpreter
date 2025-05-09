public class ASTSeq implements ASTNode {
    private ASTNode first;
    private ASTNode second;
    
    public ASTSeq(ASTNode first, ASTNode second) {
        this.first = first;
        this.second = second;
    }
    
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        first.eval(e);
        return second.eval(e);
    }
}