public class VCell implements IValue {
    private IValue value;
    
    public VCell(IValue initialValue) {
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
        return "ref@" + this;
    }
}