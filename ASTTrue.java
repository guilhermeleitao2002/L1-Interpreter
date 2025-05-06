public class ASTTrue implements ASTNode {
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VBool(true);
    }
}