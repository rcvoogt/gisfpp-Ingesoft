package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.Arrays;
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
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.Button;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocable;
import unpsjb.fipm.gisfpp.entidades.persona.Persona;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.EstadoProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.TipoProyecto;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServicioSubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosProyecto;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

public class MVCrudProyecto {

	private IServiciosProyecto servicio;
	private IServicioUsuario servUsuario;
	private Proyecto item;
	private Logger log;
	private boolean creando = false;
	private boolean editando = false;
	private boolean ver = false;
	private boolean existeConvocatoriaAbierta;
	private String modo = null;
	private String titulo = " ";

	@Init
	@NotifyChange({ "modo", "creando", "editando", "ver", "titulo", "item" })
	public void init() throws Exception {
		log = UtilGisfpp.getLogger();
		servicio = (IServiciosProyecto) SpringUtil.getBean("servProyecto");
		servUsuario = (IServicioUsuario) SpringUtil.getBean("servUsuario");
		@SuppressWarnings("unchecked")
		final HashMap<String, Object> map = (HashMap<String, Object>) Sessions.getCurrent()
				.getAttribute(UtilGuiGisfpp.PRM_PNL_CENTRAL);
		modo = (String) map.get("modo");
		if (modo.equals(UtilGisfpp.MOD_NUEVO)) {
			item = new Proyecto("", "", "", "", null, null, null, "", null, null, null);
			creando = true;
			editando = false;
			ver = false;
			titulo = "Nuevo Proyecto";
			existeConvocatoriaAbierta = false;
		} else if (modo.equals(UtilGisfpp.MOD_EDICION)) {
			item = servicio.getInstancia((Integer) map.get("idItem"));
			editando = true;
			creando = ver = false;
			titulo = "Editando Proyecto: " + item.getCodigo() + " - " + item.getTitulo();
			existeConvocatoriaAbierta = puedeCrearConvocatoria();
		} else if (modo.equals(UtilGisfpp.MOD_VER)) {
			item = servicio.getInstancia((Integer) map.get("idItem"));
			ver = true;
			creando = editando = false;
			titulo = "Ver Proyecto: " + item.getCodigo() + " - " + item.getTitulo();
		}

	}

	private boolean puedeCrearConvocatoria() {
		/*
		 * TODO: Agregar validacion con respecto al estado del proyecto y las fechas del
		 * proyecto
		 */

		/*
		 * if (!(item.getEstado().equals(EstadoProyecto.ACTIVO) ||
		 * item.getEstado().equals(EstadoProyecto.GENERADO)) ) return true; //
		 * if(item.getFecha_fin().equals(new Date()) || item.getFecha_fin().before(new
		 * Date())) // return true; if (item.getConvocatorias().size() > 0) { for
		 * (Convocatoria convocatoria : item.getConvocatorias()) { // Si hay una
		 * convocatoria con fecha de vencimiento posterior al // dia de hoy, es que
		 * est� activa if (convocatoria.getFechaVencimiento().after(new Date())) {
		 * return true; } } }
		 */
		return false;
	}

	public boolean isExisteConvocatoriaAbierta() {
		return existeConvocatoriaAbierta;
	}

	public void setExisteConvocatoriaAbierta(boolean existeConvocatoriaAbierta) {
		this.existeConvocatoriaAbierta = existeConvocatoriaAbierta;
	}

	public String getTitulo() {
		return titulo;
	}

	public List<TipoProyecto> getTipos() {
		return Arrays.asList(TipoProyecto.values());
	}

	public List<EstadoProyecto> getEstados() {
		return Arrays.asList(EstadoProyecto.values());
	}

	public String getModo() {
		return modo;
	}

	public Proyecto getItem() {
		return item;
	}

	public Boolean getCreando() {
		return creando;
	}

	public Boolean getEditando() {
		return editando;
	}

	public Boolean getVer() {
		return ver;
	}

	@Command("nuevoProyecto")
	@NotifyChange({ "item", "creando", "modo" })
	public void nuevoProyecto() {
		item = new Proyecto("", "", "", "", null, null, null, "", null, null, null);
		creando = true;
		editando = false;
		ver = false;
		modo = UtilGisfpp.MOD_NUEVO;
	}

	@Command("editar")
	@NotifyChange({ "creando", "editando", "ver", "modo" })
	public void reEditar() {
		editando = true;
		creando = false;
		ver = false;
		modo = UtilGisfpp.MOD_EDICION;
	}

