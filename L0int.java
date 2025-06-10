public class L0int {
    public static void main(String args[]) {
		@SuppressWarnings("unused")
		Parser parser = new Parser(System.in);
		ASTNode exp;
		
		@SuppressWarnings("unused")
		boolean showTypes = args.length > 0 && args[0].equals("--show-types");
		
		System.out.println("L0 interpreter PL MEIC 2024/25 (v0.0)\n");

		while (true) {
			try {
				exp = Parser.Start();
				if (exp==null) System.exit(0);
				
				@SuppressWarnings("unused")
				ASTType type = TypeChecker.typecheck(exp);
				
				@SuppressWarnings("Convert2Diamond")
				IValue v = exp.eval(new Environment<IValue>());
				System.out.println(v.toStr());
			} catch (InterpreterError | ParseException | TypeError e) {
				System.out.println(e);
				Parser.ReInit(System.in);
			}
		}
    }
}