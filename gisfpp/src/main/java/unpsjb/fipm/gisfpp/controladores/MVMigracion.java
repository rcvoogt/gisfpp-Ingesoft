package unpsjb.fipm.gisfpp.controladores;

import org.slf4j.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zul.Messagebox;

import unpsjb.fipm.gisfpp.controladores.integracion.IControladorIntegracion;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

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

		if (controllerIntegracion != null) {
			try {
				controllerIntegracion.migrar();
				Messagebox.show("La integracion a finalizado correctamente", "Finalizado", Messagebox.OK,
						Messagebox.INFORMATION);

			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("Error", "Error", Messagebox.OK, Messagebox.ERROR);

			}

		} else {
			System.out.println("Controller en null!!!");
		}

	}

}// fin de la clase
