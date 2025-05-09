public class VBool implements IValue {
    private final boolean value;

    public VBool(boolean v) {
        this.value = v;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public final String toStr() {
        return Boolean.toString(this.value);
    }
}