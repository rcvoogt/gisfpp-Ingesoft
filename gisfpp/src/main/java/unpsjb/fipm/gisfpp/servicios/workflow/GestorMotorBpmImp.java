package unpsjb.fipm.gisfpp.servicios.workflow;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricData;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.activiti.engine.history.ProcessInstanceHistoryLog;
import org.activiti.engine.history.ProcessInstanceHistoryLogQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import unpsjb.fipm.gisfpp.entidades.workflow.DefinicionProceso;
import unpsjb.fipm.gisfpp.entidades.workflow.InstanciaActividad;
import unpsjb.fipm.gisfpp.entidades.workflow.InstanciaProceso;
import unpsjb.fipm.gisfpp.util.GisfppWorkflowException;

@Service("servGestorMotorBpm")
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS)
public class GestorMotorBpmImp implements GestorMotorBpm {

	private RepositoryService servRepository;
	private RuntimeService rtService;
	private HistoryService servHistory;
		
		
	@Override
	public List<DefinicionProceso> getDefinicionProcesos()
			throws GisfppWorkflowException {
		List<DefinicionProceso> devolucion = new ArrayList<DefinicionProceso>(); 
		try {
			ProcessDefinitionQuery query = servRepository.createProcessDefinitionQuery();
			List<ProcessDefinition> resultQuery = query.orderByProcessDefinitionVersion().desc().list();
			for (ProcessDefinition processDefinition : resultQuery) {
				devolucion.add(convertirDefProceso(processDefinition));
			}
		} catch (Exception exc1) {
			new GisfppWorkflowException("Se ha generado un error al tratar de obtener un listado de todas las"
					+ " definiciones de procesos desplegados en el repositorio del motor BPM.", exc1);
		}
		return devolucion;
	}

	@Override
	public void activarDefinicionProceso(String idDefinicion)
			throws GisfppWorkflowException {
		try {
				servRepository.activateProcessDefinitionById(idDefinicion);
		} catch (ActivitiObjectNotFoundException exc1) {
			throw new GisfppWorkflowException("La definición de proceso que desea \"activar\" no existe o ya se encuentra"
					+ " activa.");
		}catch (Exception exc2) {
			new GisfppWorkflowException("Se ha generado un error al tratar de \"activar\" la definición de proceso indicada.", exc2);
		}
	}

	@Override
	public void suspenderDefinicionProceso(String idDefinicion)
			throws GisfppWorkflowException {
		try {
			servRepository.suspendProcessDefinitionById(idDefinicion);
		} catch (ActivitiObjectNotFoundException exc1) {
			throw new GisfppWorkflowException("La definición de proceso que desea \"suspender\" no existe o ya se encuentra"
					+ " suspendida.");
		}catch (Exception exc2) {
			new GisfppWorkflowException("Se ha generado un error al tratar de \"suspender\" la definición de proceso indicada.", exc2);
		}
	}

	@Override
	public void eliminarDefinicionProceso(String idDespliegue,
			boolean cascada) throws GisfppWorkflowException {
		try {
			servRepository.deleteDeployment(idDespliegue, cascada);
		} catch (Exception exc1) {
			new GisfppWorkflowException("Se ha generado un error al tratar de \"eliminar\" de la BD la definición de proceso indicada.", exc1);
		}
		
	}
	
	@Override
	public List<InstanciaProceso> getProcesosEnEjecucion()
			throws GisfppWorkflowException {
		List<InstanciaProceso> resultado = new ArrayList<InstanciaProceso>();
		try {
			ProcessInstanceQuery query = rtService.createProcessInstanceQuery();
			List<ProcessInstance> instanciasProcesos = query.orderByProcessInstanceId().asc().list();
			
			for (ProcessInstance instancia : instanciasProcesos) {
				resultado.add(convertirInstanciaProceso(instancia));
			}
		} catch (Exception exc) {
			throw new GisfppWorkflowException("Se ha producido un error al consultar las instancias de procesos"
					+ " en ejecución.", exc);
		}
		return resultado;
	}
	
	@Override
	public void activarInstanciaProceso(String idInstancia)
			throws GisfppWorkflowException {
		try {
			rtService.activateProcessInstanceById(idInstancia);
		} catch (ActivitiObjectNotFoundException exc1) {
			new GisfppWorkflowException("No se puede activar. Instancia de Proceso inexistente.");
		}catch (ActivitiException exc2) {
			//La instancia de proceso ya se encontraba activa. No hacemos nada.
		}
		
	}

	@Override
	public void suspenderInstanciaProceso(String idInstancia)
			throws GisfppWorkflowException {
		try {
			rtService.suspendProcessInstanceById(idInstancia);
		} catch (ActivitiObjectNotFoundException exc1) {
			new GisfppWorkflowException("No se puede suspender. Instancia de Proceso inexistente.");
		}catch (ActivitiException exc2) {
			//La instancia de proceso ya se encontraba suspendida. No hacemos nada.
		}
		
	}

