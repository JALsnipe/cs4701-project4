import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class EntailmentTester {

	public static void main(String[] args) {

//		System.out.println(args.length);

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
				System.out.println(backwardChaining(file, symbol));
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
			if(line.contains("=>")) {
				kb.add(line);
			}
			// start parsing			
			if(line.contains("=>")) {
				tokens = line.split("=>");
				for(int i = 0; i < tokens.length; i++) { // split tokens, remove whitespace
					System.out.println("removing whitespace");
					tokens[i] = tokens[i].replaceAll("\\s+","");
					if(tokens[0].length() == 1) { // for a case like P => Q
						System.out.println("in token[1] == 1");
						System.out.println(line);
						count.put(line, 1);
						inferred.put(tokens[0].replaceAll("\\s+",""), null);
						agenda.push(tokens[0].replaceAll("\\s+",""));
					}
					System.out.println("printing tokens:");
					System.out.println(tokens[i]);
				}
				System.out.println("end print tokens");
				System.out.println(tokens.length);
				for(int i = 0; i < tokens.length; i++) {
					System.out.println("printing token " + i + ": " + tokens[i]);
				}
				
				inferred.put(tokens[1], null); // adding the second token as null to inferred
				agenda.push(tokens[1].replaceAll("\\s+","")); // push second token to agenda
				System.out.println("done parsing second token");
				if(tokens[0].contains("^")) {
					tokens2 = tokens[0].split("\\^"); // splitting by ^ (and) symbol
					System.out.println("print tokens2");
					for(int i = 0; i < tokens2.length; i++) {
						System.out.println(tokens2[i]);
					}
					System.out.println("end print tokens2");
					for(int i = 0; i < tokens2.length; i++) {
						agenda.push(tokens2[i].replaceAll("\\s+",""));
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
		// Use this to test if the data structures are being populated correctly
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
		
//		return true;
		
		System.out.println("check kb");
		for(int i = 0; i < kb.size(); i++) {
			System.out.println(kb.get(i));
		}
		
		// list of horn clauses is ArrayList kb
		// START ALGORITHM
		System.out.println("start of algorithm");
//		System.out.println("agenda.size(): " + agenda.size());
		while (agenda.size() != 0) {
			//				Symbol p = agenda.pop();
			String p = agenda.pop();
//			if(inferred.get(p) == Boolean.TRUE) {
			System.out.println(p); // don't comment
//			}
//			while (!inferredBool(p, inferred)) {
			while (inferred.get(p) == null) {
//			while (inferred(p, null)) {
//				System.out.println("reached?");
				inferred.put(p, Boolean.TRUE);
//				if(inferred.get(p) == Boolean.TRUE) {
//					System.out.println(p);
//				}
//				System.out.println(p + " should now be true: " + inferred.get(p));
				
//				System.out.println("kb.size(): " + kb.size());
				for (int i = 0; i < kb.size(); i++) {
					String hornClause = kb.get(i);
//					System.out.println("hornClause: " + hornClause);
					if (hornClause.contains(p)) {
//						System.out.println(hornClause);
						if(count.get(hornClause) == null) {
							return false;
						}
						decrementCount(hornClause, count);
//						System.out.println("decrement count");
//						System.out.println("count.get(hornClause): " + count.get(hornClause));
						if (countisZero(hornClause, count)) { // count.get(hornClause) == 0
//							System.out.println("count is 0");
							System.out.println(hornClause); // don't comment
							String[] temp = hornClause.split("=>");
							if(temp.length == 2) {
//								System.out.println("temp length is 2");
//								System.out.println(temp[1]);
								String parsed = temp[1].replaceAll("\\s+","");
//								System.out.println("parsed: " + parsed);
//								System.out.println("symbol: " + symbol);
								if (parsed.equals(symbol)) {
//									System.out.println("last");
//									System.out.println(hornClause);
									return true;
//									System.out.println("true");
								} else {
									agenda.push(temp[1].replaceAll("\\s+",""));
								}
							}
							if(temp.length == 1) {
								if (temp[0].equals(symbol)) {
									return true;
								} else {
									agenda.push(temp[0].replaceAll("\\s+",""));
								}
							}
						}
					}
				}
			}
		}
		return false;
//		System.out.println("false");
	} //END
		
	
	public static boolean backwardChaining(Scanner file, String symbol){
		
		System.out.println("in backward chaining");
		System.out.println("setup");

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
			if(line.contains("=>")) {
				kb.add(line);
			}
			// start parsing			
			if(line.contains("=>")) {
				tokens = line.split("=>");
				for(int i = 0; i < tokens.length; i++) { // split tokens, remove whitespace
					System.out.println("removing whitespace");
					tokens[i] = tokens[i].replaceAll("\\s+","");
					if(tokens[0].length() == 1) { // for a case like P => Q
						System.out.println("in token[1] == 1");
						System.out.println(line);
						count.put(line, 1);
						inferred.put(tokens[0].replaceAll("\\s+",""), null);
						agenda.push(tokens[0].replaceAll("\\s+",""));
					}
					System.out.println("printing tokens:");
					System.out.println(tokens[i]);
				}
				System.out.println("end print tokens");
				System.out.println(tokens.length);
				for(int i = 0; i < tokens.length; i++) {
					System.out.println("printing token " + i + ": " + tokens[i]);
				}
				
				inferred.put(tokens[1], null); // adding the second token as null to inferred
				agenda.push(tokens[1].replaceAll("\\s+","")); // push second token to agenda
				System.out.println("done parsing second token");
				if(tokens[0].contains("^")) {
					tokens2 = tokens[0].split("\\^"); // splitting by ^ (and) symbol
					System.out.println("print tokens2");
					for(int i = 0; i < tokens2.length; i++) {
						System.out.println(tokens2[i]);
					}
					System.out.println("end print tokens2");
					for(int i = 0; i < tokens2.length; i++) {
						agenda.push(tokens2[i].replaceAll("\\s+",""));
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
		// Use this to test if the data structures are being populated correctly
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
		
//		return true;
		
		// reverse stack
		System.out.println("here???");
		Stack<String> agenda2 = new Stack<String>();

		while (!agenda.isEmpty()) {
            String s = agenda.pop();
            agenda2.push(s);
        }
//        while (!agenda2.isEmpty()) {
//            String s = agenda2.pop();
//            agenda.push(s);
//        }
		
		System.out.println("check kb");
		for(int i = 0; i < kb.size(); i++) {
			System.out.println(kb.get(i));
		}
		
		// list of horn clauses is ArrayList kb
		// START ALGORITHM
		System.out.println("start of algorithm");
		// while the list of symbols are not empty
		
		while (agenda2.size() != 0) {
			// get current symbol
			String p = agenda2.pop();
			System.out.println(p);
			// add the entailed array
//			entailed.add(q);
			
			// if this element is a fact then we dont need to go further
			while (inferred.get(p) == null) {
				// .. but it isnt so..
				// create array to hold new symbols to be processed 
				ArrayList<String> b = new ArrayList<String>();
				for(int i = 0; i < kb.size(); i++) {
				// for each clause..
					System.out.println(kb.get(i));
						if (kb.get(i).contains(symbol)) {
						// that contains the symbol as its conclusion
						
								ArrayList<String> temp = getPremises(kb.get(i), agenda2);
								for(int j=0;j<temp.size();j++){
									// add the symbols to a temp array
									b.add(temp.get(j));
								}
							}						
				}
				// no symbols were generated and since it isnt a fact either 
				// then this sybmol and eventually ASK  cannot be implied by TELL
				if (b.size() == 0) {
//					System.out.println("b.size() == 0");
					return false;
				}
				else{
						// there are symbols so check for previously processed ones and add to agenda
						for(int i=0;i<b.size();i++){
								if (!kb.contains(b.get(i)))
										agenda2.add(b.get(i));
								}
		

				}
			}
			
		}//while end
		return true;
	}

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
	
	private static ArrayList<String> getPremises(String string, Stack<String> agenda) {
		// get the premise
		String premise = string.split("=>")[0];
		ArrayList<String> temp = new ArrayList<String>();
		String[] conjuncts = premise.split("&");
		// for each conjunct
		for(int i=0;i<conjuncts.length;i++){
			if (!agenda.contains(conjuncts[i]))
				temp.add(conjuncts[i]);
		}
		return temp;
	}

	private static void decrementCount(String hornClause, HashMap<String, Integer> count) {
		int value = (count.get(hornClause)).intValue();
		count.put(hornClause, new Integer(value - 1));

	}
	
	private static boolean countisZero(String hornClause, HashMap<String, Integer> count) {

		return (count.get(hornClause)).intValue() == 0;
	}
	
}
