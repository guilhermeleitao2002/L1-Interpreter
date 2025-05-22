public class Bind {
    private final String id;
    private final ASTNode exp;

    public Bind( String _id, ASTNode _exp) {
        this.id = _id;
        this.exp = _exp;
    }

    public final String getId() {
        return this.id;
    }

    public final ASTNode getExp() {
        return this.exp;
    }
}
