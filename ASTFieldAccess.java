public class ASTFieldAccess implements ASTNode {
    private final ASTNode struct;
    private final String fieldName;
    
    public ASTFieldAccess(ASTNode struct, String fieldName) {
        this.struct = struct;
        this.fieldName = fieldName;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue structValue = this.struct.eval(e);
        
        if (!(structValue instanceof VStruct)) {
            throw new InterpreterError("Field access requires a struct");
        }
        
        final VStruct vStruct = (VStruct) structValue;
        final IValue fieldValue = vStruct.getField(this.fieldName);
        
        if (fieldValue == null) {
            throw new InterpreterError("Field " + this.fieldName + " not found");
        }
        
        return fieldValue;
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        ASTType structType = this.struct.typecheck(gamma, typeDefs);
        
        // Resolve type if it's a type identifier
        if (structType instanceof ASTTId aSTTId) {
            structType = typeDefs.find(aSTTId.id);
        }
        
        if (!(structType instanceof ASTTStruct) && structType != null) {
            throw new TypeError("Field access requires a struct type, got " + structType.toStr());
        }
        
        final ASTTStruct structTypeAST = (ASTTStruct) structType;
        final ASTType fieldType = structTypeAST.getFields().get(this.fieldName);
        
        if (fieldType == null) {
            throw new TypeError("Field " + this.fieldName + " not found in struct");
        }
        
        return fieldType;
    }
}