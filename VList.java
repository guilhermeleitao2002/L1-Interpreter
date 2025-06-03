public class VList implements IValue {
    private final IValue head;
    private final IValue tail;
    private final boolean isNil;
    
    public VList() {
        this.head = null;
        this.tail = null;
        this.isNil = true;
    }
    
    public VList(IValue head, IValue tail) {
        this.head = head;
        this.tail = tail;
        this.isNil = false;
    }
    
    public final boolean isNil() {
        return this.isNil;
    }
    
    public final IValue getHead() {
        if (this.isNil)
            throw new RuntimeException("Cannot get head of nil list");

        return this.head;
    }
    
    public final IValue getTail() {
        if (this.isNil)
            throw new RuntimeException("Cannot get tail of nil list");

        return this.tail;
    }
    
    @Override
    public final String toStr() {
        if (this.isNil)
            return "nil";
        
        final StringBuilder sb = new StringBuilder();
        sb.append("[");
        
        IValue current = this;
        while (current instanceof VList && !((VList)current).isNil()) {
            final VList currentList = (VList)current;

            sb.append(currentList.getHead().toStr());
            
            current = currentList.getTail();
            
            if (current instanceof VList && !((VList)current).isNil())
                sb.append(", ");
        }
        
        sb.append("]");
        
        return sb.toString();
    }
}