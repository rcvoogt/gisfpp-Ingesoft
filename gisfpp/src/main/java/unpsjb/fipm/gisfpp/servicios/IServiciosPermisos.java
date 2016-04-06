package unpsjb.fipm.gisfpp.servicios;

import java.util.List;

import unpsjb.fipm.gisfpp.entidades.Operaciones;
import unpsjb.fipm.gisfpp.entidades.Permiso;
import unpsjb.fipm.gisfpp.entidades.Roles;

public interface IServiciosPermisos extends IServicioGenerico<Permiso, Integer> {

	public List<Operaciones> getOperacionesxRol(Roles rol) throws Exception;
	
	public List<Permiso> getPermisosxRol(Roles rol) throws Exception;
	
}	