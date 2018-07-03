package unpsjb.fipm.gisfpp.controladores.workflow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import unpsjb.fipm.gisfpp.entidades.ItemBreadCrumb;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.entidades.workflow.EstadosTarea;
import unpsjb.fipm.gisfpp.entidades.workflow.InfoTarea;
import unpsjb.fipm.gisfpp.entidades.workflow.InstanciaProceso;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorTareas;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflow;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

public class MVBandejaWorkflow {

	private GestorTareas servGTareas;
	private GestorWorkflow servGWorkflow;
	private List<InfoTarea> tareasAsignadas;
	private List<InfoTarea> tareasPropuestas;
	private List<InfoTarea> tareasDelegadas;
	private List<InfoTarea> tareasPendientes;
	private List<InfoTarea> tareasRealizadas;
	private List<InstanciaProceso> procesosActivos;
	private List<InstanciaProceso> procesosFinalizados;
	private List<InfoTarea> tareas;
	private List<InstanciaProceso> procesos;
	
	private InfoTarea tareaSeleccionada;
	private InstanciaProceso procesoSeleccionado;
	
	private boolean vistaTareas;
	private boolean vistaProcesos;
	
	private Logger log;
	private Usuario usuarioConectado;
	
	private String tituloPnlLista;
	
	//Reutilizo este objeto que tiene la misma estructura que necesito para navegar
	private ItemBreadCrumb navegable;
	
	
	@Init
	@NotifyChange({"tareasPendientes", "vistaTareas", "vistaProcesos"})
	public void init() throws Exception{
		
		try {
			usuarioConectado = UtilSecurity.getUsuarioConectado();
			log = UtilGisfpp.getLogger();
			
			servGTareas = (GestorTareas) SpringUtil.getBean("servGestionTareas");
			servGWorkflow = (GestorWorkflow) SpringUtil.getBean("servGestionWorkflow");
			tareasAsignadas = servGTareas.getTareasAsignadas(usuarioConectado.getNickname(), GestorTareas.ORDEN_FECHA_VENC, true);
			tareasPropuestas = servGTareas.getTareasPropuestas(usuarioConectado.getNickname(), GestorTareas.ORDEN_FECHA_VENC, true);
			tareasDelegadas = servGTareas.getTareasDelegadas(usuarioConectado.getNickname(), GestorTareas.ORDEN_FECHA_VENC, true);
			tareasPendientes = getTareasPendientes();
			procesosActivos = servGWorkflow.getProcesosActivos(usuarioConectado.getNickname());
		} catch (Exception exc1) {
			log.error("Clase: "+this.getClass().getName()+"- Metodo: Init().", exc1);
			throw exc1;
		}
				
		tareas = tareasPendientes;
		procesos = null;
		tareaSeleccionada = null;
		procesoSeleccionado = null;
		vistaTareas = true;
		vistaProcesos = false;
	}

	public List<InfoTarea> getTareas() {
		return tareas;
	}
	
	public List<InstanciaProceso> getProcesos(){
		return procesos;
	}
	
	public InfoTarea getTareaSeleccionada() {
		return tareaSeleccionada;
	}

	public InstanciaProceso getProcesoSeleccionado() {
		return procesoSeleccionado;
	}

	public int getCantPendientes(){
		return tareasPendientes.size();
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
		if (tareasRealizadas==null) {
			return (int) servGTareas.getCantidadPorEstado(usuarioConectado.getNickname(), EstadosTarea.REALIZADA);
		}
		return tareasRealizadas.size();
	}
	
	public int getCantProcesosActivos(){
		return procesosActivos.size();
	}
	
	public int getCantProcesosFinalizados(){
		if (procesosFinalizados==null) {
			return (int) servGWorkflow.getCantidadProcesosFinalizados(usuarioConectado.getNickname());
		}
		return procesosFinalizados.size();
	}
	
	public int getCantHistorial(){
		return getCantTareasRealizadas() + getCantProcesosFinalizados();
	}
	
	public String getTituloPnlLista() {
		return tituloPnlLista;
	}
	
