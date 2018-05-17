package unpsjb.fipm.gisfpp.servicios;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/daoContext-test.xml",
		"file:src/main/webapp/WEB-INF/app-security.xml" })
public class TemplateTest {



	@Before
	public void setBefore() {
		}

	@Test
	public void test() {


	}

	@After
	public void setAfter() {
		
	}

}
