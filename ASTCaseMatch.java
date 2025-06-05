import java.util.*;

public class ASTCaseMatch implements ASTNode {
    private final ASTNode expr;
    private final Map<String, ASTCaseBranch> cases;
    
    public ASTCaseMatch(ASTNode expr, Map<String, ASTCaseBranch> cases) {
        this.expr = expr;
        this.cases = cases;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        final IValue value = this.expr.eval(e);
        
        if (!(value instanceof VVariant))
            throw new InterpreterError("Case match requires a variant value");
        
        final VVariant variant = (VVariant) value;
        final ASTCaseBranch branch = this.cases.get(variant.getLabel());
        
        if (branch == null)
            throw new InterpreterError("No case for variant " + variant.getLabel());
        
        final Environment<IValue> newEnv = e.beginScope();
        newEnv.assoc(branch.getVariable(), variant.getValue());
        
        return branch.getBody().eval(newEnv);
    }
    
    @Override
    public ASTType typecheck(TypeEnvironment gamma, TypeDefEnvironment typeDefs) throws TypeError {
        ASTType exprType = this.expr.typecheck(gamma, typeDefs);
        
        // Resolve type if its a type id
        if (exprType instanceof ASTTId aSTTId)
            exprType = typeDefs.find(aSTTId.id);
        
        if (!(exprType instanceof ASTTUnion) && exprType != null)
            throw new TypeError("Case match requires a union type, got " + exprType.toStr());
        
        final ASTTUnion unionType = (ASTTUnion) exprType;
        final Map<String, ASTType> variants = unionType.getVariants();
        
        ASTType resultType = null;
        
        for (Map.Entry<String, ASTCaseBranch> caseEntry : this.cases.entrySet()) {
            final String caseName = caseEntry.getKey();
            final ASTCaseBranch branch = caseEntry.getValue();
            
            if (!variants.containsKey(caseName))
                throw new TypeError("Case " + caseName + " not found in union type");
            
            final ASTType variantType = variants.get(caseName);
            final TypeEnvironment newGamma = gamma.beginScope();
            newGamma.assoc(branch.getVariable(), variantType);
            
            final ASTType branchType = branch.getBody().typecheck(newGamma, typeDefs);
            
            if (resultType == null)
                resultType = branchType;
            else if (!Subtyping.isSubtype(branchType, resultType, typeDefs) && 
                      !Subtyping.isSubtype(resultType, branchType, typeDefs))
                throw new TypeError("All case branches must have compatible types");
        }
        
        // Check if all variants are covered
        for (String variantName : variants.keySet())
            if (!this.cases.containsKey(variantName))
                throw new TypeError("Missing case for variant " + variantName);
        
        return resultType;
    }
}