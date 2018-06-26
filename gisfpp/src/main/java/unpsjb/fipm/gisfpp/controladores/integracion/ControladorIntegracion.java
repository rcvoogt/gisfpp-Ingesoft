package unpsjb.fipm.gisfpp.controladores.integracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControladorIntegracion {
	
	@Autowired
	ControladorPersonas controladorPersonas;


	public ControladorIntegracion() {
		super();
	}

	public void init() {

	}

	

	public void migrar() {
		try {
			controladorPersonas.integrarPersonas();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
	
}
