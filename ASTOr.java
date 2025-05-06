public class ASTOr implements ASTNode {
    ASTNode lhs, rhs;

    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v1 = lhs.eval(e);
        if (!(v1 instanceof VBool)) {
            throw new InterpreterError("Left operand of || must be boolean");
        }
        
        if (((VBool)v1).getval()) {
            return new VBool(true); // Short-circuit evaluation
        }
        
        IValue v2 = rhs.eval(e);
        if (!(v2 instanceof VBool)) {
            throw new InterpreterError("Right operand of || must be boolean");
        }
        
        return new VBool(((VBool)v2).getval());
    }

    public ASTOr(ASTNode l, ASTNode r) {
        lhs = l;
        rhs = r;
    }
}