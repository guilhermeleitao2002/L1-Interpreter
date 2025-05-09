public class ASTNil implements ASTNode {
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VList();
    }
}