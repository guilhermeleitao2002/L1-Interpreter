import java.util.ArrayList;
import java.util.List;

public class ASTFun implements ASTNode {
    private List<String> params;
    private ASTNode body;
    
    public ASTFun(List<String> params, ASTNode body) {
        this.params = params;
        this.body = body;
    }
    
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        // Create a closure with the current environment and function parameters/body
        return new VClosure(e, params, body);
    }
}