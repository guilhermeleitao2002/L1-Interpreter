import java.util.ArrayList;
import java.util.List;

public class ASTFun implements ASTNode {
    private final List<String> params;
    private final ASTNode body;
    private final List<ASTType> paramTypes; // Add type annotations
    
    public ASTFun(List<String> params, ASTNode body) {
        this.params = params;
        this.body = body;
        this.paramTypes = new ArrayList<>(); // Will be set by parser
    }
    
    public ASTFun(List<String> params, List<ASTType> paramTypes, ASTNode body) {
        this.params = params;
        this.paramTypes = paramTypes;
        this.body = body;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        // Current currying implementation
        if (this.params.size() > 1) {
            List<String> lastParam = new ArrayList<>();
            lastParam.add(this.params.get(this.params.size() - 1));

            ASTNode currentBody = this.body;
            
            for (int i = this.params.size() - 2; i >= 0; i--) {
                List<String> currentParam = new ArrayList<>();
                currentParam.add(this.params.get(i));

                final ASTNode innerFun = new ASTFun(lastParam, currentBody);
                lastParam = currentParam;
                currentBody = innerFun;
            }
            
            return new VClosure(e, lastParam.get(0), currentBody);
        }
        
        return new VClosure(e, this.params.get(0), this.body);
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        if (this.paramTypes.isEmpty()) {
            throw new TypeError("Function parameters must have type annotations");
        }
        
        if (this.params.size() != this.paramTypes.size()) {
            throw new TypeError("Number of parameters and type annotations must match");
        }
        
        final TypeEnvironment newGamma = gamma.beginScope();
        
        // Add all parameters to environment
        for (int i = 0; i < this.params.size(); i++) {
            newGamma.assoc(this.params.get(i), this.paramTypes.get(i));
        }
        
        final ASTType bodyType = this.body.typecheck(newGamma, typeDefs);
        
        // Build function type (right-associative for currying)
        ASTType resultType = bodyType;
        for (int i = this.params.size() - 1; i >= 0; i--) {
            resultType = new ASTTArrow(this.paramTypes.get(i), resultType);
        }
        
        return resultType;
    }
}