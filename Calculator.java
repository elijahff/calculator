/*H*****************************************************************************
 * Filename: Calculator.java
 * Description: Defines a calculator class 
 * Comment: Paranthesis implementation is not complete.
 * Modified: 2021-05-11	File Created.
 * 	     2021-05-17 Finished implementation w/o parenthesis.
 * Author: Elijah Freeman (elijah@elijahfreeman.com, elijahfreeman.com)
 ****************************************************************************H*/

import java.util.*;

/**
 * Defines a Calculator that can perform the following calculations: 
 * multiplication, addition, subtraction, exponentiation. Calculator  
 * operate in EMDAS fashion.
 */
public class Calculator {

	private static final int NUMBER_OF_OPERATORS = 6;

	/**
	 * Buffer that contains the operators and operands.
	 */
	private final Deque<String> buffer;

	/** 
	 * Most recent operator.
	 */
	private String recentOperator;

	/**
	 * List of operators.
	 */
	private final String[] operators;

	/**
	 * Constructs calculator object. Initializes buffer and sets the official
	 * list of acceptable operators.
	 */
	public Calculator() {
		buffer = new ArrayDeque<>();
		operators = new String[NUMBER_OF_OPERATORS];
		recentOperator = null;
		operators[0] = ")";
		operators[1] = "^";
		operators[2] = "*";
		operators[3] = "/";
		operators[4] = "+";
		operators[5] = "-";
	}

	/**
	 * Adds an element to the buffer. If element is not an operator and
	 * more than two elements in a buffer then check precedence of the
	 * most recent operator.
	 *
	 * @param  element  a string representing either an operator or an 
	 * 		    operand.
	 */
	public void addElement(String element) {
		buffer.addFirst(element);
		checkIfMultiDigit();
		if (!isOperator(element) && buffer.size() > 2) {
			checkExponents(recentOperator); 
		}
	}

	private void checkIfMultiDigit() {
		if (buffer.size() > 1) {
			String currentElement = buffer.removeFirst();
			while (!isOperator(currentElement) && buffer.size() > 0) {
				String nextElement = buffer.removeFirst();
				if (isOperator(nextElement)) {
					buffer.addFirst(nextElement);
					break;
				} else {
					currentElement = combineDigits(nextElement, currentElement);
				}
			}
			buffer.addFirst(currentElement);
		}
	}

	private String combineDigits(String firstDigit, String secondDigit) {
			firstDigit += secondDigit;
			String multiDigitNumber = firstDigit;
			return multiDigitNumber;
	}

