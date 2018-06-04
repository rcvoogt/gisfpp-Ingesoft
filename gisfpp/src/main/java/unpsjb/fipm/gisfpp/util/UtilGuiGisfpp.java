package unpsjb.fipm.gisfpp.util;

import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.ItemBreadCrumb;

public class UtilGuiGisfpp {

	public static final String PRM_PNL_CENTRAL = "prmPnlCentral";

	public static void loadPnlCentral(String origen, String destino, Map parametros) {
		Sessions.getCurrent().setAttribute(PRM_PNL_CENTRAL, parametros);
		
		Component panel = (Component) Path.getComponent(origen);
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.detach();;
			include.setSrc(null);
			include.setSrc(destino);
		}
		
	}

	public static void loadPnlCentral(String origen, String destino) {
		Component panel = (Component) Path.getComponent(origen);
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.detach();
			include.setSrc(null);
			include.setSrc(destino);
		}
	}

	public static void loadPnlCentral(String pnl) {
		Include include = (Include) Path.getComponent("/panelCentro");
		Component panel = include.getFirstChild();
		if(panel!=null){
			panel.detach();
		}
		
		EventQueues.lookup("breadcrumb", EventQueues.DESKTOP, true)
		  .publish(new Event("onNavigate", null, new ItemBreadCrumb(pnl,traducirZul(pnl),null)));
		include.setSrc(null);
		include.setSrc(pnl);
	}

	public static void loadPnlCentral(String pnl, Map parametros){
		Sessions.getCurrent().setAttribute(PRM_PNL_CENTRAL, parametros);
		Include include = (Include) Path.getComponent("/panelCentro");
		Component panel = include.getFirstChild();
		if(panel!=null){
			panel.detach();
		}
		//BindUtils.postGlobalCommand(null, null, "agregarABreadCrumb", null);
		EventQueues.lookup("breadcrumbe", EventQueues.DESKTOP, true)
		  .publish(new Event("onNavigate", null,  new ItemBreadCrumb(pnl,traducirZul(pnl),null)));
		
		include.setSrc(null);
		include.setSrc(pnl);
	}
	
	public static void quitarPnlCentral(String pnl) {
		Component panel = (Component) Path.getComponent(pnl);
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.detach();
			include.setSrc(null);
		}
		EventQueues.lookup("breadcrumb", EventQueues.DESKTOP, true)
		  .publish(new Event("onExit", null, null));
	}
	
	public static void mostrarDialogoBox(String path, Map <String,Object> args){
		
		Window dlgBox = (Window) Executions.createComponents(path, null, args);
		dlgBox.doModal();		
	}
	
	public static String traducirZul(String zulLlamado) throws IllegalArgumentException {
		switch (zulLlamado) {
        case "vistas/workflow/bandejaActividades.zul":
            return "Bandeja de actividades";
            
        case "vistas/persona/listadoPersonas.zul":
        	return "Listado de Personas";
        case "vistas/isfpp/listadoIsfpp.zul":
        	return "ISFPPs";
        case "vistas/convocatoria/listadoConvocatoria.zul":
        	return "Convocatorias";
        case "vistas/proyecto/listaOfertaActividades.zul":
        	return "Oferta de actividades";
        case "vistas/staff/listadoStaffFI.zul":
        	return "Staff FI";
        case "vistas/persona/listadoOrganizaciones.zul":
        	return "Organizaciones";
        case "vistas/workflow/pnlMantMotorBpm.zul":
        	return "Workflows";
        case "vistas/persona/crudPersona.zul":
        	return "Persona";
        case "vistas/proyecto/listadoProyectos.zul":
        	return "Proyectos";
        case "vistas/proyecto/crudProyecto.zul":
        	return " Proyecto ";
        case "vistas/proyecto/crudSubProyecto.zul":
        	return " SubProyecto ";
        case "vistas/proyecto/crudIsfpp.zul":
        	return " Isfpp ";
        case "vistas/convocatoria/verConvocatoriaIndependiente.zul":
        	return " Convocatoria ";
        case "vistas/convocatoria/verCrearConvocatoria.zul":
        	return " Convocatoria ";
        	
        default:
            throw new IllegalArgumentException("pantalla desconocida para el breadcrumb	: " + zulLlamado);
    }
		
	}
	

}// fin de la clase
