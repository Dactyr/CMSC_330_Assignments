/**
* evaluates the relational Greater Than operation on two Expression Objects.
*/
class GreaterThan : public SubExpression
{
public:
	GreaterThan(Expression* left, Expression* right) : SubExpression(left, right)
	{
	}
	int evaluate()
	{
		return left->evaluate() > right->evaluate();
	}
}; 
