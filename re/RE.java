package re;

import fa.nfa.NFA;

/**
 * Implementation of the REInterface. 
 * 
 * This class takes a regular expression and builds an NFA
 * from said regular expression, using the recursive descent parser
 * algorithm.
 * 
 * @author Benjamin Warner
 */

 public class RE implements REInterface {

	private String expr;

	/**
	 * Create a new RE from the regular expression expr.
	 */
	public RE(String expr) {
		this.expr = expr;
	}

	/**
	 * Build an NFA from the regular expression provided to the class.
	 */
	public NFA getNFA() {
		NFA nfa = new NFA();
		return nfa;
	}
 }