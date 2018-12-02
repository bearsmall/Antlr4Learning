// Generated from D:/Users/bearsmall/IdeaProjects/Antlr4Learning/src/main/java/com/task/mvn\Dependency.g4 by ANTLR 4.7
package com.task.mvn;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DependencyParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, Identifier=4, Number=5, INFO=6, SWITCH=7, Tail=8;
	public static final int
		RULE_depend = 0, RULE_dependList = 1, RULE_dependItem = 2, RULE_dependentUnit = 3, 
		RULE_versionUnit = 4, RULE_packageName = 5;
	public static final String[] ruleNames = {
		"depend", "dependList", "dependItem", "dependentUnit", "versionUnit", 
		"packageName"
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

	@Override
	public String getGrammarFileName() { return "Dependency.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DependencyParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class DependContext extends ParserRuleContext {
		public List<DependListContext> dependList() {
			return getRuleContexts(DependListContext.class);
		}
		public DependListContext dependList(int i) {
			return getRuleContext(DependListContext.class,i);
		}
		public DependContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_depend; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DependencyListener ) ((DependencyListener)listener).enterDepend(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DependencyListener ) ((DependencyListener)listener).exitDepend(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DependencyVisitor ) return ((DependencyVisitor<? extends T>)visitor).visitDepend(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DependContext depend() throws RecognitionException {
		DependContext _localctx = new DependContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_depend);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(15);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==INFO) {
				{
				{
				setState(12);
				dependList();
				}
				}
				setState(17);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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

	public static class DependListContext extends ParserRuleContext {
		public TerminalNode INFO() { return getToken(DependencyParser.INFO, 0); }
		public TerminalNode SWITCH() { return getToken(DependencyParser.SWITCH, 0); }
		public DependItemContext dependItem() {
			return getRuleContext(DependItemContext.class,0);
		}
		public DependListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dependList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DependencyListener ) ((DependencyListener)listener).enterDependList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DependencyListener ) ((DependencyListener)listener).exitDependList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DependencyVisitor ) return ((DependencyVisitor<? extends T>)visitor).visitDependList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DependListContext dependList() throws RecognitionException {
		DependListContext _localctx = new DependListContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_dependList);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			match(INFO);
			setState(19);
			match(SWITCH);
			setState(20);
			dependItem();
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

	public static class DependItemContext extends ParserRuleContext {
		public DependentUnitContext dependentUnit() {
			return getRuleContext(DependentUnitContext.class,0);
		}
		public TerminalNode Tail() { return getToken(DependencyParser.Tail, 0); }
		public TerminalNode Identifier() { return getToken(DependencyParser.Identifier, 0); }
		public DependItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dependItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DependencyListener ) ((DependencyListener)listener).enterDependItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DependencyListener ) ((DependencyListener)listener).exitDependItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DependencyVisitor ) return ((DependencyVisitor<? extends T>)visitor).visitDependItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DependItemContext dependItem() throws RecognitionException {
		DependItemContext _localctx = new DependItemContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_dependItem);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(22);
			dependentUnit();
			{
			setState(23);
			match(T__0);
			setState(24);
			match(Identifier);
			}
			setState(26);
			match(Tail);
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

	public static class DependentUnitContext extends ParserRuleContext {
		public List<PackageNameContext> packageName() {
			return getRuleContexts(PackageNameContext.class);
		}
		public PackageNameContext packageName(int i) {
			return getRuleContext(PackageNameContext.class,i);
		}
		public VersionUnitContext versionUnit() {
			return getRuleContext(VersionUnitContext.class,0);
		}
		public List<TerminalNode> Identifier() { return getTokens(DependencyParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(DependencyParser.Identifier, i);
		}
		public DependentUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dependentUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DependencyListener ) ((DependencyListener)listener).enterDependentUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DependencyListener ) ((DependencyListener)listener).exitDependentUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DependencyVisitor ) return ((DependencyVisitor<? extends T>)visitor).visitDependentUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DependentUnitContext dependentUnit() throws RecognitionException {
		DependentUnitContext _localctx = new DependentUnitContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_dependentUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			packageName(0);
			setState(29);
			match(T__0);
			setState(30);
			packageName(0);
			setState(31);
			match(T__1);
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Identifier) {
				{
				{
				setState(32);
				match(Identifier);
				setState(33);
				match(T__0);
				}
				}
				setState(38);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(39);
			versionUnit();
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

	public static class VersionUnitContext extends ParserRuleContext {
		public List<TerminalNode> Number() { return getTokens(DependencyParser.Number); }
		public TerminalNode Number(int i) {
			return getToken(DependencyParser.Number, i);
		}
		public List<TerminalNode> Identifier() { return getTokens(DependencyParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(DependencyParser.Identifier, i);
		}
		public VersionUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_versionUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DependencyListener ) ((DependencyListener)listener).enterVersionUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DependencyListener ) ((DependencyListener)listener).exitVersionUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DependencyVisitor ) return ((DependencyVisitor<? extends T>)visitor).visitVersionUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VersionUnitContext versionUnit() throws RecognitionException {
		VersionUnitContext _localctx = new VersionUnitContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_versionUnit);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			match(Number);
			setState(46);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(42);
					match(T__2);
					setState(43);
					match(Number);
					}
					} 
				}
				setState(48);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			setState(53);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(49);
				match(T__2);
				setState(50);
				match(Identifier);
				}
				}
				setState(55);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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

	public static class PackageNameContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(DependencyParser.Identifier, 0); }
		public PackageNameContext packageName() {
			return getRuleContext(PackageNameContext.class,0);
		}
		public PackageNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_packageName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DependencyListener ) ((DependencyListener)listener).enterPackageName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DependencyListener ) ((DependencyListener)listener).exitPackageName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DependencyVisitor ) return ((DependencyVisitor<? extends T>)visitor).visitPackageName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PackageNameContext packageName() throws RecognitionException {
		return packageName(0);
	}

	private PackageNameContext packageName(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PackageNameContext _localctx = new PackageNameContext(_ctx, _parentState);
		PackageNameContext _prevctx = _localctx;
		int _startState = 10;
		enterRecursionRule(_localctx, 10, RULE_packageName, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(57);
			match(Identifier);
			}
			_ctx.stop = _input.LT(-1);
			setState(64);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new PackageNameContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_packageName);
					setState(59);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(60);
					match(T__2);
					setState(61);
					match(Identifier);
					}
					} 
				}
				setState(66);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 5:
			return packageName_sempred((PackageNameContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean packageName_sempred(PackageNameContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\nF\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\7\2\20\n\2\f\2\16\2\23\13\2\3\3"+
		"\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\7\5%\n\5"+
		"\f\5\16\5(\13\5\3\5\3\5\3\6\3\6\3\6\7\6/\n\6\f\6\16\6\62\13\6\3\6\3\6"+
		"\7\6\66\n\6\f\6\16\69\13\6\3\7\3\7\3\7\3\7\3\7\3\7\7\7A\n\7\f\7\16\7D"+
		"\13\7\3\7\2\3\f\b\2\4\6\b\n\f\2\2\2D\2\21\3\2\2\2\4\24\3\2\2\2\6\30\3"+
		"\2\2\2\b\36\3\2\2\2\n+\3\2\2\2\f:\3\2\2\2\16\20\5\4\3\2\17\16\3\2\2\2"+
		"\20\23\3\2\2\2\21\17\3\2\2\2\21\22\3\2\2\2\22\3\3\2\2\2\23\21\3\2\2\2"+
		"\24\25\7\b\2\2\25\26\7\t\2\2\26\27\5\6\4\2\27\5\3\2\2\2\30\31\5\b\5\2"+
		"\31\32\7\3\2\2\32\33\7\6\2\2\33\34\3\2\2\2\34\35\7\n\2\2\35\7\3\2\2\2"+
		"\36\37\5\f\7\2\37 \7\3\2\2 !\5\f\7\2!&\7\4\2\2\"#\7\6\2\2#%\7\3\2\2$\""+
		"\3\2\2\2%(\3\2\2\2&$\3\2\2\2&\'\3\2\2\2\')\3\2\2\2(&\3\2\2\2)*\5\n\6\2"+
		"*\t\3\2\2\2+\60\7\7\2\2,-\7\5\2\2-/\7\7\2\2.,\3\2\2\2/\62\3\2\2\2\60."+
		"\3\2\2\2\60\61\3\2\2\2\61\67\3\2\2\2\62\60\3\2\2\2\63\64\7\5\2\2\64\66"+
		"\7\6\2\2\65\63\3\2\2\2\669\3\2\2\2\67\65\3\2\2\2\678\3\2\2\28\13\3\2\2"+
		"\29\67\3\2\2\2:;\b\7\1\2;<\7\6\2\2<B\3\2\2\2=>\f\3\2\2>?\7\5\2\2?A\7\6"+
		"\2\2@=\3\2\2\2AD\3\2\2\2B@\3\2\2\2BC\3\2\2\2C\r\3\2\2\2DB\3\2\2\2\7\21"+
		"&\60\67B";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}