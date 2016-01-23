package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.impl.BindContextUtil;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.EEstadosIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class MVCrudIsfpp {

	private Logger log;
	private Isfpp item;
	private IServiciosIsfpp servicio;
	private SubProyecto perteneceA;
	private String modo;
	private boolean creando;
	private boolean editando;
	private boolean ver;
	private HashMap<String, Object> args;

	@Init
	@NotifyChange({ "modo", "item", "creando", "editando", "ver" })
	public void init() throws Exception {
		log = UtilGisfpp.getLogger();
		servicio = (IServiciosIsfpp) SpringUtil.getBean("servIsfpp");
		args = (HashMap<String, Object>) Executions.getCurrent().getAttribute("argsCrudIsfpp");
		perteneceA = (SubProyecto) args.get("perteneceA");
		modo = (String) args.get("modo");
		switch (modo) {
		case UtilGisfpp.MOD_NUEVO: {
			item = new Isfpp(perteneceA, "", "", new Date(), new Date(), "",null, null);
			creando = true;
			editando = (ver = false);
			break;
		}
		case UtilGisfpp.MOD_EDICION: {
			item = servicio.getInstancia((Integer) args.get("idItem"));
			item.setPerteneceA(perteneceA);
			creando = (ver = false);
			editando = true;
			break;
		}
		case UtilGisfpp.MOD_VER: {
			item = servicio.getInstancia((Integer) args.get("idItem"));
			item.setPerteneceA(perteneceA);
			creando = (editando = false);
			ver = true;
		}
		}

	}

	public Isfpp getItem() {
		return item;
	}

	public String getModo() {
		return modo;
	}

	public boolean isCreando() {
		return creando;
	}

	public boolean isEditando() {
		return editando;
	}

	public boolean isVer() {
		return ver;
	}

	public List<EEstadosIsfpp> getEstados() {
		return Arrays.asList(EEstadosIsfpp.values());
	}

	@Command("guardar")
	@NotifyChange({ "item", "creando", "editando", "ver" })
	public void guardar() throws Exception {
		try {
			if (creando) {
				servicio.persistir(item);
				Clients.showNotification("Nueva ISFPP guardada", Clients.NOTIFICATION_TYPE_INFO, null, "top_right",
						3500);
			}
			if (editando) {
				servicio.editar(item);
				Clients.showNotification("ISFPP actualizada.", Clients.NOTIFICATION_TYPE_INFO, null,
						"top_right", 3500);
			}
			creando = editando = false;
			ver=true;
		} catch (ConstraintViolationException cve) {
			Messagebox.show(UtilGisfpp.getMensajeValidations(cve), "Error: Validación de datos.", Messagebox.OK,
					Messagebox.ERROR);
		} catch (DataIntegrityViolationException | org.hibernate.exception.ConstraintViolationException dive) {
			Messagebox.show(dive.getMessage(), "Error: Violacion Restricciones de Integridad BD.", Messagebox.OK,
					Messagebox.ERROR);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Command("nuevaIsfpp")
	@NotifyChange({ "item", "creando", "editando", "ver" })
	public void nuevaIsfpp() {
		item = new Isfpp(perteneceA, "", "", new Date(), new Date(), "",null, null);
		creando = true;
		editando = (ver = false);
	}

	@Command("editarIsfpp")
	@NotifyChange({ "creando", "editando", "ver" })
	public void editar() {
		editando = true;
		creando = (ver = false);
	}

	@Command("cancelar")
	@NotifyChange({ "item", "creando", "editando", "ver" })
	public void cancelar() {
		creando = (editando = false);
		ver = true;
	}

	@Command("salir")
	public void salir() {
		Map<String, Object> map = new HashMap<>();
		if(modo.equals(UtilGisfpp.MOD_NUEVO) || modo.equals(UtilGisfpp.MOD_EDICION)){
			map.put("actualizar", true);
		}else{
			map.put("actualizar", false);
		}
		BindUtils.postGlobalCommand(null, null, "cerrandoTab", map);
		Tab tab = (Tab) args.get("tab");
		tab.close();
	}
	
	@Command("verDlgStaff")
	public void verDlgStaff(@BindingParam("modo") String arg1, @BindingParam("itemStaff") MiembroStaffIsfpp arg2){
		Map<String, Object> args = new HashMap<>();
		args.put("modo", arg1);
		args.put("item", arg2);
		args.put("de", item);
		Window dlg = (Window) Executions.createComponents("vistas/proyecto/dlgStaffIsfpp.zul", null, args);
		dlg.doModal();
	}
	
	@GlobalCommand("retornoDlgStaffIsfpp"	)
	@NotifyChange("item")
	public void retornoDlgStaffIsfpp(@BindingParam("modo")String arg1, @BindingParam("newItem") MiembroStaffIsfpp arg2){
		if(arg1.equals(UtilGisfpp.MOD_NUEVO)){
			item.getStaff().add(arg2);
		}
		Clients.showNotification("Guarde cambios para confirmar la operacion.", Clients.NOTIFICATION_TYPE_WARNING, null, 
				"top_right", 4000);
	}
	
	@Command("quitarMiembroSatff")
	@NotifyChange("item")
	public void quitarMiembroStaff(@BindingParam("itemStaff") MiembroStaffIsfpp arg1){
		item.getStaff().remove(arg1);
		Clients.showNotification("Guarde cambios para confirmar la operacion.", Clients.NOTIFICATION_TYPE_WARNING, null, 
				"top_right", 4000);
	}

}// fin de la clase
