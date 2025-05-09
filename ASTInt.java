class ASTInt implements ASTNode  {
    private final int v;

    ASTInt(int v0) {
        this.v = v0;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VInt(v);                
    }

}
