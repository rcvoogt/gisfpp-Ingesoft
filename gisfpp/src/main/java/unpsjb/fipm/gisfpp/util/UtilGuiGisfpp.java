package unpsjb.fipm.gisfpp.util;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;

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
	}
	
	public static void mostrarDialogoBox(String path, Map <String,Object> args){
		
		Window dlgBox = (Window) Executions.createComponents(path, null, args);
		dlgBox.doModal();		
	}

}// fin de la clase
