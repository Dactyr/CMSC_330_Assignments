/**
* This interpreter uses a symbol table that is implemented with an unsorted list defined by the class SymbolTable.
* Its class definition is contained in the file symboltable.h, shown below:
*/
class SymbolTable
{
public:
	SymbolTable() {}
	void insert(string variable, int value);
	int lookUp(string variable) const;
	void clearSymbolTable();
private:
	struct Symbol
	{
		Symbol(string variable, int value)
		{
			this->variable = variable;
			this->value = value;
		}
		string variable;
		int value;
	};
	vector <Symbol> elements;
};