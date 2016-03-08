package unpsjb.fipm.gisfpp.servicios.workflow;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.ActivitiTaskAlreadyClaimedException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.slf4j.Logger;

import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;
import unpsjb.fipm.gisfpp.util.GisfppWorkflowException;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public abstract class ProcesoGisfppImpl implements ProcesoGisfpp{
	
	protected IServicioUsuario servUsuario;
	protected RuntimeService servRunTime;
	protected TaskService servTask;
	
	protected Logger log;
	
	@PostConstruct
	public void inicializacion(){
		log = UtilGisfpp.getLogger();
	}
	
	@Override
	public abstract String instanciarProceso(Object entidad)
			throws GisfppWorkflowException;
	
	@Override
	public abstract String instanciarProceso(String keyBusiness, Map<String, Object> variables)
			throws GisfppWorkflowException;

	@Override
	public String getIdInstanciaProceso(String keyBusiness) {
		ProcessInstanceQuery query = servRunTime.createProcessInstanceQuery();
		ProcessInstance instancia = query.processInstanceBusinessKey(keyBusiness).singleResult();
		return instancia.getProcessInstanceId();
	}

	@Override
	public Map<String, Object> getVariables(String keyBusiness) {
		ProcessInstanceQuery query = servRunTime.createProcessInstanceQuery();
		ProcessInstance instancia = query.processInstanceBusinessKey(keyBusiness).singleResult();
		return instancia.getProcessVariables();
	}

	@Override
	public Object getVariable(String keyBusiness, String nombreVariable) {
		ProcessInstanceQuery query = servRunTime.createProcessInstanceQuery();
		ProcessInstance instancia = query.processInstanceBusinessKey(keyBusiness).singleResult();
		return servRunTime.getVariable(instancia.getId(), nombreVariable);
	}

	@Override
	public String getKeyBusiness(String idInstanciaProceso) {
		ProcessInstanceQuery query = servRunTime.createProcessInstanceQuery();
		ProcessInstance instancia = query.processInstanceBusinessKey(idInstanciaProceso).singleResult();
		return instancia.getBusinessKey();
	}

	@Override
	public void completarTarea(String idTarea) throws GisfppWorkflowException {
		try {
			servTask.complete(idTarea);
		}catch(ActivitiObjectNotFoundException ecx1){
			throw new GisfppWorkflowException("La tarea que intenta realizar no existe.");
		}catch (Exception exc2) {
			throw new GisfppWorkflowException("Se ha producido un error al  intentar realizar la tarea.\n"
					+ exc2.getLocalizedMessage(), exc2);
		}
	}

	@Override
	public void completarTarea(String idTarea, Map<String, Object> variables)
			throws GisfppWorkflowException {
		try {
			servTask.complete(idTarea, variables);
		} catch(ActivitiObjectNotFoundException ecx1){
			throw new GisfppWorkflowException("La tarea que intenta realizar no existe.");
		}catch (Exception exc2) {
			throw new GisfppWorkflowException("Se ha producido un error al intentar realizar la tarea.\n"
					+ exc2.getLocalizedMessage(), exc2);
		}
		
	}

	@Override
	public void reclamarTarea(String idTarea, String usuario)
			throws GisfppWorkflowException {
		try {
			servTask.claim(idTarea, usuario);
		} catch (ActivitiObjectNotFoundException exc1) {
			throw new GisfppWorkflowException("La tarea que se intenta reclamar no existe.");
		}catch (ActivitiTaskAlreadyClaimedException exc2) {
			throw new GisfppWorkflowException("La tarea que intenta reclamar ya ha sido reclamada por otro usuario.");
		}catch (Exception exc3) {
			log.error(this.getClass().getName(), exc3);
			throw new GisfppWorkflowException("Se ha producido un error al intertar reclamar la tarea.\n"
					+ exc3.getLocalizedMessage(), exc3);
		}
		
	}

	@Override
	public void renunciarTarea(String idTarea) throws GisfppWorkflowException {
		try {
			servTask.unclaim(idTarea);
		} catch (ActivitiObjectNotFoundException exc1) {
			throw new GisfppWorkflowException("La tarea a la que intenta renunciar no existe.");
		}catch (Exception exc2) {
			log.error(this.getClass().getName(), exc2);
			throw new GisfppWorkflowException("Se ha producido un error al intertar renunciar a la tarea.\n"
					+ exc2.getLocalizedMessage(), exc2);
		}
		
	}

	@Override
	public void delegarTarea(String idTarea, String usuarioDestino)
			throws GisfppWorkflowException {
		try {
			servTask.delegateTask(idTarea, usuarioDestino);
		} catch (ActivitiObjectNotFoundException exc1) {
			throw new GisfppWorkflowException("La tarea que intenta delegar no existe.");
		}catch (Exception exc2) {
			log.error(this.getClass().getName(), exc2);
			throw new GisfppWorkflowException("Se ha producido un error al intertar delegar la tarea.\n"
					+ exc2.getLocalizedMessage(), exc2);
		}
		
	}

	@Override
	public void resolverTarea(String idTarea) throws GisfppWorkflowException {
		try {
			servTask.resolveTask(idTarea);
		} catch (ActivitiObjectNotFoundException exc1) {
			throw new GisfppWorkflowException("La tarea que intenta resolver no existe.");
		}catch (Exception exc2) {
			log.error(this.getClass().getName(), exc2);
			throw new GisfppWorkflowException("Se ha producido un error al intertar resolver la tarea.\n"
					+ exc2.getLocalizedMessage(), exc2);
		}
		
	}

	@Override
	public void resolverTarea(String idTarea, Map<String, Object> variables)
			throws GisfppWorkflowException {
		try {
			servTask.resolveTask(idTarea, variables);
		} catch (ActivitiObjectNotFoundException exc1) {
			throw new GisfppWorkflowException("La tarea que intenta resolver no existe.");
		}catch (Exception exc2) {
			log.error(this.getClass().getName(), exc2);
			throw new GisfppWorkflowException("Se ha producido un error al intertar resolver la tarea.\n"
					+exc2.getLocalizedMessage(), exc2);
		}
		
	}

}