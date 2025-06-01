public class VString implements IValue {
    private final String value;
    
    public VString(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
    
    @Override
    public String toStr() {
        return this.value;
    }
}