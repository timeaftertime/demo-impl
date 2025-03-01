package cn.milai.demoimpl.compiler.frontend;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		FrontendTestSuite.class, StringUtilTest.class, CompilerTest.class
})
public class AllTestSuite {

}
