package unpsjb.fipm.gisfpp.controladores.proyecto;

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

import unpsjb.fipm.gisfpp.entidades.persona.Persona;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;
import unpsjb.fipm.gisfpp.entidades.persona.TIdentificador;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPF;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPJ;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class MVDlgLkpDemandante {
	
	private List<PersonaFisica> resultPF;
	private List<PersonaJuridica> resultPJ;
	private IServicioPJ servicioPJ;
	private IServicioPF servicioPF;
	private Logger log;
	private String mensjPF;
	private String mensjPJ;
	private Window thisDlg;
	
	@Init
	public void init(){
		servicioPF = (IServicioPF) SpringUtil.getBean("servPersonaFisica");
		servicioPJ = (IServicioPJ) SpringUtil.getBean("servPersonaJuridica");
		log = UtilGisfpp.getLogger();
		thisDlg = (Window) Path.getComponent("/dlgLkpDemandante");
		mensjPF ="Establezca los criterios de busqueda para realizar la consulta.";
		mensjPJ ="Establezca los criterios de busqueda para realizar la consulta.";
	}
	
	@Command("buscarPF")
	@NotifyChange({"resultPF","mensjPF"})
	public void buscarPF(@BindingParam("campoLkp") String campoLkp, @BindingParam("valorLkp") String valorLkp)
		throws Exception{
		Clients.showBusy(thisDlg,"Realizando busqueda...");
		try {
			switch (campoLkp) {
			case ("Nombre y Apellido"):{
				resultPF = servicioPF.getxNombre(valorLkp);
				if(resultPF == null || resultPF.isEmpty()){
					mensjPF="La consulta no arrojo ningun resultado, intentelo de nuevo.";
				}
				break;
			}	
			case("DNI"):{
				resultPF = servicioPF.getxIdentificador(TIdentificador.DNI, valorLkp);
				if(resultPF == null || resultPF.isEmpty()){
					mensjPF="La consulta no arrojo ningun resultado, intentelo de nuevo.";
				}	
				break;
			}
			case("CUIL"):{
				resultPF = servicioPF.getxIdentificador(TIdentificador.CUIL, valorLkp);
				if(resultPF == null || resultPF.isEmpty()){
					mensjPF="La consulta no arrojo ningun resultado, intentelo de nuevo.";
				}	
				break;
			}
			case("N° Legajo"):{
				resultPF = servicioPF.getxIdentificador(TIdentificador.LEGAJO, valorLkp);
				if(resultPF == null || resultPF.isEmpty()){
					mensjPF="La consulta no arrojo ningun resultado, intentelo de nuevo.";
				}	
				break;
			}
			case("N° Matricula"):{
				resultPF = servicioPF.getxIdentificador(TIdentificador.MATRICULA, valorLkp);
				if(resultPF == null || resultPF.isEmpty()){
					mensjPF="La consulta no arrojo ningun resultado, intentelo de nuevo";
				}	
				break;
			}
		}
		Clients.clearBusy(thisDlg);
		} catch (Exception e) {
			log.debug(this.getClass().getName(), e);
			Clients.clearBusy(thisDlg);
			throw e;
		}
	}
	
	@Command("buscarPJ")
	@NotifyChange({"resultPJ", "mensjPJ"})
	public void buscarPJ(@BindingParam("campoLkp") String campoLkp, @BindingParam("valorLkp") String valorLkp)
		throws Exception{
		Clients.showBusy(thisDlg,"Realizando busqueda...");
		try {
			switch (campoLkp) {
			case ("Razon Social"):{
				resultPJ = servicioPJ.getxNombre(valorLkp);
				if (resultPJ ==null || resultPJ.isEmpty()){
					mensjPJ ="La consulta no arrojo ningun resultado, intentelo de nuevo.";
				}
				break;
			}
			case("CUIT"):{
				resultPJ = servicioPJ.getxCuit(valorLkp);
				if(resultPJ == null || resultPJ.isEmpty()){
					mensjPJ ="La consulta no arrojo ningun resultado, intentelo de nuevo.";
				}
				break;
			}
		}
		Clients.clearBusy(thisDlg);	
		} catch (Exception e) {
			log.debug(this.getClass().getName(), e);
			Clients.clearBusy(thisDlg);
			throw e;
		}
	}
	
	@Command("seleccion")
	public void devolverSeleccion(@BindingParam("item") Persona itemSeleccionado){
		HashMap<String, Object> mapOpc = new HashMap<>();
		mapOpc.put("seleccion", itemSeleccionado);
		BindUtils.postGlobalCommand(null, null, "obtenerLkpDemandante", mapOpc);
		cerrarse();
	}
	
	private void cerrarse(){
			thisDlg.detach();
	}

	public List<PersonaFisica> getResultPF() {
		return resultPF;
	}

	public List<PersonaJuridica> getResultPJ() {
		return resultPJ;
	}

	public String getMensjPF() {
		return mensjPF;
	}

	public String getMensjPJ() {
		return mensjPJ;
	}
	
	
}//fin de la clase MVDlgLkpDemandante
