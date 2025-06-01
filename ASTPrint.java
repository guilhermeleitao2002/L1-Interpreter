public class ASTPrint implements ASTNode {
    private final ASTNode expr;
    
    public ASTPrint(ASTNode expr) {
        this.expr = expr;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue value = this.expr.eval(e);

        System.out.print(value.toStr());
        
        return value;
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        return this.expr.typecheck(gamma, typeDefs);
    }
}