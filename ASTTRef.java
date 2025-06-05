public class ASTTRef implements ASTType {
    private final ASTType type;

    public ASTTRef(ASTType type) {
        this.type = type;
    }
    
    public ASTType getType() {
        return this.type;
    }

    @Override
    public String toStr() {
        return "ref<" + this.type.toStr() + ">";
    }
}