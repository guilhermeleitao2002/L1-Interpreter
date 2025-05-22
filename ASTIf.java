public class ASTIf implements ASTNode {
    private final ASTNode condition;
    private final ASTNode thenBranch;
    private final ASTNode elseBranch;

    public ASTIf(ASTNode cond, ASTNode thenB, ASTNode elseB) {
        this.condition = cond;
        this.thenBranch = thenB;
        this.elseBranch = elseB;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue condValue = this.condition.eval(e);
        
        if (!(condValue instanceof VBool))
            throw new InterpreterError("if condition must be boolean");
        
        if (((VBool)condValue).getValue())
            return this.thenBranch.eval(e);
        else
            return this.elseBranch.eval(e);
    }
}