package unpsjb.fipm.gisfpp.servicios.workflow;

import java.util.List;
import java.util.Map;

import unpsjb.fipm.gisfpp.entidades.workflow.InfoTarea;
import unpsjb.fipm.gisfpp.util.GisfppWorkflowException;

public interface GestorTareas {
	
	public static final String ORDEN_PRIORIDAD = "porPrioridad";
	public static final String ORDEN_FECHA_VENC = "porFechaVencimiento";
	public static final String ORDEN_NOMBRE_TAREA = "porNombreTarea";
	
	public List<InfoTarea> getTareasAsignadas(String usuario, String ordenadoPor, boolean asc) throws GisfppWorkflowException;
	
	public List<InfoTarea> getTareasPropuestas(String usuario, String ordenadoPor, boolean asc) throws GisfppWorkflowException;
	
	public List<InfoTarea> getTareasDelegadas(String usuario, String ordenadoPor, boolean asc) throws GisfppWorkflowException; 
	
	public List<InfoTarea> getTareasRealizadas (String usuario) throws GisfppWorkflowException;
	
	public void reclamarTarea(InfoTarea tarea, String usuario) throws GisfppWorkflowException;
	
	public void delegarTarea(InfoTarea tarea, String usuarioDestino) throws GisfppWorkflowException;
	
	public void tratarTarea(InfoTarea tarea) throws GisfppWorkflowException;
	
	public void tratarTarea(InfoTarea tarea, Map<String, Object> variables) throws GisfppWorkflowException;
	
}
