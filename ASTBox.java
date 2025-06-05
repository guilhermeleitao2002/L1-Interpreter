public class ASTBox implements ASTNode {
    private final ASTNode expr;
    
    public ASTBox(ASTNode expr) {
        this.expr = expr;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue value = this.expr.eval(e);
        return new VCell(value);
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        final ASTType exprType = this.expr.typecheck(gamma, typeDefs);
        return new ASTTRef(exprType);
    }
}