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
}