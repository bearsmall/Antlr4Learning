grammar PropertyFile;
@members {
	void startFile(){ }	//空实现
	void finishFile(){ }
	void defineProperty(Token name, Token value){ }
}
file: {startFile();} prop+ {finishFile();};
prop: ID '=' STRING '\r'?'\n' {defineProperty($ID, $STRING);};
ID: [a-z]+;
STRING: '"' .*? '"';