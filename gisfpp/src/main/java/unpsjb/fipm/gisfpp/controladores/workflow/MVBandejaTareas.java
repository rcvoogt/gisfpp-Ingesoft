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
import unpsjb.fipm.gisfpp.servicios.workflow.BandejaDeTareas;
import unpsjb.fipm.gisfpp.servicios.workflow.entidades.InfoTarea;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

public class MVBandejaTareas {

	private BandejaDeTareas servBandejaTareas ;
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
		
		servBandejaTareas = (BandejaDeTareas) SpringUtil.getBean("servBandejaTareas");
		tareasAsignadas = servBandejaTareas.getTareasAsignado(usuarioConectado.getNickname(), BandejaDeTareas.ORDEN_FECHA_VENC, true);
		tareasPropuestas = servBandejaTareas.getTareasCandidato(usuarioConectado.getNickname(), BandejaDeTareas.ORDEN_FECHA_VENC, true);
		tareasDelegadas = servBandejaTareas.getTareasDelegadas(usuarioConectado.getNickname());
		tareasRealizadas = servBandejaTareas.getTareasConcluidas(usuarioConectado.getNickname());
		
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
	@NotifyChange({"tareas", "tituloPnlLista"})
	public void actualizarListaSeleccionada(@BindingParam ("opcion") int arg1){
		switch (arg1) {
		case 1:{
			tareas = tareasAsignadas;
			tituloPnlLista = "Tareas Asignadas: " + getCantTareasAsignadas();
			break;
		}
		case 2:{
			tareas = tareasPropuestas;
			tituloPnlLista = "Tareas Propuestas: " + getCantTareasPropuestas();
			break;
		}
		case 3:{
			tareas = tareasDelegadas;
			tituloPnlLista = "Tareas Delegadas: " + getCantTareasDelegadas();
			break;
		}
		case 4:{
			tareas = tareasRealizadas;
			tituloPnlLista = "Tareas Realizadas: " + getCantTareasRealizadas();
			break;
		}
		default:{
			tareas = null;
			tituloPnlLista = "Tareas";
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
	@NotifyChange({"tareasAsignadas", "cantTareasAsignadas"})
	public void refrescarTareasAsignadas(){
		tareasAsignadas = servBandejaTareas.getTareasAsignado(usuarioConectado.getNickname(), 
				BandejaDeTareas.ORDEN_FECHA_VENC, true);
	}
	
	@GlobalCommand("refrescarTareasPropuestas")
	@NotifyChange({"tareasPropuestas", "cantTareasPropuestas"})
	public void refrescarTareasPropuestas(){
		tareasPropuestas = servBandejaTareas.getTareasCandidato(usuarioConectado.getNickname(), 
				BandejaDeTareas.ORDEN_FECHA_VENC, true);
	}
	
	@GlobalCommand("refrescarTareasDelegadas")
	@NotifyChange({"tareasDelegadas", "cantTareasDelegadas"})
	public void refrescarTareasDelegadas(){
		tareasDelegadas = servBandejaTareas.getTareasDelegadas(usuarioConectado.getNickname());
	}
	
	@GlobalCommand("refrescarTareasRealizadas")
	@NotifyChange({"tareasRealizadas", "cantTareasRealizadas"})
	public void refrescarTareasRealizadas(){
		tareasRealizadas = servBandejaTareas.getTareasConcluidas(usuarioConectado.getNickname());
	}
	
	@Command("realizarTarea")
	public void realizarTarea(@BindingParam ("item") InfoTarea tarea){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tarea", tarea);
		Window dlg = (Window) Executions.createComponents("vistas/workflow/"+tarea.getIdFormulario(), null, parametros);
		dlg.doModal();
	}
	
	@Command("salir")
	public void salir(){
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/pnlBandejaTareas");
	}
	
}//fin de la clase
