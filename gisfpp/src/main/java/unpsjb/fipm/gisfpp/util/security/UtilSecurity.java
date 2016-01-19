package unpsjb.fipm.gisfpp.util.security;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

import unpsjb.fipm.gisfpp.entidades.proyecto.ERolStaffProyecto;
import unpsjb.fipm.gisfpp.entidades.staff.ECargosStaffFi;

public class UtilSecurity {
	
	/**
	 * Devuelve Verdadero (true) si el usuario se ha
	 * autenticado e iniciado sesion exitosamente.
	 * @return Verdadero o Falso.
	 */
	public static boolean isLogueado(){
		String nameUser = SecurityContextHolder.getContext().getAuthentication().getName();
		if(nameUser.equals("anonymousUser")){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * Devuelve el nombre del Usuario conectado
	 * @return nombre del Usuario: String
	 */
	public static String getNombreUsuario(){
		if(isLogueado()){
			DetalleUsuario detalle = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return detalle.getUsuario().getPersona().getNombre();
		}else{
			return "Anonimo";
		}
	}
	
	/**
	 * Devuelve una lista con los roles que posee el usuario actualmente conectado.
	 * @return List<RolUsuario>
	 */
	public static List<RolUsuario> getRoles(){
		DetalleUsuario detalle = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return detalle.getRoles();
	}
	
	/**
	 * Metodo para verificar si el usuario actualmente conectado tiene permisos
	 * para poder crear un nuevo proyecto.
	 * Los perfiles autorizados para crear nuevos proyectos son:
	 * - Coordinador (Staff-FI)
	 * - Delegado Academico (Staff-FI)
	 * - Profesor (Staff-FI)
	 * @return verdadero o falso: boolean
	 */
	public static boolean autorizadoCrearProyecto(){
		boolean autorizado = isRolStaffFI(ECargosStaffFi.COORDINADOR) || isRolStaffFI(ECargosStaffFi.DELEGADO) || isRolStaffFI(ECargosStaffFi.PROFESOR);
		if (autorizado){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Metodo para verificar si el usuario actualmente conectado posee
	 * permiso para ver el detalle del Proyecto referenciado por el "id"
	 * pasado como parametro
	 * @param idProyecto: int
	 * @return verdadero o false: boolean.
	 */
	public static boolean autorizadoVerProyecto(int idProyecto){
		boolean autorizado1 = isRolStaffFI(ECargosStaffFi.PROFESOR) || isRolStaffFI(ECargosStaffFi.DELEGADO) || isRolStaffFI(ECargosStaffFi.COORDINADOR);
		boolean autorizado2 = isRolStaffProyecto(ERolStaffProyecto.MIEMBRO_STAFF, idProyecto) || isRolStaffProyecto(ERolStaffProyecto.RESPONSABLE, idProyecto);
		if(autorizado1 || autorizado2){
			return true;
		}
		return false;
	}
	
	/**
	 * Metodo para verificar si el usuario actualmente conectado posee
	 * permiso para editar o dar de baja el Proyecto referenciado por el "id"
	 * pasado como parametro
	 * @param idProyecto: int
	 * @return verdadero o falso: boolean
	 */
	public static boolean autorizadoEditarBajaProyecto(int idProyecto){
		boolean autorizado1 = isRolStaffFI(ECargosStaffFi.PROFESOR) || isRolStaffFI(ECargosStaffFi.DELEGADO) || isRolStaffFI(ECargosStaffFi.COORDINADOR);
		boolean autorizado2 = isRolStaffProyecto(ERolStaffProyecto.RESPONSABLE, idProyecto);
		if (autorizado1 || autorizado2) {
			return true;
		}
		return false;
	}
	
	/**
	 * Por medio de este metodo se puede verificar si el usuario actualmente conectado 
	 * posee el cargo, pasado como parametro, en el Staff-FI.
	 * @param cargo: ECargosStaffFi
	 * @return boolean. 
	 */
	private static boolean isRolStaffFI(ECargosStaffFi cargo){
		DetalleUsuario detalle = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (detalle.getRoles()!=null && !detalle.getRoles().isEmpty()) {
			for (RolUsuario rol : detalle.getRoles()) {
				if(rol.getTabla().equals("staff_fi") && rol.getRol().equals(cargo.name())){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Metodo para verificar que el usuario actualmente conectado posee
	 * el rol, pasado como parametro, en el Staff del Proyecto referenciado por
	 * el id, tambien pasado como parametro. 
	 * @param rol: ERolStaffProyecto
	 * @param idProyecto: int
	 * @return verdadero o false: boolean
	 */
	private static boolean isRolStaffProyecto(ERolStaffProyecto rol, int idProyecto){
		DetalleUsuario detalle = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(detalle.getRoles() !=null && !detalle.getRoles().isEmpty()){
			for (RolUsuario auxRol : detalle.getRoles()) {
				if(auxRol.getTabla().equals("proyecto") && auxRol.getRol().equals(rol.name())
						&& auxRol.getIdTabla() == idProyecto){
					return true;
				}
			}
		}
		return false;
	}
	
}//fin de la clase
