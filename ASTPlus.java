public class ASTPlus implements ASTNode {
    private final ASTNode lhs;
    private final ASTNode rhs;

    public ASTPlus(ASTNode l, ASTNode r) {
        this.lhs = l;
        this.rhs = r;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue v1 = this.lhs.eval(e);
        final IValue v2 = this.rhs.eval(e);
        
        // Integer addition
        if (v1 instanceof VInt && v2 instanceof VInt) {
            final int i1 = ((VInt) v1).getVal();
            final int i2 = ((VInt) v2).getVal();
            return new VInt(i1 + i2);
        }
        
        // String concatenation (including type conversion)
        if ((v1 instanceof VString || v2 instanceof VString) &&
            v1 != null && v2 != null) {
            return new VString(v1.toStr() + v2.toStr());
        }
        
        throw new InterpreterError("Illegal types to + operator");
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        ASTType leftType = this.lhs.typecheck(gamma, typeDefs);
        ASTType rightType = this.rhs.typecheck(gamma, typeDefs);
        
        // Integer addition
        if (leftType instanceof ASTTInt && rightType instanceof ASTTInt) {
            return new ASTTInt();
        }
        
        // String concatenation
        if (leftType instanceof ASTTString || rightType instanceof ASTTString) {
            return new ASTTString();
        }
        
        throw new TypeError("+ operator requires int or string operands, got " + 
                          leftType.toStr() + " and " + rightType.toStr());
    }
}