	/**
	 * Checks if element is an operator. If true then the operator is saved.
	 *
	 * @param  element  a string representing either an operator or an 
	 * 		    operand.
	 */
	private boolean isOperator(String element) {
		for (int i = 0; i < NUMBER_OF_OPERATORS; i++) {
			if (element.equals(operators[i])) {
				recentOperator = element;
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the operator specifies exponentiation. If true, then carry
	 * out operation.
	 *
	 * @param  operator  an operator to be checked.
	 */
	private void checkExponents(String operator) {
		if (isExponentiation(operator)) {
			final double secondOperand = Double.parseDouble(buffer.removeFirst());
			buffer.removeFirst();
			final double firstOperand = Double.parseDouble(buffer.removeFirst());
			exponentiate(firstOperand, secondOperand);
		} 
	}


	/**
	 * Checks if operand specifies exponentiation.
	 *
	 * @param  element  a string representing either an operator or an 
	 * 		    operand.
	 * @return          true if operand specifies exponentiation. False otherwise.
	 */
	private boolean isExponentiation(String element) {
		return element.equals("^");
	}

	/**
	 * Checks if operand specifies mulitiplication.
	 *
	 * @param  element  a string representing either an operator or an 
	 * 		    operand.
	 * @return          true if operand specifies multiplicatin. False otherwise.
	 */
	private boolean isMultiplication(String element) {
		return element.equals("*");
	}

	/**
	 * Checks if operand specifies division.
	 *
	 * @param  element  a string representing either an operator or an 
	 * 		    operand.
	 * @return          true if operand specifies division. False otherwise.
	 */
	private boolean isDivision(String element) {
		return element.equals("/");
	}

	/**
	 * Checks if operand specifies addition.
	 *
	 * @param  element  a string representing either an operator or an 
	 * 		    operand.
	 * @return          true if operand specifies addition. False otherwise.
	 */
	private boolean isAddition(String element) {
		return element.equals("+");
	}

	/**
	 * Checks if operand specifies subtraction.
	 *
	 * @param  element  a string representing either an operator or an 
	 * 		    operand.
	 * @return          true if operand specifies subtraction. False otherwise.
	 */
	private boolean isSubtraction(String element) {
		return element.equals("-");
	}

	/**
	 * Checks if operand specifies exponentiation.
	 * 
	 * @param  base      the base that the exponent is to operate on.
	 * @param  exponent  the power that the base is raised too.
	 */
	private void exponentiate(double base, double exponent) {
		final double result = Math.pow(base, exponent);
		buffer.addFirst(Double.toString(result));
	}

	/**
	 * Checks if operand specifies multiplication..
	 */
	private void multiply(double multiplier, double multiplicand) {
		final double result = multiplier * multiplicand;
		buffer.addFirst(Double.toString(result));
	}

	/**
	 * Checks if operand specifies multiplication.
	 */
	private void divide(double dividend, double divisor) {
		final double result = dividend / divisor;
		buffer.addFirst(Double.toString(result));
	}

	/**
	 * Checks if operand specifies addition.
	 */
	private void add(double firstSummand, double secondSummand) {
		final double result = firstSummand + secondSummand;
		buffer.addFirst(Double.toString(result));
	}

	/**
	 * Checks if operand specifies subtraction.
	 */
	private void subtract(double minuend, double subtrahend) {
		final double result = minuend - subtrahend;
		buffer.addFirst(Double.toString(result));
	}

	/**
	 * Returns the final result of the calculation.
	 *
	 * @return  the final result of the calculation.
	 */
	public double getResult() {
		execute();
		return Double.parseDouble(buffer.removeFirst());
	}

	private void execute() {
		Deque<String> temp = new ArrayDeque<>();
		System.out.printf("Buffer in Execute: %s\n", buffer.toString());
		while (buffer.size() > 0) {
			final String element = buffer.removeFirst();
			if (isOperator(element)) {
				if (isMultiplication(element)) {
					final double secondOperand = Double.parseDouble(buffer.removeFirst());
					final double firstOperand = Double.parseDouble(temp.removeFirst());
					multiply(firstOperand, secondOperand);
				} else if (isDivision(element)) {
					final double secondOperand = Double.parseDouble(temp.removeFirst());
					final double firstOperand = Double.parseDouble(buffer.removeFirst());
					divide(firstOperand, secondOperand);
				} else {
					temp.addFirst(element);
				}
			} else {
				temp.addFirst(element);
			}
		}
		while (temp.size() > 0) {
			final String element = temp.removeFirst();
			if (isOperator(element)) {
				final double secondOperand = Double.parseDouble(temp.removeFirst());
				final double firstOperand = Double.parseDouble(buffer.removeFirst());
				if (isAddition(element)) {
					add(firstOperand, secondOperand);
				} else if (isSubtraction(element)) {
					subtract(firstOperand, secondOperand);
				}
			} else {
				buffer.addFirst(element);
			}
		}
	}

	/**
	 * Removes element that was added last.
	 */
	public void removeLastElement() {
		buffer.removeFirst();
	}

	/**
	 * Removes all elements from the buffer.
	 */
	public void clearBuffer() {
		buffer.clear();
	}

	/**
	 * Returns the size of the buffer.
	 */
	public int getBufferSize() {
		return buffer.size();
	}
}

