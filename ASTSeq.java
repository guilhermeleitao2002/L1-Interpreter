public class ASTSeq implements ASTNode {
    private final ASTNode first;
    private final ASTNode second;
    
    public ASTSeq(ASTNode first, ASTNode second) {
        this.first = first;
        this.second = second;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        first.eval(e);
        return second.eval(e);
    }
}