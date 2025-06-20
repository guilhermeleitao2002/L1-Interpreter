public class ASTPrintln implements ASTNode {
    private final ASTNode expr;
    
    public ASTPrintln(ASTNode expr) {
        this.expr = expr;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue value = this.expr.eval(e);
        System.out.println(value.toStr());
        return value;
    }

    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        return this.expr.typecheck(gamma, typeDefs);
    }
}