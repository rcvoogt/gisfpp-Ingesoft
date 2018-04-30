package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

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
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.Button;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.Convocado;
import unpsjb.fipm.gisfpp.entidades.proyecto.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosConvocatoria;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflow;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

public class MVCrudConvocatoria {

	private Logger log;
	private Convocatoria item;
	
	private IServiciosConvocatoria servicio;
	private Isfpp isfpp;
	private String modo;
	private boolean creando;
	private boolean editando;
	private boolean ver;
	private HashMap<String, Object> args;
	private String detalle;

	@Init
	@NotifyChange({ "modo", "item", "creando", "editando", "ver" })
	public void init() throws Exception {
		log = UtilGisfpp.getLogger();
		/*gestorWkFl = (GestorWorkflow) SpringUtil.getBean("servGestionWorkflow");*/
		servicio = (IServiciosConvocatoria) SpringUtil.getBean("servConvocatoria");
		args = (HashMap<String, Object>) Executions.getCurrent().getAttribute("argsCrudConvocatoria");
		isfpp = (Isfpp) args.get("isfpp");
		modo = args.get("modo") == null?(String) args.get("modo") : UtilGisfpp.MOD_NUEVO;
		
		detalle = (String) args.get("detalle");
		switch (modo) {
		case UtilGisfpp.MOD_NUEVO: {
			item = new Convocatoria(new Date(), null,detalle, isfpp);
			creando = true;
			editando = (ver = false);
			break;
		}
		case UtilGisfpp.MOD_EDICION: {
			item = servicio.getInstancia((Integer) args.get("idItem"));
			creando = (ver = false);
			editando = true;
			break;
		}
		case UtilGisfpp.MOD_VER: {
			item = servicio.getInstancia((Integer) args.get("idItem"));
			creando = (editando = false);
			ver = true;
		}
		}

	}

	public Convocatoria getItem() {
		return item;
	}

	public String getModo() {
		return modo;
	}
	
	public List<Convocado> getConvocados() {
		List<Convocado> convocados;
		try {
			System.out.println("salida");
			convocados = servicio.getConvocados(item.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		System.out.println("salida2");
		return convocados;
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

	@Command("guardar")
	@NotifyChange({ "item", "creando", "editando", "ver" })
	public void guardar() throws Exception {
		try {
			if (creando) {
				servicio.persistir(item);
				
			}
			if (editando) {
				servicio.editar(item);
				
			}
			creando = editando = false;
			ver=true;
		} catch (ConstraintViolationException cve) {
			Messagebox.show(UtilGisfpp.getMensajeValidations(cve), "Error: Validación de datos.", Messagebox.OK,
					Messagebox.ERROR);
		} catch (DataIntegrityViolationException | org.hibernate.exception.ConstraintViolationException dive) {
			Messagebox.show(dive.getMessage(), "Error: Violacion Restricciones de Integridad BD.", Messagebox.OK,
					Messagebox.ERROR);
		}catch (Exception exc) {
			log.error(this.getClass().getName(), exc);
			throw exc;
		}
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
	
	@Command("verDlgConvocado")
	public void verDlgConvocado(@BindingParam("modo") String arg1, @BindingParam("itemConvocado") Convocado arg2){
		Map<String, Object> args = new HashMap<>();
		args.put("modo", arg1);
		args.put("item", arg2);
		args.put("de", item);
		Window dlg = (Window) Executions.createComponents("vistas/proyecto/dlgSeleccionarConvocado.zul", null, args);
		dlg.doModal();
	}
	
	@GlobalCommand("retornoDlgConvocado"	)
	@NotifyChange("item")
	public void retornoDlgConvocado(@BindingParam("modo")String arg1, @BindingParam("newItem") Convocado arg2){
		if(arg1.equals(UtilGisfpp.MOD_NUEVO)){
			item.agregarConvocado(arg2);
		}
		Clients.showNotification("Guarde cambios para confirmar la operacion.", Clients.NOTIFICATION_TYPE_WARNING, null, 
				"top_right", 4000);
	}
	
	@Command("quitarConvocado")
	@NotifyChange("item")
	public void quitarConvocado(@BindingParam("itemConvocado") Convocado arg1){
		item.quitarConvocado(arg1);
		Clients.showNotification("Guarde cambios para confirmar la operacion.", Clients.NOTIFICATION_TYPE_WARNING, null, 
				"top_right", 4000);
	}
	
	
	
	
	
	@Command("crearConvocatoria")
	public void crearConvocatoria() throws Exception{
		/*Messagebox.show("Desea realmente \"Suspender\" esta Isfpp ? Si la suspende todo Workflow activo asociada a la misma"
				+ " también será suspendido.", "Gisfpp: Suspendiendo Isfpp", new Button [] {Button.YES, Button.NO}
				, Messagebox.QUESTION, new EventListener<Messagebox.ClickEvent>() {
					
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						if (event.getName().equals(Messagebox.ON_YES)) {
							servicio.suspenderIsfpp(item.getId());
							Clients.showNotification("Estado de Isfpp actualizado.", Clients.NOTIFICATION_TYPE_INFO, null, "top_right",
									3500);
							String listaWf = UtilGisfpp.convertirEnCadena(gestorWkFl
									.nombreProcesosInstanciados(String.valueOf(item.getId()), "Isfpp", "Suspender"));
							if (!listaWf.isEmpty()) {
								Clients.showNotification("Workflows instanciados: "+listaWf, Clients.NOTIFICATION_TYPE_INFO, null, 
										"middle_right", 4000);
							}
							item = servicio.getInstancia(item.getId());
							BindUtils.postNotifyChange(null, null, getAutoReferencia(), "*");
							
						}
					}
				});*/
	}
	
	
	
	private String getTituloNewIsfpp(){
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		String hoy = formateador.format(new Date());
		String solicitante = UtilSecurity.getNickName();
		
		return "Solicita: " + solicitante + " el " +hoy;
	}
	
	@Command("iniciarConvocatoria")
	public void iniciarConvocatoria() {
		
	}
	
	public void verDlgConvocado(@BindingParam("modo") String arg1) {
		Map<String, Object> args = new HashMap<>();
		args.put("modo", arg1);
		args.put("de", item);
		Window dlg = (Window) Executions.createComponents("vistas/proyecto/dlgConvocado.zul", null, args);
		dlg.doModal();
	}
	
	private MVCrudConvocatoria getAutoReferencia(){
		return this;
	}

}// fin de la clase
