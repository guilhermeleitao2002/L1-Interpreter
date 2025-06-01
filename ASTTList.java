public class ASTTList implements ASTType {
    private final ASTType elt;

    public ASTTList(ASTType eltt) {
        this.elt = eltt;
    }
    
    @Override
    public String toStr() {
        return "list<" + this.elt.toStr() + ">";
    }
    
}
