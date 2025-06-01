public class ASTFalse implements ASTNode {
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VBool(false);
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        return new ASTTBool();
    }
}