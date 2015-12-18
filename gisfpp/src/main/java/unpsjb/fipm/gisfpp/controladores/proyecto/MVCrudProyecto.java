package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import unpsjb.fipm.gisfpp.entidades.proyecto.EstadoProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.TipoProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.ServiciosProyecto;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVCrudProyecto {

	private ServiciosProyecto servicio;
	private Proyecto seleccionado;
	private List<SubProyecto> subProyectos;
	private Logger log;
	private boolean creando = false;
	private boolean editando = false;
	private boolean ver = false;
	private String modo = null;
	private String titulo = " ";

	@Init
	@NotifyChange({ "modo", "creando", "editando", "ver", "titulo" })
	public void init() throws Exception {
		servicio = (ServiciosProyecto) SpringUtil.getBean("servProyecto");
		@SuppressWarnings("unchecked")
		final HashMap<String, Object> map = (HashMap<String, Object>) Sessions.getCurrent()
				.getAttribute(UtilGuiGisfpp.PRM_PNL_CENTRAL);
		modo = (String) map.get("modo");
		if (modo.equals(UtilGisfpp.MOD_NUEVO)) {
			seleccionado = new Proyecto("", "", "", "", null, EstadoProyecto.GENERADO, null, null, "", null);
			creando = true;
			editando = false;
			ver = false;
			titulo = "Nuevo Proyecto";
		} else if (modo.equals(UtilGisfpp.MOD_EDICION)) {
			Proyecto aux = servicio.getInstancia((Integer) map.get("idItem"));
			if (aux == null) {
				aux = new Proyecto("", "", "", "", TipoProyecto.INTERNO, EstadoProyecto.GENERADO, null, null, "", null);
			}
			seleccionado = aux;
			editando = true;
			creando = false;
			ver = false;
			titulo = "Editando Proyecto:  (" + seleccionado.getCodigo() + ") " + seleccionado.getTitulo();
		} else if (modo.equals(UtilGisfpp.MOD_VER)) {
			Proyecto aux = servicio.getInstancia((Integer) map.get("idItem"));
			if (aux == null) {
				aux = new Proyecto("", "", "", "", TipoProyecto.INTERNO, EstadoProyecto.GENERADO, null, null, "", null);
			}
			seleccionado = aux;
			ver = true;
			creando = false;
			editando = false;
			titulo = "Ver Proyecto:  (" + seleccionado.getCodigo() + ") " + seleccionado.getTitulo();
		}
	}

	public String getTitulo() {
		return titulo;
	}

	public List<TipoProyecto> getTipos() {
		return Arrays.asList(TipoProyecto.values());
	}

	public List<SubProyecto> getSubProyectos() throws Exception {
		return seleccionado.getSubProyectos();
	}

	public String getModo() {
		return modo;
	}

	public Proyecto getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Proyecto proyecto) {
		seleccionado = proyecto;
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
	@NotifyChange({ "seleccionado", "creando" })
	public void nuevoProyecto() {
		seleccionado = new Proyecto("", "", "", "", null, EstadoProyecto.GENERADO, null, null, "", null);
		creando = true;
		editando = false;
		ver = false;
	}

	@Command("editar")
	@NotifyChange({ "creando", "editando", "ver" })
	public void reEditar() {
		editando = true;
		creando = false;
		ver = false;
	}

	@Command("guardar")
	@NotifyChange({ "creando", "editando", "ver" })
	public void guardar() throws Exception {
		try {
			if (creando) {
				int id = servicio.persistir(seleccionado);
				Clients.showNotification("Nuevo Proyecto creado. ", Clients.NOTIFICATION_TYPE_INFO, null, "top_right",
						3500);
				creando = false;
				editando = false;
				ver = true;
			} else if (editando) {
				servicio.editar(seleccionado);
				Clients.showNotification("Proyecto actualizado.", Clients.NOTIFICATION_TYPE_INFO, null, "top_right",
						3500);
				creando = false;
				editando = false;
				ver = true;
			}
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

	@Command("cancelar")
	@NotifyChange({ "creando", "editando", "ver" })
	public void cancelar() {
		creando = false;
		editando = false;
		ver = true;
	}

	@Command("volver")
	public void volver() {
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlCrudProyecto", "vistas/proyecto/listarProyectos.zul");
	}

	@Command("nuevoSP")
	public void nuevoSP() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("perteneceA", seleccionado);
		map.put("modo", UtilGisfpp.MOD_NUEVO);
		map.put("volverA", "/vistas/proyecto/crudProyecto.zul");
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlCrudProyecto", "/vistas/proyecto/crudSubProyecto.zul", map);
	}

	@Command("verSP")
	public void verSp(@BindingParam("idItem") Integer idItem) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("perteneceA", seleccionado);
		map.put("idItem", idItem);
		map.put("modo", UtilGisfpp.MOD_VER);
		map.put("volverA", "/vistas/proyecto/crudProyecto.zul");
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlCrudProyecto", "/vistas/proyecto/crudSubProyecto.zul", map);
	}

	@Command("editarSP")
	public void editarSP(@BindingParam("idItem") Integer idItem) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("perteneceA", seleccionado);
		map.put("idItem", idItem);
		map.put("modo", UtilGisfpp.MOD_EDICION);
		map.put("volverA", "/vistas/proyecto/crudProyecto.zul");
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlCrudProyecto", "/vistas/proyecto/crudSubProyecto.zul", map);
	}

}
