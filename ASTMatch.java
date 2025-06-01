public class ASTMatch implements ASTNode {
    private final ASTNode expr;
    private final ASTNode nilCase;
    private final String headVar;
    private final String tailVar;
    private final ASTNode consCase;
    
    public ASTMatch(ASTNode expr, ASTNode nilCase, String headVar, String tailVar, ASTNode consCase) {
        this.expr = expr;
        this.nilCase = nilCase;
        this.headVar = headVar;
        this.tailVar = tailVar;
        this.consCase = consCase;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue value = this.expr.eval(e);
        
        switch (value) {
            case VList vList -> {
                if (vList.isNil())
                    return this.nilCase.eval(e);
                else {
                    final Environment<IValue> newEnv = e.beginScope();
                    newEnv.assoc(this.headVar, vList.getHead());
                    newEnv.assoc(this.tailVar, vList.getTail());
                    return this.consCase.eval(newEnv);
                }
            }
            case VLazyList lazyList -> {
                try {
                    final IValue head = lazyList.getHead();
                    final IValue tail = lazyList.getTail();
                    
                    final Environment<IValue> newEnv = e.beginScope();
                    newEnv.assoc(this.headVar, head);
                    newEnv.assoc(this.tailVar, tail);
                    
                    return this.consCase.eval(newEnv);
                } catch (InterpreterError err) {
                    try {
                        if (lazyList.isNil())
                            return this.nilCase.eval(e);
                    } catch (InterpreterError err2) {
                        throw err;
                    }
                    throw err;
                }
            }
            default -> throw new InterpreterError("Match expression must evaluate to a list or lazy list");
        }
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        ASTType exprType = this.expr.typecheck(gamma, typeDefs);
        
        if (!(exprType instanceof ASTTList)) {
            throw new TypeError("List match requires a list type, got " + exprType.toStr());
        }
        
        ASTTList listType = (ASTTList) exprType;
        ASTType elementType = listType.getElementType();
        
        // Type check nil case
        ASTType nilCaseType = this.nilCase.typecheck(gamma, typeDefs);
        
        // Type check cons case with head and tail variables
        TypeEnvironment consEnv = gamma.beginScope();
        consEnv.assoc(this.headVar, elementType);
        consEnv.assoc(this.tailVar, listType);
        ASTType consCaseType = this.consCase.typecheck(consEnv, typeDefs);
        
        // Both cases must have compatible types
        if (!Subtyping.isSubtype(nilCaseType, consCaseType, typeDefs) && 
            !Subtyping.isSubtype(consCaseType, nilCaseType, typeDefs)) {
            throw new TypeError("Match cases must have compatible types, got " + 
                              nilCaseType.toStr() + " and " + consCaseType.toStr());
        }
        
        // Return the more general type
        if (Subtyping.isSubtype(nilCaseType, consCaseType, typeDefs)) {
            return consCaseType;
        } else {
            return nilCaseType;
        }
    }
}