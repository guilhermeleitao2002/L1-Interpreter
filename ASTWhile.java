public class ASTWhile implements ASTNode {
    private final ASTNode condition;
    private final ASTNode body;
    
    public ASTWhile(ASTNode condition, ASTNode body) {
        this.condition = condition;
        this.body = body;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        while (true) {
            final IValue condValue = this.condition.eval(e);
            if (!(condValue instanceof VBool))
                throw new InterpreterError("While condition must be a boolean");
            
            if (!((VBool) condValue).getValue())
                return new VBool(false);
            
            this.body.eval(e);
        }
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        final ASTType condType = this.condition.typecheck(gamma, typeDefs);
        
        if (!(condType instanceof ASTTBool) && condType != null)
            throw new TypeError("while condition must be bool, got " + condType.toStr());
        
        this.body.typecheck(gamma, typeDefs);
        
        // Always returns bool (false when loop exits)
        return new ASTTBool();
    }
}