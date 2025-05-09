public class ASTNeg implements ASTNode {
    private final ASTNode exp;

    public ASTNeg(ASTNode e) {
		this.exp = e;
    }

	@Override
    public IValue eval(Environment <IValue>e) throws InterpreterError { 
		final IValue v0 = exp.eval(e);
		if (v0 instanceof VInt vInt) { 
			return new VInt(-vInt.getVal()); 
		} else { 
			throw new InterpreterError("illegal types to neg operator"); 
		}
    }
}

