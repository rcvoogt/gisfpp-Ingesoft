package unpsjb.fipm.gisfpp.servicios.workflow;

import java.util.Map;

import unpsjb.fipm.gisfpp.util.GisfppException;

public interface ProcesoGisfpp {
	
	/**
	 * Crea una nueva instancia de ejecución del proceso.
	 * @param entidad: entidad de negocio utilizada para crear una instancia del proceso.
	 * @return "Id" (String) que identifica de forma unívoca la instancia creada del proceso.
	 * @throws GisfppException
	 */
	public String instanciarProceso(Object entidad) throws GisfppException;
	
	/**
	 * Crea una nueva instancia de ejecución del proceso.
	 * @param keyBusiness: clave del negocio que se asocia de forma univoca con la instancia del proceso. Por ejemplo: "id" de la 
	 * Isfpp.
	 * @param variables: variables utilizadas a traves de la ejecución del proceso.
	 * @return "Id" (String) que identifica de forma unívoca la instancia creada del proceso.
	 * @throws GisfppException
	 */
	public String instanciarProceso(String keyBusiness, Map<String, Object> variables) throws GisfppException; 
	
	/**
	 * Devuelve el "Id" de la instancia del proceso asociado con el "keyBusiness" pasado como parámetro.	
	 * @param keyBusiness: clave del negocio que se asocia de forma univoca con la instancia del proceso. Por ejemplo: "id" de la 
	 * Isfpp.
	 * @return "Id" (String) que identifica de forma unívoca una instancia ejecutable del proceso.
	  */
	public String getIdInstancia(String keyBusiness);
	
	/**
	 * Devuelve las variables de proceso de una instancia asociada con el "keyBusiness" pasado como parámetro.	
	 * @param keyBusiness: clave del negocio que se asocia de forma univoca con la instancia del proceso. Por ejemplo: "id" de la 
	 * Isfpp.
	 * @return variables de proceso (Map <String, Object>)
	 */
	public Map<String, Object> getVariables(String keyBusiness);
	
	/**
	 * Devuelve el valor de una variable de proceso de una instancia asociada con el "keyBusiness" pasado como parámetro.
	 * @param keyBusiness: clave del negocio que se asocia de forma univoca con la instancia del proceso. Por ejemplo: "id" de la 
	 * Isfpp.
	 * @param nombreVariable
	 * @return valor de la variable de proceso.
	 */
	public Object getVariable(String keyBusiness, String nombreVariable);
	
}
