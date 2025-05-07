import java.util.List;

public class ASTLet implements ASTNode {
    List<Bind> decls;
    ASTNode body;

    public IValue eval(Environment<IValue> e) throws InterpreterError {
        Environment<IValue> en = e.beginScope();
        
        // Evaluate each declaration and add to environment
        for (Bind decl : decls) {
            IValue val = decl.getExp().eval(e);
            en.assoc(decl.getId(), val);
        }
        
        // Evaluate body in the new environment
        IValue result = body.eval(en);
        
        // Return to the outer scope (though the result is already captured)
        // This is not strictly necessary but good practice
        en.endScope();
        
        return result;
    }

    public ASTLet(List<Bind> decls, ASTNode b) {
        this.decls = decls;
        body = b;
    }
}