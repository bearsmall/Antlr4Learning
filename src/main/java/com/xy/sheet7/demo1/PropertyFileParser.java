// Generated from D:/Users/bearsmall/IdeaProjects/Antlr4Learning/src/main/java/com/xy/sheet7/demo1\PropertyFile.g4 by ANTLR 4.7
package com.xy.sheet7.demo1;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PropertyFileParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, ID=4, STRING=5;
	public static final int
		RULE_file = 0, RULE_prop = 1;
	public static final String[] ruleNames = {
		"file", "prop"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'='", "'\r'", "'\n'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, "ID", "STRING"
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

	@Override
	public String getGrammarFileName() { return "PropertyFile.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


		void startFile(){ }
		void finishFile(){ }
		void defineProperty(Token name, Token value){ }

	public PropertyFileParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class FileContext extends ParserRuleContext {
		public List<PropContext> prop() {
			return getRuleContexts(PropContext.class);
		}
		public PropContext prop(int i) {
			return getRuleContext(PropContext.class,i);
		}
		public FileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_file; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PropertyFileListener ) ((PropertyFileListener)listener).enterFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PropertyFileListener ) ((PropertyFileListener)listener).exitFile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PropertyFileVisitor ) return ((PropertyFileVisitor<? extends T>)visitor).visitFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FileContext file() throws RecognitionException {
		FileContext _localctx = new FileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_file);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			startFile();
			setState(6); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(5);
				prop();
				}
				}
				setState(8); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ID );
			finishFile();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropContext extends ParserRuleContext {
		public Token ID;
		public Token STRING;
		public TerminalNode ID() { return getToken(PropertyFileParser.ID, 0); }
		public TerminalNode STRING() { return getToken(PropertyFileParser.STRING, 0); }
		public PropContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PropertyFileListener ) ((PropertyFileListener)listener).enterProp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PropertyFileListener ) ((PropertyFileListener)listener).exitProp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PropertyFileVisitor ) return ((PropertyFileVisitor<? extends T>)visitor).visitProp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropContext prop() throws RecognitionException {
		PropContext _localctx = new PropContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_prop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(12);
			((PropContext)_localctx).ID = match(ID);
			setState(13);
			match(T__0);
			setState(14);
			((PropContext)_localctx).STRING = match(STRING);
			setState(16);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(15);
				match(T__1);
				}
			}

			setState(18);
			match(T__2);
			defineProperty(((PropContext)_localctx).ID, ((PropContext)_localctx).STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\7\30\4\2\t\2\4\3"+
		"\t\3\3\2\3\2\6\2\t\n\2\r\2\16\2\n\3\2\3\2\3\3\3\3\3\3\3\3\5\3\23\n\3\3"+
		"\3\3\3\3\3\3\3\2\2\4\2\4\2\2\2\27\2\6\3\2\2\2\4\16\3\2\2\2\6\b\b\2\1\2"+
		"\7\t\5\4\3\2\b\7\3\2\2\2\t\n\3\2\2\2\n\b\3\2\2\2\n\13\3\2\2\2\13\f\3\2"+
		"\2\2\f\r\b\2\1\2\r\3\3\2\2\2\16\17\7\6\2\2\17\20\7\3\2\2\20\22\7\7\2\2"+
		"\21\23\7\4\2\2\22\21\3\2\2\2\22\23\3\2\2\2\23\24\3\2\2\2\24\25\7\5\2\2"+
		"\25\26\b\3\1\2\26\5\3\2\2\2\4\n\22";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}