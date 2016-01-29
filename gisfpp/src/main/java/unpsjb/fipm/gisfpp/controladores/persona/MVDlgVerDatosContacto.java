package unpsjb.fipm.gisfpp.controladores.persona;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPF;

public class MVDlgVerDatosContacto {

	private PersonaFisica persona;
	private IServicioPF servicio;
	
	@Init
	@NotifyChange("persona")
	public void init() throws Exception{
		Map<String, Object> args = new HashMap<>();
		servicio = (IServicioPF) SpringUtil.getBean("servPersonaFisica");
		args = (Map<String, Object>) Executions.getCurrent().getArg();
		PersonaFisica arg1 = (PersonaFisica) args.get("itemPersona");
		persona = servicio.getInstancia(arg1.getId());
	}

	public PersonaFisica getPersona() {
		return persona;
	}
	
}
