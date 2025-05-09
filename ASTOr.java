public class ASTOr implements ASTNode {
    private final ASTNode lhs;
    private final ASTNode rhs;

    public ASTOr(ASTNode l, ASTNode r) {
        this.lhs = l;
        this.rhs = r;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue v1 = lhs.eval(e);
        
        if (!(v1 instanceof VBool)) {
            throw new InterpreterError("Type error: || requires boolean operands");
        }
        
        // Short-circuit evaluation
        if (((VBool)v1).getValue()) {
            return new VBool(true);
        }
        
        final IValue v2 = rhs.eval(e);
        if (!(v2 instanceof VBool)) {
            throw new InterpreterError("Type error: || requires boolean operands");
        }
        
        return new VBool(((VBool)v2).getValue());
    }
}