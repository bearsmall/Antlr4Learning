grammar Cymbol;

file : (functionDecl | varDecl)+;
varDecl
	: type ID ('=' expr)? ';'
	;

type : 'float' | 'int' | 'void' ; //用户定义的类型

functionDecl
	: type ID '(' formalParameters? ')' block //"void f(int x) {...}"
	;

formalParameters
	: formalParameter (',' formalParameter)*
	;

formalParameter
	: type ID
	;

block : '{' stat* '}';	//语句组成的代码块，可以为空
stat  : block
	  | varDecl
	  | 'if' expr 'then' stat ('else' stat)?
	  | 'return' expr? ';'
	  | expr '=' expr ';' //赋值
	  | expr ';'
	  ;

expr  : ID '(' exprList? ')'	//类似f(),f(x),f(1,2)的函数调用表达式
	  | expr '{' expr '}'		//类似a[i],a[i][j]的数组索引表达式
	  | '-' expr
	  | '!' expr
	  | expr '*' expr
	  | expr ('+'|'-') expr		//等同性判断表达式（它是优先级最低的运算符）
	  | expr '==' expr 			//variable reference
	  | INT
	  | '(' expr ')'
	  ;

exprList : expr (',' expr)* ;	//参数列表