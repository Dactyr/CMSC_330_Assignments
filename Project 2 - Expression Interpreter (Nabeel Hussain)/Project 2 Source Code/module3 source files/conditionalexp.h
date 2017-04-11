/**
* Returns the results of the left subEpxression if the conitional subExpression evaluates to true, otherwise it
* returns the results of the right subExpression if false.
*/

class ConditionalExp : public SubExpression
{
public:
	ConditionalExp(Expression* left, Expression* right, Expression* condition) : SubExpression(left, right)
	{
		this->left = left;
		this->right = right;
		this->condition = condition;
	}
	int evaluate()
	{
		return condition->evaluate() ? left->evaluate() : right->evaluate();
	}

private:
	Expression* left;
	Expression* right;
	Expression* condition;
};
