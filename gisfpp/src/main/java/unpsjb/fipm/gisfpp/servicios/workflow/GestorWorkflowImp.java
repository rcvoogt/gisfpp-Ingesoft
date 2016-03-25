/**
 * 
 */
package unpsjb.fipm.gisfpp.servicios.workflow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.ClassClassPath;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.IdentityLinkType;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import unpsjb.fipm.gisfpp.servicios.workflow.entidades.DefinicionProceso;
import unpsjb.fipm.gisfpp.servicios.workflow.entidades.InstanciaProceso;
import unpsjb.fipm.gisfpp.util.GisfppWorkflowException;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

/**
 * @author jldevia
 *
 */
@Service("servGestionWorkflow")
public class GestorWorkflowImp implements GestorWorkflow {
	
	private RuntimeService rtService;
	private RepositoryService repoService;
	private Logger log;
	private ProcessInstanceQuery queryProInst;
	private ProcessDefinitionQuery queryProDef;
	
	@PostConstruct
	public void init(){
		log = UtilGisfpp.getLogger();
		queryProInst = rtService.createProcessInstanceQuery();
		queryProDef = repoService.createProcessDefinitionQuery();		
	}

	@Override
	public void instanciarProceso(String categoria, String operacion,
			String iniciador, String keyBusiness)	throws GisfppWorkflowException {
		try {
			List<String> procesosAInstanciar = consultarProcesosAsociados(categoria, operacion);
			if (!procesosAInstanciar.isEmpty()) {
				for (String idProceso : procesosAInstanciar) {
					/*new Thread( new Runnable() {
						
						@Override
						public void run(){
							try {
								ProcessInstance instancia = rtService.startProcessInstanceByKey(idProceso, keyBusiness);
								rtService.addUserIdentityLink(instancia.getProcessDefinitionId(), iniciador, IdentityLinkType.STARTER);
							}catch(ActivitiObjectNotFoundException exc1){
								log.error(this.getClass().getName(), exc1);
								throw new GisfppWorkflowException("La definición del proceso que se quiere instanciar no existe.\n");
							}
							catch (Exception exc2) {
								log.error(this.getClass().getName(), exc2);
								throw new GisfppWorkflowException("Se ha producido un error al intentar generar una instancia del proceso"
										+ " con id: "+idProceso+". \nMensaje: " +exc2.getLocalizedMessage(), exc2);
							}
						}
					}).start();*/
					Map<String, Object> variables = new HashMap<String, Object>();
					variables.put("usuarioSolicitante", iniciador);
					ProcessInstance instancia = rtService.startProcessInstanceByKey(idProceso, keyBusiness, variables);
				}
			}
		} catch (Exception exc1) {
			log.error(this.getClass().getName(), exc1);
			throw new GisfppWorkflowException("Se ha generado un error al intentar instanciar un proceso.", exc1);
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
		ProcessInstance instancia = queryProInst.processInstanceBusinessKey(keyBusiness, idDefinicion).singleResult();
		return instancia.getProcessInstanceId();
	}

	@Override
	public String getKeyBusiness(String idInstanciaProceso) {
		ProcessInstance instancia = queryProInst.processInstanceId(idInstanciaProceso).singleResult();
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
		return resultado;
	}
	
	@Override
	public List<InstanciaProceso> getInstanciasProcesos(String keyBusiness)
			throws GisfppWorkflowException {
		List<InstanciaProceso> resultado = new ArrayList<InstanciaProceso>();
		try {
			List<ProcessInstance> resultQuery = queryProInst.processInstanceBusinessKey(keyBusiness).list();
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
	public InstanciaProceso getInstanciaProceso(String idInstancia)
			throws GisfppWorkflowException {
		ProcessInstance instancia;
		try {
			instancia = queryProInst.processInstanceId(idInstancia).singleResult();
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
			List<IdentityLink> identidades = rtService.getIdentityLinksForProcessInstance(idInstancia);
			for (IdentityLink identity : identidades) {
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
	
	private InstanciaProceso convertir (ProcessInstance item){
		ProcessDefinition processDef = queryProDef.processDefinitionKey(item.getProcessDefinitionKey()).singleResult();
		
		InstanciaProceso itemConvertido = new InstanciaProceso();
		DefinicionProceso definicion = new DefinicionProceso();
		
		definicion.setIdDefinicion(processDef.getKey());
		definicion.setNombre(processDef.getName());
		definicion.setDescripcion(processDef.getDescription());
		definicion.setCategoria(processDef.getCategory());
		definicion.setVersion(processDef.getVersion());
		
		itemConvertido.setDefinicion(definicion);
		itemConvertido.setIdInstancia(item.getId());
		itemConvertido.setKeyBusiness(item.getBusinessKey());
		itemConvertido.setNombre(item.getName());
		itemConvertido.setSuspendido(item.isSuspended());
				
		return itemConvertido;
	}
	
	@Autowired(required=true)
	public void setRtService(RuntimeService rtService) {
		this.rtService = rtService;
	}

	@Autowired(required=true)
	public void setRepoService(RepositoryService repoService) {
		this.repoService = repoService;
	}

					
}//fin de la clase
