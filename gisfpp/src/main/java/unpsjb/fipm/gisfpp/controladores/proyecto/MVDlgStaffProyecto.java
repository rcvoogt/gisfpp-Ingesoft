package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.ERolStaffProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffProyecto;
import unpsjb.fipm.gisfpp.servicios.staff.IServiciosStaffFI;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class MVDlgStaffProyecto {
	
	private MiembroStaffProyecto item;
	private IServiciosStaffFI servStaffFi;
	private List<PersonaFisica> staffFi;
	private Map<String, Object> args;
	private Window thisDlg;
	private String titulo;
	private String modo;
	private Logger log;	
	
	@Init
	@NotifyChange({"item","titulo"})
	public void init() throws Exception{
		try {
			log = UtilGisfpp.getLogger();
			servStaffFi = (IServiciosStaffFI) SpringUtil.getBean("servStaffFI");
			staffFi = servStaffFi.getListadoStaffPersonas();
			thisDlg = (Window) Path.getComponent("/dlgStaffProyecto");
			args = (Map<String, Object>) Executions.getCurrent().getArg();
			modo = (String) args.get("modo");
			if(modo.equals(UtilGisfpp.MOD_NUEVO)){
				item = new MiembroStaffProyecto();
				titulo = "Nuevo miembro Staff del Proyecto";
			}else if(modo.equals(UtilGisfpp.MOD_EDICION)){
				MiembroStaffProyecto original = (MiembroStaffProyecto) args.get("itemStaff");
				item = new MiembroStaffProyecto(null, original.getMiembro(), original.getRol());
				titulo = "Editando miembro Staff del Proyecto";
			}
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
		
	}
	
	@Command("aceptar")
	public void aceptar(){
		Map<String, Object> argsRetorno = new HashMap<>();
		argsRetorno.put("modo", modo);
		if (modo.equals(UtilGisfpp.MOD_EDICION)){
			MiembroStaffProyecto original = (MiembroStaffProyecto) args.get("itemStaff");
			original.setRol(item.getRol());
		}else{
			argsRetorno.put("newItem", item);
		}
		BindUtils.postGlobalCommand(null, null, "retornoDlgStaffProyecto", argsRetorno);
		thisDlg.detach();
	}
	
	@Command("cancelar")
	public void cancelar(){
		thisDlg.detach();
	}

	public MiembroStaffProyecto getItem() {
		return item;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getModo() {
		return modo;
	}
	
	public List<PersonaFisica> getStaffFI(){
		return staffFi;
	}
	
	public List<ERolStaffProyecto> getRolesStaff(){
		return Arrays.asList(ERolStaffProyecto.values());
	}

}//fin de la clase
