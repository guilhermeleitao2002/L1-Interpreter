public class L0int {
    public static void main(String args[]) {
		@SuppressWarnings("unused")
		Parser parser = new Parser(System.in);
		ASTNode exp;
		
		System.out.println("L0 interpreter PL MEIC 2024/25 (v0.0)\n");

		while (true) {
			try {
				exp = Parser.Start();
				if (exp==null) System.exit(0);
				IValue v = exp.eval(new Environment<IValue>());
				System.out.println(v.toStr());
			} catch (ParseException e) {
				System.out.println("Syntax Error:\n" + e);
				Parser.ReInit(System.in);
			} catch (InterpreterError e) {
				System.out.println("Runtime Error:\n" + e);
				Parser.ReInit(System.in);
			} catch (Exception e) {
				System.out.println("Error: " + e);
				Parser.ReInit(System.in);
			}
		}
    }
}
