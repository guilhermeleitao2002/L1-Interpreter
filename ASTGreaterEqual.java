public class ASTGreaterEqual implements ASTNode {
    private final ASTNode lhs;
    private final ASTNode rhs;

    public ASTGreaterEqual(ASTNode l, ASTNode r) {
        this.lhs = l;
        this.rhs = r;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue v1 = this.lhs.eval(e);
        final IValue v2 = this.rhs.eval(e);
        
        if (!(v1 instanceof VInt && v2 instanceof VInt))
            throw new InterpreterError(">= requires numeric operands");

        return new VBool(((VInt)v1).getVal() >= ((VInt)v2).getVal());
    }

    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        final ASTType leftType = this.lhs.typecheck(gamma, typeDefs);
        final ASTType rightType = this.rhs.typecheck(gamma, typeDefs);
        
        if (!(leftType instanceof ASTTInt) && leftType != null)
            throw new TypeError("Left operand of >= must be int, got " + leftType.toStr());
        if (!(rightType instanceof ASTTInt) && rightType != null)
            throw new TypeError("Right operand of >= must be int, got " + rightType.toStr());
        
        return new ASTTBool();
    }
}