	@Command("guardar")
	@NotifyChange({ "creando", "editando", "ver", "item" })
	public void guardar() throws Exception {
		try {
			if (creando) {
				servicio.persistir(item);
				Clients.showNotification("Nuevo Proyecto guardado. ", Clients.NOTIFICATION_TYPE_INFO, null, "top_right",
						3500);
			} else if (editando) {
				servicio.editar(item);
				Clients.showNotification("Proyecto actualizado.", Clients.NOTIFICATION_TYPE_INFO, null, "top_right",
						3500);
			}
			creando = (editando = false);
			ver = true;
		} catch (ConstraintViolationException cve) {
			Messagebox.show(UtilGisfpp.getMensajeValidations(cve), "Error: Validaci�n de datos.", Messagebox.OK,
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
	@NotifyChange({ "creando", "editando", "ver", "modo" })
	public void cancelar() {
		creando = false;
		editando = false;
		ver = true;
		modo = UtilGisfpp.MOD_VER;
	}

	@Command("volver")
	public void volver() {
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlCrudProyecto", "vistas/proyecto/listadoProyectos.zul");
	}

	@Command("salir")
	public void salir() {
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/pnlCrudProyecto");
	}

	@Command("nuevoSP")
	public void nuevoSP() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("perteneceA", item);
		map.put("modo", UtilGisfpp.MOD_NUEVO);
		map.put("volverA", "/vistas/proyecto/crudProyecto.zul");
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlCrudProyecto", "/vistas/proyecto/crudSubProyecto.zul", map);
	}

	@Command("verSP")
	public void verSp(@BindingParam("idItem") Integer idItem) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("perteneceA", item);
		map.put("idItem", idItem);
		map.put("modo", UtilGisfpp.MOD_VER);
		map.put("volverA", "/vistas/proyecto/crudProyecto.zul");
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlCrudProyecto", "/vistas/proyecto/crudSubProyecto.zul", map);
	}

	@Command("editarSP")
	public void editarSP(@BindingParam("idItem") Integer idItem) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("perteneceA", item);
		map.put("idItem", idItem);
		map.put("modo", UtilGisfpp.MOD_EDICION);
		map.put("volverA", "/vistas/proyecto/crudProyecto.zul");
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlCrudProyecto", "/vistas/proyecto/crudSubProyecto.zul", map);
	}

	@Command("eliminarSP")
	public void eliminarSP(@BindingParam("item") SubProyecto arg1) throws Exception {
		Messagebox.show("Desea realmente eliminar este Sub-Proyecto?", "Gisfpp: Eliminando Sub-Proyecto",
				new Button[] { Button.YES, Button.NO }, Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {

					@Override
					public void onEvent(ClickEvent event) throws Exception {
						if (event.getName().equals(Messagebox.ON_YES)) {
							IServicioSubProyecto servSP = (IServicioSubProyecto) SpringUtil.getBean("servSubProyecto");
							servSP.eliminar(arg1);
							Clients.showNotification("Sub-Proyecto eliminado.", Clients.NOTIFICATION_TYPE_INFO, null,
									"top_right", 3500);
							item.getSubProyectos().remove(arg1);
							BindUtils.postNotifyChange(null, null, getAutoReferencia(), "*");
						}
					}
				});
	}

	@Command("lkpDemandante")
	public void verDlgLkpDemandante() {
		Window dlg = (Window) Executions.createComponents("vistas/proyecto/dlgLkpDemandante.zul", null, null);
		dlg.doModal();
	}

	@GlobalCommand("obtenerLkpDemandante")
	@NotifyChange("item")
	public void obtenerLkpDemandante(@BindingParam("seleccion") Persona demandante) {
		item.getDemandantes().add(demandante);
		Clients.showNotification("Guarde cambios para confirmar la operacion.", Clients.NOTIFICATION_TYPE_WARNING, null,
				"top_right", 4000);
	}

	@Command("quitarDemandante")
	@NotifyChange("item")
	public void quitarDemandante(@BindingParam("item") Persona demandante) {
		item.getDemandantes().remove(demandante);
		Clients.showNotification("Guarde cambios para confirmar la operacion.", Clients.NOTIFICATION_TYPE_WARNING, null,
				"top_right", 4000);
	}

	@Command("verDlgStaffProyecto")
	public void verDlgStaffProyecto(@BindingParam("modo") String arg1,
			@BindingParam("itemStaff") MiembroStaffProyecto arg2) {
		Map<String, Object> args = new HashMap<>();
		if (arg1.equals(UtilGisfpp.MOD_NUEVO)) {
			args.put("modo", UtilGisfpp.MOD_NUEVO);
		} else {
			args.put("modo", UtilGisfpp.MOD_EDICION);
			args.put("itemStaff", arg2);
		}
		Window dlg = (Window) Executions.createComponents("vistas/proyecto/dlgStaffProyecto.zul", null, args);
		dlg.doModal();
	}

	@GlobalCommand("retornoDlgStaffProyecto")
	@NotifyChange("item")
	public void retornoDlgStaffProyecto(@BindingParam("modo") String arg1,
			@BindingParam("newItem") MiembroStaffProyecto arg2) {
		if (arg1.equals(UtilGisfpp.MOD_NUEVO)) {
			arg2.setProyecto(item);
			item.getStaff().add(arg2);
		}
		Clients.showNotification("Guarde cambios para confirmar la operacion.", Clients.NOTIFICATION_TYPE_WARNING, null,
				"top_right", 4000);
	}

	@Command("quitarMiembroSatff")
	@NotifyChange("item")
	public void quitarMiembroStaff(@BindingParam("itemStaff") MiembroStaffProyecto arg1) {
		item.getStaff().remove(arg1);
		Clients.showNotification("Guarde cambios para confirmar la operacion.", Clients.NOTIFICATION_TYPE_WARNING, null,
				"top_right", 4000);
	}

	@Command("nuevaConvocatoria")
	public void nuevaConvocatoria() {

		HashMap<String, Object> map = new HashMap<>();
		map.put("modo", UtilGisfpp.MOD_NUEVO);
		map.put("convocable", item);
		map.put("volverA", "/vistas/proyecto/crudProyecto.zul");
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlCrudProyecto",
				"/vistas/convocatoria/verConvocatoriaIndependiente.zul", map);
	}

	private MVCrudProyecto getAutoReferencia() {
		return this;
	}

	@NotifyChange("item")
	public boolean isAsignador() {
		PersonaFisica personaFisica;
		try {
			personaFisica = servUsuario.getUsuario(UtilSecurity.getNickName()).getPersona();
			return item.isAsignador(personaFisica);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}// fin de la clase
