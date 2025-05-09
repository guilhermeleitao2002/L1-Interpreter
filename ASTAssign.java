public class ASTAssign implements ASTNode {
    private ASTNode lhs;
    private ASTNode rhs;
    
    public ASTAssign(ASTNode lhs, ASTNode rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
    
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue lhsValue = lhs.eval(e);
        if (!(lhsValue instanceof VRef)) {
            throw new InterpreterError("Left side of assignment must be a reference");
        }
        
        IValue rhsValue = rhs.eval(e);
        ((VRef) lhsValue).setValue(rhsValue);
        return rhsValue;
    }
}