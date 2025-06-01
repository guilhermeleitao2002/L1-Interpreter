import java.util.*;

public class ASTTypeDef implements ASTNode {
    HashMap<String,ASTType> ltd;
    ASTNode body;

    public ASTTypeDef(HashMap<String,ASTType> ltdp, ASTNode b) {
        this.ltd = ltdp;
        this.body = b;
    }
    
    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        return this.body.eval(env);
    }
}
