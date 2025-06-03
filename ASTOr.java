public class ASTOr implements ASTNode {
    private final ASTNode lhs;
    private final ASTNode rhs;

    public ASTOr(ASTNode l, ASTNode r) {
        this.lhs = l;
        this.rhs = r;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue v1 = this.lhs.eval(e);        
        if (!(v1 instanceof VBool))
            throw new InterpreterError("Type error: || requires boolean operands");
        
        final IValue v2 = this.rhs.eval(e);
        if (!(v2 instanceof VBool)) {
            throw new InterpreterError("Type error: || requires boolean operands");
        }
        
        return new VBool(((VBool)v1).getValue() || ((VBool)v2).getValue());
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        final ASTType leftType = this.lhs.typecheck(gamma, typeDefs);
        final ASTType rightType = this.rhs.typecheck(gamma, typeDefs);
        
        if (!(leftType instanceof ASTTBool) && leftType != null)
            throw new TypeError("Left operand of || must be bool, got " + leftType.toStr());
        if (!(rightType instanceof ASTTBool) && rightType != null)
            throw new TypeError("Right operand of || must be bool, got " + rightType.toStr());
        
        return new ASTTBool();
    }
}