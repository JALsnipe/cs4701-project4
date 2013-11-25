import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class EntailmentTester {

	public static void main(String[] args) {
		
		System.out.println(args.length);
		
		if (args.length < 3) {
			System.out.println("usage: EntailmentTester <forward or backward> "
					+ "<knowledge base file> <symbol>");
			System.exit(1);
		}
		
		String entailmentType = args[0];
		String knowledgeBase = args[1];
		String symbol = args[2];

		System.out.println("Welcome to Assignment 4: Inference Algorithms");
		System.out.println("Loading knowledge base file...");
		
		try {
			Scanner file = new Scanner(new File(knowledgeBase));
//			while(file.hasNext()) {
//				System.out.println(file.nextLine());
//			}
			
			if (entailmentType.equals("forward")) {
				System.out.println("TODO: Forward Chaining");
				forwardChaining(file, symbol);
			}
			
			if (entailmentType.equals("backward")) {
				System.out.println("TODO: Backward Chaining");
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Error loading knowledge file!");
			System.exit(1);
		}

	}
	
	public static boolean forwardChaining(Scanner file, String symbol) {
		System.out.println("in forward chaining");
		
		symbol = symbol.toUpperCase();
		
		String line;
		System.out.println(file.hasNextLine());
		while (file.hasNextLine()) {
			line = file.nextLine();
			if(line == null) {
				System.out.println("in break");
				break;
			}
			System.out.println("creating tokens");
			String[] tokens = line.split("=>");
			for(int i = 0; i < tokens.length; i++) {
				System.out.println(tokens[i]);
			}
		}
		
		System.out.println("returning");
		
		return false;
		
	}

}
