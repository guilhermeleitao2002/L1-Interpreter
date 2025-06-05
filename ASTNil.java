public class ASTNil implements ASTNode {
    private final ASTType explicitType; // Optional explicit type
    
    public ASTNil() {
        this.explicitType = null;
    }
    
    public ASTNil(ASTType type) {
        this.explicitType = type;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VList();
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        if (this.explicitType != null)
            return this.explicitType;
        
        // default
        return new ASTTList();
    }
}