import java.util.*;

public class Environment <E>{
    final Environment<E> anc;
    final Map<String, E> bindings;

    public Environment(){
        this.anc = null;
        this.bindings = new HashMap<>();
    }
    
    public Environment(Environment<E> ancestor){
        this.anc = ancestor;
        this.bindings = new HashMap<>();
    }

    public Environment<E> beginScope(){
        return new Environment<>(this);
    }
    
    public Environment<E> endScope(){
        return this.anc;
    }

    public void assoc(String id, E bind) throws InterpreterError {
        if (bindings.containsKey(id)) {
            throw new InterpreterError("Variable " + id + " already defined in this scope");
        }
        bindings.put(id, bind);
    }

    public E find(String id) throws InterpreterError {
        final E value = bindings.get(id);
        if (value != null) {
            return value;
        }
        if (this.anc != null) {
            return this.anc.find(id);
        }
        
        throw new InterpreterError("Variable " + id + " not found");
    }

    public Environment <E> copy() {
        Environment<E> newEnv = new Environment<>(this.anc);
        newEnv.bindings.putAll(this.bindings);
        return newEnv;
    }
}