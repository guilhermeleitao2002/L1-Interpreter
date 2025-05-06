public class ASTSeq implements ASTNode {
    ASTNode left, right;

    public IValue eval(Environment<IValue> e) throws InterpreterError {
        left.eval(e);  // Evaluate the left expression for side effects
        return right.eval(e);  // Return the result of the right expression
    }

    public ASTSeq(ASTNode l, ASTNode r) {
        left = l;
        right = r;
    }
}