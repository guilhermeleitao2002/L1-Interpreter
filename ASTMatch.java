public class ASTMatch implements ASTNode {
    private ASTNode expr;
    private ASTNode nilCase;
    private String headVar;
    private String tailVar;
    private ASTNode consCase;
    
    public ASTMatch(ASTNode expr, ASTNode nilCase, String headVar, String tailVar, ASTNode consCase) {
        this.expr = expr;
        this.nilCase = nilCase;
        this.headVar = headVar;
        this.tailVar = tailVar;
        this.consCase = consCase;
    }
    
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue value = expr.eval(e);
        
        if (value instanceof VList) {
            VList list = (VList) value;
            if (list.isNil()) {
                return nilCase.eval(e);
            } else {
                Environment<IValue> newEnv = e.beginScope();
                newEnv.assoc(headVar, list.getHead());
                newEnv.assoc(tailVar, list.getTail());
                return consCase.eval(newEnv);
            }
        } else {
            throw new InterpreterError("Match expression must evaluate to a list");
        }
    }
}