public class VBool implements IValue {
    private boolean value;

    public VBool(boolean v) {
        value = v;
    }

    public boolean getValue() {
        return value;
    }

    public String toStr() {
        return Boolean.toString(value);
    }
}