package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.TIdentificador;
import unpsjb.fipm.gisfpp.entidades.proyecto.Convocado;
import unpsjb.fipm.gisfpp.entidades.proyecto.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosConvocatoria;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class MVDlgLkpPracticante {

	private List<Convocado> resultado;
	private IServiciosConvocatoria servicio;
	private String campoLookup="";
	private String valorLookup = "";
	private Logger log;
	private String mensaje;
    private List<Convocado> asignados;
    private Isfpp isfpp;
    
	@Init
	@NotifyChange("resultado")
	public void init() throws Exception {
		int idConvocatoria = 1;
		servicio = (IServiciosConvocatoria) SpringUtil.getBean("servConvocatoria");
		log = UtilGisfpp.getLogger();
		mensaje = "Establezca los criterios de busqueda para efectuar la consulta.";
		Convocatoria c = servicio.getInstancia(idConvocatoria);
		isfpp = c.getIsfpp();
		
	    asignados = new ArrayList<Convocado>();
	   
		resultado = servicio.getConvocadosAceptadores(idConvocatoria);

	}	
	
//	@Command("buscar")
//	@NotifyChange({"resultado","mensaje"})
//	public void buscar() throws Exception {
//		try {
//			switch (campoLookup) {
//			case ("Nombre y Apellido"): {
//				Clients.showBusy("Ejecutando consulta...");
//				resultado = servicioPF.getxNombre(valorLookup);
//				if(resultado==null){
//					mensaje="La consulta no arrojo ningun resultado.";
//				}
//				Clients.clearBusy();
//				break;
//			}
//			case ("DNI"): {
//				Clients.showBusy("Ejecutando consulta...");
//				resultado = servicio.getxIdentificador(TIdentificador.DNI, valorLookup);
//				if(resultado==null){
//					mensaje="La consulta no arrojo ningun resultado.";
//				}
//				Clients.clearBusy();
//				break;
//			}
//			case ("CUIL"): {
//				Clients.showBusy("Ejecutando consulta...");
//				resultado = servicio.getxIdentificador(TIdentificador.CUIL, valorLookup);
//				if(resultado == null){
//					mensaje="La consulta no arrojo ningun resultado.";
//				}
//				Clients.clearBusy();
//				break;
//			}
//			case("N° Legajo"):{
//				Clients.showBusy("Ejecutando consulta...");
//				resultado =servicio.getxIdentificador(TIdentificador.LEGAJO, valorLookup);
//				if(resultado==null){
//					mensaje="La consulta no arrojo ningun resultado.";
//				}
//				Clients.clearBusy();
//				break;
//			}
//			case("N° Matricula"):{
//				Clients.showBusy("Ejecutando consulta...");
//				resultado = servicio.getxIdentificador(TIdentificador.MATRICULA, valorLookup);
//				if(resultado==null){
//					mensaje="La consulta no arrojo ningun resultado.";
//				}
//				Clients.clearBusy();
//				break;
//			}
//			}
//		} catch (Exception e) {
//			log.debug(this.getClass().getName(), e);
//			Clients.clearBusy();
//			throw e;
//		}
//	}

	// persona seleccionada
	@Command("seleccion")
	public void devolverSeleccion(@BindingParam("item") Convocado persona) throws Exception {
		persona.setAceptado(true);
		asignados.add(persona);

		final HashMap<String, Object> parametros = new HashMap<>();
		try {
			PersonaFisica personaF = (PersonaFisica) persona.getPersona();
			parametros.put("seleccion", personaF);
			BindUtils.postGlobalCommand(null, null, "obtenerLkpPersona", parametros);
			finalizar();
		} catch (Exception e) {
			log.debug(this.getClass().getName(), e);
			throw e;
		}
	
	};

	@Command("finalizar")
	public void finalizar() {
		for(Convocado c : asignados){
			isfpp.addPracticante(c.getPersona());
		}
		Window dlg = (Window) Path.getComponent("/dlgLkpPracticante");
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

	public List<Convocado> getResultado() {
		return resultado;
	}

	public String getMensaje() {
		return mensaje;
	}
			
}// fin de la clase
