public class ASTNot implements ASTNode {
    ASTNode exp;

    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v = exp.eval(e);
        
        if (v instanceof VBool) {
            return new VBool(!((VBool)v).getValue());
        }
        throw new InterpreterError("Type error: ~ requires a boolean operand");
    }

    public ASTNot(ASTNode e) {
        exp = e;
    }
}