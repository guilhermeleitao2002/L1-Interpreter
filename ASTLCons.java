public class ASTLCons implements ASTNode {
    private final ASTNode head;
    private final ASTNode tail;
    
    public ASTLCons(ASTNode head, ASTNode tail) {
        this.head = head;
        this.tail = tail;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        // For lazy cons, we don't evaluate the expressions yet
        // Instead, we store the unevaluated expressions and the environment
        // We make a copy of the environment to capture the current bindings
        final Environment<IValue> capturedEnv = e.copy();
        return new VLazyList(capturedEnv, this.head, this.tail);
    }

    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        final ASTType headType = this.head.typecheck(gamma, typeDefs);
        final ASTType tailType = this.tail.typecheck(gamma, typeDefs);
        
        if (!(tailType instanceof ASTTList) && tailType != null)
            throw new TypeError("Lazy cons tail must be a list type, got " + tailType.toStr());
        
        final ASTType listElementType = ((ASTTList) tailType).getElementType();
        
        if (!Subtyping.isSubtype(headType, listElementType, typeDefs))
            throw new TypeError("Lazy list element type mismatch: expected " + listElementType.toStr() + 
                            ", got " + headType.toStr());
        
        return new ASTTList(listElementType);
    }
}