package unpsjb.fipm.gisfpp.controladores.persona;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.DatoDeContacto;
import unpsjb.fipm.gisfpp.entidades.persona.TDatosContacto;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class MVDlgDatosContacto {
	private String modo;
	private DatoDeContacto datosContacto;
	private DatoDeContacto aux;
	private String titulo;

	@SuppressWarnings("unchecked")
	@Init
	@NotifyChange({ "datosContacto", "modo", "titulo" })
	public void init() {
		HashMap<String, Object> map = (HashMap<String, Object>) Executions.getCurrent().getArg();
		modo = (String) map.get("modo");
		if (modo.equals(UtilGisfpp.MOD_NUEVO)) {
			titulo = "Nuevo Dato de Contacto";
			datosContacto = new DatoDeContacto();
		} else {
			titulo = "Editar Dato de Contacto";
			aux = (DatoDeContacto) map.get("datosContacto");
			datosContacto = new DatoDeContacto();
			copiar(aux, datosContacto);
		}

	}

	public String getTitulo() {
		return titulo;
	}

	public String getModo() {
		return modo;
	}

	public List<TDatosContacto> getTipos() {
		return Arrays.asList(TDatosContacto.values());
	}

	public DatoDeContacto getDatosContacto() {
		return datosContacto;
	}

	public void setDatosContacto(DatoDeContacto datosContacto) {
		this.datosContacto = datosContacto;
	}

	private void cerrar() {
		Window dlg = (Window) Path.getComponent("/dlgDatosContacto");
		dlg.detach();
	}

	@Command("aceptar")
	public void aceptar() {
		HashMap<String, Object> argsRetorno = new HashMap<>();
		argsRetorno.put("modo", modo);
		if (modo.equals(UtilGisfpp.MOD_EDICION)) {
			copiar(datosContacto, aux);
		} else {
			argsRetorno.put("newItem", datosContacto);
		}
		BindUtils.postGlobalCommand(null, null, "retornoDlgDatosContacto", argsRetorno);
		cerrar();
	}

	@Command("cancelar")
	public void cancelar() {
		cerrar();
	}

	private void copiar(DatoDeContacto origen, DatoDeContacto destino) {
		destino.setTipo(origen.getTipo());
		destino.setValor(origen.getValor());
	}

}// fin de la clase
