package unpsjb.fipm.gisfpp.controladores.convocatoria;

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
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocable;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocado;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.servicios.convocatoria.IServiciosConvocable;
import unpsjb.fipm.gisfpp.servicios.convocatoria.IServiciosConvocado;
import unpsjb.fipm.gisfpp.servicios.convocatoria.IServiciosConvocatoria;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPersona;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflow;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

public class MVCrudConvocatoria {

	private Logger log;
	private Convocatoria item;

	private IServicioUsuario servUsuario;
	private IServiciosConvocado servConvocado;
	private IServicioPersona servPersona;
	private IServiciosConvocable servConvocable;

	private GestorWorkflow gestorWkFl;
	private IServiciosConvocatoria servicio;
	private Convocable convocable;
	private String modo;
	private boolean creando;
	private boolean editando;
	private boolean ver;
	private boolean modoTab;
	private HashMap<String, Object> args;
	private String detalle;
	private String volverA;
	private String configCKEditor;
	private List<Convocado> convocados;

	@Init
	@NotifyChange({ "modo", "item", "creando", "editando", "ver"})
	public void init() throws Exception {
		log = UtilGisfpp.getLogger();
		gestorWkFl = (GestorWorkflow) SpringUtil.getBean("servGestionWorkflow");
		servicio = (IServiciosConvocatoria) SpringUtil.getBean("servConvocatoria");
		servUsuario = (IServicioUsuario) SpringUtil.getBean("servUsuario");
		servConvocado = (IServiciosConvocado) SpringUtil.getBean("servConvocado");
		servPersona =  (IServicioPersona) SpringUtil.getBean("servPersona");
		servConvocable = (IServiciosConvocable) SpringUtil.getBean("servConvocable");
		Integer idConvocatoria = null;

		args = (HashMap<String, Object>) Executions.getCurrent().getAttribute("argsCrudConvocatoria");
		//args = (HashMap<String, Object>) Sessions.getCurrent().getAttribute(UtilGuiGisfpp.PRM_PNL_CENTRAL);

		if (args == null) {
			modoTab = false;
			args = (HashMap<String, Object>) Sessions.getCurrent().getAttribute(UtilGuiGisfpp.PRM_PNL_CENTRAL);
			volverA = args.get("volverA") == null? "" : (String) args.get("volverA");
			idConvocatoria = (Integer) args.get("convocatoria");
			
			
			
		}
		else {
			modoTab = true;
			detalle = "";
			configCKEditor = "/recursos/js/ckeditor-config.js";
		}
		convocable = (Convocable) args.get("convocable");
		modo = args.get("modo") == null? UtilGisfpp.MOD_NUEVO : (String) args.get("modo");
		
		switch (modo) {
		case UtilGisfpp.MOD_NUEVO: {
			item = new Convocatoria(new Date(), null,detalle, convocable,servUsuario.getUsuario(UtilSecurity.getNickName()));
			creando = true;
			editando = (ver = false);
			configCKEditor = "/recursos/js/ckeditor-config.js";
			break;
		}
		case UtilGisfpp.MOD_EDICION: {
			
			creando = (ver = false);
			editando = true;
			configCKEditor = "/recursos/js/ckeditor-config.js";
			item = servicio.getInstancia(idConvocatoria);
			break;
		}
		case UtilGisfpp.MOD_VER: {
			
			creando = (editando = false);
			ver = true;
			configCKEditor = "/recursos/js/ckeditor-config-readonly.js";
			item = servicio.getInstancia(idConvocatoria);
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
//		List<Convocado> convocados = new ArrayList<Convocado>();
//		convocados.addAll(item.getConvocados());
		List<Convocado> convocados;
		if(item == null){
			System.out.println("es null el item");
		}
			
		try {
			Integer idConvocatoria = (Integer) args.get("convocatoria");
			
			convocados = servConvocado.getConvocados(item.getId());
			return convocados;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	
	public String getConfigCKEditor() {
		return configCKEditor;
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
			Messagebox.show(UtilGisfpp.getMensajeValidations(cve), "Error: Validaci�n de datos.", Messagebox.OK,
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
		if(modoTab) {
			Map<String, Object> map = new HashMap<>();
			if(modo.equals(UtilGisfpp.MOD_NUEVO) || modo.equals(UtilGisfpp.MOD_EDICION)){
				map.put("actualizar", true);
			}else{
				map.put("actualizar", false);
			}
			BindUtils.postGlobalCommand(null, null, "cerrandoTab", map);
			Tab tab = (Tab) args.get("tab");
			tab.close();
		}else {
			HashMap<String, Object> mapAux = new HashMap<>();
			UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlVerConvocatoria", (String) args.get("volverA"), mapAux);
		}
	}
	
	@Command("verDlgConvocado")
	public void verDlgConvocado(@BindingParam("modo") String arg1){
		System.out.println("Entra a cargar convocado");
		Map<String, Object> args = new HashMap<>();
		args.put("modo", arg1);
		args.put("de", item);
		Window dlg = (Window) Executions.createComponents("vistas/convocatoria/dlgConvocado.zul", null, args);
		dlg.doModal();
		System.out.println("Sale de  cargar convocado");
	}
	
	@GlobalCommand("retornoDlgConvocado")
	@NotifyChange("item")
	public void retornoDlgConvocado(@BindingParam("modo")String arg1, @BindingParam("newItem") Convocado arg2){
		System.out.println("intenta grabar:" + arg2.getPersona().getNombre());
		item.agregarConvocado(arg2);
		System.out.println("despues de grabar");
		
	}
	
	@Command("quitarConvocado")
	@NotifyChange("item")
	public void quitarConvocado(@BindingParam("itemConvocado") Convocado arg1){
		item.quitarConvocado(arg1);
		
	}
	
	@Command("verDlgAsignarConvocados")
	public void verDlgConvocado(){
		System.out.println("Entra a cargar convocado");
		Map<String, Object> args = new HashMap<>();
		args.put("convocatoria", item);
		args.put("convocable", item.getConvocable());
		Window dlg = (Window) Executions.createComponents("vistas/workflow/tareas/dlgAsignarConvocados.zul", null, args);
		dlg.doModal();
		System.out.println("Sale de  cargar convocado");
	}
	
	
	
	@Command("iniciarConvocatoria")
	public void iniciarConvocatoria() throws Exception{
		try {
			this.guardar();
			Clients.showNotification("Nueva ISFPP guardada", Clients.NOTIFICATION_TYPE_INFO, null, "top_right",
					3500);
			String listaWf = UtilGisfpp.convertirEnCadena(gestorWkFl
					.nombreProcesosInstanciados(String.valueOf(item.getId()), "Convocatoria", "Crear"));
			if (!listaWf.isEmpty()) {
				Clients.showNotification("Workflows instanciados: "+listaWf, Clients.NOTIFICATION_TYPE_INFO, null, 
						"middle_right", 4000);
			}
			creando = editando = false;
			ver=true;
		} catch (ConstraintViolationException cve) {
			Messagebox.show(UtilGisfpp.getMensajeValidations(cve), "Error: Validaci�n de datos.", Messagebox.OK,
					Messagebox.ERROR);
		} catch (DataIntegrityViolationException | org.hibernate.exception.ConstraintViolationException dive) {
			Messagebox.show(dive.getMessage(), "Error: Violacion Restricciones de Integridad BD.", Messagebox.OK,
					Messagebox.ERROR);
		}catch (Exception exc) {
			log.error(this.getClass().getName(), exc);
			throw exc;
		}	
		
		
	}	
	
	private String getTituloNewIsfpp(){
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		String hoy = formateador.format(new Date());
		String solicitante = UtilSecurity.getNickName();
		
		return "Solicita: " + solicitante + " el " +hoy;
	}
	
	@Command("buscarEmail")
	public String buscarEmail(PersonaFisica persona) {
		return persona.getEmail();
	}
	
	@Command("buscarDNI")
	public String buscarDNI(PersonaFisica persona) {
		return persona.getDni();
	}
	
	
	
	private MVCrudConvocatoria getAutoReferencia(){
		return this;
	}
	
	public boolean isVencida() {
		return true;
	}
	
	@NotifyChange("convocable")
	public boolean isConvocador() {
		PersonaFisica personaFisica;
		try {
			Convocable convocable = servConvocable.getInstancia(item.getConvocable());
			personaFisica = servUsuario.getUsuario(UtilSecurity.getNickName()).getPersona();
			return convocable.isAsignador(personaFisica);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}// fin de la clase
