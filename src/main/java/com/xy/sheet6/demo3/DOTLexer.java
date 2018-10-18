// Generated from D:/Users/bearsmalll/IdeaProjects/Antlr4Learning/src/main/java/com/xy/sheet6/demo3\DOT.g4 by ANTLR 4.7
package com.xy.sheet6.demo3;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DOTLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, DIGRAPH=11, STRICT=12, GRAPH=13, IDGRAPH=14, NODE=15, EDGE=16, 
		SUBGRAPH=17, ID=18, NUMBER=19, STRING=20, HTML_STRING=21, PREPROC=22, 
		COMMENT=23, LINE_COMMENT=24, WS=25;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "DIGRAPH", "STRICT", "GRAPH", "IDGRAPH", "NODE", "EDGE", "SUBGRAPH", 
		"ID", "LETTER", "NUMBER", "DIGIT", "STRING", "HTML_STRING", "TAG", "PREPROC", 
		"COMMENT", "LINE_COMMENT", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'{'", "'}'", "';'", "'='", "'['", "']'", "','", "'->'", "'--'", 
		"':'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, "DIGRAPH", 
		"STRICT", "GRAPH", "IDGRAPH", "NODE", "EDGE", "SUBGRAPH", "ID", "NUMBER", 
		"STRING", "HTML_STRING", "PREPROC", "COMMENT", "LINE_COMMENT", "WS"
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


	public DOTLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DOT.g4"; }

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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\33\u00f4\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\3\2\3\2\3\3\3\3\3\4\3\4"+
		"\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20"+
		"\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\23\3\23\3\23\7\23\u0085\n\23\f\23\16\23\u0088\13\23\3\24"+
		"\3\24\3\25\5\25\u008d\n\25\3\25\3\25\6\25\u0091\n\25\r\25\16\25\u0092"+
		"\3\25\6\25\u0096\n\25\r\25\16\25\u0097\3\25\3\25\7\25\u009c\n\25\f\25"+
		"\16\25\u009f\13\25\5\25\u00a1\n\25\5\25\u00a3\n\25\3\26\3\26\3\27\3\27"+
		"\3\27\3\27\7\27\u00ab\n\27\f\27\16\27\u00ae\13\27\3\27\3\27\3\30\3\30"+
		"\3\30\7\30\u00b5\n\30\f\30\16\30\u00b8\13\30\3\30\3\30\3\31\3\31\7\31"+
		"\u00be\n\31\f\31\16\31\u00c1\13\31\3\31\3\31\3\32\3\32\7\32\u00c7\n\32"+
		"\f\32\16\32\u00ca\13\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\7\33\u00d4"+
		"\n\33\f\33\16\33\u00d7\13\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3"+
		"\34\7\34\u00e2\n\34\f\34\16\34\u00e5\13\34\3\34\5\34\u00e8\n\34\3\34\3"+
		"\34\3\34\3\34\3\35\6\35\u00ef\n\35\r\35\16\35\u00f0\3\35\3\35\7\u00ac"+
		"\u00bf\u00c8\u00d5\u00e3\2\36\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13"+
		"\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\2)\25+\2-\26/\27\61\2"+
		"\63\30\65\31\67\329\33\3\2\25\4\2FFff\4\2KKkk\4\2IIii\4\2TTtt\4\2CCcc"+
		"\4\2RRrr\4\2JJjj\4\2UUuu\4\2VVvv\4\2EEee\4\2PPpp\4\2QQqq\4\2GGgg\4\2W"+
		"Www\4\2DDdd\5\2C\\c|\u0082\u0101\3\2\62;\4\2>>@@\5\2\13\f\17\17\"\"\2"+
		"\u0102\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2"+
		"\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3"+
		"\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2"+
		"\2#\3\2\2\2\2%\3\2\2\2\2)\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\63\3\2\2\2\2"+
		"\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\3;\3\2\2\2\5=\3\2\2\2\7?\3\2\2\2\t"+
		"A\3\2\2\2\13C\3\2\2\2\rE\3\2\2\2\17G\3\2\2\2\21I\3\2\2\2\23L\3\2\2\2\25"+
		"O\3\2\2\2\27Q\3\2\2\2\31Y\3\2\2\2\33`\3\2\2\2\35f\3\2\2\2\37n\3\2\2\2"+
		"!s\3\2\2\2#x\3\2\2\2%\u0081\3\2\2\2\'\u0089\3\2\2\2)\u008c\3\2\2\2+\u00a4"+
		"\3\2\2\2-\u00a6\3\2\2\2/\u00b1\3\2\2\2\61\u00bb\3\2\2\2\63\u00c4\3\2\2"+
		"\2\65\u00cf\3\2\2\2\67\u00dd\3\2\2\29\u00ee\3\2\2\2;<\7}\2\2<\4\3\2\2"+
		"\2=>\7\177\2\2>\6\3\2\2\2?@\7=\2\2@\b\3\2\2\2AB\7?\2\2B\n\3\2\2\2CD\7"+
		"]\2\2D\f\3\2\2\2EF\7_\2\2F\16\3\2\2\2GH\7.\2\2H\20\3\2\2\2IJ\7/\2\2JK"+
		"\7@\2\2K\22\3\2\2\2LM\7/\2\2MN\7/\2\2N\24\3\2\2\2OP\7<\2\2P\26\3\2\2\2"+
		"QR\t\2\2\2RS\t\3\2\2ST\t\4\2\2TU\t\5\2\2UV\t\6\2\2VW\t\7\2\2WX\t\b\2\2"+
		"X\30\3\2\2\2YZ\t\t\2\2Z[\t\n\2\2[\\\t\5\2\2\\]\t\3\2\2]^\t\13\2\2^_\t"+
		"\n\2\2_\32\3\2\2\2`a\t\4\2\2ab\t\5\2\2bc\t\6\2\2cd\t\7\2\2de\t\b\2\2e"+
		"\34\3\2\2\2fg\t\2\2\2gh\t\3\2\2hi\t\4\2\2ij\t\5\2\2jk\t\6\2\2kl\t\7\2"+
		"\2lm\t\b\2\2m\36\3\2\2\2no\t\f\2\2op\t\r\2\2pq\t\2\2\2qr\t\16\2\2r \3"+
		"\2\2\2st\t\16\2\2tu\t\2\2\2uv\t\4\2\2vw\t\16\2\2w\"\3\2\2\2xy\t\t\2\2"+
		"yz\t\17\2\2z{\t\20\2\2{|\t\4\2\2|}\t\5\2\2}~\t\6\2\2~\177\t\7\2\2\177"+
		"\u0080\t\b\2\2\u0080$\3\2\2\2\u0081\u0086\5\'\24\2\u0082\u0085\5\'\24"+
		"\2\u0083\u0085\5+\26\2\u0084\u0082\3\2\2\2\u0084\u0083\3\2\2\2\u0085\u0088"+
		"\3\2\2\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087&\3\2\2\2\u0088"+
		"\u0086\3\2\2\2\u0089\u008a\t\21\2\2\u008a(\3\2\2\2\u008b\u008d\7/\2\2"+
		"\u008c\u008b\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u00a2\3\2\2\2\u008e\u0090"+
		"\7\60\2\2\u008f\u0091\5+\26\2\u0090\u008f\3\2\2\2\u0091\u0092\3\2\2\2"+
		"\u0092\u0090\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u00a3\3\2\2\2\u0094\u0096"+
		"\5+\26\2\u0095\u0094\3\2\2\2\u0096\u0097\3\2\2\2\u0097\u0095\3\2\2\2\u0097"+
		"\u0098\3\2\2\2\u0098\u00a0\3\2\2\2\u0099\u009d\7\60\2\2\u009a\u009c\5"+
		"+\26\2\u009b\u009a\3\2\2\2\u009c\u009f\3\2\2\2\u009d\u009b\3\2\2\2\u009d"+
		"\u009e\3\2\2\2\u009e\u00a1\3\2\2\2\u009f\u009d\3\2\2\2\u00a0\u0099\3\2"+
		"\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a3\3\2\2\2\u00a2\u008e\3\2\2\2\u00a2"+
		"\u0095\3\2\2\2\u00a3*\3\2\2\2\u00a4\u00a5\t\22\2\2\u00a5,\3\2\2\2\u00a6"+
		"\u00ac\7$\2\2\u00a7\u00a8\7^\2\2\u00a8\u00ab\7$\2\2\u00a9\u00ab\13\2\2"+
		"\2\u00aa\u00a7\3\2\2\2\u00aa\u00a9\3\2\2\2\u00ab\u00ae\3\2\2\2\u00ac\u00ad"+
		"\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ad\u00af\3\2\2\2\u00ae\u00ac\3\2\2\2\u00af"+
		"\u00b0\7$\2\2\u00b0.\3\2\2\2\u00b1\u00b6\7>\2\2\u00b2\u00b5\5\61\31\2"+
		"\u00b3\u00b5\n\23\2\2\u00b4\u00b2\3\2\2\2\u00b4\u00b3\3\2\2\2\u00b5\u00b8"+
		"\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b6\u00b7\3\2\2\2\u00b7\u00b9\3\2\2\2\u00b8"+
		"\u00b6\3\2\2\2\u00b9\u00ba\7@\2\2\u00ba\60\3\2\2\2\u00bb\u00bf\7>\2\2"+
		"\u00bc\u00be\13\2\2\2\u00bd\u00bc\3\2\2\2\u00be\u00c1\3\2\2\2\u00bf\u00c0"+
		"\3\2\2\2\u00bf\u00bd\3\2\2\2\u00c0\u00c2\3\2\2\2\u00c1\u00bf\3\2\2\2\u00c2"+
		"\u00c3\7@\2\2\u00c3\62\3\2\2\2\u00c4\u00c8\7%\2\2\u00c5\u00c7\13\2\2\2"+
		"\u00c6\u00c5\3\2\2\2\u00c7\u00ca\3\2\2\2\u00c8\u00c9\3\2\2\2\u00c8\u00c6"+
		"\3\2\2\2\u00c9\u00cb\3\2\2\2\u00ca\u00c8\3\2\2\2\u00cb\u00cc\7\f\2\2\u00cc"+
		"\u00cd\3\2\2\2\u00cd\u00ce\b\32\2\2\u00ce\64\3\2\2\2\u00cf\u00d0\7\61"+
		"\2\2\u00d0\u00d1\7,\2\2\u00d1\u00d5\3\2\2\2\u00d2\u00d4\13\2\2\2\u00d3"+
		"\u00d2\3\2\2\2\u00d4\u00d7\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d5\u00d3\3\2"+
		"\2\2\u00d6\u00d8\3\2\2\2\u00d7\u00d5\3\2\2\2\u00d8\u00d9\7,\2\2\u00d9"+
		"\u00da\7\61\2\2\u00da\u00db\3\2\2\2\u00db\u00dc\b\33\2\2\u00dc\66\3\2"+
		"\2\2\u00dd\u00de\7\61\2\2\u00de\u00df\7\61\2\2\u00df\u00e3\3\2\2\2\u00e0"+
		"\u00e2\13\2\2\2\u00e1\u00e0\3\2\2\2\u00e2\u00e5\3\2\2\2\u00e3\u00e4\3"+
		"\2\2\2\u00e3\u00e1\3\2\2\2\u00e4\u00e7\3\2\2\2\u00e5\u00e3\3\2\2\2\u00e6"+
		"\u00e8\7\17\2\2\u00e7\u00e6\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8\u00e9\3"+
		"\2\2\2\u00e9\u00ea\7\f\2\2\u00ea\u00eb\3\2\2\2\u00eb\u00ec\b\34\2\2\u00ec"+
		"8\3\2\2\2\u00ed\u00ef\t\24\2\2\u00ee\u00ed\3\2\2\2\u00ef\u00f0\3\2\2\2"+
		"\u00f0\u00ee\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f3"+
		"\b\35\2\2\u00f3:\3\2\2\2\25\2\u0084\u0086\u008c\u0092\u0097\u009d\u00a0"+
		"\u00a2\u00aa\u00ac\u00b4\u00b6\u00bf\u00c8\u00d5\u00e3\u00e7\u00f0\3\b"+
		"\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}