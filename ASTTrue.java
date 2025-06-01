public class ASTTrue implements ASTNode {
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VBool(true);
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        return new ASTTBool();
    }
}