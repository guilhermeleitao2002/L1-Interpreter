import java.util.List;

public class ASTTFunction implements ASTType {
    private final List<ASTType> paramTypes;
    private final ASTType returnType;
    
    public ASTTFunction(List<ASTType> paramTypes, ASTType returnType) {
        this.paramTypes = paramTypes;
        this.returnType = returnType;
    }
    
    public List<ASTType> getParamTypes() {
        return this.paramTypes;
    }
    
    public ASTType getReturnType() {
        return this.returnType;
    }
    
    public int getParamCount() {
        return this.paramTypes.size();
    }
    
    @Override
    public String toStr() {
        if (paramTypes.isEmpty()) {
            return returnType.toStr();
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paramTypes.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(paramTypes.get(i).toStr());
        }
        sb.append(" -> ").append(returnType.toStr());
        return sb.toString();
    }
    
    // Convert to traditional curried arrow type for compatibility
    public ASTType toCurriedType() {
        ASTType result = returnType;
        for (int i = paramTypes.size() - 1; i >= 0; i--) {
            result = new ASTTArrow(paramTypes.get(i), result);
        }
        return result;
    }
}