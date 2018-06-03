package unpsjb.fipm.gisfpp.util;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import unpsjb.fipm.gisfpp.servicios.persona.IServicioPF;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPJ;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServicioSubProyecto;
import unpsjb.fipm.gisfpp.servicios.convocatoria.IServiciosConvocado;
import unpsjb.fipm.gisfpp.servicios.convocatoria.IServiciosConvocatoria;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosProyecto;
import unpsjb.fipm.gisfpp.servicios.staff.IServiciosStaffFI;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorTareas;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflow;

public class MySpringUtil implements ApplicationContextAware {

	private static ApplicationContext appContext;
	
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		appContext = arg0;
	}
	
	public static IServiciosIsfpp getServicioIsfpp(){
		return (IServiciosIsfpp) appContext.getBean("servIsfpp");
	}

	public static IServiciosProyecto getServicioProyecto() {
		return (IServiciosProyecto) appContext.getBean("servProyecto");
	}
	
	public static IServicioSubProyecto getServicioSubProyecto() {
		return(IServicioSubProyecto) appContext.getBean("servSubProyecto");
	}
	
	public static IServicioUsuario getServicioUsuario(){
		return (IServicioUsuario) appContext.getBean("servUsuario");
	}
	
	public static IServicioPF getServicioPersonaFisica() {
		return (IServicioPF) appContext.getBean("servPersonaFisica");
	}
	
	public static IServicioPJ getServicioPersonaJuridica() {
		return (IServicioPJ) appContext.getBean("servPersonaJuridica");
	}
	
	public static IServiciosConvocatoria getServicioConvocatoria() {
		return (IServiciosConvocatoria) appContext.getBean("servConvocatoria");
	}
	
	public static IServiciosConvocado getServicioConvocado() {
		return (IServiciosConvocado) appContext.getBean("servConvocado");
	}
	
	public static IServiciosStaffFI getServicioStaffFi() {
		return (IServiciosStaffFI) appContext.getBean("servStaffFI");
	}
	
	public static GestorWorkflow getServicioGestorWkFl() {
		return (GestorWorkflow) appContext.getBean("servGestionWorkflow");
	}
	
	public static GestorTareas getServicioGestorTareas() {
		return (GestorTareas) appContext.getBean("servGestionTareas");
	}
	
	/**
	 * Devuelve una referencia al Servicio Runtime del motor de workflow Activiti.
	 * @return RunTimeService
	 */
	public static RuntimeService getRunTimeService() {
		return (RuntimeService) appContext.getBean("runtimeService");
	}
	
	/**
	 * Devuelve una referencia al Servicio Repository del motor de workflow Activiti.
	 * @return RepositoryService
	 */
	public static RepositoryService getRepositoryService() {
		return (RepositoryService) appContext.getBean("repoService");
	}
	
	/**
	 * Devuelve una referencia al Servicio Task del motor de workflow Activiti.
	 * @return TaskService
	 */
	public static TaskService getTaskService() {
		return (TaskService) appContext.getBean("taskService");
	}
	
	/**
	 * Devuelve una referencia al Servicio History del motor de workflow Activiti.
	 * @return HistoryService
	 */
	public static HistoryService getHistoryService() {
		return (HistoryService) appContext.getBean("historyService");
	}
	
	public static ApplicationContext getApplicationContext(){
		return appContext;
	}
}
