package cn.milai.demoimpl.compiler.frontend;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cn.milai.demoimpl.compiler.frontend.lex.LexTestSuite;
import cn.milai.demoimpl.compiler.frontend.parsing.ParsingTestSuite;

@RunWith(Suite.class)
@SuiteClasses({ LexTestSuite.class, ParsingTestSuite.class, StringUtilTest.class })
public class FrontendTestSuite {

}
