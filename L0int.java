public class L0int {
    public static void main(String args[]) {
		Parser parser = new Parser(System.in);
		ASTNode exp;
		
		System.out.println("L0 interpreter PL MEIC 2024/25 (v0.0)\n");

		while (true) {
			try {
				exp = parser.Start();
				if (exp==null) System.exit(0);
				IValue v = exp.eval(new Environment<IValue>());
				System.out.println(v.toStr());
			} catch (ParseException e) {
				System.out.println("Syntax Error:\n" + e);
				parser.ReInit(System.in);
			} catch (Exception e) {
				e.printStackTrace();
				parser.ReInit(System.in);
			}
		}
    }

	private static boolean endsWithPrint(ASTNode node) {
        if (node instanceof ASTPrint) {
            return true;
        }
        if (node instanceof ASTSeq) {
            // For ASTSeq, check if the second expression ends with print
            ASTSeq seq = (ASTSeq) node;
            return endsWithPrint(seq.getSecond());
        }
        if (node instanceof ASTLet) {
            // For ASTLet, check if the body ends with print
            ASTLet let = (ASTLet) node;
            return endsWithPrint(let.getBody());
        }
        return false;
    }
}
