class ASTInt implements ASTNode  {
    private final int v;

    ASTInt(int v) {
        this.v = v;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VInt(this.v);
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        return new ASTTInt();
    }
}