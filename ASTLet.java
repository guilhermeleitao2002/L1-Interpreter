import java.util.List;

public class ASTLet implements ASTNode {
    List<Bind> decls;
    ASTNode body;

    public IValue eval(Environment<IValue> e) throws InterpreterError {
        // Start with the original environment
        Environment<IValue> newEnv = e.beginScope();
        
        // Process each binding sequentially
        for (Bind decl : decls) {
            // Evaluate the expression in the current environment
            IValue val = decl.getExp().eval(newEnv);
            // Add the binding to the current environment
            newEnv.assoc(decl.getId(), val);
        }
        
        // Evaluate the body in the final environment
        IValue result = body.eval(newEnv);
        
        return result;
    }

    public ASTLet(List<Bind> decls, ASTNode b) {
        this.decls = decls;
        body = b;
    }
}