/**
* The other subclass of Expression is Operand, which defines the leaf nodes of the arithmetic expression tree.
* The class definition for Operand is contained in the file operand.h, shown below:
*/

class Operand : public Expression
{
public:
	static Expression* parse(stringstream& in);
};