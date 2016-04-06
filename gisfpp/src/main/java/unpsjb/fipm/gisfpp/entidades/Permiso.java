package unpsjb.fipm.gisfpp.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="permisos", uniqueConstraints=@UniqueConstraint(columnNames={"rol","operacion"}))
public class Permiso implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id; 
	
	@Enumerated (EnumType.STRING)
	@Column(nullable=false)
	private Roles rol;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable=false)
	private Operaciones operacion;
	
	@Column(length=150)
	private String descripcion;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha_creacion;
	
	private String usuario_creador;

	public Permiso() {
		super();
	}

	public Permiso(Roles rol, Operaciones operacion, String descripcion,
			Date fecha_creacion, String usuario) {
		super();
		this.rol = rol;
		this.operacion = operacion;
		this.descripcion = descripcion;
		this.fecha_creacion = fecha_creacion;
		this.usuario_creador = usuario;
	}

	public Integer getId() {
		return id;
	}

	protected void setId(Integer id) {
		this.id = id;
	}

	@NotNull(message="Debe especificar un \"Rol\" para generar el correspondiente permiso.")
	public Roles getRol() {
		return rol;
	}

	public void setRol(Roles rol) {
		this.rol = rol;
	}

	@NotNull(message="Debe especificar una \"Operación\" para generar el correspondiente permiso.")
	public Operaciones getOperacion() {
		return operacion;
	}

	public void setOperacion(Operaciones operacion) {
		this.operacion = operacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	public String getUsuario_creador() {
		return usuario_creador;
	}

	public void setUsuario_creador(String usuario_creador) {
		this.usuario_creador = usuario_creador;
	}

}
