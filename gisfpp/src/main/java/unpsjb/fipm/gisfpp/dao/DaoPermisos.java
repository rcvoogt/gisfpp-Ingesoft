package unpsjb.fipm.gisfpp.dao;

import java.util.List;

import unpsjb.fipm.gisfpp.entidades.Operaciones;
import unpsjb.fipm.gisfpp.entidades.Permiso;
import unpsjb.fipm.gisfpp.entidades.Roles;

public interface DaoPermisos extends DaoGenerico<Permiso, Integer> {

	public List<Operaciones> getOperacionesxRol(Roles rol) throws Exception;
	
	public List<Permiso> getPermisosxRol(Roles rol) throws Exception;
	
}
