public class ASTNil implements ASTNode {
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VList();
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        // nil can be a list of any type - we need type inference here
        // For now, return a polymorphic list type or require explicit typing
        throw new TypeError("nil requires explicit type annotation");
    }
}