import java.util.ArrayList;
import java.util.List;

public class ASTFun implements ASTNode {
    private final List<String> params;
    private final ASTNode body;
    private final List<ASTType> paramTypes;
    private ASTType expectedType;
    
    public ASTFun(List<String> params, List<ASTType> paramTypes, ASTNode body) {
        this.params = params;
        this.paramTypes = paramTypes;
        this.body = body;
        this.expectedType = null;
    }
    
    public ASTFun(List<String> params, ASTNode body) {
        this.params = params;
        this.paramTypes = new ArrayList<>();
        this.body = body;
        this.expectedType = null;
    }

    public void setExpectedType(ASTType expectedType) {
        this.expectedType = expectedType;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
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
        if (this.expectedType != null) {
            final List<ASTType> inferredParamTypes = extractParameterTypes(this.expectedType);
            
            if (inferredParamTypes.size() != this.params.size()) {
                throw new TypeError("Parameter count mismatch: expected " + inferredParamTypes.size() + 
                                ", got " + this.params.size());
            }
            
            final TypeEnvironment newGamma = gamma.beginScope();
            
            for (int i = 0; i < this.params.size(); i++) {
                newGamma.assoc(this.params.get(i), inferredParamTypes.get(i));
            }
            
            final ASTType bodyType = this.body.typecheck(newGamma, typeDefs);
            final ASTType expectedReturnType = extractReturnType(this.expectedType);
            
            if (!Subtyping.isSubtype(bodyType, expectedReturnType, typeDefs)) {
                throw new TypeError("Function body type " + bodyType.toStr() + 
                                " does not match expected return type " + expectedReturnType.toStr());
            }
            
            return this.expectedType;
        } else {
            if (this.paramTypes.isEmpty()) {
                throw new TypeError("Function parameters need type annotations when no function type is declared");
            }
            
            final TypeEnvironment newGamma = gamma.beginScope();
            
            for (int i = 0; i < this.params.size(); i++) {
                newGamma.assoc(this.params.get(i), this.paramTypes.get(i));
            }
            
            final ASTType bodyType = this.body.typecheck(newGamma, typeDefs);
            
            ASTType resultType = bodyType;
            for (int i = this.params.size() - 1; i >= 0; i--) {
                resultType = new ASTTArrow(this.paramTypes.get(i), resultType);
            }
            
            return resultType;
        }
    }

    private List<ASTType> extractParameterTypes(ASTType functionType) {
        final List<ASTType> paramTypes = new ArrayList<>();
        ASTType current = functionType;
        
        while (current instanceof ASTTArrow) {
            final ASTTArrow arrow = (ASTTArrow) current;
            paramTypes.add(arrow.getDomain());
            current = arrow.getCodomain();
        }
        
        return paramTypes;
    }

    private ASTType extractReturnType(ASTType functionType) {
        ASTType current = functionType;
        while (current instanceof ASTTArrow) {
            current = ((ASTTArrow) current).getCodomain();
        }
        return current;
    }
}