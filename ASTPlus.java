public class ASTPlus implements ASTNode {
        private final ASTNode lhs;
        private final ASTNode rhs;

        public ASTPlus(ASTNode l, ASTNode r) {
                this.lhs = l;
                this.rhs = r;
        }

        @Override
        public IValue eval(Environment<IValue> e) throws InterpreterError {
                final IValue v1 = this.lhs.eval(e);
                final IValue v2 = this.rhs.eval(e);
                
                if (v1 instanceof VInt && v2 instanceof VInt) {
                        final int i1 = ((VInt) v1).getVal();
                        final int i2 = ((VInt) v2).getVal();

                        return new VInt(i1 + i2);
                } else
                        throw new InterpreterError("Illegal types to + operator");
        }
}
