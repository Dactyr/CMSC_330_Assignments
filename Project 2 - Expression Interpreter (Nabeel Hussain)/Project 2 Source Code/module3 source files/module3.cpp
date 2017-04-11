// module3.cpp : Defines the entry point for the console application.

#include "stdafx.h"
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

#include "expression.h"
#include "subexpression.h"
#include "symboltable.h"
#include "parse.h"

/**
* Class: CMSC 330
* Instructor: Amitava Karmaker
* Project Number: 2
* Name: Nabeel Hussain
* Date: 02/28/2017
* Platform/Compiler: Microsoft Visual Studio 2015
*
* The main function reads in an input file, allowing multiple expressions arranged one per line. For each expression in the file, it will call upon the static function parse
* of the SubExpression class to parse it, and build an arithmetic expression tree.
* It then calls the subordinate function parseAssignments to parse the assignments and enter them into the symbol table, and then evaluates the expression and displays the result.
*/


SymbolTable symbolTable;


void parseAssignments(stringstream& in);


int main()
{
	Expression* expression;
	char paren, comma;
	string line;

	std::ifstream infile("ExpressionInputs.txt"); // create a stream to the file contining the expressions

	// If the file exists
	if (infile.is_open())
	{
		//While the file contains a line
		while (std::getline(infile, line))
		{
			// Will clear the symboltable, so that the correct variables and values are stored for the current expression being evaluated.
			// This way, the symbols stored in the table from the previous expression will not cause the wrong result to be displayed. 
			symbolTable.clearSymbolTable();

			stringstream in(line); // Line-based parsing, using a stringstream:

			in >> paren;
			cout << line << " ";
			expression = SubExpression::parse(in);
			in >> comma;
			parseAssignments(in);
			cout << "Value = " << expression->evaluate() << endl;

		}

		infile.close();
	}
	// If the file does not exist
	else
	{
		cout << "Unable to open file" << endl;
	}

	return 0;
}

void parseAssignments(stringstream& in)
{
	char assignop, delimiter;
	string variable;
	int value;
	do
	{
		variable = parseName(in);
		in >> ws >> assignop >> value >> delimiter;
		symbolTable.insert(variable, value);
	} while (delimiter == ',');
}