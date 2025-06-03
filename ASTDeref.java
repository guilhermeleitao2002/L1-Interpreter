public class ASTDeref implements ASTNode {
    private final ASTNode expr;
    
    public ASTDeref(ASTNode expr) {
        this.expr = expr;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue value = this.expr.eval(e);
        if (!(value instanceof VCell))
            throw new InterpreterError("Cannot dereference non-reference value");

        return ((VCell) value).getValue();
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        final ASTType exprType = this.expr.typecheck(gamma, typeDefs);
        
        if (!(exprType instanceof ASTTRef) && exprType != null)
            throw new TypeError("Cannot dereference non-reference type " + exprType.toStr());
        
        return ((ASTTRef) exprType).getType();
    }
}