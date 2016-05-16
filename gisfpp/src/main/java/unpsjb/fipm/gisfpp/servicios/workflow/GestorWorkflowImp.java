/**
 * 
 */
package unpsjb.fipm.gisfpp.servicios.workflow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.IdentityLinkType;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import unpsjb.fipm.gisfpp.entidades.workflow.DefinicionProceso;
import unpsjb.fipm.gisfpp.entidades.workflow.InstanciaActividad;
import unpsjb.fipm.gisfpp.entidades.workflow.InstanciaProceso;
import unpsjb.fipm.gisfpp.util.GisfppWorkflowException;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

/**
 * @author jldevia
 *
 */
@Service("servGestionWorkflow")
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS)
public class GestorWorkflowImp implements GestorWorkflow {
	
	private RuntimeService rtService;
	private RepositoryService repoService;
	private HistoryService historyService;
	private Logger log;
	
	@PostConstruct
	public void init(){
		log = UtilGisfpp.getLogger();
	}

	@Override
	public void instanciarProceso(String categoria, String operacion,
			String iniciador, String keyBusiness)	throws GisfppWorkflowException {
		String keyDefinicionProceso = null;
		try {
			List<String> procesosAInstanciar = consultarProcesosAsociados(categoria, operacion);
			if (!procesosAInstanciar.isEmpty()) {
				for (String keyDefProc : procesosAInstanciar) {
					keyDefinicionProceso = keyDefProc;
					Map<String, Object> variables = new HashMap<String, Object>();
					variables.put("usuarioIniciador", iniciador);
					try {
						rtService.startProcessInstanceByKey(keyDefProc, keyBusiness, variables);
					} catch(ActivitiObjectNotFoundException exc1){
						log.error("Alerta Workflow: No se encontro ningún proceso registrado con el id: "+ keyDefinicionProceso);
					}
				}
			}
		}catch (Exception exc2) {
			throw new GisfppWorkflowException("Error al instanciar proceso.\n Mensaje: "+exc2.getMessage());
		}
	}

	@Override
	public void setVariables(String idInstancia, Map<String, Object> variables)
			throws GisfppWorkflowException {
		if(idInstancia==null){
			throw new IllegalArgumentException("El \"Id\" de la instancia de proceso pasado como parámetro es \"null\"."
					+ "\nClase:"+this.getClass().getName()+" Método: setVariables.");
		}
		try {
			rtService.setVariables(idInstancia, variables);
		}catch(ActivitiObjectNotFoundException exc1){
			log.error(this.getClass().getName(), exc1);
			throw new GisfppWorkflowException("La instancia de proceso a la cual le quiere establecer las variables no existe. \n"
					+ "Instancia Proceso: "+idInstancia,exc1);
		}catch (Exception exc2) {
			throw new GisfppWorkflowException("Error generado al tratar de asignar variables a la instancia de un proceso. \n"
					+ "Instancia Proceso: "+idInstancia, exc2);
		}

	}

	@Override
	public Map<String, Object> getVariables(String idInstancia)
			throws GisfppWorkflowException {
		if(idInstancia==null){
			throw new IllegalArgumentException("El \"Id\" de la instancia de proceso pasado como parámetro es \"null\"."
					+ "\nClase:"+this.getClass().getName()+" Método: getVariables.");
		}
		try {
			return rtService.getVariables(idInstancia);
		}catch(ActivitiObjectNotFoundException exc1){
			log.error(this.getClass().getName(), exc1);
			throw new GisfppWorkflowException("La instancia de proceso de la cual quiere obtener sus variables no existe. \n"
					+ "Instancia Proceso: "+idInstancia,exc1);
		}catch (Exception exc2) {
			throw new GisfppWorkflowException("Error generado al tratar de obtener las variables de la instancia de proceso. \n"
					+ "Instancia Proceso: "+idInstancia, exc2);
		} 
		
	}

