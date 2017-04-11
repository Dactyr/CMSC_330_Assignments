/**
* The Operand class has two subclasses. The first is Variable, which defines leaf nodes of the tree that contain variables.
* The class definition for Variable is contained in the file variable.h, shown below:
*/

class Variable : public Operand
{
public:
	Variable(string name)
	{
		this->name = name;
	}
	int Variable::evaluate();
private:
	string name;
};