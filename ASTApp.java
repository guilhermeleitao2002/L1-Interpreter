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
        
        if (!(funType instanceof ASTTArrow)) {
            throw new TypeError("Function application requires a function type, got " + funType.toStr());
        }
        
        final ASTTArrow arrowType = (ASTTArrow) funType;
        
        if (!Subtyping.isSubtype(argType, arrowType.getDomain(), typeDefs)) {
            throw new TypeError("Argument type " + argType.toStr() + 
                              " is not compatible with parameter type " + arrowType.getDomain().toStr());
        }
        
        return arrowType.getCodomain();
    }
}