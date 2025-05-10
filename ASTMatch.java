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
        
        // Case 1: Regular list
        switch (value) {
            case VList vList -> {
                if (vList.isNil()) {
                    return nilCase.eval(e);
                } else {
                    final Environment<IValue> newEnv = e.beginScope();
                    newEnv.assoc(headVar, vList.getHead());
                    newEnv.assoc(tailVar, vList.getTail());
                    return consCase.eval(newEnv);
                }
            }
            case VLazyList lazyList -> {
                // Force evaluation of the lazy list when matched
                try {
                    // Get head and tail - this will force evaluation if needed
                    IValue head = lazyList.getHead();
                    IValue tail = lazyList.getTail();
                    
                    // Create new environment with bindings for head and tail
                    final Environment<IValue> newEnv = e.beginScope();
                    newEnv.assoc(headVar, head);
                    newEnv.assoc(tailVar, tail);
                    
                    return consCase.eval(newEnv);
                } catch (InterpreterError ex) {
                    // If there's an error evaluating the lazy list, it might be nil
                    try {
                        if (lazyList.isNil()) {
                            return nilCase.eval(e);
                        }
                    } catch (InterpreterError inner) {
                        // Re-throw the original error
                        throw ex;
                    }
                    throw ex;
                }
            }
            default -> throw new InterpreterError("Match expression must evaluate to a list or lazy list");
        }
    }
}