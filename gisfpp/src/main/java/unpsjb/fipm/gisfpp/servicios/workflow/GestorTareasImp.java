package unpsjb.fipm.gisfpp.servicios.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.ActivitiTaskAlreadyClaimedException;
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
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import unpsjb.fipm.gisfpp.entidades.workflow.EstadosTarea;
import unpsjb.fipm.gisfpp.entidades.workflow.InfoTarea;
import unpsjb.fipm.gisfpp.util.GisfppWorkflowException;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

@Service("servGestionTareas")
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS)
public class GestorTareasImp implements GestorTareas {
	
	private TaskService taskService;
	private RepositoryService repoService;
	private HistoryService historyService;
	
	private Logger log;
	
	@PostConstruct
	public void init(){
		log = UtilGisfpp.getLogger();
	}

	/**
	 * Devuelve el listado de tareas asignadas al usuario pasado como parámetro.
	 * @param usuario (String)
	 * @param ordenadoPor (String): criterio por el cual se va a ordenar el listado.
	 * @param asc (boolean):  determina si el modo de ordenación del listado es en forma ascendente. "true" si se desea ordenar
	 * en forma ascendente, "false" si se desea ordenar de forma descendente.
	 * @return Lista de tareas ordenada según los parámetros "ordenadoPor" y "asc".
	 * @throws GisfppWorkflowException
	 */
	@Override
	public List<InfoTarea> getTareasAsignadas(String usuario,
			String ordenadoPor, boolean asc) throws GisfppWorkflowException {
		TaskQuery query = taskService.createTaskQuery();
		List<Task> resultadoConsulta=null;
		try {
			switch (ordenadoPor) {
			case "porFechaVencimiento":{
				resultadoConsulta = (asc == true)?query.taskAssignee(usuario).orderByDueDateNullsLast().asc().list():
					query.taskAssignee(usuario).orderByDueDateNullsLast().desc().list(); 
				break;
			}	
			case "porPrioridad":{
				resultadoConsulta = (asc==true)? query.taskAssignee(usuario).orderByTaskPriority().asc().list():
					query.taskAssignee(usuario).orderByTaskPriority().desc().list();
				break;
			}
			case "porNombreTarea":{
				resultadoConsulta = (asc==true)? query.taskAssignee(usuario).orderByTaskName().asc().list():
					query.taskAssignee(usuario).orderByTaskName().desc().list();
			}
			}
		} catch (Exception exc1) {
			log.error("Clase: " +this.getClass().getName()+"- Método: getTareasAsignadas(usuario)", exc1);
			throw new GisfppWorkflowException("Se ha generado un error al intentar obtener el listado de tareas asignadas"
					+ "para un usuario específico.", exc1);
		}
		if (resultadoConsulta != null && !resultadoConsulta.isEmpty() ) {
			return convertirListaInfoTarea(resultadoConsulta, EstadosTarea.ASIGNADA);
		}
		return new ArrayList<InfoTarea>();
	}

	/**
	 * Devuelve el listado de tareas de las cuales el usuario pasado como parametro es candidato para su tratamiento.
	 * @param usuario (String)
	 * @param ordenadoPor (String): criterio por el cual se va a ordenar el listado.
	 * @param asc (boolean): determina si el modo de ordenación del listado es en forma ascendente. "true" si se desea ordenar
	 * en forma ascendente, "false" si se desea ordenar de forma descendente.
	 * @return Lista de tareas ordenada según los parámetros "ordenadoPor" y "asc".
	 * @throws GisfppWorkflowException
	 */
	@Override
	public List<InfoTarea> getTareasPropuestas(String usuario,
			String ordenadoPor, boolean asc) throws GisfppWorkflowException {
		TaskQuery query = taskService.createTaskQuery();
		List<Task> resultadoQuery = null;
		try {
			switch (ordenadoPor) {
			case "porFechaVencimiento":{
				resultadoQuery = (asc == true)?query.taskCandidateUser(usuario).orderByDueDateNullsLast().asc().list():
					query.taskCandidateUser(usuario).orderByDueDateNullsLast().desc().list(); 
				break;
			}	
			case "porPrioridad":{
				resultadoQuery = (asc==true)? query.taskCandidateUser(usuario).orderByTaskPriority().asc().list():
					query.taskCandidateUser(usuario).orderByTaskPriority().desc().list();
				break;
			}
			case "porNombreTarea":{
				resultadoQuery = (asc==true)? query.taskCandidateUser(usuario).orderByTaskName().asc().list():
					query.taskCandidateUser(usuario).orderByTaskName().desc().list();
			}
			}
		} catch (Exception exc1) {
			log.error("Clase: " +this.getClass().getName()+"- Método: getTareasPropuestas(usuario)", exc1);
			throw new GisfppWorkflowException("Se ha generado un error al intentar obtener el listado de tareas propuestas"
					+ "para un usuario candidato específico.", exc1);
		}
		if (resultadoQuery!=null && !resultadoQuery.isEmpty()) {
			return convertirListaInfoTarea(resultadoQuery, EstadosTarea.PROPUESTA);
		}
		return new ArrayList<InfoTarea>();
	}

