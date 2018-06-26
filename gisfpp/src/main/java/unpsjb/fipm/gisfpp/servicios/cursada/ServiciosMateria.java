package unpsjb.fipm.gisfpp.servicios.cursada;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import unpsjb.fipm.gisfpp.dao.cursada.IDaoMateria;
import unpsjb.fipm.gisfpp.entidades.cursada.Materia;

public class ServiciosMateria implements IServiciosMateria{
	
	private IDaoMateria dao;

	@Override
	public Integer persistir(Materia instancia) throws Exception {
		return dao.crear(instancia);
	}

	@Override
	public void editar(Materia instancia) throws Exception {
		dao.actualizar(instancia);
	}

	@Override
	public void eliminar(Materia instancia) throws Exception {
		dao.eliminar(instancia);
	}

	@Override
	public Materia getInstancia(Integer id) throws Exception {
		return dao.recuperarxId(id);
	}

	@Override
	public List<Materia> getListado() throws Exception {
		return dao.recuperarTodo();
	}
	
	@Autowired
	public void setDao(IDaoMateria dao) {
		this.dao = dao;
	}

	@Override
	public int actualizarOguardar(Materia instancia) throws Exception {
		dao.actualizarOguardar(instancia);
		return instancia.getId();
	}

}
