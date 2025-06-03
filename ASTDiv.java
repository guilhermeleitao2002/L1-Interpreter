public class ASTDiv implements ASTNode {
    private final ASTNode lhs;
    private final ASTNode rhs;

    public ASTDiv(ASTNode l, ASTNode r) {
		this.lhs = l;
		this.rhs = r;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
		final IValue v1 = this.lhs.eval(e);
		final IValue v2 = this.rhs.eval(e);

		if (v1 instanceof VInt && v2 instanceof VInt) {
			final int i1 = ((VInt) v1).getVal();
			final int i2 = ((VInt) v2).getVal();

			if (i2 == 0)
				throw new InterpreterError("/ by zero");

			return new VInt(i1 / i2);
		} else
			throw new InterpreterError("Illegal types to / operator");
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        final ASTType leftType = this.lhs.typecheck(gamma, typeDefs);
        final ASTType rightType = this.rhs.typecheck(gamma, typeDefs);
        
        if (!(leftType instanceof ASTTInt) && leftType != null)
            throw new TypeError("Left operand of / must be int, got " + leftType.toStr());
        if (!(rightType instanceof ASTTInt) && rightType != null)
            throw new TypeError("Right operand of / must be int, got " + rightType.toStr());
        
        return new ASTTInt();
    }
}