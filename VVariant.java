public class VVariant implements IValue {
    private final String label;
    private final IValue value;
    
    public VVariant(String label, IValue value) {
        this.label = label;
        this.value = value;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public IValue getValue() {
        return this.value;
    }
    
    @Override
    public String toStr() {
        return this.label + "(" + this.value.toStr() + ")";
    }
}