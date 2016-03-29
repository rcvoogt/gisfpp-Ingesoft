package unpsjb.fipm.gisfpp.servicios.workflow;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import unpsjb.fipm.gisfpp.servicios.workflow.entidades.InstanciaProceso;
import unpsjb.fipm.gisfpp.util.GisfppWorkflowException;

public interface GestorWorkflow {

	/**
	 * Genera una nueva isntancia del proceso (o procesos) asociado con la categoria y operacion establecidos como parámetros. 
	 * @param categoria (String): 
	 * @param operacion (String):
	 * @param iniciador (String):
	 * @param keyBusiness (String):
	 * @throws GisfppWorkflowException
	 */
	public void instanciarProceso(String categoria, String operacion, String iniciador, String keyBusiness) throws GisfppWorkflowException;
	
	/**
	 * Devuelve las variables de proceso de una instancia de proceso referenciado con el "id" pasado como parámetro.	
	 * @param id: "id" de la instancia del proceso.
	 * @return variables de proceso (Map <String, Object>)
	 */
	public Map<String, Object> getVariables(String idInstanciaProceso);
	
	/**
	 * Devuelve el valor de la variable de proceso con nombre "nombreVariable" de la instancia de proceso referenciada por 
	 * "idInstanciaProceso".
	 * @param id: "id" de la instancia del proceso.
	 * @param nombreVariable
	 * @return valor de la variable de proceso.
	 */
	public Object getVariable(String idInstanciaProceso, String nombreVariable);
	
	public void setVariables(String idInstanciaProceso, Map<String, Object> variables) throws GisfppWorkflowException;
	
	/**
	 * Devuelve el "Id" de la instancia del proceso asociado con el "keyBusiness" y el "idDefinicion"pasados como parámetro.	
	 * @param keyBusiness(String): clave del negocio que se asocia de forma univoca con la instancia del proceso. Por ejemplo: "id" de la 
	 * Isfpp.
	 * @param idDefinicion(String): Identificador de la definicion del proceso.
	 * @return "Id" (String) que identifica de forma unívoca una instancia ejecutable del proceso.
	  */
	public String getIdInstanciaProceso(String keyBusiness, String idDefinicion);
	
	public String getKeyBusiness(String idInstanciaProceso);
	
	public List<String> consultarProcesosAsociados(String categoria, String operacion) throws ParserConfigurationException, SAXException, IOException;
	
	public List<InstanciaProceso> getInstanciasProcesos(String keyBusiness) throws GisfppWorkflowException;
	
	public InstanciaProceso getInstanciaProceso(String idInstancia) throws GisfppWorkflowException;
	
	public String getIniciadorProceso(String idInstancia) throws GisfppWorkflowException;
	
	/**
	 * Devuelve el listado de procesos activos en los cuales el usuario pasado como parámetro tiene cierto 
	 * grado de participación en los mismos.
	 * @param "id" del usuario
	 * @return listado de instancias de procesos.
	 * @throws GisfppWorkflowException
	 */
	public List<InstanciaProceso> getProcesosActivos(String idUsuario) throws GisfppWorkflowException;
	
	/**
	 * Devuelve el listado de procesos ya ejecutados en los cuales el usuario pasado como parametro tuvo algún grado
	 * de participación.
	 * @param "id" del usuario
	 * @return listado de procesos ejecutados.
	 * @throws GisfppWorkflowException
	 */
	public List<InstanciaProceso> getProcesosFinalizados(String idUsuario) throws GisfppWorkflowException;
	
}
