import java.util.List;

public class ASTLet implements ASTNode {
    private final List<Bind> decls;
    private final ASTNode body;

    public ASTLet(List<Bind> decls, ASTNode body) {
        this.decls = decls;
        this.body = body;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        // Start with the original environment
        final Environment<IValue> newEnv = e.beginScope();
        
        // Process each binding sequentially
        for (Bind decl : decls) {
            // Evaluate the expression in the current environment
            final IValue val = decl.getExp().eval(newEnv);
            // Add the binding to the current environment
            newEnv.assoc(decl.getId(), val);
        }
        
        // Evaluate the body in the final environment
        final IValue result = body.eval(newEnv);
        
        return result;
    }
}