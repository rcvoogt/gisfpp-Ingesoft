package unpsjb.fipm.gisfpp.controladores.integracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("contIntegracion")
public class ControladorIntegracion implements IControladorIntegracion{
	
	@Autowired
	ControladorPersonas controladorPersonas;
	@Autowired
	ControladorMaterias controladorMaterias;
	@Autowired
	ControladorCursadas controladorCursadas;

	public ControladorIntegracion() {
		super();
	}

	public void init() {

	}

	

	public void migrar() {
		try {
			controladorPersonas.integrarPersonas();
			controladorMaterias.integrarMaterias();
			controladorCursadas.integrarCursadas();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
	
}
