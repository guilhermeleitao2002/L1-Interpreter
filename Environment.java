import java.util.*;

public class Environment <E>{
    private final Environment<E> anc;
    private final Map<String, E> bindings;

    public Environment(){
        this.anc = null;
        this.bindings = new HashMap<>();
    }
    
    public Environment(Environment<E> ancestor){
        this.anc = ancestor;
        this.bindings = new HashMap<>();
    }

    public final Environment<E> beginScope(){
        return new Environment<>(this);
    }
    
    public final Environment<E> endScope(){
        return this.anc;
    }

    public final void assoc(String id, E bind) throws InterpreterError {
        if (this.bindings.containsKey(id))
            throw new InterpreterError("Variable " + id + " already defined in this scope");

        this.bindings.put(id, bind);
    }

    public final E find(String id) throws InterpreterError {
        final E value = this.bindings.get(id);
        if (value != null)
            return value;
        if (this.anc != null)
            return this.anc.find(id);
        
        throw new InterpreterError("Variable " + id + " not found");
    }

    public final Environment <E> copy() {
        final Environment<E> newEnv = new Environment<>(this.anc);

        newEnv.bindings.putAll(this.bindings);
        
        return newEnv;
    }
}