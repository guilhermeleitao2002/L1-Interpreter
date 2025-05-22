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
}
