package unpsjb.fipm.gisfpp.servicios.proyecto;

import java.util.List;

import org.springframework.stereotype.Service;

import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.TipoProyecto;

@Service
public class ServiceProyectoImp implements ServiceProyecto {

	private List<Proyecto> proyectos;
	private int cuenta;

	public ServiceProyectoImp() {
		cuenta = 1;
		proyectos.add(new Proyecto(cuenta, "Proyecto 1", "Descripción Proyecto 1", TipoProyecto.EMPRESA, null, null));
		cuenta = +1;
		proyectos.add(new Proyecto(cuenta, "Proyecto 2", "Descripción Proyecto 2", TipoProyecto.EXTENSION, null, null));
		cuenta = +1;
		proyectos.add(new Proyecto(cuenta, "Proyecto 3", "Descripción Proyecto 3", TipoProyecto.EMPRESA, null, null));
		cuenta = +1;
		proyectos.add(new Proyecto(cuenta, "Proyecto 4", "Descripción Proyecto 4", TipoProyecto.INTERNO, null, null));
		cuenta = +1;
		proyectos.add(
				new Proyecto(cuenta, "Proyecto 5", "Descripción Proyecto 5", TipoProyecto.INVESTIGACION, null, null));
	}

	@Override
	public List<Proyecto> getProyectos(String campoBusqueda, String patronBusqueda) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Proyecto> getAllProyectos() throws Exception {
		return proyectos;
	}

	@Override
	public Integer crearProyecto(Proyecto proyecto) throws Exception {
		cuenta = +1;
		proyecto.setNum_proy(cuenta);
		proyectos.add(proyecto);
		return cuenta;
	}

	@Override
	public void editarProyecto(Proyecto proyecto) throws Exception {

	}

	@Override
	public Proyecto verProyecto(Integer id) throws Exception {

		Proyecto resultado = null;

		for (Proyecto proyecto : proyectos) {
			if (proyecto.getNum_proy() == id) {
				resultado = proyecto;
			}
		}
		return resultado;
	}

	@Override
	public void bajaProyecto(Proyecto proyecto) throws Exception {
		// TODO Auto-generated method stub

	}

}// fin de la clase
