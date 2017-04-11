/**
* Peforms an integer adition operation on two Expression Objects.
*/
class Plus : public SubExpression
{
public:
	Plus(Expression* left, Expression* right) : SubExpression(left, right)
	{
	}
	int evaluate()
	{
		return left->evaluate() + right->evaluate();
	}
};