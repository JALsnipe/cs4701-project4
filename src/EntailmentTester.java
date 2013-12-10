import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

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
				System.out.println(forwardChaining(file, symbol));
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

		// read all lines into string array list for KB
		// iterate through list and parse lines
		// unknown symbols have no whitespaces/are on the left side of =>

		//ArrayList<String> lines = new ArrayList<String>();

		HashMap<String, Integer> count = new HashMap<String, Integer>(); // Horn Clause
		HashMap<String, Boolean> inferred = new HashMap<String, Boolean>(); // Symbol
		Stack<String> agenda = new Stack<String>(); // Symbol
		String[] tokens;
		String[] tokens2;

		ArrayList<String> kb = new ArrayList<String>();
		String line = new String();
		System.out.println(file.hasNextLine());
		while (file.hasNextLine()) {
			line = file.nextLine();

			if(line == null) {
				System.out.println("in break");
				break;
			}
			kb.add(line);
			// start parsing			
			if(line.contains("=>")) {
				tokens = line.split("=>");
				for(int i = 0; i < tokens.length; i++) { // split tokens, remove whitespace
					System.out.println("removing whitespace");
					tokens[i] = tokens[i].replaceAll("\\s+","");
					System.out.println("printing tokens:");
					System.out.println(tokens[i]);
				}
				System.out.println("end print tokens");
				System.out.println(tokens.length);
				for(int i = 0; i < tokens.length; i++) {
					System.out.println("printing token " + i + ": " + tokens[i]);
				}
//				String temp = tokens[1];
				inferred.put(tokens[1], null); // adding the second token as null to inferred
				agenda.push(tokens[1]); // push second token to agenda
				System.out.println("done parsing second token");
				if(tokens[0].contains("^")) {
					tokens2 = tokens[0].split("\\^"); // splitting by ^ (and) symbol
					System.out.println("print tokens2");
					for(int i = 0; i < tokens2.length; i++) {
						System.out.println(tokens2[i]);
					}
					System.out.println("end print tokens2");
					for(int i = 0; i < tokens2.length; i++) {
						agenda.push(tokens2[i]);
					}
					int x = tokens2.length;
					count.put(line, x); // should I remove all whitespace here?
					
				}
			}
			if(!line.contains("=>")) {
				count.put(line, 0);
				inferred.put(line, null);
			}
		}
		
		// all data structures should be populated now
//		System.out.println("printing count");
//		System.out.println(count.toString());
//		
//		System.out.println("printing inferred");
//		System.out.println(inferred.toString());
//		
//		System.out.println("printing agenda");
//		System.out.println(agenda.size());		
//		while (!agenda.isEmpty()){
//		    System.out.println(agenda.peek());
//		    agenda.pop();
//		}
		
		// list of horn clauses is ArrayList kb
		// START ALGORITHM
		System.out.println("start of algorithm");
		while (agenda.size() != 0) {
			//				Symbol p = agenda.pop();
			String p = agenda.pop();
			System.out.println(p);
			while (!inferred(p, inferred)) {
				inferred.put(p, Boolean.TRUE);

				for (int i = 0; i < kb.size(); i++) {
					String hornClause = kb.get(i);
					System.out.println(hornClause);
					if (hornClause.contains(p)) {
						decrementCount(hornClause, count);
						if (countisZero(hornClause, count)) {
							String[] temp = line.split("=>");
							if (temp[1].equals(symbol)) {
								return true;
//								System.out.println("true");
							} else {
								agenda.push(temp[1]);
							}
						}
					}
				}
			}
		}
		return false;
//		System.out.println("false");
		

//		//		public boolean plfcEntails(KnowledgeBase kb, Symbol q) {
//		boolean plfcEntails(ArrayList<String> kb, String q) {
//			//			List<HornClause> hornClauses = asHornClauses(kb.getSentences());
//			List<String> hornClauses = kb.get(i); // loop?
//			while (agenda.size() != 0) {
//				//				Symbol p = agenda.pop();
//				String p = agenda.pop();
//				while (!inferred(p)) {
//					inferred.put(p, Boolean.TRUE);
//
//					for (int i = 0; i < hornClauses.size(); i++) {
//						HornClause hornClause = hornClauses.get(i);
//						if (hornClause.premisesContainsSymbol(p)) {
//							decrementCount(hornClause, count);
//							if (countisZero(hornClause)) {
//								if (hornClause.head().equals(q)) {
//									return true;
//								} else {
//									agenda.push(hornClause.head());
//								}
//							}
//						}
//					}
//				}
//			}
//			return false;
//		}

		//		System.out.println("creating tokens");
		//		String[] tokens = line.split("=>");
		//		for(int i = 0; i < tokens.length; i++) {
		//			System.out.println(tokens[i]);
		//		}
		//
		//		System.out.println("returning");
		//
//		return false;
		//
	}

	private static boolean inferred(String p, HashMap<String, Boolean> inferred) {
		Object value = inferred.get(p);
		return ((value == null) || value.equals(Boolean.TRUE));
	}
	
	private static void decrementCount(String hornClause, HashMap<String, Integer> count) {
		int value = (count.get(hornClause)).intValue();
		count.put(hornClause, new Integer(value - 1));

	}
	
	private static boolean countisZero(String hornClause, HashMap<String, Integer> count) {

		return (count.get(hornClause)).intValue() == 0;
	}
	
}
