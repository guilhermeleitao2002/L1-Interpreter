public class ASTNot implements ASTNode {
    private final ASTNode exp;

    public ASTNot(ASTNode e) {
        this.exp = e;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue v = exp.eval(e);
        
        if (v instanceof VBool vBool) {
            return new VBool(!vBool.getValue());
        }
        throw new InterpreterError("Type error: ~ requires a boolean operand");
    }
}