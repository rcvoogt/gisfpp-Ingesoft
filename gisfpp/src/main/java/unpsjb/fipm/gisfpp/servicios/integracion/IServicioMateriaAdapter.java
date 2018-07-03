package unpsjb.fipm.gisfpp.servicios.integracion;

import unpsjb.fipm.gisfpp.entidades.cursada.Materia;
import unpsjb.fipm.gisfpp.integracion.entidades.MateriaAdapter;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;

public interface IServicioMateriaAdapter extends IServicioGenerico<MateriaAdapter, Integer>{
	public int actualizarOguardar(MateriaAdapter instancia) throws Exception;
	/**
	 * 
	 * @param codigoMateria
	 * @return id si existe, materia en gisfpp; si no existe retorna null
	 * @throws Exception
	 */
	int existe(String codigoMateria) throws Exception;
	public Materia getMateriaxCodigo(String materia);

}
