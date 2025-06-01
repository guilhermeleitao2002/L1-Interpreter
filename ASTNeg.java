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
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        ASTType expType = this.exp.typecheck(gamma, typeDefs);
        
        if (!(expType instanceof ASTTInt) && expType != null) {
            throw new TypeError("Operand of unary - must be int, got " + expType.toStr());
        }
        
        return new ASTTInt();
    }
}