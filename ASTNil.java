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
        if (explicitType != null) {
            return explicitType;
        }
        
        // For now, create a polymorphic int list type as default
        // In a full implementation, this would use proper type inference
        return new ASTTList(new ASTTInt());
    }
}