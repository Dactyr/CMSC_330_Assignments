#include "stdafx.h"
#include <string> 
#include <vector> 
using namespace std;

#include "symboltable.h"


void SymbolTable::insert(string variable, int value)
{
	const Symbol& symbol = Symbol(variable, value);
	elements.push_back(symbol);
}

int SymbolTable::lookUp(string variable) const
{
	for (int i = 0; i < elements.size(); i++)
		if (elements[i].variable == variable)
			return elements[i].value;
	return -1;
}
 
// Each line in the file will be a new expression that needs to be evaluated, containing different values in the variables
// As a result, we need a method that will clear all the symbols stored in the vector<Symbol> table, so that new values can be stored for the current expresssion.
void SymbolTable::clearSymbolTable()
{
	if (elements.size() > 0)
	{
		for (int i = elements.size(); i > 0; i--)
		{
			elements.pop_back();
		}
	}
}

