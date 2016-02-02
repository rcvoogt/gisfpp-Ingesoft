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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.Identificador;
import unpsjb.fipm.gisfpp.entidades.persona.TIdentificador;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class MVDlgIdentificacion {

	private String modo;
	private Identificador identificador;
	private Identificador aux;
	private String titulo;

	@SuppressWarnings("unchecked")
	@Init
	@NotifyChange({ "identificador", "modo", "titulo" })
	public void init() {
		HashMap<String, Object> map = (HashMap<String, Object>) Executions.getCurrent().getArg();
		modo = (String) map.get("modo");
		if (modo.equals(UtilGisfpp.MOD_NUEVO)) {
			titulo = "Nueva Identificación";
			identificador = new Identificador();
		} else {
			titulo = "Editar Identificacion";
			aux = (Identificador) map.get("valor");
			identificador = new Identificador();
			copiar(aux, identificador);
		}

	}

	public String getTitulo() {
		return titulo;
	}

	public String getModo() {
		return modo;
	}

	public Identificador getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Identificador valor) {
		this.identificador = valor;
	}

	public List<TIdentificador> getTipos() {
		return Arrays.asList(TIdentificador.values());
	}

	private void cerrar() {
		Window dlg = (Window) Path.getComponent("/dlgIdentificacion");
		dlg.detach();
	}

	@Command("aceptar")
	public void aceptar() {
		HashMap<String, Object> argsRetorno = new HashMap<>();
		argsRetorno.put("modo", modo);
		if (modo.equals(UtilGisfpp.MOD_EDICION)) {
			copiar(identificador, aux);
		} else {
			argsRetorno.put("itemNew", identificador);
		}
		BindUtils.postGlobalCommand(null, null, "retornoDlgIdentificacion", argsRetorno);
		cerrar();
	}

	@Command("cancelar")
	public void cancelar() {
		cerrar();
	}

	private void copiar(Identificador origen, Identificador destino) {
		destino.setTipo(origen.getTipo());
		destino.setValor(origen.getValor());
	}

}// fin de la clase
