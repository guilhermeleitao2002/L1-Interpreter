public class VConstructor implements IValue {
    private final String constructorName;
    private final String typeName;
    
    public VConstructor(String constructorName, String typeName) {
        this.constructorName = constructorName;
        this.typeName = typeName;
    }
    
    public String getConstructorName() {
        return this.constructorName;
    }
    
    public String getTypeName() {
        return this.typeName;
    }
    
    @Override
    public String toStr() {
        return "<constructor " + constructorName + ">";
    }
}