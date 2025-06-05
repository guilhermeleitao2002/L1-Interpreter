import java.util.List;

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

        // Handle union constructors
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
        final ASTType funType = this.function.typecheck(gamma, typeDefs);
        final ASTType argType = this.argument.typecheck(gamma, typeDefs);
        
        // Handle both ASTTFunction and ASTTArrow
        if (funType instanceof ASTTFunction aSTTFunction) {
            final ASTTFunction funcType = aSTTFunction;
            final List<ASTType> paramTypes = funcType.getParamTypes();
            
            if (paramTypes.isEmpty()) {
                throw new TypeError("Function application requires a function type, got " + funType.toStr());
            }
            
            if (!Subtyping.isSubtype(argType, paramTypes.get(0), typeDefs))
                throw new TypeError("Argument type " + argType.toStr() + 
                                " is not compatible with parameter type " + paramTypes.get(0).toStr());
            
            if (paramTypes.size() == 1)
                return funcType.getReturnType();
            else {
                final List<ASTType> remainingParams = paramTypes.subList(1, paramTypes.size());
                return new ASTTFunction(remainingParams, funcType.getReturnType());
            }
            
        } else if (funType instanceof ASTTArrow aSTTArrow) {
            final ASTTArrow arrowType = aSTTArrow;
            
            if (!Subtyping.isSubtype(argType, arrowType.getDomain(), typeDefs)) {
                throw new TypeError("Argument type " + argType.toStr() + 
                                " is not compatible with parameter type " + arrowType.getDomain().toStr());
            }
            
            return arrowType.getCodomain();
            
        } else
            throw new TypeError("Function application requires a function type, got " + funType.toStr());
    }
}