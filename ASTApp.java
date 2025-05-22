
public class ASTApp implements ASTNode {
    private final ASTNode function;
    private final ASTNode argument;
    
    public ASTApp(ASTNode function, ASTNode argument) {
        this.function = function;
        this.argument = argument;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue currentValue = this.function.eval(e);

        if (!(currentValue instanceof VClosure))
            throw new InterpreterError("Function application requires a function");
        
        final VClosure closure = (VClosure)currentValue;
        final String param = closure.getParam();
        
        final IValue argValue = this.argument.eval(e);

        final Environment<IValue> funEnv = closure.getEnv().beginScope();
        
        funEnv.assoc(param, argValue);
        
        return closure.getBody().eval(funEnv);
    }
}