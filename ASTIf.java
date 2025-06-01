public class ASTIf implements ASTNode {
    private final ASTNode condition;
    private final ASTNode thenBranch;
    private final ASTNode elseBranch;

    public ASTIf(ASTNode cond, ASTNode thenB, ASTNode elseB) {
        this.condition = cond;
        this.thenBranch = thenB;
        this.elseBranch = elseB;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue condValue = this.condition.eval(e);
        
        if (!(condValue instanceof VBool))
            throw new InterpreterError("if condition must be boolean");
        
        if (((VBool)condValue).getValue())
            return this.thenBranch.eval(e);
        else
            return this.elseBranch.eval(e);
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        ASTType condType = this.condition.typecheck(gamma, typeDefs);
        
        if (!(condType instanceof ASTTBool) && condType != null) {
            throw new TypeError("if condition must be bool, got " + condType.toStr());
        }
        
        ASTType thenType = this.thenBranch.typecheck(gamma, typeDefs);
        ASTType elseType = this.elseBranch.typecheck(gamma, typeDefs);
        
        if (!Subtyping.isSubtype(thenType, elseType, typeDefs) && 
            !Subtyping.isSubtype(elseType, thenType, typeDefs)) {
            throw new TypeError("if branches must have compatible types, got " + 
                              thenType.toStr() + " and " + elseType.toStr());
        }
        
        // Return the more general type (supertype)
        if (Subtyping.isSubtype(thenType, elseType, typeDefs)) {
            return elseType;
        } else {
            return thenType;
        }
    }
}