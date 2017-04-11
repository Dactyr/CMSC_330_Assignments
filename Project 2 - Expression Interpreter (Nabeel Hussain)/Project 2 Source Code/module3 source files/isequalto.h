/**
* Performs the relational Equals operation on two Expression Objects. Returns a 1 or true if the two expression obects are the same. 
*/
class IsEqualTo : public SubExpression
{
public:
	IsEqualTo(Expression* left, Expression* right) : SubExpression(left, right)
	{
	}
	int evaluate()
	{
		return left->evaluate() == right->evaluate();
	}
};
