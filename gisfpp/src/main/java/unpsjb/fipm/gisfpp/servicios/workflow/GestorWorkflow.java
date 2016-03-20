package unpsjb.fipm.gisfpp.servicios.workflow;

import java.util.Map;

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
	 * Devuelve el "Id" de la instancia del proceso asociado con el "keyBusiness" pasado como parámetro.	
	 * @param keyBusiness: clave del negocio que se asocia de forma univoca con la instancia del proceso. Por ejemplo: "id" de la 
	 * Isfpp.
	 * @return "Id" (String) que identifica de forma unívoca una instancia ejecutable del proceso.
	  */
	public String getIdInstanciaProceso(String keyBusiness);
	
	public String getKeyBusiness(String idInstanciaProceso);
	
}
