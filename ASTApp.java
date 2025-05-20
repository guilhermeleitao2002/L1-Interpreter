
public class ASTApp implements ASTNode {
    private final ASTNode function;
    private final ASTNode argument;
    
    public ASTApp(ASTNode function, ASTNode argument) {
        this.function = function;
        this.argument = argument;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        // Start with the function
        IValue currentValue = function.eval(e);
        
        if (!(currentValue instanceof VClosure)) {
            throw new InterpreterError("Function application requires a function");
        }
        
        final VClosure closure = (VClosure) currentValue;
        final String param = closure.getParam();
        
        // Evaluate the argument
        final IValue argValue = argument.eval(e);
        
        // Create a new environment for function execution
        final Environment<IValue> funEnv = closure.getEnv().beginScope();
        
        // Bind the parameter to the argument
        funEnv.assoc(param, argValue);
        
        // Evaluate the function body in the new environment
        currentValue = closure.getBody().eval(funEnv);
        
        return currentValue;
    }
}