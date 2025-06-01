public interface ASTNode {
    IValue eval(Environment<IValue> e) throws InterpreterError;
    ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError;
}