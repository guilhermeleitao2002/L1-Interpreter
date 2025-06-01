public class ASTTArrow implements ASTType {
    ASTType dom;
    ASTType codom;

    public ASTTArrow(ASTType d, ASTType co) {
        this.dom = d;
        this.codom = co;
    }

    @Override
    public String toStr() {
        return this.dom.toStr()+"->"+codom.toStr();
    }   
}

