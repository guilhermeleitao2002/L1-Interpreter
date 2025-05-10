public class VLazyList implements IValue {
    private final Environment<IValue> env;
    private final ASTNode headExpr;
    private final ASTNode tailExpr;
    private boolean evaluated;
    private IValue head;
    private IValue tail;
    
    public VLazyList(Environment<IValue> env, ASTNode headExpr, ASTNode tailExpr) {
        this.env = env;
        this.headExpr = headExpr;
        this.tailExpr = tailExpr;
        this.evaluated = false;
    }
    
    public void evaluate() throws InterpreterError {
        if (!this.evaluated) {
            this.head = headExpr.eval(env);
            this.tail = tailExpr.eval(env);
            this.evaluated = true;
        }
    }
    
    public boolean isEvaluated() {
        return this.evaluated;
    }
    
    public IValue getHead() throws InterpreterError {
        evaluate();
        return this.head;
    }
    
    public IValue getTail() throws InterpreterError {
        evaluate();
        return this.tail;
    }
    
    @Override
    public String toStr() {
        try {
            evaluate();
            return "<lazy list>";
        } catch (InterpreterError e) {
            return "<unevaluated lazy list>";
        }
    }
}