	@Override
	public Object getVariable(String idInstancia, String nombreVariable) {
		if(idInstancia==null){
			throw new IllegalArgumentException("El \"Id\" de la instancia de proceso pasado como parámetro es \"null\"."
					+ "\nClase:"+this.getClass().getName()+" Método: getVariable.");
		}
		try {
			return rtService.getVariable(idInstancia, nombreVariable);
		}catch(ActivitiObjectNotFoundException exc1){
			log.error(this.getClass().getName(), exc1);
			throw new GisfppWorkflowException("La instancia de proceso de la cual se quiere obtener el valor de la variable, no existe. \n"
					+ "Instancia Proceso: "+idInstancia+". Nombre variable: "+nombreVariable,exc1);
		}catch (Exception exc2) {
			throw new GisfppWorkflowException("Error generado al tratar de obtener el valor de la variable de proceso. \n"
					+ "Instancia Proceso: "+idInstancia+". Nombre variable: "+nombreVariable, exc2);
		}
	}

	@Override
	public String getIdInstanciaProceso(String keyBusiness, String idDefinicion) {
		ProcessInstanceQuery query = rtService.createProcessInstanceQuery(); 
		ProcessInstance instancia = query.processInstanceBusinessKey(keyBusiness, idDefinicion).singleResult();
		return instancia.getProcessInstanceId();
	}

	@Override
	public String getKeyBusiness(String idInstanciaProceso) {
		ProcessInstanceQuery query = rtService.createProcessInstanceQuery();
		ProcessInstance instancia = query.processInstanceId(idInstanciaProceso).singleResult();
		return instancia.getBusinessKey();
	}
	
	@Override
	public List<String> consultarProcesosAsociados(String categoria, String operacion) throws ParserConfigurationException,
		SAXException, IOException {
		ClassPathResource path = new ClassPathResource("definicionProcesos/Procesos.xml");
		List<String> resultado = new ArrayList<String>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document documento = builder.parse(path.getFile());
						
		NodeList categorias = documento.getElementsByTagName("categoria");
		for (int i = 0; i < categorias.getLength(); i++) {
			Element itemCategoria = (Element) categorias.item(i);
			if (itemCategoria.getAttribute("nombre").equals(categoria)) {
				NodeList operaciones = itemCategoria.getElementsByTagName("operacion");
				for (int j = 0; j < operaciones.getLength(); j++) {
					Element itemOperacion = (Element) operaciones.item(j);
					if (itemOperacion.getAttribute("nombre").equals(operacion)) {
							NodeList procesos = itemOperacion.getElementsByTagName("workflow");
							for (int k = 0; k < procesos.getLength(); k++) {
								Element itemProceso = (Element) procesos.item(k);
								resultado.add(itemProceso.getAttribute("id"));
							}
							return resultado;
					}
				}
			}
		}
		for (String proceso : resultado) {
			System.out.println(proceso);
		}
		return resultado;
	}
	
	@Override
	public List<InstanciaProceso> getInstanciasProcesos(String keyBusiness)
			throws GisfppWorkflowException {
		ProcessInstanceQuery query = rtService.createProcessInstanceQuery();
		List<InstanciaProceso> resultado = new ArrayList<InstanciaProceso>();
		try {
			List<ProcessInstance> resultQuery = query.processInstanceBusinessKey(keyBusiness).list();
			if (resultQuery!=null && !resultQuery.isEmpty()) {
				for (ProcessInstance instance : resultQuery) {
					resultado.add(convertir(instance));
				}
			}
		} catch (Exception exc1) {
			log.error(this.getClass().getName(), exc1);
			throw new GisfppWorkflowException("Error de workflow. Clase: " + this.getClass().getName()+" Método: "
					+ "getInstanciasProcesos(keyBusiness)", exc1);
		}
		return resultado;
	}
	
	@Override
	public List<String> getNombresInstancias(String keyBusiness)
			throws GisfppWorkflowException {
		List<String> resultado = new ArrayList<String>();
		ProcessInstanceQuery query = rtService.createProcessInstanceQuery();
		
		List<ProcessInstance> instancias = query.processInstanceBusinessKey(keyBusiness).list();
		for (ProcessInstance processInstance : instancias) {
			DefinicionProceso definicion = cargarDefinicionProceso(processInstance.getProcessDefinitionId());
			resultado.add(definicion.getNombre());
		}
		return resultado;
	}

	@Override
	public InstanciaProceso getInstanciaProceso(String idInstancia)
			throws GisfppWorkflowException {
		ProcessInstance instancia;
		try {
			ProcessInstanceQuery query = rtService.createProcessInstanceQuery();
			instancia = query.processInstanceId(idInstancia).singleResult();
		} catch (Exception exc1) {
			log.error(this.getClass().getName(), exc1);
			throw new GisfppWorkflowException("Error de workflow. Clase: " + this.getClass().getName()+" Método: "
					+ "getInstanciaProceso(idInstancia)", exc1);
		}
		if(instancia!=null){
			return convertir(instancia);
		}
		return new InstanciaProceso();
	}
	
