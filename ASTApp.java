import java.util.List;

public class ASTApp implements ASTNode {
    private final ASTNode function;
    private final List<ASTNode> arguments;
    
    public ASTApp(ASTNode function, List<ASTNode> arguments) {
        this.function = function;
        this.arguments = arguments;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        // Start with the function
        IValue currentValue = function.eval(e);
        
        // Apply each argument one at a time (currying)
        for (ASTNode arg : arguments) {
            if (!(currentValue instanceof VClosure)) {
                throw new InterpreterError("Function application requires a function");
            }
            
            final VClosure closure = (VClosure) currentValue;
            final List<String> params = closure.getParams();
            
            // Each function should take exactly one parameter in this language
            if (params.size() != 1) {
                throw new InterpreterError("Function must take exactly one parameter");
            }
            
            // Evaluate the argument
            final IValue argValue = arg.eval(e);
            
            // Create a new environment for function execution
            final Environment<IValue> funEnv = closure.getEnv().beginScope();
            
            // Bind the parameter to the argument
            funEnv.assoc(params.get(0), argValue);
            
            // Evaluate the function body in the new environment
            currentValue = closure.getBody().eval(funEnv);
        }
        
        return currentValue;
    }
}