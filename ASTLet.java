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
            if (decl.hasType()) {
                final ASTType declaredType = decl.getType();
                
                // For functions, set expected type and add to environment first - for recursion!
                if (decl.getExp() instanceof ASTFun aSTFun) {
                    final ASTFun fun = aSTFun;
                    fun.setExpectedType(declaredType);
                    newGamma.assoc(decl.getId(), declaredType);
                }
                
                final ASTType exprType = decl.getExp().typecheck(newGamma, typeDefs);
                
                if (!Subtyping.isSubtype(exprType, declaredType, typeDefs))
                    throw new TypeError("Expression type " + exprType.toStr() + 
                                    " does not match declared type " + declaredType.toStr());
                
                if (!(decl.getExp() instanceof ASTFun))
                    newGamma.assoc(decl.getId(), declaredType);
            } else {
                final ASTType declType = decl.getExp().typecheck(newGamma, typeDefs);
                newGamma.assoc(decl.getId(), declType);
            }
        }
        
        return this.body.typecheck(newGamma, typeDefs);
    }
}