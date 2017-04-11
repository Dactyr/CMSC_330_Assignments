/**
* The other subclass of Operand is Literal, which defines leaf nodes of the tree that contain literal values.
* The class definition for Literal is contained in the file literal.h, shown below:
*/

class Literal : public Operand
{
public:
	Literal(int value)
	{
		this->value = value;
	}
	int evaluate()
	{
		return value;
	}
private:
	int value;
};