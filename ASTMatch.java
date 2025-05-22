public class ASTMatch implements ASTNode {
    private final ASTNode expr;
    private final ASTNode nilCase;
    private final String headVar;
    private final String tailVar;
    private final ASTNode consCase;
    
    public ASTMatch(ASTNode expr, ASTNode nilCase, String headVar, String tailVar, ASTNode consCase) {
        this.expr = expr;
        this.nilCase = nilCase;
        this.headVar = headVar;
        this.tailVar = tailVar;
        this.consCase = consCase;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue value = this.expr.eval(e);
        
        // Case 1: Regular strict list
        switch (value) {
            case VList vList -> {
                if (vList.isNil())
                    return this.nilCase.eval(e);
                else {
                    final Environment<IValue> newEnv = e.beginScope();

                    newEnv.assoc(this.headVar, vList.getHead());
                    newEnv.assoc(this.tailVar, vList.getTail());

                    return this.consCase.eval(newEnv);
                }
            }
            case VLazyList lazyList -> {
                // It is here that we force the evaluation of the lazy list when matched
                try {
                    final IValue head = lazyList.getHead();
                    final IValue tail = lazyList.getTail();
                    
                    final Environment<IValue> newEnv = e.beginScope();
                    newEnv.assoc(this.headVar, head);
                    newEnv.assoc(this.tailVar, tail);
                    
                    return this.consCase.eval(newEnv);
                } catch (InterpreterError err) {
                    // If there's an error evaluating the lazy list, it might be nil
                    // Although this is rarely the case
                    try {
                        if (lazyList.isNil())
                            return this.nilCase.eval(e);
                    } catch (InterpreterError err2) {
                        throw err;
                    }
                    
                    throw err;
                }
            }
            default -> throw new InterpreterError("Match expression must evaluate to a list or lazy list");
        }
    }
}