public	class ASTTId implements ASTType	{
    String id;	
    
    public ASTTId(String id)	{
        this.id = id;
    }

    @Override
    public String toStr() {
        return this.id;
    }

}	
