package unpsjb.fipm.gisfpp.controladores.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorTareas;
import unpsjb.fipm.gisfpp.servicios.workflow.entidades.InfoTarea;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

public class MVBandejaTareas {

	private GestorTareas servGTareas;
	private List<InfoTarea> tareasAsignadas;
	private List<InfoTarea> tareasPropuestas;
	private List<InfoTarea> tareasDelegadas;
	private List<InfoTarea> tareasRealizadas;
	private List<InfoTarea> tareas;
	
	private InfoTarea itemSeleccionado;
	
	private Logger log;
	private Usuario usuarioConectado;
	
	private String tituloPnlLista;
	
	@Init
	@NotifyChange({"tareasAsignadas", "tareasPropuestas", "tareasDelegadas", "tareasRealizadas"
		, "tituloBtnAsignadas", "tituloBtnPropuestas", "tituloBtnDelegadas", "tituloBtnRealizadas"})
	public void init(){
		
		usuarioConectado = UtilSecurity.getUsuarioConectado();
		log = UtilGisfpp.getLogger();
		
		servGTareas = (GestorTareas) SpringUtil.getBean("servGestionTareas");
		tareasAsignadas = servGTareas.getTareasAsignadas(usuarioConectado.getNickname(), GestorTareas.ORDEN_FECHA_VENC, true);
		tareasPropuestas = servGTareas.getTareasPropuestas(usuarioConectado.getNickname(), GestorTareas.ORDEN_FECHA_VENC, true);
		tareasDelegadas = servGTareas.getTareasDelegadas(usuarioConectado.getNickname(), GestorTareas.ORDEN_FECHA_VENC, true);
		tareasRealizadas = servGTareas.getTareasRealizadas(usuarioConectado.getNickname());
		
		tareas = null;
		tituloPnlLista = "Tareas";
		itemSeleccionado = null;
	}

	public List<InfoTarea> getTareas() {
		return tareas;
	}
	
	public InfoTarea getItemSeleccionado() {
		return itemSeleccionado;
	}

	public int getCantTareasAsignadas() {
		return tareasAsignadas.size();
	}

	public int getCantTareasPropuestas() {
		return tareasPropuestas.size();
	}

	public int getCantTareasDelegadas() {
		return tareasDelegadas.size();
	}

	public int getCantTareasRealizadas() {
		return tareasRealizadas.size();
	}
	
	public String getTituloPnlLista() {
		return tituloPnlLista;
	}

	@Command("seleccionarLista")
	@NotifyChange({"tareas", "tituloPnlLista", "itemSeleccionado"})
	public void actualizarListaSeleccionada(@BindingParam ("opcion") int arg1){
		switch (arg1) {
		case 1:{
			tareas = tareasAsignadas;
			tituloPnlLista = "Tareas Asignadas: " + getCantTareasAsignadas();
			itemSeleccionado = null;
			break;
		}
		case 2:{
			tareas = tareasPropuestas;
			tituloPnlLista = "Tareas Propuestas: " + getCantTareasPropuestas();
			itemSeleccionado = null;
			break;
		}
		case 3:{
			tareas = tareasDelegadas;
			tituloPnlLista = "Tareas Delegadas: " + getCantTareasDelegadas();
			itemSeleccionado = null;
			break;
		}
		case 4:{
			tareas = tareasRealizadas;
			tituloPnlLista = "Tareas Realizadas: " + getCantTareasRealizadas();
			itemSeleccionado = null;
			break;
		}
		default:{
			tareas = null;
			tituloPnlLista = "Tareas";
			itemSeleccionado = null;
			break;
		}
			
		}
	}
	
	@Command("actualizarItem")
	@NotifyChange("itemSeleccionado")
	public void actualizarItem(@BindingParam("item") InfoTarea arg1){
		itemSeleccionado = arg1;
	}
	
	@GlobalCommand("refrescarTareasAsignadas")
	@NotifyChange({"tareasAsignadas", "cantTareasAsignadas", "itemSeleccionado","tareas"})
	public void refrescarTareasAsignadas(){
		tareasAsignadas = servGTareas.getTareasAsignadas(usuarioConectado.getNickname(), 
				GestorTareas.ORDEN_FECHA_VENC, true);
		itemSeleccionado = null;
		tareas = null;
	}
	
	@GlobalCommand("refrescarTareasPropuestas")
	@NotifyChange({"tareasPropuestas", "cantTareasPropuestas", "itemSeleccionado", "tareas"})
	public void refrescarTareasPropuestas(){
		tareasPropuestas = servGTareas.getTareasPropuestas(usuarioConectado.getNickname(), 
				GestorTareas.ORDEN_FECHA_VENC, true);
		itemSeleccionado = null;
		tareas = null;
	}
	
	@GlobalCommand("refrescarTareasDelegadas")
	@NotifyChange({"tareasDelegadas", "cantTareasDelegadas", "itemSeleccionado", "tareas"})
	public void refrescarTareasDelegadas(){
		tareasDelegadas = servGTareas.getTareasDelegadas(usuarioConectado.getNickname(), GestorTareas.ORDEN_FECHA_VENC
				, true);
		itemSeleccionado = null;
		tareas = null;
	}
	
	@GlobalCommand("refrescarTareasRealizadas")
	@NotifyChange({"tareasRealizadas", "cantTareasRealizadas", "itemSeleccionado", "tareas"})
	public void refrescarTareasRealizadas(){
		tareasRealizadas = servGTareas.getTareasRealizadas(usuarioConectado.getNickname());
		itemSeleccionado = null;
		tareas = null;
	}
	
	@Command("realizarTarea")
	public void realizarTarea(){
		InfoTarea item = getItemSeleccionado();
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tarea", item);
		Window dlg = (Window) Executions.createComponents("vistas/workflow/"+item.getIdFormulario(), null, parametros);
		dlg.doModal();
	}
	
	@Command("salir")
	public void salir(){
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/pnlBandejaTareas");
	}
	
}//fin de la clase
