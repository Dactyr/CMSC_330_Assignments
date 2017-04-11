/**
* The SubExpression class defines the node of a binary expression tree for evaluating an expressions. The parse method determines if the expression contains
* any conditional, negation, or any other type of expressions, and uses a switch statement to determine which of the sub classes of SubExpression should be
* called to evaluate the statement.
*/
class SubExpression : public Expression
{
public:
	SubExpression(Expression* left, Expression* right);
	static Expression* parse(stringstream& in);


protected:
	Expression* left;
	Expression* right;
};