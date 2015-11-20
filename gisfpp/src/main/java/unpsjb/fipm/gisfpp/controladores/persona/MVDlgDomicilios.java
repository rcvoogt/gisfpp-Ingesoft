package unpsjb.fipm.gisfpp.controladores.persona;

import java.util.HashMap;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.Domicilio;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class MVDlgDomicilios {

	private String modo;
	private Domicilio domicilio;
	private Domicilio aux;
	private String titulo;

	@SuppressWarnings("unchecked")
	@Init
	@NotifyChange({ "domicilio", "modo", "titulo" })
	public void init() {
		HashMap<String, Object> map = (HashMap<String, Object>) Executions.getCurrent().getArg();
		modo = (String) map.get("modo");
		switch (modo) {
		case UtilGisfpp.MOD_NUEVO: {
			titulo = "Nuevo Domicilio";
			domicilio = new Domicilio();
			break;
		}
		case UtilGisfpp.MOD_EDICION: {
			aux = (Domicilio) map.get("domicilio");
			domicilio = new Domicilio();
			copiar(aux, domicilio);
			titulo = "Editar Domicilio";
			break;
		}
		}
	}

	public String getTitulo() {
		return titulo;
	}

	public String getModo() {
		return modo;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	private void cerrar() {
		Window dlg = (Window) Path.getComponent("/dlgDomicilio");
		dlg.detach();
	}

	@Command("aceptar")
	public void aceptar() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("modo", modo);
		map.put("opcion", Messagebox.OK);
		if (modo.equals(UtilGisfpp.MOD_EDICION)) {
			copiar(domicilio, aux);
			map.put("domicilio", aux);
		} else {
			map.put("domicilio", domicilio);
		}
		BindUtils.postGlobalCommand(null, null, "retornoDlgDomicilios", map);
		cerrar();
	}

	@Command("cancelar")
	public void cancelar() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("opcion", Messagebox.CANCEL);
		BindUtils.postGlobalCommand(null, null, "retornoDlgDomicilios", map);
		cerrar();
	}

	private void copiar(Domicilio origen, Domicilio destino) {
		destino.setAltura(origen.getAltura());
		destino.setCalle(origen.getCalle());
		destino.setPiso(origen.getPiso());
		destino.setNum_dpto(origen.getNum_dpto());
		destino.setCod_postal(origen.getCod_postal());
		destino.setLocalidad(origen.getLocalidad());
		destino.setProvincia(origen.getProvincia());
	}

}// fin de la clase
