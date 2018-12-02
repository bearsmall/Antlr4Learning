grammar Dependency;


dependency:BLANK dependList BLANK;

dependList: '[INFO] '('|  ')*('+- '|'\- ')denpendItem;

denpendItem: dependentUnit(':compile'|':runtime');

dependentUnit: packageName':'JavaLetter':jar:'versionUnit;

versionUnit: versionDigit ':' LDEVL;

LEVEL : 'RELEASE'|'DEBUG';

versionDigit : Number'.'Number'.'Number;

Number:[0-1]+;

packageName
	:	Identifier
	|	packageName '.' Identifier
	;

packageOrTypeName
	:	Identifier
	|	packageOrTypeName '.' Identifier
	;

Identifier
	:	JavaLetter JavaLetterOrDigit*
	;
fragment
JavaLetter
	:	[a-zA-Z$_] // these are the "java letters" below 0x7F
	|	// covers all characters above 0x7F which are not a surrogate
		~[\u0000-\u007F\uD800-\uDBFF]
		{Character.isJavaIdentifierStart(_input.LA(-1))}?
	|	// covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
		[\uD800-\uDBFF] [\uDC00-\uDFFF]
		{Character.isJavaIdentifierStart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
	;

fragment
JavaLetterOrDigit
	:	[a-zA-Z0-9$_] // these are the "java letters or digits" below 0x7F
	|	// covers all characters above 0x7F which are not a surrogate
		~[\u0000-\u007F\uD800-\uDBFF]
		{Character.isJavaIdentifierPart(_input.LA(-1))}?
	|	// covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
		[\uD800-\uDBFF] [\uDC00-\uDFFF]
		{Character.isJavaIdentifierPart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
	;

BLANK :  .*?;

WS  :  [ \t\r\n\u000C]+ -> skip;

BEGIN : [\r\n]*'[INFO] --- maven-dependency-plugin:';

END : [\r\n]*'[INFO] ---';