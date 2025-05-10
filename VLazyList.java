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
            // Evaluate head and tail expressions in the captured environment
            this.head = headExpr.eval(env);
            this.tail = tailExpr.eval(env);
            this.evaluated = true;
        }
    }
    
    public boolean isEvaluated() {
        return this.evaluated;
    }
    
    public boolean isNil() throws InterpreterError {
        // A lazy list is never nil directly - we need to evaluate it first
        evaluate();
        if (this.tail instanceof VList vList) {
            return vList.isNil() && this.head == null;
        }
        return false;
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
            return "<lazy " + this.head.toStr() + " :: " + this.tail.toStr() + ">";
        } catch (InterpreterError e) {
            return "<unevaluated lazy list>";
        }
    }
}