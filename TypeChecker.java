public class TypeChecker {
    public static ASTType typecheck(ASTNode program) throws TypeError {
        final TypeEnvironment gamma = new TypeEnvironment();
        final TypeDefEnvironment typeDefs = new TypeDefEnvironment();
        
        return program.typecheck(gamma, typeDefs);
    }
    
    public static ASTType typecheck(ASTNode program, TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        return program.typecheck(gamma, typeDefs);
    }
}