public class ASTNot implements ASTNode {
    private final ASTNode exp;

    public ASTNot(ASTNode e) {
        this.exp = e;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue v = this.exp.eval(e);
        
        if (!(v instanceof VBool vBool))
            throw new InterpreterError("~ requires a boolean operand");
        
        return new VBool(!vBool.getValue());
    }
}