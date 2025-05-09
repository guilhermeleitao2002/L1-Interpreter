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
        IValue value = expr.eval(e);
        
        if (value instanceof VList vList) {
            if (vList.isNil()) {
                return nilCase.eval(e);
            } else {
                final Environment<IValue> newEnv = e.beginScope();
                newEnv.assoc(headVar, vList.getHead());
                newEnv.assoc(tailVar, vList.getTail());
                return consCase.eval(newEnv);
            }
        } else {
            throw new InterpreterError("Match expression must evaluate to a list");
        }
    }
}