	@Override
	public String getIniciadorProceso(String idInstancia)
			throws GisfppWorkflowException {
		try {
			List<HistoricIdentityLink> identidades = historyService.getHistoricIdentityLinksForProcessInstance(idInstancia);
			for (HistoricIdentityLink identity : identidades) {
				if (identity.getType().equals(IdentityLinkType.STARTER)) {
					return identity.getUserId();
				}
			}
		} catch (Exception exc1) {
			log.error("Clase: "+this.getClass().getName()+"- Metodo: getIniciadorProceso(idInstancia)" , exc1);
			throw new GisfppWorkflowException("Se ha generado un error al intentar obtener el iniciador de la instancia de proceso"
					+ " con id:"+idInstancia,exc1);
		}
		return "";
	}
	
	@Override
	public List<InstanciaProceso> getProcesosActivos(String idUsuario)
			throws GisfppWorkflowException {
		List<InstanciaProceso> procesos = new ArrayList<InstanciaProceso>();
		try {
			HistoricProcessInstanceQuery qry = historyService.createHistoricProcessInstanceQuery();
			List<HistoricProcessInstance> resultQuery = qry.involvedUser(idUsuario).unfinished()
					.orderByProcessInstanceStartTime().desc().list();
			for (HistoricProcessInstance instancia : resultQuery) {
				procesos.add(convertir2(instancia));
			}
		}catch(GisfppWorkflowException exc1){
			log.error("Clase:" +this.getClass().getName()+"- Metodo: getProcesosActivos(String)", exc1);
			throw new GisfppWorkflowException("Se ha generado un error al intertar recuperar los procesos activos en los cuales"
					+ " el usuario "+idUsuario+" tiene algún grado de participación.");
		}
		catch (Exception exc2) {
			log.error("Clase:" +this.getClass().getName()+"- Metodo: getProcesosActivos(String)", exc2);
			throw new GisfppWorkflowException("Se ha generado un error al intertar recuperar los procesos activos en los cuales"
					+ " el usuario "+idUsuario+" tiene algún grado de participación.", exc2);
		}
		return procesos;
	}

	@Override
	public List<InstanciaProceso> getProcesosFinalizados(String idUsuario)
			throws GisfppWorkflowException {
		List<InstanciaProceso> procesos = new ArrayList<InstanciaProceso>();
		try {
			HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();
			List<HistoricProcessInstance> resulQry = query.involvedUser(idUsuario).finished().
					orderByProcessInstanceStartTime().desc().list();
			for (HistoricProcessInstance instance : resulQry) {
				procesos.add(convertir2(instance));
			}
		}catch(GisfppWorkflowException exc1){
			log.error("Clase:" +this.getClass().getName()+"- Metodo: getProcesosFinalizados(idUsuario)", exc1);
			throw new GisfppWorkflowException("Se ha generado un error al intertar recuperar los procesos finalizados en los cuales"
					+ " el usuario "+idUsuario+" tuvo algún grado de participación.");
		}
		catch (Exception exc2) {
			log.error("Clase:" +this.getClass().getName()+"- Metodo: getProcesosFinalizados(idUsuario)", exc2);
			throw new GisfppWorkflowException("Se ha generado un error al intertar recuperar los procesos finalizados en los cuales"
					+ " el usuario "+idUsuario+" tuvo algún grado de participación.", exc2);
		}
		return procesos;
	}
	
	@Override
	public long getCantidadProcesosFinalizados(String idUsuario)
			throws GisfppWorkflowException {
		try {
			HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();
			return query.involvedUser(idUsuario).finished().count();
		} catch (Exception exc) {
			log.error("Clase: "+this.getClass().getName()+" - Metodo: long getCantidadProcesosFinalizados(String idUsuario)", exc);
			throw new GisfppWorkflowException("Se ha producido un error al intentar consultar la cantidad de procesos finalizados"
					+ " para el usuario conectado.", exc);
		}
	}
	
