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
			while(file.hasNext()) {
				System.out.println(file.nextLine());
			}
			
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
		
		symbol = symbol.toUpperCase();
		
		String line;
		
		
		while (true) {
			line = file.nextLine();
			if(line == null) {
				break;
			}
			String[] tokens = line.split("=>");
		}
		
		return false;
		
	}

}
