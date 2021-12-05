package re;

import java.util.*;

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

	private String input;
	private Character firstChoice;
	private Character secondChoice;
	private NFA nfa;
	private Set<Character> alphabet;
	private int stateCounter;
	private String lastStateName;

	/**
	 * Create a new RE from the regular expression expr.
	 */
	public RE(String input) {
		this.input = input;
		this.nfa = new NFA();
		this.alphabet = new LinkedHashSet<Character>();
		this.stateCounter = 1;
	}

	/**
	 * Build an NFA from the regular expression provided to the class.
	 */
	public NFA getNFA() {
		this.nfa.addStartState("q0");
		this.lastStateName = "q0";

		while (this.more()) {
			switch (peek()) {
				case '(':
					this.eat('(');
					this.processTerm();
					break;
				default:
					this.processCharacter(this.next());
					break;
			}
		}
		this.nfa.addFinalState(this.lastStateName);

		return this.nfa;
	}

	private void processTerm() {
		while (this.more()) {
			switch (peek()) {
				case '(':
					this.eat('(');
					this.processTerm();
					break;
				case ')':
					this.eat(')');
					this.addStateToNFA();
					return;
				case '|':
					this.eat('|');
					this.processChoice(this.next());
					break;
				default:
					this.processCharacter(this.next());
					break;
			}
		}
	}

	private void addStateToNFA() {
		if (this.peek() == '*') {
			this.eat('*');
			this.nfa.addTransition(this.lastStateName, this.firstChoice, this.lastStateName);
			this.firstChoice = null;
			if (this.secondChoice != null) {
				this.nfa.addTransition(this.lastStateName, this.secondChoice, this.lastStateName);
				this.secondChoice = null;
			}
		} else if (this.peek() == '|') {
			this.eat('|');
			this.processChoice(this.next());
			String nextStateName = String.format("q%d", this.stateCounter);
			this.nfa.addTransition(this.lastStateName, this.firstChoice, nextStateName);
			this.nfa.addTransition(this.lastStateName, this.secondChoice, nextStateName);
			this.lastStateName = nextStateName;
			this.stateCounter++;
			this.secondChoice = null;
		} else {
			String nextStateName = String.format("q%d", this.stateCounter);
			this.nfa.addTransition(this.lastStateName, this.firstChoice, nextStateName);
			this.lastStateName = nextStateName;
			this.stateCounter++;
		}
	}

	private void processCharacter(Character c) {
		this.firstChoice = c;
		this.alphabet.add(c);
		this.nfa.addAbc(this.alphabet);
		if (!this.more())
			this.addStateToNFA();
	}

	private void processChoice(Character c) {
		this.secondChoice = c;
		this.alphabet.add(c);
		this.nfa.addAbc(this.alphabet);
	}

	/**
	 * See what the next character is in the input string without consuming it.
	 * 
	 * @return the next character in the input string
	 */
	private char peek() {
		if (!this.more())
			return 'e';
		return input.charAt(0);
	}

	/**
	 * Consume the next character from the input string.
	 * 
	 * @param c the character that should be consumed from the input string
	 * @throws RuntimeException if the character to consume doesn't match the next character from the input string.
	 */
	private void eat(char c) {
		if (peek() == c)
			this.input = this.input.substring(1);
		else
			throw new RuntimeException("Expected " + c + " but got " + peek());
	}

	/**
	 * Read the next character and consume it.
	 * 
	 * @return the next character in the input string after consuming it
	 */
	private char next() {
		char c = peek();
		eat(c);
		return c;
	}

	/**
	 * Determine if there are more characters to process.
	 * 
	 * @return true if there are more characters to process, false otherwise.
	 */
	private boolean more() {
		return input.length() > 0;
	}
 }