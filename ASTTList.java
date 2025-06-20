public class ASTTList implements ASTType {
    private final ASTType elt;

    public ASTTList(ASTType eltt) {
        this.elt = eltt;
    }

    public ASTTList() {
        this.elt = new ASTTInt(); // Default to int type for lists - why not...
    }
    
    public ASTType getElementType() {
        return this.elt;
    }
    
    @Override
    public String toStr() {
        return "list<" + this.elt.toStr() + ">";
    }
}