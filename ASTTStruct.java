public class ASTTStruct implements ASTType {
    private final TypeBindList ll;

    public ASTTStruct(TypeBindList llp) {
        this.ll = llp;
    }
    
    @Override
    public String toStr() {
        return "union { ... }";
    }

}