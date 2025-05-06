public class ASTNotEqual implements ASTNode {
    ASTNode lhs, rhs;

    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v1 = lhs.eval(e);
        IValue v2 = rhs.eval(e);
        
        if (v1 instanceof VInt && v2 instanceof VInt) {
            return new VBool(((VInt)v1).getval() != ((VInt)v2).getval());
        } else if (v1 instanceof VBool && v2 instanceof VBool) {
            return new VBool(((VBool)v1).getval() != ((VBool)v2).getval());
        } else {
            throw new InterpreterError("Type error in ~= operator");
        }
    }

    public ASTNotEqual(ASTNode l, ASTNode r) {
        lhs = l;
        rhs = r;
    }
}