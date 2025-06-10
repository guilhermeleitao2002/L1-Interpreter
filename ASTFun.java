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
                final List<String> currentParam = new ArrayList<>();
                currentParam.add(this.params.get(i));

                final ASTNode innerFun;
                if (!this.paramTypes.isEmpty() && i + 1 < this.paramTypes.size()) {
                    final List<ASTType> remainingTypes = this.paramTypes.subList(i + 1, this.paramTypes.size());
                    innerFun = new ASTFun(lastParam, remainingTypes, currentBody);
                } else
                    innerFun = new ASTFun(lastParam, currentBody);
                
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
            // Resolve type aliases in expectedType
            ASTType resolvedExpectedType = this.expectedType;
            if (resolvedExpectedType instanceof ASTTId typeId)
                resolvedExpectedType = typeDefs.find(typeId.getId());
            
            List<ASTType> inferredParamTypes;
            ASTType expectedReturnType;
            
            switch (resolvedExpectedType) {
                case ASTTFunction funcType -> {
                    inferredParamTypes = funcType.getParamTypes();
                    expectedReturnType = funcType.getReturnType();
                }
                case ASTTArrow arrowType -> {
                    // Convert curried arrow type to parameter list
                    inferredParamTypes = new ArrayList<>();
                    ASTType current = arrowType;
                    while (current instanceof ASTTArrow currentArrow) {
                        inferredParamTypes.add(currentArrow.getDomain());
                        current = currentArrow.getCodomain();
                    }
                    expectedReturnType = current;
                }
                default -> throw new TypeError("Expected function type, got " + resolvedExpectedType.toStr());
            }
            
            if (inferredParamTypes.size() != this.params.size())
                throw new TypeError("Parameter count mismatch: expected " + inferredParamTypes.size() + 
                                ", got " + this.params.size());
            
            final TypeEnvironment newGamma = gamma.beginScope();
            
            for (int i = 0; i < this.params.size(); i++) {
                ASTType paramType = inferredParamTypes.get(i);
                if (paramType instanceof ASTTId typeId)
                    paramType = typeDefs.find(typeId.getId());

                newGamma.assoc(this.params.get(i), paramType);
            }
            
            final ASTType bodyType = this.body.typecheck(newGamma, typeDefs);
            
            if (!Subtyping.isSubtype(bodyType, expectedReturnType, typeDefs))
                throw new TypeError("Function body type " + bodyType.toStr() + 
                                " does not match expected return type " + expectedReturnType.toStr());
            
            return resolvedExpectedType;
        } else if (!this.paramTypes.isEmpty()) {
            final TypeEnvironment newGamma = gamma.beginScope();
            
            for (int i = 0; i < this.params.size(); i++) {
                ASTType paramType = this.paramTypes.get(i);
                if (paramType instanceof ASTTId typeId)
                    paramType = typeDefs.find(typeId.getId());

                newGamma.assoc(this.params.get(i), paramType);
            }
            
            final ASTType bodyType = this.body.typecheck(newGamma, typeDefs);
            
            ASTType resultType = bodyType;
            for (int i = this.params.size() - 1; i >= 0; i--) {
                ASTType paramType = this.paramTypes.get(i);
                if (paramType instanceof ASTTId typeId)
                    paramType = typeDefs.find(typeId.getId());

                resultType = new ASTTArrow(paramType, resultType);
            }
            
            return resultType;
        } else
            throw new TypeError("Function parameters need type annotations when no function type is declared");
    }
}