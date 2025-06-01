public class ASTSub implements ASTNode {
    private final ASTNode lhs;
    private final ASTNode rhs;

    public ASTSub(ASTNode l, ASTNode r) {
		this.lhs = l;
		this.rhs = r;
    }

	@Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
		final IValue v1 = this.lhs.eval(e);
		final IValue v2 = this.rhs.eval(e);

		if (v1 instanceof VInt val1 && v2 instanceof VInt val2)
			return new VInt(val1.getVal() - val2.getVal());
		else
			throw new InterpreterError("illegal types to - (subtract) operator");
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        ASTType leftType = this.lhs.typecheck(gamma, typeDefs);
        ASTType rightType = this.rhs.typecheck(gamma, typeDefs);
        
        if (!(leftType instanceof ASTTInt) && leftType != null) {
            throw new TypeError("Left operand of - must be int, got " + leftType.toStr());
        }
        if (!(rightType instanceof ASTTInt) && rightType != null) {
            throw new TypeError("Right operand of - must be int, got " + rightType.toStr());
        }
        
        return new ASTTInt();
    }
}