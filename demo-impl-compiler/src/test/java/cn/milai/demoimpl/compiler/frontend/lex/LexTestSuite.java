package cn.milai.demoimpl.compiler.frontend.lex;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cn.milai.demoimpl.compiler.frontend.lex.acceptor.CharAcceptorTestSuite;

@RunWith(Suite.class)
@SuiteClasses({ NFABuilderTest.class, DFABuilderTest.class, LexerTest.class, CharSetsTest.class,
		CharAcceptorTestSuite.class, NodeUtilTest.class })
public class LexTestSuite {

}
