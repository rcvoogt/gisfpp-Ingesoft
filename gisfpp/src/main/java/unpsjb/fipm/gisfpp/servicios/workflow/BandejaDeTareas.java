package unpsjb.fipm.gisfpp.servicios.workflow;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import unpsjb.fipm.gisfpp.servicios.workflow.entidades.EstadosTarea;
import unpsjb.fipm.gisfpp.servicios.workflow.entidades.InfoTarea;

@Service("servBandejaTareas")
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS)
public class BandejaDeTareas {
	
	public static final String ORDEN_PRIORIDAD = "porPrioridad";
	public static final String ORDEN_FECHA_VENC = "porFechaVencimiento";
	public static final String ORDEN_NOMBRE_TAREA = "porNombreTarea";
	
	private TaskService taskServicio;
	private RepositoryService repoServicio;
	private HistoryService historyServicio;
	
	/**
	 * Devuelve el listado de tareas asignadas al usuario pasado como parámetro.
	 * @param usuario (String)
	 * @param ordenadoPor (String): criterio por el cual se va a ordenar el listado.
	 * @param asc (boolean):  determina si el modo de ordenación del listado es en forma ascendente. "true" si se desea ordenar
	 * en forma ascendente, "false" si se desea ordenar de forma descendente.
	 * @return Lista de tareas ordenada según los parámetros "ordenadoPor" y "asc".
	 */
	public List<InfoTarea> getTareasAsignado(String usuario, String ordenadoPor, boolean asc) {
		TaskQuery query = taskServicio.createTaskQuery();
		List<Task> resultadoConsulta=null;
		
		switch (ordenadoPor) {
			case "porFechaVencimiento":{
				resultadoConsulta = (asc == true)?query.taskAssignee(usuario).orderByDueDateNullsLast().asc().list():
					query.taskAssignee(usuario).orderByDueDateNullsLast().desc().list(); 
				break;
			}	
			case "porPrioridad":{
				resultadoConsulta = (asc==true)? query.taskAssignee(usuario).orderByTaskPriority().asc().list():
					query.taskAssignee(usuario).orderByTaskPriority().desc().list();
			}
			case "porNombreTarea":{
				resultadoConsulta = (asc==true)? query.taskAssignee(usuario).orderByTaskName().asc().list():
					query.taskAssignee(usuario).orderByTaskName().desc().list();
			}
		}
		
		return convertirListaInfoTarea(resultadoConsulta, EstadosTarea.ASIGNADA);
	}

	/**
	 * Devuelve el listado de tareas de las cuales el usuario pasado como parametro es candidato para su tratamiento.
	 * @param usuario (String)
	 * @param ordenadoPor (String): criterio por el cual se va a ordenar el listado.
	 * @param asc (boolean): determina si el modo de ordenación del listado es en forma ascendente. "true" si se desea ordenar
	 * en forma ascendente, "false" si se desea ordenar de forma descendente.
	 * @return Lista de tareas ordenada según los parámetros "ordenadoPor" y "asc".
	 */
	public List<InfoTarea> getTareasCandidato(String usuario, String ordenadoPor, boolean asc){
		TaskQuery query = taskServicio.createTaskQuery();
		List<Task> resultadoQuery = null;
		
		switch (ordenadoPor) {
		case "porFechaVencimiento":{
			resultadoQuery = (asc == true)?query.taskCandidateUser(usuario).orderByDueDateNullsLast().asc().list():
				query.taskCandidateUser(usuario).orderByDueDateNullsLast().desc().list(); 
			break;
		}	
		case "porPrioridad":{
			resultadoQuery = (asc==true)? query.taskCandidateUser(usuario).orderByTaskPriority().asc().list():
				query.taskCandidateUser(usuario).orderByTaskPriority().desc().list();
		}
		case "porNombreTarea":{
			resultadoQuery = (asc==true)? query.taskCandidateUser(usuario).orderByTaskName().asc().list():
				query.taskCandidateUser(usuario).orderByTaskName().desc().list();
		}
	}
		
		return convertirListaInfoTarea(resultadoQuery, EstadosTarea.PROPUESTA);
	}
	
