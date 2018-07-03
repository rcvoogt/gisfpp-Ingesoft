package unpsjb.fipm.gisfpp.controladores.integracion;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

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

	

	public void migrar() throws Exception {
	
			controladorPersonas.integrarPersonas();
			controladorMaterias.integrarMaterias();
			controladorCursadas.integrarCursadas();
		
	}


	
	
}
