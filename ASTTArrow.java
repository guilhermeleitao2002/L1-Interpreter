public class ASTTArrow implements ASTType {
    final ASTType dom;
    final ASTType codom;

    public ASTTArrow(ASTType d, ASTType co) {
        this.dom = d;
        this.codom = co;
    }
    
    public ASTType getDomain() {
        return this.dom;
    }
    
    public ASTType getCodomain() {
        return this.codom;
    }

    @Override
    public String toStr() {
        return this.dom.toStr() + " -> " + codom.toStr();
    }   
}