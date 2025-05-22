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
        if (this.params.size() > 1) {
            // The innermost function (with the last parameter)
            List<String> lastParam = new ArrayList<>();
            lastParam.add(this.params.get(this.params.size() - 1));

            ASTNode currentBody = this.body;
            
            // Build nested functions from the inside out
            for (int i = this.params.size() - 2; i >= 0; i--) {
                List<String> currentParam = new ArrayList<>();
                currentParam.add(this.params.get(i));

                final ASTNode innerFun = new ASTFun(lastParam, currentBody);

                lastParam = currentParam;
                
                currentBody = innerFun;
            }
            
            // Return the outermost function
            return new VClosure(e, lastParam.get(0), currentBody);
        }
        
        // Single parameter case
        return new VClosure(e, this.params.get(0), this.body);
    }
}