import java.util.ArrayList;
import java.util.List;

public class ASTFun implements ASTNode {
    private final List<String> params;
    private final ASTNode body;
    
    public ASTFun(List<String> params, ASTNode body) {
        this.params = params;
        this.body = body;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        // If there are multiple parameters, transform into nested single-parameter functions
        if (params.size() > 1) {
            // The innermost function (with the last parameter)
            List<String> lastParam = new ArrayList<>();
            lastParam.add(params.get(params.size() - 1));
            ASTNode currentBody = body;
            
            // Build nested functions from inside out
            for (int i = params.size() - 2; i >= 0; i--) {
                List<String> currentParam = new ArrayList<>();
                currentParam.add(params.get(i));
                final ASTNode innerFun = new ASTFun(lastParam, currentBody);
                currentBody = innerFun;
                lastParam = currentParam;
            }
            
            // Return the outermost function
            return new VClosure(e, lastParam, currentBody);
        }
        
        // Single parameter case (base case for the curried functions)
        return new VClosure(e, params, body);
    }
}