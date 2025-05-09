public class ASTWhile implements ASTNode {
    private ASTNode condition;
    private ASTNode body;
    
    public ASTWhile(ASTNode condition, ASTNode body) {
        this.condition = condition;
        this.body = body;
    }
    
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        while (true) {
            IValue condValue = condition.eval(e);
            if (!(condValue instanceof VBool)) {
                throw new InterpreterError("While condition must be a boolean");
            }
            
            if (!((VBool) condValue).getValue()) {
                return new VBool(false); // Return false as a dummy value
            }
            
            body.eval(e);
        }
    }
}