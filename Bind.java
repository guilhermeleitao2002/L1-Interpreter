public class Bind {
    private final String id;
    private final ASTNode exp;
    private final ASTType type;

    public Bind(String _id, ASTNode _exp) {
        this.id = _id;
        this.exp = _exp;
        this.type = null;
    }
    
    public Bind(String _id, ASTType _type, ASTNode _exp) {
        this.id = _id;
        this.exp = _exp;
        this.type = _type;
    }

    public final String getId() {
        return this.id;
    }

    public final ASTNode getExp() {
        return this.exp;
    }
    
    public final ASTType getType() {
        return this.type;
    }
    
    public final boolean hasType() {
        return this.type != null;
    }
}