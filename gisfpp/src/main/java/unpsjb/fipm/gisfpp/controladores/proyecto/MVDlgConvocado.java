package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.Convocado;
import unpsjb.fipm.gisfpp.entidades.proyecto.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.proyecto.ERolStaffIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffProyecto;
import unpsjb.fipm.gisfpp.entidades.staff.ECargosStaffFi;
import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPF;
import unpsjb.fipm.gisfpp.servicios.staff.IServiciosStaffFI;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class MVDlgConvocado {

	private StaffFI item;
	private Convocado original;
	private Convocatoria de;
	private IServicioPF servicioPF;
	private IServiciosStaffFI servicioStaff;
	private List<PersonaFisica> listaPF;
	private List<PersonaFisica> listaPFConvocados;
	private List<PersonaFisica> listaPersonas;
	private List<StaffFI> listaStaff;
	
	
	private Map<String, Object> args;
	private Window thisDlg;
	private String modo;
	private String titulo;
	private Logger log;
	
	@Init
	@NotifyChange({"item", "titulo", "listaPersonas"})
	public void init() throws Exception{
		try {
			log = UtilGisfpp.getLogger();
			listaPersonas = new ArrayList<>();
			servicioPF = (IServicioPF) SpringUtil.getBean("servPersonaFisica");
			servicioStaff = (IServiciosStaffFI) SpringUtil.getBean("servStaffFI");
			listaStaff = servicioStaff.getMiembroPorRol(ECargosStaffFi.ALUMNO);
			thisDlg= (Window) Path.getComponent("/dlgConvocado");
			args = (Map<String, Object>) Executions.getCurrent().getArg();
			modo = (String) args.get("modo");
			de = (Convocatoria) args.get("de");
			if(modo.equals(UtilGisfpp.MOD_NUEVO)){
				//item=new Convocado( de, persona);
				item=new StaffFI();
				titulo="Nuevo convocado";
			}
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	
	
	public StaffFI getItem() {
		return item;
	}
	
	public void setItem(StaffFI item) {
		this.item = item;
	}

	public List<PersonaFisica> getListaPersonas() {
		return listaPersonas;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public String getModo() {
		return modo;
	}

	@Command("aceptarOtro")
	@NotifyChange("item")
	public void aceptarOtro(){
		this.item = null; //Setear el seleccionado como null!!!!! 
		Map<String, Object> argsRetorno = new HashMap<>();
		argsRetorno.put("modo", modo);
		argsRetorno.put("newItem", item);
		
		BindUtils.postGlobalCommand(null, null, "retornoDlgConvocado", argsRetorno);
	
	}
	
	@Command("aceptar")
	public void aceptar(){
		
		Map<String, Object> argsRetorno = new HashMap<>();
		argsRetorno.put("modo", modo);
		argsRetorno.put("newItem", new Convocado(de,item.getMiembro()));
		
		BindUtils.postGlobalCommand(null, null, "retornoDlgConvocado", argsRetorno);
		thisDlg.detach();
	}
	
	@Command("cancelar")
	public void cancelar(){
		thisDlg.detach();
	}



	public List<StaffFI> getListaStaff() {
		return listaStaff;
	}



	public void setListaStaff(List<StaffFI> listaStaff) {
		this.listaStaff = listaStaff;
	}
	
	
	
}//fin de la clase