	private InstanciaProceso convertir (ProcessInstance item) throws ActivitiException{
		HistoricProcessInstanceQuery qry = historyService.createHistoricProcessInstanceQuery();
		HistoricProcessInstance histProcIns = qry.processInstanceId(item.getId()).singleResult();
		
		InstanciaProceso itemConvertido = new InstanciaProceso();
		
		itemConvertido.setIdInstancia(item.getId());
		itemConvertido.setKeyBusiness(item.getBusinessKey());
		itemConvertido.setSuspendido(item.isSuspended());
		itemConvertido.setInicia(histProcIns.getStartTime());
		itemConvertido.setFinaliza(histProcIns.getEndTime());
		itemConvertido.setFinalizada((histProcIns.getEndTime()==null)?false:true);
		itemConvertido.setIniciador(getIniciadorProceso(item.getId()));
				
		return itemConvertido;
	}
	
	private InstanciaProceso convertir2 (HistoricProcessInstance item) throws ActivitiException, GisfppWorkflowException{
		ProcessInstanceQuery qryProcInst = rtService.createProcessInstanceQuery();
		ProcessInstance procInst = qryProcInst.processInstanceId(item.getId()).singleResult(); 
		HistoricActivityInstanceQuery qryHisInsActv = historyService.createHistoricActivityInstanceQuery();
		
		InstanciaProceso itemConvertido = new InstanciaProceso();
		DefinicionProceso definicion = cargarDefinicionProceso(item.getProcessDefinitionId());
		
		List<HistoricActivityInstance> instancesActv = qryHisInsActv.processInstanceId(item.getId()).list(); 
		List<InstanciaActividad> historialActividades = new ArrayList<InstanciaActividad>();
		if (instancesActv!=null && !instancesActv.isEmpty()) {
			for (HistoricActivityInstance instance : instancesActv) {
				if(!instance.getActivityType().equals("exclusiveGateway") && !instance.getActivityType().equals("parallelGateway")
						&& !instance.getActivityType().equals("inclusiveGateway") && !instance.getActivityType().equals("eventBasedGateway")){
					historialActividades.add(cargarInstanciaActividad(instance));
				}
			}
		}
		
		itemConvertido.setActividades(historialActividades);
		itemConvertido.setDefinicion(definicion);
		itemConvertido.setIdInstancia(item.getId());
		itemConvertido.setKeyBusiness(item.getBusinessKey());
		itemConvertido.setIniciador(getIniciadorProceso(item.getId()));
		itemConvertido.setInicia(item.getStartTime());
		itemConvertido.setFinaliza(item.getEndTime());
		itemConvertido.setSuspendido((procInst==null)?false:procInst.isSuspended());
		itemConvertido.setFinalizada((item.getEndTime()==null)?false:true);
		
		return itemConvertido;
	}
	
	private DefinicionProceso cargarDefinicionProceso(String idDefProc){
		ProcessDefinitionQuery queryProcDef = repoService.createProcessDefinitionQuery();
		ProcessDefinition procDef = queryProcDef.processDefinitionId(idDefProc).singleResult();
		
		DefinicionProceso definicion = new DefinicionProceso();
		
		definicion.setIdDefinicion(procDef.getKey());
		definicion.setNombre(procDef.getName());
		definicion.setDescripcion(procDef.getDescription());
		definicion.setVersion(procDef.getVersion());
		
		return definicion;
	}
	
	private InstanciaActividad cargarInstanciaActividad(HistoricActivityInstance histInstActv){
		InstanciaActividad instancia = new InstanciaActividad();
	
		instancia.setIdActividad(histInstActv.getActivityId());
		instancia.setIdInstanciaProceso(histInstActv.getProcessInstanceId());
		instancia.setIdTarea(histInstActv.getTaskId());
		instancia.setAsignado(histInstActv.getAssignee());
		instancia.setNombre(histInstActv.getActivityName());
		instancia.setTipo(histInstActv.getActivityType());
		instancia.setInicia(histInstActv.getStartTime());
		instancia.setFinaliza(histInstActv.getEndTime());
		
		return instancia;
	}
	
	@Autowired(required=true)
	public void setRtService(RuntimeService rtService) {
		this.rtService = rtService;
	}

	@Autowired(required=true)
	public void setRepoService(RepositoryService repoService) {
		this.repoService = repoService;
	}

	@Autowired(required=true)
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
	
					
}//fin de la clase
