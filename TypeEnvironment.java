import java.util.*;

public class TypeEnvironment {
    private final TypeEnvironment anc;
    private final Map<String, ASTType> bindings;

    public TypeEnvironment() {
        this.anc = null;
        this.bindings = new HashMap<>();
    }
    
    public TypeEnvironment(TypeEnvironment ancestor) {
        this.anc = ancestor;
        this.bindings = new HashMap<>();
    }

    public final TypeEnvironment beginScope() {
        return new TypeEnvironment(this);
    }
    
    public final TypeEnvironment endScope() {
        return this.anc;
    }

    public final void assoc(String id, ASTType type) throws TypeError {
        if (this.bindings.containsKey(id))
            throw new TypeError("Variable " + id + " already defined in this scope");
        this.bindings.put(id, type);
    }

    public final ASTType find(String id) throws TypeError {
        final ASTType type = this.bindings.get(id);
        if (type != null)
            return type;
        if (this.anc != null)
            return this.anc.find(id);
        
        throw new TypeError("Variable " + id + " not found");
    }

    public final TypeEnvironment copy() {
        final TypeEnvironment newEnv = new TypeEnvironment(this.anc);
        newEnv.bindings.putAll(this.bindings);
        return newEnv;
    }
}