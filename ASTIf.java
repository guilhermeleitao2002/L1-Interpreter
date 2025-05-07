public class ASTIf implements ASTNode {
    ASTNode condition, thenBranch, elseBranch;

    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue condValue = condition.eval(e);
        
        if (!(condValue instanceof VBool)) {
            throw new InterpreterError("Type error: if condition must be boolean");
        }
        
        if (((VBool)condValue).getValue()) {
            return thenBranch.eval(e);
        } else {
            return elseBranch.eval(e);
        }
    }

    public ASTIf(ASTNode cond, ASTNode thenB, ASTNode elseB) {
        condition = cond;
        thenBranch = thenB;
        elseBranch = elseB;
    }
}