	/**
	 * Devuleve el listado de tareas delegadas al usuario pasado como parametro.
	 * @param usuario (String)
	 * @param ordenadoPor (String): criterio por el cual se va a ordenar el listado.
	 * @param asc (boolean): determina si el modo de ordenación del listado es en forma ascendente. "true" si se desea ordenar
	 * en forma ascendente, "false" si se desea ordenar de forma descendente.
	 * @return Lista de tareas ordenada según los parámetros "ordenadoPor" y "asc".
	 * @throws GisfppWorkflowException
	 */
	@Override
	public List<InfoTarea> getTareasDelegadas(String usuario,
			String ordenadoPor, boolean asc) throws GisfppWorkflowException {
		TaskQuery query = taskService.createTaskQuery();
		List<Task> resultadoQuery=null;
		try {
			TaskQuery queryParcial = query.taskDelegationState(DelegationState.PENDING).taskAssignee(usuario);
			switch (ordenadoPor) {
			case "porFechaVencimiento":{
				resultadoQuery = (asc==true)? queryParcial.orderByDueDateNullsLast().asc().list(): queryParcial.desc().list();
				break;
			}
			case "porPrioridad":{
				resultadoQuery = (asc==true)? queryParcial.orderByTaskPriority().asc().list():queryParcial.orderByTaskPriority().desc().list();
				break;
			}
			case "porNombreTarea":{
				resultadoQuery =(asc==true)? queryParcial.orderByTaskName().asc().list(): queryParcial.orderByTaskName().desc().list();
			}
			}
		} catch (Exception exc1) {
			log.error("Clase: " +this.getClass().getName()+"- Método: getTareasDelegadas(usuario)", exc1);
			throw new GisfppWorkflowException("Se ha generado un error al intentar obtener el listado de tareas delegadas"
					+ "para un usuario específico.", exc1);
		}
		if (resultadoQuery!=null && !resultadoQuery.isEmpty()) {
			return convertirListaInfoTarea(resultadoQuery, EstadosTarea.DELEGADA);
		}
		return new ArrayList<InfoTarea>();
	}

	/**
	 * Devuelve el listado de tareas ya realizadas por el usuario pasado como parámetro.
	 * @param usuario (String)
	 * @return Lista de tareas concluidas, ordenadas por fecha de conclusion en forma descendente.
	 */
	@Override
	public List<InfoTarea> getTareasRealizadas(String usuario)
			throws GisfppWorkflowException {
		HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
		List<HistoricTaskInstance> resultadoQuery = null;
		try {
			resultadoQuery = query.finished().taskDeleteReason("completed").taskAssignee(usuario)
					.orderByHistoricTaskInstanceEndTime().desc().list();
		} catch (Exception exc1) {
			log.error("Clase: " +this.getClass().getName()+"- Método: getTareasRealizadas(usuario)", exc1);
			throw new GisfppWorkflowException("Se ha generado un error al intentar obtener el listado de tareas realizadas"
					+ "para un usuario específico.", exc1);
		}
		if (resultadoQuery!=null && !resultadoQuery.isEmpty()) {
			return convertirListaInfoTarea(resultadoQuery, EstadosTarea.REALIZADA);
		}
		return new ArrayList<InfoTarea>();
	}
	
	@Override
	public void tratarTarea(InfoTarea tarea) throws GisfppWorkflowException {
		if (tarea.getEstado()==EstadosTarea.ASIGNADA) {
			completarTarea(tarea);
		}else if(tarea.getEstado()== EstadosTarea.DELEGADA){
			resolverTarea(tarea);
		}
	}
	
	@Override
	public void tratarTarea(InfoTarea tarea, Map<String, Object> variables)
			throws GisfppWorkflowException {
		if (tarea.getEstado()==EstadosTarea.ASIGNADA) {
			completarTarea(tarea, variables);
		}else if(tarea.getEstado()== EstadosTarea.DELEGADA){
			resolverTarea(tarea, variables);
		}
		
	}

	@Override
	public void reclamarTarea(InfoTarea tarea, String usuario)
			throws GisfppWorkflowException {
		try {
			taskService.claim(tarea.getId(), usuario);
		} catch (ActivitiObjectNotFoundException exc1) {
			throw new GisfppWorkflowException("La tarea que se intenta reclamar no existe.");
		}catch (ActivitiTaskAlreadyClaimedException exc2) {
			throw new GisfppWorkflowException("La tarea que intenta reclamar ya ha sido reclamada por otro usuario.");
		}catch (Exception exc3) {
			log.error(this.getClass().getName(), exc3);
			throw new GisfppWorkflowException("Se ha producido un error al intertar reclamar la tarea con id: "+tarea.getId()+".", exc3);
		}

	}

