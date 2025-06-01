public class ASTEqual implements ASTNode {
    private final ASTNode lhs;
    private final ASTNode rhs;
    
    public ASTEqual(ASTNode l, ASTNode r) {
        this.lhs = l;
        this.rhs = r;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue v1 = this.lhs.eval(e);
        final IValue v2 = this.rhs.eval(e);
        
        if (v1 instanceof VInt && v2 instanceof VInt)
            return new VBool(((VInt)v1).getVal() == ((VInt)v2).getVal());

        if (v1 instanceof VBool && v2 instanceof VBool)
            return new VBool(((VBool)v1).getValue() == ((VBool)v2).getValue());
            
        throw new InterpreterError("== requires matching numeric or boolean operands");
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        ASTType leftType = this.lhs.typecheck(gamma, typeDefs);
        ASTType rightType = this.rhs.typecheck(gamma, typeDefs);
        
        if (leftType instanceof ASTTInt && rightType instanceof ASTTInt) {
            return new ASTTBool();
        }
        if (leftType instanceof ASTTBool && rightType instanceof ASTTBool) {
            return new ASTTBool();
        }

       throw new TypeError("== requires matching int or bool operands, got " + 
                          leftType.toStr() + " and " + rightType.toStr());
    }
}