public class ASTLowerEqual implements ASTNode {
    private final ASTNode lhs;
    private final ASTNode rhs;

    public ASTLowerEqual(ASTNode l, ASTNode r) {
        this.lhs = l;
        this.rhs = r;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue v1 = lhs.eval(e);
        final IValue v2 = rhs.eval(e);
        
        if (!(v1 instanceof VInt && v2 instanceof VInt))
            throw new InterpreterError("<= requires numeric operands");
            
        return new VBool(((VInt)v1).getVal() <= ((VInt)v2).getVal());
    }
}