	public boolean isVistaTareas() {
		return vistaTareas;
	}

	public boolean isVistaProcesos() {
		return vistaProcesos;
	}
	
	@NotifyChange({"vistaTareas", "vistaProcesos", "tareas", "procesos", "tituloPnlLista", "tareaSeleccionada", "procesoSeleccionado"})
	@Command("establecerVista")
	public void establecerVista(@BindingParam ("opcion") int arg1){
		switch (arg1) {
		case 1:{
			vistaTareas = true;
			vistaProcesos = false;
			tareas = tareasAsignadas;
			tituloPnlLista = "Tareas Asignadas: " + getCantTareasAsignadas();
			tareaSeleccionada = null;
			procesoSeleccionado = null;
			break;
		}
		case 2:{
			vistaTareas = true;
			vistaProcesos = false;
			tareas = tareasPropuestas;
			tituloPnlLista = "Tareas Propuestas: " + getCantTareasPropuestas();
			tareaSeleccionada = null;
			procesoSeleccionado = null;
			break;
		}
		case 3:{
			vistaTareas = true;
			vistaProcesos = false;
			tareas = tareasDelegadas;
			tituloPnlLista = "Tareas Delegadas: " + getCantTareasDelegadas();
			tareaSeleccionada = null;
			procesoSeleccionado = null;
			break;
		}
		case 4:{
			vistaProcesos=true;
			vistaTareas = false;
			procesos = procesosActivos;
			tituloPnlLista = "Procesos Activos: " + getCantProcesosActivos();
			procesoSeleccionado = null;
			tareaSeleccionada = null;
			break;
		}
		case 5:{
			vistaTareas = true;
			vistaProcesos = false;
			if (tareasRealizadas==null) {
				tareasRealizadas = servGTareas.getTareasRealizadas(usuarioConectado.getNickname());
			}
			tareas = tareasRealizadas;
			tituloPnlLista = "Tareas realizadas: " + getCantTareasRealizadas();
			tareaSeleccionada = null;
			procesoSeleccionado = null;
			break;
		}
		case 6: {
			vistaProcesos = true;
			vistaTareas = false;
			if (procesosFinalizados==null) {
				procesosFinalizados = servGWorkflow.getProcesosFinalizados(usuarioConectado.getNickname());
			}
			procesos = procesosFinalizados;
			tituloPnlLista = "Procesos finalizados: " + getCantProcesosFinalizados();
			procesoSeleccionado = null;
			tareaSeleccionada = null;
			break;
		}
		case 7: {
			vistaTareas = true;
			vistaProcesos = false;
			tareas = tareasPendientes;
			tituloPnlLista = "Total tareas pendientes: " + getCantPendientes();
			procesoSeleccionado = null;
			tareaSeleccionada = null;
			break;
		}
		default:{
			vistaProcesos = false;
			vistaTareas = false;
			procesos = null;
			tareas = null;
			tituloPnlLista = "Procesos/Tareas";
			tareaSeleccionada = null;
			procesoSeleccionado = null;
		}
		}
	}
	
	@NotifyChange("tareaSeleccionada")
	@Command("actualizarItemTarea")
	public void actualizarItemTarea(@BindingParam("item") InfoTarea arg1){
		tareaSeleccionada = arg1;
	}
	
	@NotifyChange("procesoSeleccionado")
	@Command("actualizarItemProceso")
	public void actualizarItemProcesos(@BindingParam("item") InstanciaProceso arg1) {
		procesoSeleccionado = arg1;
	}
	
	private List<InfoTarea> getTareasPendientes(){
		List<InfoTarea> resultado = new ArrayList<InfoTarea>();
		resultado.addAll(tareasAsignadas);
		resultado.addAll(tareasDelegadas);
		resultado.addAll(tareasPropuestas);
		
		if(!resultado.isEmpty()) {
			Collections.sort(resultado, new Comparator<InfoTarea>() {
	
				@Override
				public int compare(InfoTarea o1, InfoTarea o2) {
					if(o2 == null) 
						return -1;
					
					return (o1.getFecha_vencimiento().compareTo(o2.getFecha_vencimiento()));
				}
			});
		}
		return resultado;
	}
	
