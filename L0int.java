import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class L0int {

    public static void main(String args[]) {
        Parser parser;
        ASTNode exp;
        
        System.out.println("L0 interpreter PL MEIC 2024/25 (v0.0)\n");
        
        try {
            // Check if a filename was provided as a command-line argument
            if (args.length > 0) {
                String filename = args[0];
                try {
                    FileInputStream fileInput = new FileInputStream(filename);
                    parser = new Parser(fileInput);
                    System.out.println("Reading from file: " + filename);
                } catch (FileNotFoundException e) {
                    System.err.println("Error: File not found: " + filename);
                    System.exit(1);
                    return;
                }
            } else {
                // If no file provided, fall back to System.in
                System.out.println("No input file specified. Reading from standard input...");
                parser = new Parser(System.in);
            }
            
            while (true) {
                try {
                    System.out.print("# ");
                    exp = parser.Start();
                    if (exp == null) System.exit(0);
                    IValue v = exp.eval(new Environment<IValue>());
                    System.out.println(v.toStr());
                } catch (ParseException e) {
                    System.out.println("Syntax Error.");
                    parser.ReInit(System.in);
                } catch (Exception e) {
                    e.printStackTrace();
                    parser.ReInit(System.in);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}