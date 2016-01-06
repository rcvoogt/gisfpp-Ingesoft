package unpsjb.fipm.gisfpp.controladores.persona;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.jca.cci.object.EisOperation;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Path;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.TIdentificador;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPF;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class MVDlgLkpPersona {

	private List<PersonaFisica> resultado;
	private IServicioPF servicio;
	private String campoLookup="";
	private String valorLookup = "";
	private Logger log;
	private String mensaje;

	@Init
	@NotifyChange("lista")
	public void init() throws Exception {
		servicio = (IServicioPF) SpringUtil.getBean("servPersonaFisica");
		log = UtilGisfpp.getLogger();
		mensaje = "Establezca los criterios de busqueda para efectuar la consulta.";
	}	
	
	@Command("buscar")
	@NotifyChange({"resultado","mensaje"})
	public void buscar() throws Exception {
		try {
			switch (campoLookup) {
			case ("Nombre y Apellido"): {
				resultado = servicio.getxNombre(valorLookup);
				if(resultado==null || resultado.isEmpty()){
					mensaje="La consulta no arrojo ningun resultado.";
				}
				break;
			}
			case ("DNI"): {
				resultado = servicio.getxIdentificador(TIdentificador.DNI.name(), valorLookup);
				if(resultado==null || resultado.isEmpty()){
					mensaje="La consulta no arrojo ningun resultado.";
				}
				break;
			}
			case ("CUIL"): {
				resultado = servicio.getxIdentificador(TIdentificador.CUIL.name(), valorLookup);
				break;
			}
			case("N° Legajo"):{
				resultado =servicio.getxIdentificador(TIdentificador.LEGAJO.name(), valorLookup);
				if(resultado==null || resultado.isEmpty()){
					mensaje="La consulta no arrojo ningun resultado.";
				}
				break;
			}
			case("N° Matricula"):{
				resultado = servicio.getxIdentificador(TIdentificador.MATRICULA.name(), valorLookup);
				if(resultado==null || resultado.isEmpty()){
					mensaje="La consulta no arrojo ningun resultado.";
				}
				break;
			}
			}
		} catch (Exception e) {
			log.debug(this.getClass().getName(), e);
			throw e;
		}
	}

	// En cada parte del sistema que se utilice este DlgLkpPersona se deberá
	// implementar un command global con nombre "obtenerLkpPersona" para recibir
	// la
	// persona seleccionada
	@Command("seleccion")
	public void devolverSeleccion(@BindingParam("item") PersonaFisica persona) throws Exception {
		final HashMap<String, Object> parametros = new HashMap<>();
		try {
			//Recuperamos todos los datos asociados a la persona seleccionada,
			//por eso esta consulta.
			PersonaFisica item = servicio.getInstancia(persona.getId());
			parametros.put("seleccion", item);
			BindUtils.postGlobalCommand(null, null, "obtenerLkpPersona", parametros);
			cerrar();
		} catch (Exception e) {
			log.debug(this.getClass().getName(), e);
			throw e;
		}
	};

	private void cerrar() {
		Window dlg = (Window) Path.getComponent("/dlgLkpPersona");
		dlg.detach();
	}

	public String getCampoLookup() {
		return campoLookup;
	}

	@NotifyChange("campoLookup")
	public void setCampoLookup(String campoLookup) {
		this.campoLookup = campoLookup;
	}

	public String getValorLookup() {
		return valorLookup;
	}

	public void setValorLookup(String valorLookup) {
		this.valorLookup = valorLookup;
	}

	public List<PersonaFisica> getResultado() {
		return resultado;
	}

	public String getMensaje() {
		return mensaje;
	}
			
}// fin de la clase
