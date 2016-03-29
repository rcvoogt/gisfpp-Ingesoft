package unpsjb.fipm.gisfpp.servicios.workflow;

import java.util.List;
import java.util.Map;

import unpsjb.fipm.gisfpp.servicios.workflow.entidades.InfoTarea;
import unpsjb.fipm.gisfpp.util.GisfppWorkflowException;

public interface GestorTareas {
	
	public static final String ORDEN_PRIORIDAD = "porPrioridad";
	public static final String ORDEN_FECHA_VENC = "porFechaVencimiento";
	public static final String ORDEN_NOMBRE_TAREA = "porNombreTarea";
	
	public List<InfoTarea> getTareasAsignadas(String usuario, String ordenadoPor, boolean asc) throws GisfppWorkflowException;
	
	public List<InfoTarea> getTareasPropuestas(String usuario, String ordenadoPor, boolean asc) throws GisfppWorkflowException;
	
	public List<InfoTarea> getTareasDelegadas(String usuario, String ordenadoPor, boolean asc) throws GisfppWorkflowException; 
	
	public List<InfoTarea> getTareasRealizadas (String usuario) throws GisfppWorkflowException;
	
	public void completarTarea(String idTarea) throws GisfppWorkflowException;
	
	public void completarTarea(String idTarea, Map<String, Object> variables) throws GisfppWorkflowException;
	
	public void reclamarTarea(String idTarea, String usuario) throws GisfppWorkflowException;
	
	public void delegarTarea(String idTarea, String usuarioDestino) throws GisfppWorkflowException;
	
	public void resolverTarea(String idTarea) throws GisfppWorkflowException;
	
	public void resolverTarea(String idTarea, Map<String, Object> variables) throws GisfppWorkflowException;
	
}
