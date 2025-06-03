public class ASTNot implements ASTNode {
    private final ASTNode exp;

    public ASTNot(ASTNode e) {
        this.exp = e;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue v = this.exp.eval(e);
        
        if (!(v instanceof VBool vBool))
            throw new InterpreterError("~ requires a boolean operand");
        
        return new VBool(!vBool.getValue());
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        final ASTType expType = this.exp.typecheck(gamma, typeDefs);
        
        if (!(expType instanceof ASTTBool) && expType != null)
            throw new TypeError("Operand of ~ must be bool, got " + expType.toStr());
        
        return new ASTTBool();
    }
}