	@Override
	public void eliminarInstanciaProceso(String idInstancia)
			throws GisfppWorkflowException {
		try {
			rtService.deleteProcessInstance(idInstancia, "Proceso_Cancelado");
		} catch (ActivitiObjectNotFoundException exc1) {
			new GisfppWorkflowException("No se puede eliminar. Instancia de Proceso inexistente.");
		}
		
	}
		
	
	private DefinicionProceso convertirDefProceso(ProcessDefinition instancia){
		DeploymentQuery query = servRepository.createDeploymentQuery();
		Deployment deployment = query.deploymentId(instancia.getDeploymentId()).singleResult();
		
		DefinicionProceso devolucion = new DefinicionProceso();
		
		devolucion.setIdDefinicion(instancia.getId());
		devolucion.setKeyDefinicion(instancia.getKey());
		devolucion.setIdDespliegue(instancia.getDeploymentId());
		devolucion.setFecha_despliegue(deployment.getDeploymentTime());
		devolucion.setDescripcion(instancia.getDescription());
		devolucion.setNombre(instancia.getName());
		devolucion.setVersion(instancia.getVersion());
		devolucion.setSuspendido(instancia.isSuspended());
		
		return devolucion;
	}
	
	private InstanciaProceso convertirInstanciaProceso(ProcessInstance instancia){
		InstanciaProceso resultado = new InstanciaProceso();
		ProcessInstanceHistoryLogQuery query = servHistory.createProcessInstanceHistoryLogQuery(instancia.getId());
		ProcessInstanceHistoryLog historyInstancia = query.includeActivities().singleResult();
		
		ProcessDefinitionQuery definitionQuery = servRepository.createProcessDefinitionQuery();
		ProcessDefinition definition = definitionQuery.processDefinitionId(instancia.getProcessDefinitionId()).singleResult();
		
		HistoricVariableInstanceQuery variableQuery = servHistory.createHistoricVariableInstanceQuery();
		HistoricVariableInstance variableTitulo = variableQuery.processInstanceId(instancia.getId()).variableName("titulo")
				.singleResult();
		
		DefinicionProceso definicionProceso = new DefinicionProceso();
		definicionProceso.setNombre(definition.getName());
		definicionProceso.setCategoria(definition.getCategory());
		definicionProceso.setVersion(definition.getVersion());
		definicionProceso.setSuspendido(definition.isSuspended());
		
		resultado.setDefinicion(definicionProceso);
		resultado.setIdInstancia(instancia.getId());
		resultado.setInicia(historyInstancia.getStartTime());
		resultado.setKeyBusiness(historyInstancia.getBusinessKey());
		resultado.setTitulo((variableTitulo == null)?"Título sin definir." : variableTitulo.getValue().toString());
		resultado.setSuspendido(instancia.isSuspended());
		
		List<HistoricIdentityLink> participantesProceso = servHistory.getHistoricIdentityLinksForProcessInstance(instancia.getId());
		for (HistoricIdentityLink item : participantesProceso) {
			if (item.getType().equals("starter")) {
				resultado.setIniciador(item.getUserId());
			}
		}
		
		List<InstanciaActividad> actividades = new ArrayList<InstanciaActividad>();
		List<HistoricData> historyActividades = historyInstancia.getHistoricData();
		for (HistoricData historicData : historyActividades) {
			HistoricActivityInstance historyActividad = (HistoricActivityInstance) historicData;
			if ((!historyActividad.getActivityType().equals("exclusiveGateway")) && (!historyActividad.getActivityType().equals("parallelGateway"))
					&& (!historyActividad.getActivityType().equals("inclusiveGateway")) && (!historyActividad.getActivityType().equals("eventBasedGateway"))) {
			
				InstanciaActividad actividad = new InstanciaActividad();
				actividad.setIdActividad(historyActividad.getActivityId());
				actividad.setNombre(historyActividad.getActivityName());
				actividad.setTipo(historyActividad.getActivityType());
				actividad.setInicia(historyActividad.getStartTime());
				actividad.setFinaliza(historyActividad.getEndTime());
				
				actividades.add(actividad);
			}
		}
		
		resultado.setActividades(actividades);
		
		return resultado;
	}

	
	@Autowired(required=true)
	public void setServRepository(RepositoryService servRepository) {
		this.servRepository = servRepository;
	}
	
	
	@Autowired(required=true)
	public void setRtService(RuntimeService rtService) {
		this.rtService = rtService;
	}

	@Autowired(required = true)
	public void setServHistory(HistoryService servHistory) {
		this.servHistory = servHistory;
	}

}
