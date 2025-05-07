import java.util.List;

public class ASTApp implements ASTNode {
    private ASTNode function;
    private List<ASTNode> arguments;
    
    public ASTApp(ASTNode function, List<ASTNode> arguments) {
        this.function = function;
        this.arguments = arguments;
    }
    
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        // Evaluate the function expression to get a closure
        IValue funValue = function.eval(e);
        if (!(funValue instanceof VClosure)) {
            throw new InterpreterError("Function application requires a function");
        }
        
        VClosure closure = (VClosure) funValue;
        
        // Check if number of arguments matches number of parameters
        List<String> params = closure.getParams();
        if (params.size() != arguments.size()) {
            throw new InterpreterError("Function application with wrong number of arguments");
        }
        
        // Create a new environment for function execution
        Environment<IValue> funEnv = closure.getEnv().beginScope();
        
        // Evaluate each argument and bind it to the corresponding parameter
        for (int i = 0; i < params.size(); i++) {
            IValue argValue = arguments.get(i).eval(e);
            funEnv.assoc(params.get(i), argValue);
        }
        
        // Evaluate the function body in the new environment
        return closure.getBody().eval(funEnv);
    }
}