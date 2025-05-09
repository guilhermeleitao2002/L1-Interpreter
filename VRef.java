public class VRef implements IValue {
    private IValue value;
    
    public VRef(IValue initialValue) {
        this.value = initialValue;
    }
    
    public IValue getValue() {
        return value;
    }
    
    public void setValue(IValue newValue) {
        this.value = newValue;
    }
    
    @Override
    public String toStr() {
        return "<ref: " + value.toStr() + ">";
    }
}