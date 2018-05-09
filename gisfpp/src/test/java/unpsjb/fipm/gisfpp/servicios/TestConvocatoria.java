package unpsjb.fipm.gisfpp.servicios;


import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import unpsjb.fipm.gisfpp.servicios.convocatoria.IServiciosConvocatoria;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/daoContext-test.xml",
		/*"file:src/main/webapp/WEB-INF/serviciosContext.xml",*/
		"file:src/main/webapp/WEB-INF/app-security.xml"/*,
		"file:src/main/webapp/WEB-INF/activiti-config.xml"*/
})
public class TestConvocatoria {
    @Autowired
    private IServiciosConvocatoria servConvocatoria;
    Integer idConvocado = new Integer(16);
    
    
    
    @Test
    public void testGetInstancia() {
        try {
			assertEquals(
			        new Integer(16),
			        this.servConvocatoria.getInstancia(idConvocado).getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    public void testPersistir() {
    	
    }
    
    //Solo puedo probar si agrego uno, que muestre la cantidad que habia mas uno dado que no conozco los datos existentes en la base
    @Test
    public void testGetListado() {

    }

    
    

    
}