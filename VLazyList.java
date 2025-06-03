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
    
    public final void evaluate() throws InterpreterError {
        if (!this.evaluated) {
            // Evaluate head and tail expressions
            this.head = this.headExpr.eval(this.env);
            this.tail = this.tailExpr.eval(this.env);
            this.evaluated = true;
        }
    }
    
    public final boolean isEvaluated() {
        return this.evaluated;
    }
    
    public final boolean isNil() throws InterpreterError {
        // A lazy list is never nil directly... we need to evaluate it first
        evaluate();

        if (this.tail instanceof VList vList)
            return vList.isNil() && this.head == null;
            
        return false;
    }
    
    public final IValue getHead() throws InterpreterError {
        evaluate();

        return this.head;
    }
    
    public final IValue getTail() throws InterpreterError {
        evaluate();

        return this.tail;
    }
    
    @Override
    public final String toStr() {
        try {
            evaluate();

            return "<lazy " + this.head.toStr() + " ?: " + this.tail.toStr() + ">";
        } catch (InterpreterError e) {
            return "<unevaluated lazy list>";
        }
    }
}