	@Override
	public void delegarTarea(InfoTarea tarea, String usuarioDestino)
			throws GisfppWorkflowException {
		try {
			taskService.delegateTask(tarea.getId(), usuarioDestino);
		} catch (ActivitiObjectNotFoundException exc1) {
			throw new GisfppWorkflowException("La tarea que intenta delegar no existe.");
		}catch (Exception exc2) {
			log.error(this.getClass().getName(), exc2);
			throw new GisfppWorkflowException("Se ha producido un error al intertar delegar la tarea.", exc2);
		}

	} 
	
	private void completarTarea(InfoTarea tarea) throws GisfppWorkflowException {
		try {
			taskService.complete(tarea.getId());
		} catch(ActivitiObjectNotFoundException exc1){
			throw new GisfppWorkflowException("La tarea con Id. "+tarea.getId()+" que intenta completar no existe.", exc1);
		}catch (Exception exc2) {
			throw new GisfppWorkflowException("Se ha producido un error al  intentar completar la tarea con id: "+ tarea.getId()+".", exc2);
		}
	}

	
	private void completarTarea(InfoTarea tarea, Map<String, Object> variables)
			throws GisfppWorkflowException {
		try {
			taskService.complete(tarea.getId(), variables);
		} catch(ActivitiObjectNotFoundException exc1){
			throw new GisfppWorkflowException("La tarea con Id. "+tarea.getId()+" que intenta completar no existe.", exc1);
		}catch (Exception exc2) {
			throw new GisfppWorkflowException("Se ha producido un error al  intentar completar la tarea con id: "+ tarea.getId()+".", exc2);
		}
	}
	
	private void resolverTarea(InfoTarea tarea) throws GisfppWorkflowException {
		try {
			taskService.resolveTask(tarea.getId());
		}  catch (ActivitiObjectNotFoundException exc1) {
			throw new GisfppWorkflowException("La tarea con id. "+ tarea.getId()+" que intenta resolver no existe.");
		}catch (Exception exc2) {
			log.error(this.getClass().getName(), exc2);
			throw new GisfppWorkflowException("Se ha producido un error al intertar resolver la tarea con id. "+tarea.getId(), exc2);
		}
		
	}
	
	private void resolverTarea(InfoTarea tarea, Map<String, Object> variables)
			throws GisfppWorkflowException {
		try {
			taskService.resolveTask(tarea.getId(), variables);
		} catch (ActivitiObjectNotFoundException exc1) {
			throw new GisfppWorkflowException("La tarea con id. "+ tarea.getId()+" que intenta resolver no existe.");
		}catch (Exception exc2) {
			log.error(this.getClass().getName(), exc2);
			throw new GisfppWorkflowException("Se ha producido un error al intertar resolver la tarea con id. "+tarea.getId(), exc2);
		}
	}
	
	@Override
	public long getCantidadPorEstado(String usuario, EstadosTarea estado)
			throws GisfppWorkflowException {
		TaskQuery query1 = taskService.createTaskQuery();
		HistoricTaskInstanceQuery query2 = historyService.createHistoricTaskInstanceQuery();
		try {
			switch (estado) {
			case ASIGNADA:
				return query1.taskAssignee(usuario).count();
			case DELEGADA:
				return query1.taskDelegationState(DelegationState.PENDING).taskAssignee(usuario).count();
			case PROPUESTA:
				return query1.taskCandidateUser(usuario).count();
			case REALIZADA:
				return query2.taskAssignee(usuario).finished().count();
			default:
				return 0;
			}
		} catch (Exception exc) {
			log.error("Clase: "+this.getClass().getName()+ " - Metodo: getCantidadPorEstado(String usuario, EstadosTarea estado)", exc);
			throw new GisfppWorkflowException("Se ha producido un error al consultar la cantidad de tareas según el tipo"
					+ " especificado.", exc);
		}
	}

	private List<InfoTarea> convertirListaInfoTarea(List<? extends TaskInfo> lista, EstadosTarea estado) {
		List<InfoTarea> tareas = new ArrayList<InfoTarea>();
				
		for (TaskInfo task : lista) {
				tareas.add(convertirTarea(task, estado));
		}
		return tareas;
	}
	
	private InfoTarea convertirTarea (TaskInfo tarea, EstadosTarea estado){
		ProcessDefinitionQuery query = repoService.createProcessDefinitionQuery(); 
		ProcessDefinition definicionProceso = query.processDefinitionId(tarea.getProcessDefinitionId()).singleResult();
		InfoTarea infoTarea = new InfoTarea();
		
		infoTarea.setId(tarea.getId());
		infoTarea.setNombre(tarea.getName());
		infoTarea.setDescripcion(tarea.getDescription());
		infoTarea.setNombreProceso(definicionProceso.getName());
		infoTarea.setEstado(estado);
		infoTarea.setFecha_inicio(tarea.getCreateTime());
		
		if (tarea instanceof HistoricTaskInstance) {
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
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@Autowired(required=true)
	public void setRepoService(RepositoryService repoService) {
		this.repoService = repoService;
	}

	@Autowired(required=true)
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

			
}
