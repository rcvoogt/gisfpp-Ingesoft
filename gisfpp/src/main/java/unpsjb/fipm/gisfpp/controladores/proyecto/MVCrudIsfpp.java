package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.text.SimpleDateFormat;
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
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.Button;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.ItemBreadCrumb;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.entidades.staff.ECargosStaffFi;
import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.servicios.staff.IServiciosStaffFI;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflow;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

public class MVCrudIsfpp {

	private Logger log;
	private Isfpp item;
	private IServiciosIsfpp servicio;
	private IServiciosStaffFI srvStaff;

	private GestorWorkflow gestorWkFl;
	private SubProyecto perteneceA;
	private String modo;
	private boolean creando;
	private boolean editando;
	private boolean ver;
	private boolean existeConvocatoriaAbierta;
	private boolean verTrace;
	private HashMap<String, Object> args;
	private boolean tabIsfppCreado;
	private Tab tab;
	private Tabbox tabboxIsfpp;
	private String titulo;

	@SuppressWarnings("unchecked")
	@Init
	@NotifyChange({ "modo", "item", "creando", "editando", "ver", "verTrace" })
	public void init() throws Exception {
		log = UtilGisfpp.getLogger();
		gestorWkFl = (GestorWorkflow) SpringUtil.getBean("servGestionWorkflow");
		servicio = (IServiciosIsfpp) SpringUtil.getBean("servIsfpp");
		srvStaff = (IServiciosStaffFI) SpringUtil.getBean("servStaffFI");
		//args = (HashMap<String, Object>) Executions.getCurrent().getAttribute("argsCrudIsfpp");
		args = (HashMap<String, Object>) Sessions.getCurrent().getAttribute(UtilGuiGisfpp.PRM_PNL_CENTRAL);
		perteneceA = (SubProyecto) args.get("perteneceA");
		modo = (String) args.get("modo");
		switch (modo) {
		case UtilGisfpp.MOD_NUEVO: {
			item = new Isfpp(perteneceA, getTituloNewIsfpp(), "", new Date(), new Date(), "", null, null);
			creando = true;
			editando = (ver = false);
			existeConvocatoriaAbierta = false;
			break;
		}
		case UtilGisfpp.MOD_EDICION: {
			item = servicio.getInstancia((Integer) args.get("idItem"));
			item.setPerteneceA(perteneceA);
			creando = (ver = false);
			editando = true;
			titulo = "Editando ISFPP " + this.item.getTitulo();
			existeConvocatoriaAbierta = puedeCrearConvocatoria();
			break;
		}
		case UtilGisfpp.MOD_VER: {
			item = servicio.getInstancia((Integer) args.get("idItem"));
			item.setPerteneceA(perteneceA);
			creando = (editando = false);
			ver = true;
			titulo = "Ver ISFPP " + this.item.getTitulo();
		}
		}
		verTrace = isUsuarioValido();
		EventQueues.lookup("breadcrumb", EventQueues.DESKTOP, true)
		  .publish(new Event("onNavigate", null, new ItemBreadCrumb("vistas/proyecto/crudIsfpp.zul",titulo,args)));
	}

