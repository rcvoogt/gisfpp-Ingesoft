package unpsjb.fipm.gisfpp.servicios.integracion;


import unpsjb.fipm.gisfpp.integracion.entidades.PersonaAdapter;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;

public interface IServicioPersonaAdapter extends IServicioGenerico<PersonaAdapter, Integer>{

	int existe(int legajo) throws Exception;
}
