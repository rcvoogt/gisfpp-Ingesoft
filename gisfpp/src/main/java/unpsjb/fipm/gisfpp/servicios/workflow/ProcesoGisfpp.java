package unpsjb.fipm.gisfpp.servicios.workflow;

import java.util.Map;

import unpsjb.fipm.gisfpp.util.GisfppException;
import unpsjb.fipm.gisfpp.util.GisfppWorkflowException;

public interface ProcesoGisfpp {
	
	/**
	 * Crea una nueva instancia de ejecución del proceso.
	 * @param entidad: entidad de negocio utilizada para crear una instancia del proceso.
	 * @return "Id" (String) que identifica de forma unívoca la instancia creada del proceso.
	 * @throws GisfppWorkflowException TODO
	 */
	public String instanciarProceso(Object entidad) throws GisfppWorkflowException;
	
	/**
	 * Crea una nueva instancia de ejecución del proceso.
	 * @param keyBusiness: clave del negocio que se asocia de forma univoca con la instancia del proceso. Por ejemplo: "id" de la 
	 * Isfpp.
	 * @param variables: variables utilizadas a traves de la ejecución del proceso.
	 * @return "Id" (String) que identifica de forma unívoca la instancia creada del proceso.
	 * @throws GisfppWorkflowException TODO
	 * @throws GisfppException
	 */
	public String instanciarProceso(String keyBusiness, Map<String, Object> variables) throws GisfppWorkflowException; 
	
	/**
	 * Devuelve el "Id" de la instancia del proceso asociado con el "keyBusiness" pasado como parámetro.	
	 * @param keyBusiness: clave del negocio que se asocia de forma univoca con la instancia del proceso. Por ejemplo: "id" de la 
	 * Isfpp.
	 * @return "Id" (String) que identifica de forma unívoca una instancia ejecutable del proceso.
	  */
	public String getIdInstanciaProceso(String keyBusiness);
	
	/**
	 * Devuelve las variables de proceso de una instancia asociada con el "keyBusiness" pasado como parámetro.	
	 * @param keyBusiness: clave del negocio que se asocia de forma univoca con la instancia del proceso. Por ejemplo: "id" de la 
	 * Isfpp.
	 * @return variables de proceso (Map <String, Object>)
	 */
	public Map<String, Object> getVariables(String keyBusinessid);
	
	/**
	 * Devuelve el valor de una variable de proceso de una instancia asociada con el "keyBusiness" pasado como parámetro.
	 * @param keyBusiness: clave del negocio que se asocia de forma univoca con la instancia del proceso. Por ejemplo: "id" de la 
	 * Isfpp.
	 * @param nombreVariable
	 * @return valor de la variable de proceso.
	 */
	public Object getVariable(String keyBusiness, String nombreVariable);
	
	public String getKeyBusiness(String idInstanciaProceso);
	
	public void completarTarea(String idTarea) throws GisfppWorkflowException;
	
	public void completarTarea(String idTarea, Map<String, Object> variables) throws GisfppWorkflowException;
	
	public void reclamarTarea(String idTarea, String usuario) throws GisfppWorkflowException;
	
	public void renunciarTarea(String idTarea) throws GisfppWorkflowException;
	
	public void delegarTarea(String idTarea, String usuarioDestino) throws GisfppWorkflowException;
	
	public void resolverTarea(String idTarea) throws GisfppWorkflowException;
	
	public void resolverTarea(String idTarea, Map<String, Object> variables) throws GisfppWorkflowException;
	
}
