/**
* The SubExpression class defines the node of a binary expression tree for evaluating an expressions. The parse method determines if the expression contains
* any conditional, negation, or any other type of expressions, and uses a switch statement to determine which of the sub classes of SubExpression should be
* called to evaluate the statement.
*/

#include "stdafx.h"
#include <iostream>
#include <sstream>
using namespace std;


#include "expression.h"
#include "subexpression.h"
#include "operand.h"
#include "plus.h"
#include "minus.h"
#include "times.h"
#include "divide.h"
#include "greaterthan.h"
#include "lessthan.h"
#include "isequalto.h"
#include "and.h"
#include "or.h"
#include "negation.h"
#include "conditionalexp.h"

SubExpression::SubExpression(Expression* left, Expression* right)
{
	this->left = left;
	this->right = right;
}


Expression* SubExpression::parse(stringstream& in)
{
	Expression* left;
	Expression* right;
	Expression* condition; // will hold the third operand value in the in a conditional expression, which represents the condition
	char operation, paren;

	//read in the first operand
	left = Operand::parse(in);


	// After the first operand has been read in, then the next thing read will either be <op>, ':', or '!'
	//This will determine what type of expression is being evaluated. 
	in >> operation;


	// <exp> -> '(' <operand> <op> <operand> ')' |
	//          '(' <operand> ':' <operand> '?' <operand> ')' |
	//          '(' <operand> '!' ')'

	// If it's a ':' character, then it means that it will be a conditional expresssion.
	if (operation == ':')
	{
		right = Operand::parse(in);
		in >> paren;// this char will be a '?' when its a conditional expression
		condition = Operand::parse(in);
		in >> paren;

		return new  ConditionalExp(left, right, condition);
	}
	// If it's a '!' character, then it means that it will be a negation expression
	else if (operation == '!')
	{
		in >> paren;

		return new Negation(left, NULL); //Passing a NULL as the second argument as the negation opeation really only functions on one operand
	}
	// otherwise, it will be an ordinary expression and evaluate it depending on the tye of operator. 
	else
	{
		right = Operand::parse(in);
		in >> paren;

		switch (operation)
		{
		// <op> -> '+' | '-' | '*' | '/' | '>' | '<' | '=' | '&' | '|'

		case '+':
			return new Plus(left, right);
		case '-':
			return new Minus(left, right);
		case '*':
			return new Times(left, right);
		case '/':
			return new Divide(left, right);
		case '>':
			return new GreaterThan(left, right);
		case '<':
			return new LessThan(left, right);
		case '=':
			return new IsEqualTo(left, right);
		case '&':
			return new And(left, right);
		case '|':
			return new Or(left, right);
		}
	}

	return 0;
}