	/**
	 * Devuelve el listado de tareas concluidas por el usuario pasado como parametro.
	 * @param usuario (String)
	 * @return Lista de tareas concluidas, ordenadas por fecha de conclusion en forma descendente.
	 */
	public List<InfoTarea> getTareasConcluidas(String usuario) {
		HistoricTaskInstanceQuery query = historyServicio.createHistoricTaskInstanceQuery();
		List<HistoricTaskInstance> resultadoQuery = query.finished().taskDeleteReason("completed").taskAssignee(usuario)
				.orderByHistoricTaskInstanceEndTime().desc().list();
				
		return convertirListaInfoTarea(resultadoQuery, EstadosTarea.REALIZADA);
	}
	
	/**
	 * Devuleve el listado de tareas delegadas al usuario pasado como parametro.
	 * @param usuario (String)
	 * @return Lista de tareas delegadas, ordenadas por fecha de vencimiento en forma ascendente.
	 */
	public List<InfoTarea> getTareasDelegadas(String usuario){
		TaskQuery query = taskServicio.createTaskQuery();
		List<Task> resultadoQuery = query.taskDelegationState(DelegationState.PENDING).taskAssignee(usuario)
				.orderByDueDateNullsLast().asc().list();
		return convertirListaInfoTarea(resultadoQuery, EstadosTarea.DELEGADA);
	}
	
	private List<InfoTarea> convertirListaInfoTarea(List<? extends TaskInfo> lista, EstadosTarea estado) {
		List<InfoTarea> tareas = new ArrayList<InfoTarea>();
				
		if(lista!=null && !lista.isEmpty()){
			for (TaskInfo task : lista) {
				tareas.add(convertirTarea(task, estado));
			}
		}
		return tareas;
	}
	
	private InfoTarea convertirTarea (TaskInfo tarea, EstadosTarea estado){
		ProcessDefinitionQuery query = repoServicio.createProcessDefinitionQuery();
		ProcessDefinition definicionProceso = query.processDefinitionId(tarea.getProcessDefinitionId()).singleResult();
		InfoTarea infoTarea = new InfoTarea();
		
		infoTarea.setId(tarea.getId());
		infoTarea.setNombre(tarea.getName());
		infoTarea.setDescripcion(tarea.getDescription());
		infoTarea.setNombreProceso(definicionProceso.getName());
		infoTarea.setEstado(estado);
		
		if (tarea instanceof HistoricTaskInstance) {
			infoTarea.setFecha_inicio(((HistoricTaskInstance)tarea).getStartTime());
			infoTarea.setFecha_concluida(((HistoricTaskInstance)tarea).getEndTime());
			infoTarea.setFecha_reclamada(((HistoricTaskInstance)tarea).getClaimTime());
			return infoTarea;
		}
		
		infoTarea.setAsignado(tarea.getAssignee());
		infoTarea.setCategoria(tarea.getCategory());
		infoTarea.setFecha_vencimiento(tarea.getDueDate());
		infoTarea.setDuenio(tarea.getOwner());
		infoTarea.setPrioridad(tarea.getPriority());
		infoTarea.setIdFormulario(tarea.getFormKey());
		infoTarea.setIdInstanciaProceso(tarea.getProcessInstanceId());
		return infoTarea;
	}
	
	@Autowired(required=true)
	protected void setServTask(TaskService servTask) {
		this.taskServicio = servTask;
	}

	@Autowired(required=true)
	protected void setRepoServicio(RepositoryService repoServicio) {
		this.repoServicio = repoServicio;
	}

	@Autowired(required=true)
	protected void setHistoryServicio(HistoryService historyServicio) {
		this.historyServicio = historyServicio;
	}
			
}