	/**
	 * Valida si el usuario puede ver el trace
	 * 
	 * @return
	 */
	public boolean isUsuarioValido() {
		PersonaFisica usuarioConectado = UtilSecurity.getUsuarioConectado().getPersona();
		try {
			StaffFI miembro = srvStaff.getMiembro(usuarioConectado);
			if (miembro.getRol().equals(ECargosStaffFi.COORDINADOR))
				return true;
			if (miembro.getRol().equals(ECargosStaffFi.DELEGADO))
				return true;
			if (servicio.getResponsableIsfpp(item).getMiembro().equals(usuarioConectado)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	private boolean puedeCrearConvocatoria() {
		if(item.getConvocatorias().size() > 0) {
			for(Convocatoria convocatoria : item.getConvocatorias()) {
				//Si hay una convocatoria con fecha de vencimiento posterior al dia de hoy, es que est� activa
				if(convocatoria.getFechaVencimiento().after(new Date()) ) {
					return true;
				}
			}
		}
		return false;
	}
	
	

	public boolean isExisteConvocatoriaAbierta() {
		return existeConvocatoriaAbierta;
	}
	public void setExisteConvocatoriaAbierta(boolean existeConvocatoriaAbierta) {
		this.existeConvocatoriaAbierta = existeConvocatoriaAbierta;
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

	public boolean isVerTrace() {
		return verTrace;
	}

	@Command("guardar")
	@NotifyChange({ "item", "creando", "editando", "ver" })
	public void guardar() throws Exception {
		try {
			if (creando) {
				servicio.persistir(item);
				Clients.showNotification("Nueva ISFPP guardada", Clients.NOTIFICATION_TYPE_INFO, null, "top_right",
						3500);
				String listaWf = UtilGisfpp.convertirEnCadena(
						gestorWkFl.nombreProcesosInstanciados(String.valueOf(item.getId()), "Isfpp", "Crear"));
				if (!listaWf.isEmpty()) {
					Clients.showNotification("Workflows instanciados: " + listaWf, Clients.NOTIFICATION_TYPE_INFO, null,
							"middle_right", 4000);
				}
			}
			if (editando) {
				servicio.editar(item);
				Clients.showNotification("ISFPP actualizada.", Clients.NOTIFICATION_TYPE_INFO, null, "top_right", 3500);
				String listaWf = UtilGisfpp.convertirEnCadena(
						gestorWkFl.nombreProcesosInstanciados(String.valueOf(item.getId()), "Isfpp", "Editar"));
				if (!listaWf.isEmpty()) {
					Clients.showNotification("Workflows instanciados: " + listaWf, Clients.NOTIFICATION_TYPE_INFO, null,
							"middle_right", 4000);
				}
			}
			creando = editando = false;
			ver = true;
		} catch (ConstraintViolationException cve) {
			Messagebox.show(UtilGisfpp.getMensajeValidations(cve), "Error: Validaci�n de datos.", Messagebox.OK,
					Messagebox.ERROR);
		} catch (DataIntegrityViolationException | org.hibernate.exception.ConstraintViolationException dive) {
			Messagebox.show(dive.getMessage(), "Error: Violacion Restricciones de Integridad BD.", Messagebox.OK,
					Messagebox.ERROR);
		} catch (Exception exc) {
			log.error(this.getClass().getName(), exc);
			throw exc;
		}
	}

	@Command("nuevaIsfpp")
	@NotifyChange({ "item", "creando", "editando", "ver", "modo" })
	public void nuevaIsfpp() {
		item = new Isfpp(perteneceA, getTituloNewIsfpp(), "", new Date(), new Date(), "", null, null);
		creando = true;
		editando = (ver = false);
		modo = UtilGisfpp.MOD_NUEVO;
	}

	@Command("editarIsfpp")
	@NotifyChange({ "creando", "editando", "ver", "modo" })
	public void editar() {
		editando = true;
		creando = (ver = false);
		modo = UtilGisfpp.MOD_EDICION;
	}

	@Command("cancelar")
	@NotifyChange({ "item", "creando", "editando", "ver" })
	public void cancelar() {
		creando = (editando = false);
		ver = true;
	}

	@Command("salir")
	public void salir() {
		/*Map<String, Object> map = new HashMap<String, Object>();
		if (modo.equals(UtilGisfpp.MOD_NUEVO) || modo.equals(UtilGisfpp.MOD_EDICION)) {
			map.put("actualizar", true);
		} else {
			map.put("actualizar", false);
		}
		BindUtils.postGlobalCommand(null, null, "cerrandoTab", map);
		Tab tab = (Tab) args.get("tab");
		tab.close();*/
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/pnlCrudISFPP");
	}

	@Command("verDlgStaff")
	public void verDlgStaff(@BindingParam("modo") String arg1, @BindingParam("itemStaff") MiembroStaffIsfpp arg2) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("modo", arg1);
		args.put("item", arg2);
		args.put("de", item);
		Window dlg = (Window) Executions.createComponents("vistas/proyecto/dlgStaffIsfpp.zul", null, args);
		dlg.doModal();
	}

	@GlobalCommand("retornoDlgStaffIsfpp")
	@NotifyChange("item")
	public void retornoDlgStaffIsfpp(@BindingParam("modo") String arg1,
			@BindingParam("newItem") MiembroStaffIsfpp arg2) {
		if (arg1.equals(UtilGisfpp.MOD_NUEVO)) {
			item.addMiembroStaff(arg2);
		}
		Clients.showNotification("Guarde cambios para confirmar la operacion.", Clients.NOTIFICATION_TYPE_WARNING, null,
				"top_right", 4000);
	}

	@Command("quitarMiembroSatff")
	@NotifyChange("item")
	public void quitarMiembroStaff(@BindingParam("itemStaff") MiembroStaffIsfpp arg1) {
		item.removerMiembroStaff(arg1);
		Clients.showNotification("Guarde cambios para confirmar la operacion.", Clients.NOTIFICATION_TYPE_WARNING, null,
				"top_right", 4000);
	}

	@Command("verDlgLkpPracticante")
	public void verDlgLkpPracticante() {
		Window dlg = (Window) Executions.createComponents("vistas/persona/dlgLookupPersona.zul", null, null);
		dlg.doModal();
	}
	
	//Para la prueba de seleccion con la lista de convocados aceptadores
//	@Command("verDlgLkpPracticantes")
//	public void verDlgLkpPracticantes(){
//		Window dlg = (Window) Executions.createComponents("vistas/proyecto/asignarPracticantesIsfpp.zul", null, null);
//		dlg.doModal();
//	}
	
	
	@GlobalCommand("obtenerLkpPersona")
	@NotifyChange("item")
	public void retornoLkpPracticante(@BindingParam("seleccion") PersonaFisica arg1) {
		item.addPracticante(arg1);
		Clients.showNotification("Guarde cambios para confirmar la operacion.", Clients.NOTIFICATION_TYPE_WARNING, null,
				"top_right", 4000);
	}

	@Command("quitarPracticante")
	@NotifyChange("item")
	public void quitarPracticante(@BindingParam("practicante") PersonaFisica arg1) {
		item.removerPracticante(arg1);
		Clients.showNotification("Guarde cambios para confirmar la operacion.", Clients.NOTIFICATION_TYPE_WARNING, null,
				"top_right", 4000);
	}

	// Dialogo para ver los datos de contacto de una persona
	@Command("dlgVerDatosContacto")
	public void verDlgVerDatosContacto(@BindingParam("itemPersona") PersonaFisica arg1) {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("itemPersona", arg1);
		Window dlg = (Window) Executions.createComponents("vistas/persona/dlgVerDatosContacto.zul", null, map);
		dlg.doModal();
	}

	@Command("activarIsfpp")
	@NotifyChange("*")
	public void activarIsfpp() throws Exception {
		servicio.reActivarISfpp(item.getId());
		Clients.showNotification("Estado de Isfpp actualizado.", Clients.NOTIFICATION_TYPE_INFO, null, "top_right",
				3500);
		String listaWf = UtilGisfpp.convertirEnCadena(
				gestorWkFl.nombreProcesosInstanciados(String.valueOf(item.getId()), "Isfpp", "Reactivar"));
		if (!listaWf.isEmpty()) {
			Clients.showNotification("Workflows instanciados: " + listaWf, Clients.NOTIFICATION_TYPE_INFO, null,
					"middle_right", 4000);
		}
		item = servicio.getInstancia(item.getId());
	}

	@Command("suspenderIsfpp")
	public void suspenderIsfpp() throws Exception {
		Messagebox.show(
				"Desea realmente \"Suspender\" esta Isfpp ? Si la suspende todo Workflow activo asociada a la misma"
						+ " tambi�n ser� suspendido.",
				"Gisfpp: Suspendiendo Isfpp", new Button[] { Button.YES, Button.NO }, Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {

					@Override
					public void onEvent(ClickEvent event) throws Exception {
						if (event.getName().equals(Messagebox.ON_YES)) {
							servicio.suspenderIsfpp(item.getId());
							Clients.showNotification("Estado de Isfpp actualizado.", Clients.NOTIFICATION_TYPE_INFO,
									null, "top_right", 3500);
							String listaWf = UtilGisfpp.convertirEnCadena(gestorWkFl
									.nombreProcesosInstanciados(String.valueOf(item.getId()), "Isfpp", "Suspender"));
							if (!listaWf.isEmpty()) {
								Clients.showNotification("Workflows instanciados: " + listaWf,
										Clients.NOTIFICATION_TYPE_INFO, null, "middle_right", 4000);
							}
							item = servicio.getInstancia(item.getId());
							BindUtils.postNotifyChange(null, null, getAutoReferencia(), "*");

						}
					}
				});
	}

	@Command("cancelarIsfpp")
	public void cancelarIsfpp() throws Exception {
		Messagebox.show(
				"Desea realmente \"Cancelar\" esta Isfpp ? Si la cancela, no podr� volver a activarla y todo Workflow activo asociada a la misma"
						+ " ser� eliminado.",
				"Gisfpp: Cancelando Isfpp", new Button[] { Button.YES, Button.NO }, Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {

					@Override
					public void onEvent(ClickEvent event) throws Exception {
						if (event.getName().equals(Messagebox.ON_YES)) {
							servicio.cancelarIsfpp(item.getId());
							Clients.showNotification("Estado de Isfpp actualizado.", Clients.NOTIFICATION_TYPE_INFO,
									null, "top_right", 3500);
							String listaWf = UtilGisfpp.convertirEnCadena(gestorWkFl
									.nombreProcesosInstanciados(String.valueOf(item.getId()), "Isfpp", "Cancelar"));
							if (!listaWf.isEmpty()) {
								Clients.showNotification("Workflows instanciados: " + listaWf,
										Clients.NOTIFICATION_TYPE_INFO, null, "middle_right", 4000);
							}
							item = servicio.getInstancia(item.getId());
							BindUtils.postNotifyChange(null, null, getAutoReferencia(), "*");
						}
					}
				});
	}

	@Command("concluirIsfpp")
	public void concluirIsfpp() throws Exception {
		Messagebox.show("Desea realmente dar por \"Concluida\" esta Isfpp?", "Gisfpp: Isfpp concluida",
				new Button[] { Button.YES, Button.NO }, Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {

					@Override
					public void onEvent(ClickEvent event) throws Exception {
						if (event.getName().equals(Messagebox.ON_YES)) {
							servicio.concluirIsfpp(item.getId());
							Clients.showNotification("Estado de Isfpp actualizado.", Clients.NOTIFICATION_TYPE_INFO,
									null, "top_right", 3500);
							String listaWf = UtilGisfpp.convertirEnCadena(gestorWkFl
									.nombreProcesosInstanciados(String.valueOf(item.getId()), "Isfpp", "Concluir"));
							if (!listaWf.isEmpty()) {
								Clients.showNotification("Workflows instanciados: " + listaWf,
										Clients.NOTIFICATION_TYPE_INFO, null, "middle_right", 4000);
							}
							item = servicio.getInstancia(item.getId());
							BindUtils.postNotifyChange(null, null, getAutoReferencia(), "*");
						}
					}
				});
	}
	
	@Command("nuevaConvocatoria")
	public void nuevaConvocatoria() {
//		crearTab(UtilGisfpp.MOD_NUEVO, "Nueva Convocatoria", item);
//		tabIsfppCreado=true;
		HashMap<String, Object> map = new HashMap<>();
		map.put("modo", UtilGisfpp.MOD_NUEVO);
		map.put("convocable", item);
		map.put("volverA", "/vistas/proyecto/crudIsfpp.zul");
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/tbbxISFPP", "vistas/convocatoria/verConvocatoriaIndependiente.zul",
				map);
		
	}
	
	@Command("verConvocatoria")
	public void verConvocatoria() {
		if(!tabIsfppCreado) {
			crearTab(UtilGisfpp.MOD_VER, "Ver Convocatoria", item);
			
		}else {
			tabboxIsfpp.setSelectedTab(tab);
		}
	}
	
	private void crearTab(String modo, String titulo, Isfpp isfpp) {
		//Obtengo el tabbox de subproyecto
		Tabbox tabBoxSubProyecto = (Tabbox) Path.getComponent("/panelCentro/tbbxSP");
		tabboxIsfpp = null;
		//El problema es buscar el tabbox inferior porque se genera dinamicamente
		//Busco el panel de isffp dentro del tabbox de subproyecto
		// Es totalmente duro esto, pero funciona... 
		// hasta que no funcionen los selectores, no encuentro mejor solucion
		for (Component component : (List<Component>) tabBoxSubProyecto.getTabpanels().getChildren()) {
			Tabpanel tabpanel = (Tabpanel) component;
			// Si es el tab que hardcodee el id al crearlo
			if(tabpanel.getId() == "pisfppTP") {
				List<Component> componentes = (List<Component>) tabpanel.getChildren();
				for (Component component2 : (List<Component>) tabpanel.getChildren()) {
					 tabboxIsfpp = (Tabbox)component2.getChildren().get(0);
					
				}
					
			}
		}
		
		
		if(tabboxIsfpp != null ) {
			//Tabbox tabbox = (Tabbox) Component.
			tab = new Tab(titulo);
			Tabpanel tabPanel = new Tabpanel();
			tabPanel.setId("pConvocatoriaTP");
			Include include = new Include("vistas/convocatoria/verCrearConvocatoria.zul");
			HashMap<String, Object> args = new HashMap<>();
			args.put("isfpp", item);
			args.put("modo", modo);
			args.put("idItem", isfpp.getId());
			args.put("tab", tab);
			
			include.setDynamicProperty("argsCrudConvocatoria", args);
			tabboxIsfpp.getTabs().appendChild(tab);
			tabPanel.getChildren().add(include);
			
			
			tabboxIsfpp.getTabpanels().getChildren().add(tabPanel);
			tabIsfppCreado=true;
			tabboxIsfpp.setSelectedTab(tab);
		}
	}
	
	private String getTituloNewIsfpp(){
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		String hoy = formateador.format(new Date());
		String solicitante = UtilSecurity.getNickName();

		return "Solicita: " + solicitante + " el " + hoy;
	}

	public String getTitulo() {
		return titulo;
	}
	private MVCrudIsfpp getAutoReferencia() {
		return this;
	}

}// fin de la clase
