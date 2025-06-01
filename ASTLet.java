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
        final Environment<IValue> newEnv = e.beginScope();
        
        for (Bind decl : this.decls) {
            final IValue val = decl.getExp().eval(newEnv);            
            newEnv.assoc(decl.getId(), val);
        }
        
        return body.eval(newEnv);
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        final TypeEnvironment newGamma = gamma.beginScope();
        
        for (Bind decl : this.decls) {
            final ASTType declType = decl.getExp().typecheck(newGamma, typeDefs);
            newGamma.assoc(decl.getId(), declType);
        }
        
        return this.body.typecheck(newGamma, typeDefs);
    }
}