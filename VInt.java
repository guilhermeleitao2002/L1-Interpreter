public class VInt implements IValue {
    private final int v;

    public VInt(int v) {
        this.v = v;
    }

    public int getVal() {
        return this.v;
    }

    @Override
    public final String toStr() {
        return Integer.toString(this.v);
    }
}