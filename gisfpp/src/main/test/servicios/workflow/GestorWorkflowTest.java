package servicios.workflow;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosConvocatoria;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflow;
import unpsjb.fipm.gisfpp.util.MySpringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/daoContext-test.xml",
		/*"file:src/main/webapp/WEB-INF/serviciosContext.xml",*/
		"file:src/main/webapp/WEB-INF/app-security.xml",/*
		"file:src/main/webapp/WEB-INF/activiti-config.xml"*/
})
public class GestorWorkflowTest {
	@Autowired
	GestorWorkflow srvWorkflow;
	@Autowired
    IServiciosConvocatoria servConvocatoria;

	
	
	@Before
	public void setBefore() {
	}

	@Test
	void testGetDefinicionProcesos() {
		//this.srvWorkflow = MySpringUtil.getServicioGestorWkFl();
		assertNotNull(srvWorkflow.getDefinicionProcesos());
	}

}
