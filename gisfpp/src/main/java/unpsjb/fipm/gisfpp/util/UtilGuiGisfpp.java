package unpsjb.fipm.gisfpp.util;

import java.util.Map;

import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Include;
import org.zkoss.zul.Panel;

public class UtilGuiGisfpp {

	public static final String PRM_PNL_CENTRAL = "prmPnlCentral";

	public static void loadPnlCentral(String origen, String destino, Map parametros) {
		Sessions.getCurrent().setAttribute(PRM_PNL_CENTRAL, parametros);

		Panel panel = (Panel) Path.getComponent(origen);
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
			include.setSrc(destino);
		}
	}

	public static void loadPnlCentral(String origen, String destino) {
		Panel panel = (Panel) Path.getComponent(origen);
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
			include.setSrc(destino);
		}
	}

	public static void loadPnlCentral(String pnl) {
		Include include = (Include) Path.getComponent("/panelCentro");
		include.setSrc(null);
		include.setSrc(pnl);
	}

	public static void quitarPnlCentral(String pnl) {
		Panel panel = (Panel) Path.getComponent(pnl);
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
		}
	}

}// fin de la clase
