package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.Persona;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;
import unpsjb.fipm.gisfpp.entidades.proyecto.ERolStaffIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.util.MySpringUtil;

public class MVDlgAsignarTutorExterno {
	
	private IServiciosIsfpp servIsfpp;
	private Proyecto proyecto;
	private Isfpp isfpp;
	private Object origen;
		
	@Init
	public void init() throws Exception {
		Map<String, Object> args = (Map<String, Object>) Executions.getCurrent().getArg();
		isfpp = (Isfpp) args.get("isfpp");
		origen = args.get("origen");
		
		servIsfpp = MySpringUtil.getServicioIsfpp();
		
		proyecto = servIsfpp.getPerteneceAProyecto(isfpp.getId());
		
	}
	
	public Proyecto getProyecto(){
		return proyecto;	
	}
	
	public List<PersonaFisica> getPosiblesTutores(){
		List<PersonaFisica> resultado = new ArrayList<PersonaFisica>();
		
		for (Persona demandante : proyecto.getDemandantes()) {
			if (demandante instanceof PersonaFisica) {
				resultado.add((PersonaFisica)demandante);
			}else{
				for (PersonaFisica contacto : ((PersonaJuridica)demandante).getContactos()) {
					resultado.add(contacto);
				}
			}
		}
		return resultado;
	}
	
	@Command("asignar")
	public void asignarTutorExterno(@BindingParam("item") PersonaFisica arg1) throws Exception{
		try{
			if (arg1 == null) {
				Clients.alert("Seleccione una persona de la lista por favor", "Asignando Tutor Externo", 
						Clients.NOTIFICATION_TYPE_ERROR);
				return;
			}
			isfpp.addMiembroStaff(new MiembroStaffIsfpp(isfpp,arg1, ERolStaffIsfpp.TUTOR_EXTERNO));
			servIsfpp.editar(isfpp);
			Clients.showNotification("Tutor Externo asignado a la Isfpp", Clients.NOTIFICATION_TYPE_INFO, null, 
					"top_right", 3500);
			if (origen!=null) {
				BindUtils.postNotifyChange(null, null, origen, "*");
			}
		}finally{
			cerrar();
		}
	}
	
	@Command("cerrar")
	public void cerrar(){
		Window dlg = (Window) Path.getComponent("/dlgAsignarTutorExt");
		dlg.detach();
	}

}