	@NotifyChange({"*"})
	@GlobalCommand("refrescarTareasAsignadas")
	public void refrescarTareasAsignadas(){
		tareasAsignadas = servGTareas.getTareasAsignadas(usuarioConectado.getNickname(), 
				GestorTareas.ORDEN_FECHA_VENC, true);
		tareasPendientes = getTareasPendientes();
		tareaSeleccionada = null;
		tareas = null;
		vistaTareas = false;
		tituloPnlLista = "Procesos/Tareas";
	}
	
	@NotifyChange({"*"})
	@GlobalCommand("refrescarTareasPropuestas")
	public void refrescarTareasPropuestas(){
		tareasPropuestas = servGTareas.getTareasPropuestas(usuarioConectado.getNickname(), 
				GestorTareas.ORDEN_FECHA_VENC, true);
		tareasPendientes = getTareasPendientes();
		tareaSeleccionada = null;
		tareas = null;
		vistaTareas = false;
		tituloPnlLista = "Procesos/Tareas";
	}
	
	@NotifyChange({"*"})
	@GlobalCommand("refrescarTareasDelegadas")
	public void refrescarTareasDelegadas(){
		tareasDelegadas = servGTareas.getTareasDelegadas(usuarioConectado.getNickname(), GestorTareas.ORDEN_FECHA_VENC
				, true);
		tareasPendientes = getTareasPendientes();
		tareaSeleccionada = null;
		tareas = null;
		vistaTareas = false;
		tituloPnlLista = "Procesos/Tareas";
	}
	
	@NotifyChange({"*"})
	@GlobalCommand("refrescarTareasRealizadas")
	public void refrescarTareasRealizadas(){
		if (tareasRealizadas!=null) {
			tareasRealizadas = servGTareas.getTareasRealizadas(usuarioConectado.getNickname());
		}
		tareaSeleccionada = null;
		tareas = null;
		vistaTareas = false;
	}
	
	@NotifyChange({"*"})
	@GlobalCommand("refrescarProcesosActivos")
	public void refrescarProcesosActivos(){
		procesosActivos = servGWorkflow.getProcesosActivos(usuarioConectado.getNickname());
		procesoSeleccionado = null;
		procesos = null;
		vistaProcesos = false;
		tituloPnlLista = "Procesos/Tareas";
	}
	
	@NotifyChange({"*"})
	@GlobalCommand("refrescarProcesosFinalizados")
	public void refrescarProcesosFinalizados(){
		if (procesosFinalizados!=null) {
			procesosFinalizados = servGWorkflow.getProcesosFinalizados(usuarioConectado.getNickname());
		}
		procesoSeleccionado =null;
		procesos=null;
		vistaProcesos = false;
	}
	
	@NotifyChange({"*"})
	@GlobalCommand("abrirPantallaAlCerrarDialogo")
	public void abrirPantallaAlCerrarDialogo(@BindingParam("navegable")ItemBreadCrumb nav){
		this.navegable = nav;
	}
	
	@Command("realizarTarea")
	public void realizarTarea(){
		InfoTarea item = getTareaSeleccionada();
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tarea", item);
		Window dlg = (Window) Executions.createComponents("vistas/workflow/tareas/"+item.getIdFormulario(), null, parametros);
		dlg.doModal();
	}
	
	@NotifyChange({"*"})
	@Command("reclamarTarea")
	public void reclamarTarea(){
		try {
			servGTareas.reclamarTarea(getTareaSeleccionada(), UtilSecurity.getNickName());
			refrescarTareasAsignadas();
			refrescarTareasPropuestas();
		} catch (Exception exc1) {
			log.error("Clase: "+this.getClass().getName()+"- Metodo: reclamarTarea().", exc1);
			throw exc1;
		}
	}
		
	
	@Command("salir")
	public void salir(){
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/pnlBandejaActividades");
	}
	
}//fin de la clase
