package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tests.ArticleTests;
import tests.MyListsTests;
import tests.SearchTests;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        ArticleTests.class,
        MyListsTests.class,
        SearchTests.class
})

public class TestSuite {
}