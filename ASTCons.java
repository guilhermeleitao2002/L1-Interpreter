public class ASTCons implements ASTNode {
    private final ASTNode head;
    private final ASTNode tail;
    
    public ASTCons(ASTNode head, ASTNode tail) {
        this.head = head;
        this.tail = tail;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue headValue = this.head.eval(e);
        final IValue tailValue = this.tail.eval(e);
        
        if (!(tailValue instanceof VList))
            throw new InterpreterError("List Cons tail must be a list");
        
        return new VList(headValue, tailValue);
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        final ASTType headType = this.head.typecheck(gamma, typeDefs);
        final ASTType tailType = this.tail.typecheck(gamma, typeDefs);
        
        if (!(tailType instanceof ASTTList) && tailType != null)
            throw new TypeError("List cons tail must be a list type, got " + tailType.toStr());
        
        final ASTType listElementType = ((ASTTList) tailType).getElementType();
        
        if (!Subtyping.isSubtype(headType, listElementType, typeDefs))
            throw new TypeError("List element type mismatch: expected " + listElementType.toStr() + 
                              ", got " + headType.toStr());
        
        return new ASTTList(listElementType);
    }
}