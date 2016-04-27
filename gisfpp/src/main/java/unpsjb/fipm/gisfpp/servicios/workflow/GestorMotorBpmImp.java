package unpsjb.fipm.gisfpp.servicios.workflow;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import unpsjb.fipm.gisfpp.entidades.workflow.DefinicionProceso;
import unpsjb.fipm.gisfpp.util.GisfppWorkflowException;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

@Service("servGestorMotorBpm")
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS)
public class GestorMotorBpmImp implements GestorMotorBpm {

	private RepositoryService servRepository;
	private RuntimeService rtService;
	private Logger log;
	
	@PostConstruct
	public void init(){
		log = UtilGisfpp.getLogger();
	}
	
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
			log.error("Clase: "+this.getClass().getName()+" - Metodo: getDefinicionProcesos()", exc1);
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
			log.error("Clase: "+this.getClass().getName()+" - Metodo: activarDefinicionProceso(String idDefinicion)", exc2);
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
			log.error("Clase: "+this.getClass().getName()+" - Metodo: suspenderDefinicionProceso(String idDefinicion)", exc2);
			new GisfppWorkflowException("Se ha generado un error al tratar de \"suspender\" la definición de proceso indicada.", exc2);
		}
	}

	@Override
	public void eliminarDefinicionProceso(String idDespliegue,
			boolean cascada) throws GisfppWorkflowException {
		try {
			servRepository.deleteDeployment(idDespliegue, cascada);
		} catch (Exception exc1) {
			log.error("Clase: "+this.getClass().getName()+" - Metodo: eliminarDefinicionProceso(String ,boolean )", exc1);
			new GisfppWorkflowException("Se ha generado un error al tratar de \"eliminar\" de la BD la definición de proceso indicada.", exc1);
		}
		
	}
	
	@Override
	public void activarInstanciaProceso(String idInstancia)
			throws GisfppWorkflowException {
		try {
			rtService.activateProcessInstanceById(idInstancia);
		} catch (ActivitiObjectNotFoundException exc1) {
			log.error("Clase: " + this.getClass().getName() +" - Metodo: activarInstanciaProceso(String idInstancia)", exc1);
			new GisfppWorkflowException("No se puede activar. Instancia de Proceso inexsistente.");
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
			log.error("Clase: " + this.getClass().getName() +" - Metodo: suspenderInstanciaProceso(String idInstancia)", exc1);
			new GisfppWorkflowException("No se puede suspender. Instancia de Proceso inexsistente.");
		}catch (ActivitiException exc2) {
			//La instancia de proceso ya se encontraba suspendida. No hacemos nada.
		}
		
	}

	@Override
	public void eliminarInstanciaProceso(String idInstancia)
			throws GisfppWorkflowException {
		try {
			rtService.deleteProcessInstance(idInstancia, "Isfpp Cancelada");
		} catch (ActivitiObjectNotFoundException exc1) {
			log.error("Clase: " + this.getClass().getName() +" - Metodo: eliminarInstanciaProceso(String idInstancia)", exc1);
			new GisfppWorkflowException("No se puede eliminar. Instancia de Proceso inexsistente.");
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

	
	@Autowired(required=true)
	public void setServRepository(RepositoryService servRepository) {
		this.servRepository = servRepository;
	}
	
	
	@Autowired(required=true)
	public void setRtService(RuntimeService rtService) {
		this.rtService = rtService;
	}
	
}
