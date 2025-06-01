import java.util.*;

public class VStruct implements IValue {
    private final Map<String, IValue> fields;
    
    public VStruct(Map<String, IValue> fields) {
        this.fields = fields;
    }
    
    public IValue getField(String fieldName) {
        return this.fields.get(fieldName);
    }
    
    public Map<String, IValue> getFields() {
        return this.fields;
    }
    
    @Override
    public String toStr() {
        StringBuilder sb = new StringBuilder("{ ");
        boolean first = true;
        for (Map.Entry<String, IValue> entry : this.fields.entrySet()) {
            if (!first) sb.append(", ");
            sb.append(entry.getKey()).append(" = ").append(entry.getValue().toStr());
            first = false;
        }
        sb.append(" }");
        return sb.toString();
    }
}