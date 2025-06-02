public	class ASTTId implements ASTType	{
    public String id;	
    
    public ASTTId(String id)	{
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String toStr() {
        return this.id;
    }

}