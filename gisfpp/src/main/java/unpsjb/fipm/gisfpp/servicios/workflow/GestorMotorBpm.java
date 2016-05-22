package unpsjb.fipm.gisfpp.servicios.workflow;

import java.util.List;

import unpsjb.fipm.gisfpp.entidades.workflow.DefinicionProceso;
import unpsjb.fipm.gisfpp.entidades.workflow.InstanciaProceso;
import unpsjb.fipm.gisfpp.util.GisfppWorkflowException;

public interface GestorMotorBpm {
	
		public List<DefinicionProceso> getDefinicionProcesos() throws GisfppWorkflowException;
		
		public void activarDefinicionProceso (String idDefinicion) throws GisfppWorkflowException;
		
		public void suspenderDefinicionProceso (String idDefinicion) throws GisfppWorkflowException;
		
		public void eliminarDefinicionProceso (String idDespliegue, boolean cascada) throws GisfppWorkflowException;
		
		public List<InstanciaProceso> getProcesosEnEjecucion() throws GisfppWorkflowException;
		
		public void activarInstanciaProceso(String idInstancia) throws GisfppWorkflowException;
		
		public void suspenderInstanciaProceso(String idInstancia) throws GisfppWorkflowException;
		
		public void eliminarInstanciaProceso(String idInstancia) throws GisfppWorkflowException;
}
