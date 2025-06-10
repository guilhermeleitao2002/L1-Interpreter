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

        // Handle sum constructors
        if (currentValue instanceof VConstructor constructor) {
            IValue argValue = this.argument.eval(e);
            return new VVariant(constructor.getConstructorName(), argValue);
        }

        if (!(currentValue instanceof VClosure))
            throw new InterpreterError("Function application requires a function");
        
        final VClosure closure = (VClosure)currentValue;
        final String param = closure.getParam();
        
        final IValue argValue = this.argument.eval(e);

        final Environment<IValue> funEnv = closure.getEnv().beginScope();
        
        funEnv.assoc(param, argValue);
        
        return closure.getBody().eval(funEnv);
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        ASTType funType = this.function.typecheck(gamma, typeDefs);
        final ASTType argType = this.argument.typecheck(gamma, typeDefs);
        
        if (funType instanceof ASTTId typeId)
            funType = typeDefs.find(typeId.getId());
        
        ASTType resultType = null;
        switch (funType) {
            case ASTTFunction funcType -> {
                var paramTypes = funcType.getParamTypes();
                if (paramTypes.isEmpty())
                    throw new TypeError("Function application requires a function type, got " + funType.toStr());
                
                ASTType firstParamType = paramTypes.get(0);
                if (firstParamType instanceof ASTTId typeId)
                    firstParamType = typeDefs.find(typeId.getId());
                
                if (!Subtyping.isSubtype(argType, firstParamType, typeDefs))
                    throw new TypeError("Argument type " + argType.toStr() +
                                        " is not compatible with parameter type " + firstParamType.toStr());
                if (paramTypes.size() == 1) {
                    resultType = funcType.getReturnType();
                    break;
                }

                var remainingParams = paramTypes.subList(1, paramTypes.size());
                resultType = new ASTTFunction(remainingParams, funcType.getReturnType());
            }
            case ASTTArrow arrowType -> {
                ASTType domainType = arrowType.getDomain();
                if (domainType instanceof ASTTId typeId)
                    domainType = typeDefs.find(typeId.getId());
                
                if (!Subtyping.isSubtype(argType, domainType, typeDefs))
                    throw new TypeError("Argument type " + argType.toStr() +
                                        " is not compatible with parameter type " + domainType.toStr());
                resultType = arrowType.getCodomain();
            }
            default -> throw new TypeError("Function application requires a function type, got " + funType.toStr());
        }

        return resultType;
    }
}