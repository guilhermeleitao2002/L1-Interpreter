public class ASTNeg implements ASTNode {
    private final ASTNode exp;

    public ASTNeg(ASTNode e) {
		this.exp = e;
    }

	@Override
    public IValue eval(Environment <IValue>e) throws InterpreterError { 
		final IValue v = this.exp.eval(e);

		if (v instanceof VInt vInt) 
			return new VInt(-vInt.getVal()); 
		else
			throw new InterpreterError("Illegal types to - operator");
    }
}

