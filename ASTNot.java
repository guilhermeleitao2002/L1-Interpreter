public class ASTNot implements ASTNode {
    ASTNode exp;

    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v = exp.eval(e);
        if (v instanceof VBool) {
            return new VBool(!((VBool)v).getval());
        } else {
            throw new InterpreterError("Illegal type for ~ operator: expected boolean");
        }
    }

    public ASTNot(ASTNode e) {
        exp = e;
    }
}