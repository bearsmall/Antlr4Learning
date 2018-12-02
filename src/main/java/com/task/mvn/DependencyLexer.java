// Generated from D:/Users/bearsmall/IdeaProjects/Antlr4Learning/src/main/java/com/task/mvn\Dependency.g4 by ANTLR 4.7
package com.task.mvn;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DependencyLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, Identifier=4, Number=5, INFO=6, SWITCH=7, Tail=8;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "Identifier", "JavaLetter", "JavaLetterOrDigit", 
		"Number", "INFO", "SWITCH", "Tail"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "':'", "':jar:'", "'.'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, "Identifier", "Number", "INFO", "SWITCH", "Tail"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public DependencyLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Dependency.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 4:
			return JavaLetter_sempred((RuleContext)_localctx, predIndex);
		case 5:
			return JavaLetterOrDigit_sempred((RuleContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean JavaLetter_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return Character.isJavaIdentifierStart(_input.LA(-1));
		case 1:
			return Character.isJavaIdentifierStart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)));
		}
		return true;
	}
	private boolean JavaLetterOrDigit_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return Character.isJavaIdentifierPart(_input.LA(-1));
		case 3:
			return Character.isJavaIdentifierPart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)));
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\nj\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\7\5$\n\5\f\5\16\5"+
		"\'\13\5\3\6\3\6\3\6\3\6\3\6\3\6\5\6/\n\6\3\7\3\7\3\7\3\7\3\7\3\7\5\7\67"+
		"\n\7\3\b\6\b:\n\b\r\b\16\b;\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\tF\n\t\f"+
		"\t\16\tI\13\t\3\t\3\t\7\tM\n\t\f\t\16\tP\13\t\7\tR\n\t\f\t\16\tU\13\t"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\5\n]\n\n\3\13\7\13`\n\13\f\13\16\13c\13\13\3"+
		"\13\7\13f\n\13\f\13\16\13i\13\13\3a\2\f\3\3\5\4\7\5\t\6\13\2\r\2\17\7"+
		"\21\b\23\t\25\n\3\2\t\7\2&&//C\\aac|\4\2\2\u0081\ud802\udc01\3\2\ud802"+
		"\udc01\3\2\udc02\ue001\b\2&&//\62;C\\aac|\3\2\62;\4\2\f\f\17\17\2s\2\3"+
		"\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2"+
		"\2\23\3\2\2\2\2\25\3\2\2\2\3\27\3\2\2\2\5\31\3\2\2\2\7\37\3\2\2\2\t!\3"+
		"\2\2\2\13.\3\2\2\2\r\66\3\2\2\2\179\3\2\2\2\21=\3\2\2\2\23\\\3\2\2\2\25"+
		"a\3\2\2\2\27\30\7<\2\2\30\4\3\2\2\2\31\32\7<\2\2\32\33\7l\2\2\33\34\7"+
		"c\2\2\34\35\7t\2\2\35\36\7<\2\2\36\6\3\2\2\2\37 \7\60\2\2 \b\3\2\2\2!"+
		"%\5\13\6\2\"$\5\r\7\2#\"\3\2\2\2$\'\3\2\2\2%#\3\2\2\2%&\3\2\2\2&\n\3\2"+
		"\2\2\'%\3\2\2\2(/\t\2\2\2)*\n\3\2\2*/\6\6\2\2+,\t\4\2\2,-\t\5\2\2-/\6"+
		"\6\3\2.(\3\2\2\2.)\3\2\2\2.+\3\2\2\2/\f\3\2\2\2\60\67\t\6\2\2\61\62\n"+
		"\3\2\2\62\67\6\7\4\2\63\64\t\4\2\2\64\65\t\5\2\2\65\67\6\7\5\2\66\60\3"+
		"\2\2\2\66\61\3\2\2\2\66\63\3\2\2\2\67\16\3\2\2\28:\t\7\2\298\3\2\2\2:"+
		";\3\2\2\2;9\3\2\2\2;<\3\2\2\2<\20\3\2\2\2=>\7]\2\2>?\7K\2\2?@\7P\2\2@"+
		"A\7H\2\2AB\7Q\2\2BC\7_\2\2CG\3\2\2\2DF\7\"\2\2ED\3\2\2\2FI\3\2\2\2GE\3"+
		"\2\2\2GH\3\2\2\2HS\3\2\2\2IG\3\2\2\2JN\7~\2\2KM\7\"\2\2LK\3\2\2\2MP\3"+
		"\2\2\2NL\3\2\2\2NO\3\2\2\2OR\3\2\2\2PN\3\2\2\2QJ\3\2\2\2RU\3\2\2\2SQ\3"+
		"\2\2\2ST\3\2\2\2T\22\3\2\2\2US\3\2\2\2VW\7-\2\2WX\7/\2\2X]\7\"\2\2YZ\7"+
		"^\2\2Z[\7/\2\2[]\7\"\2\2\\V\3\2\2\2\\Y\3\2\2\2]\24\3\2\2\2^`\13\2\2\2"+
		"_^\3\2\2\2`c\3\2\2\2ab\3\2\2\2a_\3\2\2\2bg\3\2\2\2ca\3\2\2\2df\t\b\2\2"+
		"ed\3\2\2\2fi\3\2\2\2ge\3\2\2\2gh\3\2\2\2h\26\3\2\2\2ig\3\2\2\2\r\2%.\66"+
		";GNS\\ag\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}