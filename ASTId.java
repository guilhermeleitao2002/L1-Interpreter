public class ASTId implements ASTNode	{	
    private final String id;
    
    public ASTId(String id)	{
        this.id = id;
    }

    @Override
    public IValue eval(Environment<IValue> env)	throws InterpreterError {
        return env.find(this.id);
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        return gamma.find(this.id);
    }
}