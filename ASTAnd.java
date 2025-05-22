public class ASTAnd implements ASTNode {
    final ASTNode lhs, rhs;

    public ASTAnd(ASTNode l, ASTNode r) {
        this.lhs = l;
        this.rhs = r;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue v1 = lhs.eval(e);
        
        if (!(v1 instanceof VBool))
            throw new InterpreterError("Type error: && requires boolean operands");
        
        final IValue v2 = rhs.eval(e);
        if (!(v2 instanceof VBool))
            throw new InterpreterError("Type error: && requires boolean operands");
        
        return new VBool(((VBool)v1).getValue() && ((VBool)v2).getValue());
    }
}