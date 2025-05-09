public class ASTId implements ASTNode	{	
    private final String id;
    
    public ASTId(String id)	{
        this.id = id;
    }

    @Override
    public IValue eval(Environment<IValue> env)	throws InterpreterError {
        return env.find(id);
    }

}	
