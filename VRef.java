public class VRef implements IValue {
    private IValue value;
    
    public VRef(IValue initialValue) {
        this.value = initialValue;
    }
    
    public final IValue getValue() {
        return this.value;
    }
    
    public final void setValue(IValue newValue) {
        this.value = newValue;
    }
    
    @Override
    public final String toStr() {
        return "<ref: " + this.value.toStr() + ">";
    }
}