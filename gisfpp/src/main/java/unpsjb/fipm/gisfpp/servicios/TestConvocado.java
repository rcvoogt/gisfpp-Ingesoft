package unpsjb.fipm.gisfpp.servicios;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.BaseMatcher.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import unpsjb.fipm.gisfpp.util.MySpringUtil;
import unpsjb.fipm.gisfpp.entidades.proyecto.ERespuestaConvocado;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosConvocado;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosConvocatoria;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/daoContext-test.xml",
		/*"file:src/main/webapp/WEB-INF/serviciosContext.xml",*/
		"file:src/main/webapp/WEB-INF/app-security.xml"/*,
		"file:src/main/webapp/WEB-INF/activiti-config.xml"*/
})
public class TestConvocado {
    @Autowired
    private IServiciosConvocatoria servConvocatoria;
    Integer idConvocado = new Integer(16);
    
    
    @Test
    public void testSampleService() {
        try {
			assertEquals(
			        new Integer(16),
			        this.servConvocatoria.getInstancia(idConvocado).getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
}