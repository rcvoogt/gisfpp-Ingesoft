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
import unpsjb.fipm.gisfpp.entidades.proyecto.ERolStaffIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffProyecto;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPF;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class MVDlgStaffIsfpp {

	private MiembroStaffIsfpp item;
	private MiembroStaffIsfpp original;
	private Isfpp de;
	private IServicioPF servicioPF;
	private List<PersonaFisica> listaPF;
	private List<PersonaFisica> listaPFStaffProyecto;
	private List<PersonaFisica> listaPersonas;
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
			listaPF = servicioPF.getListado();
			thisDlg= (Window) Path.getComponent("/dlgStaffIsfpp");
			args = (Map<String, Object>) Executions.getCurrent().getArg();
			modo = (String) args.get("modo");
			de = (Isfpp) args.get("de");
			listaPFStaffProyecto();
			if(modo.equals(UtilGisfpp.MOD_NUEVO)){
				item=new MiembroStaffIsfpp(de, null, null);
				titulo="Nuevo miembro Staff Isfpp";
			}else {
				original = (MiembroStaffIsfpp) args.get("item");
				item = new MiembroStaffIsfpp(null, original.getMiembro(), original.getRol());
				onChangeCombxRol(original.getRol());
				titulo = "Editando miembro Staff Isfpp";
			}
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	//Mediante este método obtengo una lista de objetos "PersonaFisica"
	//que representa el listado de Personas que conforman el staff del 
	//Proyecto al cual pertenece la Isfpp.
	private void listaPFStaffProyecto(){
		listaPFStaffProyecto = new ArrayList<>();
		for (MiembroStaffProyecto miembro : de.getPerteneceA().getPerteneceA().getStaff()) {
			listaPFStaffProyecto.add(miembro.getMiembro());
		}
	}
	
	public MiembroStaffIsfpp getItem() {
		return item;
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

	public List<ERolStaffIsfpp> getRolesStaff(){
		return 	Arrays.asList(ERolStaffIsfpp.values());
	}
	
	@Command("aceptar")
	public void aceptar(){
		Map<String, Object> argsRetorno = new HashMap<>();
		argsRetorno.put("modo", modo);
		if (modo.equals(UtilGisfpp.MOD_EDICION)){
			original.setRol(item.getRol());
		}else{
			argsRetorno.put("newItem", item);
		}
		BindUtils.postGlobalCommand(null, null, "retornoDlgStaffIsfpp", argsRetorno);
		thisDlg.detach();
	}
	
	@Command("cancelar")
	public void cancelar(){
		thisDlg.detach();
	}
	
	@Command("onChangeCmbxRol")
	@NotifyChange("listaPersonas")
	public void onChangeCombxRol(@BindingParam("rol") ERolStaffIsfpp arg1){
		if (arg1 == ERolStaffIsfpp.TUTOR_ACADEMICO){
			listaPersonas = listaPFStaffProyecto;
		}else {
			listaPersonas = listaPF;
		}
	}
}//fin de la clase
