package unpsjb.fipm.gisfpp.controladores;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.Button;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.controladores.integracion.IControladorIntegracion;
import unpsjb.fipm.gisfpp.entidades.ItemBreadCrumb;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.Persona;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.entidades.proyecto.EstadoProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.TipoProyecto;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServicioSubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosProyecto;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

public class MVMigracion {

	private Logger log;
	private String titulo = "Migracion de datos desde SIU Guarani";

	private IControladorIntegracion controllerIntegracion;
	
	@Init
	public void init() throws Exception {
		log = UtilGisfpp.getLogger();
		controllerIntegracion = (IControladorIntegracion) SpringUtil.getBean("contIntegracion");
	}
	
	public String getTitulo() {
		return titulo;
	}

	@Command("migrar")
	public void migrar() {
		if( controllerIntegracion!= null) {
			controllerIntegracion.migrar();
		}
		else {
			System.out.println("Controller en null!!!");
		}
		
		
	}

	
	
	

}// fin de la clase
