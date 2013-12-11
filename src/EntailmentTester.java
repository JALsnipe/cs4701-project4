import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * EntailmentTester.java
 * @author jal2238
 *
 */
public class EntailmentTester {

	public static void main(String[] args) {


		if (args.length < 3) {
			System.out.println("usage: EntailmentTester <forward or backward> "
					+ "<knowledge base file> <symbol>");
			System.exit(1);
		}

		String entailmentType = args[0];
		String knowledgeBase = args[1];
		String symbol = args[2];


		try {
			Scanner file = new Scanner(new File(knowledgeBase));

			if (entailmentType.equals("forward")) {
				forwardChaining(file, symbol);
			}
			
			if (entailmentType.equals("backward")) {
				backwardChaining(file, symbol);
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error loading knowledge file!");
			System.exit(1);
		}

	}

	/**
	 * forwardChaining takes in a scanner and a symbol to search the kb for, and returns
	 * true if the symbol can be entailed, and false if it can not be entailed.
	 * @param file
	 * @param symbol
	 * @return boolean
	 */
	public static boolean forwardChaining(Scanner file, String symbol) {

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
		while (file.hasNextLine()) {
			line = file.nextLine();

			if(line == null) {
				
				break;
			}
			if(line.contains("=>")) {
				kb.add(line);
			}
			// start parsing			
			if(line.contains("=>")) {
				tokens = line.split("=>");
				for(int i = 0; i < tokens.length; i++) { // split tokens, remove whitespace
					tokens[i] = tokens[i].replaceAll("\\s+","");
					if(tokens[0].length() == 1) { // for a case like P => Q

						count.put(line, 1);
						inferred.put(tokens[0].replaceAll("\\s+",""), null);
						agenda.push(tokens[0].replaceAll("\\s+",""));
					}
					
				}

				for(int i = 0; i < tokens.length; i++) {
				}

				inferred.put(tokens[1], null); // adding the second token as null to inferred
				agenda.push(tokens[1].replaceAll("\\s+","")); // push second token to agenda
				if(tokens[0].contains("^")) {
					tokens2 = tokens[0].split("\\^"); // splitting by ^ (and) symbol
					
					for(int i = 0; i < tokens2.length; i++) {
						agenda.push(tokens2[i].replaceAll("\\s+",""));
					}
					int x = tokens2.length;
					count.put(line, x); // added to count with whitespaces removed
				}
			}
			if(!line.contains("=>")) {
				count.put(line, 0);
				inferred.put(line, null);
			}
		}

		// all data structures should be populated now


		// list of horn clauses is ArrayList kb
		// START ALGORITHM

		while (agenda.size() != 0) {
			String p = agenda.pop();
			System.out.println(p); // don't comment
			while (inferred.get(p) == null) {
				
				inferred.put(p, Boolean.TRUE);
				
				for (int i = 0; i < kb.size(); i++) {
					String hornClause = kb.get(i);
					if (hornClause.contains(p)) {
						if(count.get(hornClause) == null) {
							System.out.println("--> false");
							return false;
						}
						decrementCount(hornClause, count);
						if (countIsZero(hornClause, count)) { // count.get(hornClause) == 0
							System.out.println(hornClause); // don't comment
							String[] temp = hornClause.split("=>");
							if(temp.length == 2) {
								String parsed = temp[1].replaceAll("\\s+","");
								if (parsed.equals(symbol)) {
									System.out.println("--> true");
									return true;
								} else {
									agenda.push(temp[1].replaceAll("\\s+",""));
								}
							}
							if(temp.length == 1) {
								if (temp[0].equals(symbol)) {
									System.out.println("--> true");
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
		System.out.println("--> false");
		return false;
	} //END

	/**
	 * backwardChaining takes in a scanner and a symbol to search the kb for, and returns
	 * true if the symbol can be entailed, and false if it can not be entailed.
	 * @param file
	 * @param symbol
	 * @return boolean
	 */
	public static boolean backwardChaining(Scanner file, String symbol){

		symbol = symbol.toUpperCase();

		// read all lines into string array list for KB
		// iterate through list and parse lines
		// unknown symbols have no whitespaces/are on the left side of =>

		HashMap<String, Integer> count = new HashMap<String, Integer>(); // Horn Clause
		HashMap<String, Boolean> inferred = new HashMap<String, Boolean>(); // Symbol
		Stack<String> agenda = new Stack<String>(); // Symbol
		String[] tokens;
		String[] tokens2;

		ArrayList<String> kb = new ArrayList<String>();
		String line = new String();
		while (file.hasNextLine()) {
			line = file.nextLine();

			if(line == null) {
				break;
			}
			if(line.contains("=>")) {
				kb.add(line);
			}
			// start parsing			
			if(line.contains("=>")) {
				tokens = line.split("=>");
				for(int i = 0; i < tokens.length; i++) { // split tokens, remove whitespace
					tokens[i] = tokens[i].replaceAll("\\s+","");
					if(tokens[0].length() == 1) { // for a case like P => Q
						count.put(line, 1);
						inferred.put(tokens[0].replaceAll("\\s+",""), null);
						agenda.push(tokens[0].replaceAll("\\s+",""));
					}
				}


				inferred.put(tokens[1], null); // adding the second token as null to inferred
				agenda.push(tokens[1].replaceAll("\\s+","")); // push second token to agenda
				if(tokens[0].contains("^")) {
					tokens2 = tokens[0].split("\\^"); // splitting by ^ (and) symbol

					for(int i = 0; i < tokens2.length; i++) {
						agenda.push(tokens2[i].replaceAll("\\s+",""));
					}
					int x = tokens2.length;
					count.put(line, x); // added to count with whitespaces removed
				}
			}
			if(!line.contains("=>")) {
				count.put(line, 0);
				inferred.put(line, null);
			}
		}

		// all data structures should be populated now

		// reverse stack
		Stack<String> agenda2 = new Stack<String>();

		while (!agenda.isEmpty()) {
			String s = agenda.pop();
			agenda2.push(s);
		}

		// list of horn clauses is ArrayList kb
		// START ALGORITHM

		while (agenda2.size() != 0) {
			// pop from stack
			String p = agenda2.pop();
			System.out.println(p);

			while (inferred.get(p) == null) {
				ArrayList<String> b = new ArrayList<String>();
				for(int i = 0; i < kb.size(); i++) {
					System.out.println(kb.get(i));
					if (kb.get(i).contains(symbol)) {

						ArrayList<String> temp = getPremises(kb.get(i), agenda2);
						for(int j=0;j<temp.size();j++){
							b.add(temp.get(j));
						}
					}						
				}

				if (b.size() == 0) {
					System.out.println("--> false");
					return false;
				}
				else {
					for (int i = 0; i < b.size(); i++){
						if (!kb.contains(b.get(i)))
							agenda2.add(b.get(i));
					}
					
				}
				
			}

		}
		System.out.println("--> true");
		return true;
	}

	/**
	 * getPremises takes in a string and a stack, and returns an ArrayList of premises
	 * @param string
	 * @param agenda
	 * @return ArrayList<String>
	 */
	private static ArrayList<String> getPremises(String string, Stack<String> agenda) {
		// get the premise
		String premise = string.split("=>")[0];
		ArrayList<String> temp = new ArrayList<String>();
		String[] split = premise.split("^");
		// for each conjunct
		for(int i = 0; i < split.length; i++){
			if (!agenda.contains(split[i]))
				temp.add(split[i]);
		}
		return temp;
	}

	/**
	 * decrementCount takes in a horn clause as a string, and a count table (in this case a hashmap)
	 * and decrements the value of that horn clause in the table.
	 * @param hornClause
	 * @param count
	 */
	private static void decrementCount(String hornClause, HashMap<String, Integer> count) {
		int value = (count.get(hornClause)).intValue();
		count.put(hornClause, new Integer(value - 1));

	}

	/**
	 * countIsZero takes in a horn clause as a string and a count table (in this case a hashmap)
	 * and retuns if the count of that horn clause is 0 or not.
	 * @param hornClause
	 * @param count
	 * @return boolean
	 */
	private static boolean countIsZero(String hornClause, HashMap<String, Integer> count) {
		return (count.get(hornClause)).intValue() == 0;
	}

}
