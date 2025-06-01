import java.util.*;

public class ASTTStruct implements ASTType {
    private final Map<String, ASTType> fields;

    public ASTTStruct(Map<String, ASTType> fields) {
        this.fields = fields;
    }
    
    public Map<String, ASTType> getFields() {
        return this.fields;
    }
    
    @Override
    public String toStr() {
        StringBuilder sb = new StringBuilder("struct { ");
        boolean first = true;
        for (Map.Entry<String, ASTType> entry : fields.entrySet()) {
            if (!first) sb.append(", ");
            sb.append(entry.getKey()).append(": ").append(entry.getValue().toStr());
            first = false;
        }
        sb.append(" }");
        return sb.toString();
    }
}