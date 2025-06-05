import java.util.*;

public class ASTTUnion implements ASTType {
    private final Map<String, ASTType> variants;

    public ASTTUnion(Map<String, ASTType> variants) {
        this.variants = variants;
    }
    
    public Map<String, ASTType> getVariants() {
        return this.variants;
    }
    
    @Override
    public String toStr() {
        final StringBuilder sb = new StringBuilder("union { ");
        boolean first = true;
        
        for (Map.Entry<String, ASTType> entry : variants.entrySet()) {
            if (!first)
                sb.append(", ");
            sb.append(entry.getKey()).append(": ").append(entry.getValue().toStr());
            
            first = false;
        }

        sb.append(" }");
        return sb.toString();
    }
}