package unpsjb.fipm.gisfpp.controladores.staff;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.staff.ECargosStaffFi;
import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;
import unpsjb.fipm.gisfpp.servicios.staff.IServiciosStaffFI;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVCrudStaff {

	private IServiciosStaffFI servicio;
	private StaffFI item;
	private boolean creando = false;
	private boolean editando = false;
	private boolean ver = false;
	private String modo;
	private String titulo;
	private Logger log = UtilGisfpp.getLogger();

	@Init
	@NotifyChange({ "modo", "item", "titulo", "creando", "editando", "ver" })
	public void init() throws Exception {
		@SuppressWarnings("unchecked")
		final HashMap<String, Object> opciones = (HashMap<String, Object>) Sessions.getCurrent()
				.getAttribute(UtilGuiGisfpp.PRM_PNL_CENTRAL);
		modo = (String) opciones.get("modo");
		servicio = (IServiciosStaffFI) SpringUtil.getBean("servStaffFI");
		switch (modo) {
		case UtilGisfpp.MOD_NUEVO: {
			item = new StaffFI();
			titulo = "Nuevo miembro del Staff-FI";
			creando = true;
			editando = ver =false;
			break;
		}
		case UtilGisfpp.MOD_EDICION: {
			item = servicio.getInstancia((Integer) opciones.get("idItem"));
			editando = true;
			creando = ver = false;
			titulo = "Editar asociacion al Staff-FI";
			break;
		}
		case UtilGisfpp.MOD_VER:{
			item = servicio.getInstancia((Integer)opciones.get("idItem"));
			ver = true;
			creando = editando = false;
			titulo = "Ver asociacion";
		}
		}
	}

	@Command("nuevaAsociacion")
	@NotifyChange({ "item", "creando", "editando", "ver" })
	public void nuevaAsociacion() {
		item = new StaffFI();
		creando = true;
		editando = false;
		ver = false;
	}
	
	@Command("editar")
	@NotifyChange({"creando","editando","ver"})
	public void editar(){
		editando = true;
		creando = false;
		ver = false;
	}

	@Command("guardar")
	@NotifyChange({ "item","creando", "editando", "ver" })
	public void guardar() throws Exception {
		try {
			if(creando){
				servicio.persistir(item);
				Clients.showNotification("Nueva miembro del Staff guardado. ", Clients.NOTIFICATION_TYPE_INFO, null, "top_right",
						3500);
			}else if(editando){
				servicio.editar(item);
				Clients.showNotification("Se han guardado los cambios efectuados. ", Clients.NOTIFICATION_TYPE_INFO, null, "top_right",
						3500);
			}
			ver = true;
			creando = editando = false;
		}  catch (ConstraintViolationException cve) {
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

	@Command("cancelar")
	@NotifyChange({ "creando", "editando", "ver" })
	public void cancelar() {
			creando = false;
			editando = false;
			ver = true;
	}

	@Command("volver")
	public void volver() {
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlCrudStaff", "vistas/staff/listadoStaffFI.zul");
	}
	
	@Command("salir")
	public void salir(){
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/pnlCrudStaff");
	}

	@Command("verDlgLookupPersona")
	public void verDlgLookupPersona() {
		Window dlg = (Window) Executions.createComponents("vistas/persona/dlgLookupPersona.zul", null, null);
		dlg.doModal();
	}

	@GlobalCommand("obtenerLkpPersona")
	@NotifyChange("item")
	public void obtPersonaLookup(@BindingParam("seleccion") PersonaFisica arg1) {
		if (arg1 != null) {
			item.setMiembro(arg1);
		}
	}

	public StaffFI getItem() {
		return item;
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

	public String getModo() {
		return modo;
	}

	public String getTitulo() {
		return titulo;
	}

	public List<ECargosStaffFi> getCargos() {
		return Arrays.asList(ECargosStaffFi.values());
	}

}// fin de clase
