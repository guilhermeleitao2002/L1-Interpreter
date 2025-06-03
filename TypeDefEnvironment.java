import java.util.*;

public class TypeDefEnvironment {
    private final TypeDefEnvironment anc;
    private final Map<String, ASTType> typeDefs;

    public TypeDefEnvironment() {
        this.anc = null;
        this.typeDefs = new HashMap<>();
    }
    
    public TypeDefEnvironment(TypeDefEnvironment ancestor) {
        this.anc = ancestor;
        this.typeDefs = new HashMap<>();
    }

    public final TypeDefEnvironment beginScope() {
        return new TypeDefEnvironment(this);
    }

    public final void assoc(String id, ASTType type) throws TypeError {
        if (this.typeDefs.containsKey(id))
            throw new TypeError("Type " + id + " already defined in this scope");

        this.typeDefs.put(id, type);
    }

    public final ASTType find(String id) throws TypeError {
        final ASTType type = this.typeDefs.get(id);
        
        if (type != null)
            return type;
        if (this.anc != null)
            return this.anc.find(id);
        
        throw new TypeError("Type " + id + " not found");
    }
}