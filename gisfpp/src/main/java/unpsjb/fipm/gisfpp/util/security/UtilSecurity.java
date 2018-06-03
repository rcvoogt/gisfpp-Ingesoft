package unpsjb.fipm.gisfpp.util.security;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import unpsjb.fipm.gisfpp.entidades.Operaciones;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocable;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;

public class UtilSecurity {
	
	/**
	 * Método utilizado para generar un usuario por defecto para la persona
	 * pasada como parámetro. Se utiliza el numero de documento como "nickname"
	 * o si la persona no tiene establecido un numero de doc. se genera un "nickname"
	 * con el primer nombre de la Persona mas un número aleatorio. La contraseña
	 * por defecto es "gisfpp".
	 * @param persona (PersonaFisica)
	 * @return usuario default (Usuario)
	 */
	public static Usuario generarUsuario(PersonaFisica persona){
		Usuario usuarioGenerado = new Usuario();
		if(persona!=null){
			if((persona.getDni()!=null) && (!persona.getDni().isEmpty())){
				usuarioGenerado.setNickname(persona.getDni());
			}else{
				String nicknameDefault ="";
				int aleatorio = (int)(Math.random()*1000);
				String nombre = (persona.getNombre() == null || persona.getNombre().isEmpty())?"gisfpp":persona.getNombre();
				StringTokenizer tokens = new StringTokenizer(nombre);
				nicknameDefault = tokens.nextToken().toLowerCase() + aleatorio;
				usuarioGenerado.setNickname(nicknameDefault);
			}
			usuarioGenerado.setPassword("gisfpp");
			return usuarioGenerado;
		}else{
			return null;
		}
	}
	
	/**
	 * Devuelve el objeto Persona que representa al Usuario actualmente conectado.
	 * @return entidad Persona: Persona
	 */
	public static Usuario getUsuarioConectado(){
		if(isLogueado()){
			DetalleUsuario detalle = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return detalle.getUsuario();
		}else{
			return null;
		}
	}
	
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
	 * Devuelve el nickname (Nombre de Usuario) del usuario actualmente conectado.
	 * @return nickname (String)
	 */
	public static String getNickName() {
		Authentication usuarioAutenticado = SecurityContextHolder.getContext().getAuthentication();
		if (usuarioAutenticado == null) {
			return "UsuarioSinAutenticar";
		}
		return usuarioAutenticado.getName();
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
	 * Devuelve una lista con los permisos que posee el usuario (actualmente conectado) sobre la aplicación.
	 * @return conjunto de operaciones (Set)
	 */
	public static Set<Operaciones> getPermisos() {
		DetalleUsuario detalle = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return detalle.getOperacionesAutorizadas();
	}
	
	/**
	 * Devuelve "verdadero" o "falso" dependiendo de si el usurio conectado posee el permiso correspondiente (según su rol) para
	 * ejecutar la operación proporcionada como parámetro.
	 * @param operacion a verificar
	 * @return Verdadero o Falso.
	 * @throws Exception
	 */
	public static boolean isAutorizado(String operacion) throws Exception{
		DetalleUsuario detalle = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		EnumSet<Operaciones> autorizaciones = detalle.getOperacionesAutorizadas();
		
		return autorizaciones.contains(Operaciones.valueOf(operacion));
	}
	
	/**
	 * Devuelve "verdadero" o "falso" dependiendo de si el usuario conectado posee o no el permiso correspondiente para 
	 * ejecutar la operación(parámetro) sobre la entidad(parámetro) con id(parámetro).
	 * @param operacion (String)
	 * @param entidad (String)
	 * @param id (Integer)
	 * @return True o False
	 * @throws Exception
	 */
	public static boolean isAutorizado2(String operacion, String entidad, Integer id) throws Exception{
		DetalleUsuario detalle = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<RolUsuario> roles = detalle.getRoles();
		EnumSet<Operaciones> autorizaciones = detalle.getOperacionesAutorizadas();
		String entidadMayuscula = entidad.toUpperCase();
				
		if (!autorizaciones.contains(Operaciones.valueOf(operacion))) {
			return false;
		}
		
		switch (entidadMayuscula) {
		case "PROYECTO":{
			for (RolUsuario rol : roles) {
				if(rol.getTabla().equals(entidadMayuscula) && rol.getIdTabla()==id){
					return true;
				}
			}
			break;
		}
		case "ISFPP":{
			for (RolUsuario rol : roles) {
				if (rol.getTabla().equals(entidadMayuscula) && rol.getIdTabla() == id) {
					return true;
				}
			}
			break;
		}
		}
		return false;
	}
	
//	public static boolean isResponsableConvocable(Convocable convocable) {
//		PersonaFisica persona;
//		try {
//			persona = serv
//			return convocable.isAsignador(persona);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//	}
	
}//fin de la clase
