public class ASTAssign implements ASTNode {
    private final ASTNode lhs;
    private final ASTNode rhs;
    
    public ASTAssign(ASTNode lhs, ASTNode rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue lhsValue = this.lhs.eval(e);
        if (!(lhsValue instanceof VCell))
            throw new InterpreterError("Left side of assignment must be a reference");
        
        final IValue rhsValue = this.rhs.eval(e);
        ((VCell) lhsValue).setValue(rhsValue);
        
        return rhsValue;
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        ASTType lhsType = this.lhs.typecheck(gamma, typeDefs);
        ASTType rhsType = this.rhs.typecheck(gamma, typeDefs);
        
        // Resolve type aliases
        if (lhsType instanceof ASTTId typeId)
            lhsType = typeDefs.find(typeId.getId());
        if (rhsType instanceof ASTTId typeId)
            rhsType = typeDefs.find(typeId.getId());
        
        if (!(lhsType instanceof ASTTRef) && lhsType != null)
            throw new TypeError("Left side of assignment must be a reference type, got " + lhsType.toStr());
        
        ASTType refContentType = ((ASTTRef) lhsType).getType();
        if (refContentType instanceof ASTTId typeId)
            refContentType = typeDefs.find(typeId.getId());
        
        if (!Subtyping.isSubtype(rhsType, refContentType, typeDefs))
            throw new TypeError("Cannot assign " + rhsType.toStr() + 
                              " to reference of type " + refContentType.toStr());
        
        return rhsType;
    }
}