import java.util.List;

public class ASTLet implements ASTNode {
    List<Bind> decls;
    ASTNode body;

    public IValue eval(Environment<IValue> e) throws InterpreterError {
	Environment<IValue> en = e.beginScope();
	/*missing code */
    }

    public ASTLet(List<Bind> decls, ASTNode b) {
	this.decls = decls;
	body = b;
    }

}
