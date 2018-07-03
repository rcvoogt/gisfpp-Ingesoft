package unpsjb.fipm.gisfpp.integracion.dao;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.integracion.entidades.PersonaAdapter;

public interface IDaoPersonaAdapter extends DaoGenerico<PersonaAdapter, Integer>{

	int existe(String legajo);

	PersonaAdapter recuperarxLegajo(String legajo);

	
}
