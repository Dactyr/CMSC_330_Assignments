/*
The arithmetic expression tree is built using an inheritance hierarchy. At the root of the hierarchy is the abstract class Expression.
The class definition for Expression is contained in the file expression.h, shown below:
*/

class Expression
{
public:
	//In C++, virtual functions are used to denote those subprograms for which dynamic binding is performed.
	//A subprogram in the derived class with the same name and parameter signature as that of a virtual subprogram in the parent class is also considered virtual.
	virtual int evaluate() = 0;
};