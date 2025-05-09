// VList.java
public class VList implements IValue {
    private final IValue head;
    private final IValue tail;
    private final boolean isNil;
    
    // Constructor for nil
    public VList() {
        this.head = null;
        this.tail = null;
        this.isNil = true;
    }
    
    // Constructor for cons
    public VList(IValue head, IValue tail) {
        this.head = head;
        this.tail = tail;
        this.isNil = false;
    }
    
    public boolean isNil() {
        return isNil;
    }
    
    public IValue getHead() {
        if (isNil) throw new RuntimeException("Cannot get head of nil list");
        return head;
    }
    
    public IValue getTail() {
        if (isNil) throw new RuntimeException("Cannot get tail of nil list");
        return tail;
    }
    
    @Override
    public String toStr() {
        if (isNil) return "nil";
        
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        
        IValue current = this;
        while (current instanceof VList && !((VList)current).isNil()) {
            VList currentList = (VList)current;
            sb.append(currentList.getHead().toStr());
            current = currentList.getTail();
            
            if (current instanceof VList && !((VList)current).isNil()) {
                sb.append(", ");
            }
        }
        
        sb.append("]");
        return sb.toString();
    }
}