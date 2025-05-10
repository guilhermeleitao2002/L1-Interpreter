public class ASTNotEqual implements ASTNode {
    private final ASTNode lhs;
    private final ASTNode rhs;
    
    public ASTNotEqual(ASTNode l, ASTNode r) {
        this.lhs = l;
        this.rhs = r;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue v1 = lhs.eval(e);
        final IValue v2 = rhs.eval(e);
        
        if (v1 instanceof VInt && v2 instanceof VInt) {
            return new VBool(((VInt)v1).getVal() != ((VInt)v2).getVal());
        }
        if (v1 instanceof VBool && v2 instanceof VBool) {
            return new VBool(((VBool)v1).getValue() != ((VBool)v2).getValue());
        }
        throw new InterpreterError("Type error: ~= requires matching numeric or boolean operands");
    }
}