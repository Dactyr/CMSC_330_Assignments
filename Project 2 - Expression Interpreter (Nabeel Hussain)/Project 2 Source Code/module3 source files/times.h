/**
* Peforms an integer multiplication operation on two Expression Objects.
*/
class Times : public SubExpression
{
public:
	Times(Expression* left, Expression* right) : SubExpression(left, right)
	{
	}
	int evaluate()
	{
		return left->evaluate() * right->evaluate();
	}
};