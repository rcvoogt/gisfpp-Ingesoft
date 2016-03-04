package unpsjb.fipm.gisfpp.controladores.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.servicios.workflow.BandejaDeTareas;
import unpsjb.fipm.gisfpp.servicios.workflow.SolicitudNuevaIsfpp;
import unpsjb.fipm.gisfpp.servicios.workflow.entidades.InfoHistorialTarea;
import unpsjb.fipm.gisfpp.servicios.workflow.entidades.InfoTarea;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

public class MVBandejaTareas {

	private BandejaDeTareas servBandejaTareas ;
	private List<InfoTarea> tareasAsignadas;
	private List<InfoTarea> tareasPropuestas;
	private List<InfoTarea> tareasDelegadas;
	private List<InfoHistorialTarea> tareasRealizadas;
	
	private Logger log;
	private Usuario usuarioConectado;
	
	private String tituloTabAsignadas;
	private String tituloTabPropuestas;
	private String tituloTabDelegadas;
	private String tituloTabRealizadas;
	
	@Init
	@NotifyChange({"tareasAsignadas", "tareasPropuestas", "tareasDelegadas", "tareasRealizadas"
		, "tituloPanel", "tituloTabAsignadas", "tituloTabPropuestas", "tituloTabDelegadas", "tituloTabRealizadas"})
	public void init(){
		
		usuarioConectado = UtilSecurity.getUsuarioConectado();
		log = UtilGisfpp.getLogger();
		
		servBandejaTareas = (BandejaDeTareas) SpringUtil.getBean("servBandejaTareas");
		tareasAsignadas = servBandejaTareas.getTareasAsignado(usuarioConectado.getNickname(), BandejaDeTareas.ORDEN_FECHA_VENC, true);
		tareasPropuestas = servBandejaTareas.getTareasCandidato(usuarioConectado.getNickname(), BandejaDeTareas.ORDEN_FECHA_VENC, true);
		tareasDelegadas = servBandejaTareas.getTareasDelegadas(usuarioConectado.getNickname());
		tareasRealizadas = servBandejaTareas.getTareasConcluidas(usuarioConectado.getNickname());
		
		tituloTabAsignadas = "Asignadas " + "(" + tareasAsignadas.size() + ")";
		tituloTabPropuestas = "Propuestas " + "(" + tareasPropuestas.size() + ")";
		tituloTabDelegadas = "Delegadas " + "(" + tareasDelegadas.size() + ")";
		tituloTabRealizadas = "Realizadas " + "(" + tareasRealizadas.size() + ")";
		
	}

	public List<InfoTarea> getTareasAsignadas() {
		return tareasAsignadas;
	}

	public List<InfoTarea> getTareasPropuestas() {
		return tareasPropuestas;
	}

	public List<InfoTarea> getTareasDelegadas() {
		return tareasDelegadas;
	}

	public List<InfoHistorialTarea> getTareasRealizadas() {
		return tareasRealizadas;
	}

	public String getTituloTabAsignadas() {
		return tituloTabAsignadas;
	}

	public String getTituloTabPropuestas() {
		return tituloTabPropuestas;
	}

	public String getTituloTabDelegadas() {
		return tituloTabDelegadas;
	}

	public String getTituloTabRealizadas() {
		return tituloTabRealizadas;
	}
	
	@GlobalCommand("refrescarTareasAsignadas")
	@NotifyChange({"tareasAsignadas", "tituloTabAsignadas"})
	public void refrescarTareasAsignadas(){
		tareasAsignadas = servBandejaTareas.getTareasAsignado(usuarioConectado.getNickname(), 
				BandejaDeTareas.ORDEN_FECHA_VENC, true);
		tituloTabAsignadas = "Asignadas " + "(" + tareasAsignadas.size() + ")";
	}
	
	@GlobalCommand("refrescarTareasPropuestas")
	@NotifyChange({"tareasPropuestas", "tituloTabPropuestas"})
	public void refrescarTareasPropuestas(){
		tareasPropuestas = servBandejaTareas.getTareasCandidato(usuarioConectado.getNickname(), 
				BandejaDeTareas.ORDEN_FECHA_VENC, true);
		tituloTabPropuestas = "Propuestas " + "(" + tareasPropuestas.size() + ")";
	}
	
	@GlobalCommand("refrescarTareasDelegadas")
	@NotifyChange({"tareasDelegadas", "tituloTabDelegadas"})
	public void refrescarTareasDelegadas(){
		tareasDelegadas = servBandejaTareas.getTareasDelegadas(usuarioConectado.getNickname());
		tituloTabDelegadas = "Delegadas " + "(" + tareasDelegadas.size() + ")";
	}
	
	@GlobalCommand("refrescarTareasRealizadas")
	@NotifyChange({"tareasRealizadas", "tituloTabRealizadas"})
	public void refrescarTareasRealizadas(){
		tareasRealizadas = servBandejaTareas.getTareasConcluidas(usuarioConectado.getNickname());
		tituloTabRealizadas = "Realizadas " + "(" + tareasRealizadas.size() + ")";
	}
	
	@Command("realizarTarea")
	public void realizarTarea(@BindingParam ("item") InfoTarea tarea){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tarea", tarea);
		Window dlg = (Window) Executions.createComponents("vistas/workflow/"+tarea.getIdFormulario(), null, parametros);
		dlg.doModal();
	}
	
	@Command("salir")
	public void salir(){
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/pnlBandejaTareas");
	}
	
}//fin de la clase
