package unpsjb.fipm.gisfpp.entidades.proyecto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocable;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocado;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.convocatoria.TipoConvocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.util.MiembroExistenteException;

@Entity
@Table(name = "isfpp")
public class Isfpp implements Serializable, Convocable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "titulo", nullable = false, length = 80)
	private String titulo;

	@Column(name = "objetivos", length = 500)
	private String objetivos;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_inicio")
	private Date inicio;
                                                                                                                       
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_fin")
	private Date fin;

	@Lob
	private String detalle;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "subProyectoId", nullable = false, foreignKey=@ForeignKey(name="fk_subproyecto_isfpp"))
	private SubProyecto perteneceA;

	@Enumerated(EnumType.STRING)
	private EEstadosIsfpp estado;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="isfpp")
	private Set<MiembroStaffIsfpp> staff;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="isfpp")
	private List<Convocatoria> convocatorias;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade= {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name="practicantes", joinColumns=@JoinColumn(name="isfppId"), inverseJoinColumns=@JoinColumn(name="personaId")
			, foreignKey=@ForeignKey(name="fk_isfpp_practicantes"), inverseForeignKey=@ForeignKey(name="fk_persona_practicantes"))
	private Set<PersonaFisica> practicantes;

	public Isfpp() {
		inicio = new Date();
		fin = new Date();
		estado = EEstadosIsfpp.GENERADA;
		staff = new HashSet<MiembroStaffIsfpp>();
		practicantes = new HashSet<PersonaFisica>();
	}

	public Isfpp(SubProyecto perteneceA, String titulo, String objetivos, Date inicio, Date fin, String detalle
			, Set<MiembroStaffIsfpp> staff, Set<PersonaFisica> practicantes) {
		super();
		this.titulo = titulo;
		this.objetivos = objetivos;
		this.inicio = (inicio==null)?new Date():inicio;
		this.fin = (fin==null)?new Date():fin;
		this.detalle = detalle;
		this.perteneceA = perteneceA;
		this.estado = EEstadosIsfpp.GENERADA;
		this.staff = (staff == null)? new HashSet<>(): staff;
		this.practicantes = (practicantes==null)? new HashSet<>(): practicantes;
	}

	public List<Convocatoria> getConvocatorias() {
		return convocatorias;
	}

	public void setConvocatorias(List<Convocatoria> convocatorias) {
		this.convocatorias = convocatorias;
	}
	
	public void addMiembroStaff(MiembroStaffIsfpp miembro){
		if(miembro!=null){
			staff.add(miembro);
		}
	}

	public void addPracticante(PersonaFisica practicante){
		if(practicante!=null){
			practicantes.add(practicante);
		}
	}

	public String getDetalle() {
		return detalle;
	}

	public EEstadosIsfpp getEstado() {
		return estado;
	}

	public Date getFin() {
		return fin;
	}

	public Integer getId() {
		return id;
	}

	public Date getInicio() {
		return inicio;
	}

	@Length(max = 500, message = "El campo \"Objetivos\" de la ISFPP no debe superar los 500 caracteres.")
	public String getObjetivos() {
		return objetivos;
	}

	@NotNull(message="La Isfpp debe estar asignada a un \"Sub-Proyecto\".")
	public SubProyecto getPerteneceA() {
		return perteneceA;
	}

	public Set<PersonaFisica> getPracticantes() {
		return practicantes;
	}

	public Set<MiembroStaffIsfpp> getStaff() {
		return staff;
	}

	@NotBlank(message = "Debe especificarle un \"Ti�tulo\" a la ISFPP.")
	@Length(max = 80, message = "El \"Titulo\" de la ISFPP no debe superar los 80 caracteres.")
	public String getTitulo() {
		return titulo;
	}
	

	@AssertTrue(message = "La \"fecha fin\" debe ser posterior a la \"fecha inicio\".")
	private boolean isFechaFinValida1() {
		if (fin.after(inicio)) {
			return true;
		} else {
			return false;
		}
	}

	@AssertTrue(message = "La \"fecha de finalizacion\" de la ISFPP debe ser anterior a la fecha de finalizacion del Proyecto.")
	private boolean isFechaFinValida2() {
		if (fin.before(perteneceA.getPerteneceA().getFecha_fin())) {
			return true;
		} else {
			return false;
		}
	}

	public MiembroStaffIsfpp getTutorAcademico(){
		for (MiembroStaffIsfpp miembro : staff) {
			if(miembro.getRol() == ERolStaffIsfpp.TUTOR_ACADEMICO){
				return miembro;
			}
		}
		return null;
	}
	
	public MiembroStaffIsfpp getTutorExterno(){
		for (MiembroStaffIsfpp miembro : staff) {
			if (miembro.getRol() == ERolStaffIsfpp.TUTOR_EXTERNO) {
				return miembro;
			}
		}
		return null;
	}
	
	public void removerMiembroStaff(MiembroStaffIsfpp miembro){
		if(miembro!=null){
			staff.remove(miembro);
		}
	}
	
	public void removerPracticante(PersonaFisica practicante){
		if(practicante!=null){
			practicantes.remove(practicante);
		}
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
	public void setFin(Date fin) {
		this.fin = fin;
	}
	
	protected void setId(Integer id) {
		this.id = id;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	
	public void setObjetivos(String objetivos) {
		this.objetivos = objetivos;
	}
	
	public void setPerteneceA(SubProyecto sp) {
		this.perteneceA = sp;
	}

	protected void setPracticantes(Set<PersonaFisica> practicantes) {
		this.practicantes = practicantes;
	}

	protected void setStaff(Set<MiembroStaffIsfpp> staff) {
		this.staff = staff;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Isfpp))
			return false;
		Isfpp other = (Isfpp) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public List<PersonaFisica> getResponsables(){
		List<PersonaFisica> resultado = new ArrayList<PersonaFisica>();
		for (MiembroStaffIsfpp miembro : staff) {
			if (miembro.getRol() == ERolStaffIsfpp.TUTOR_ACADEMICO) {
				resultado.add(miembro.getMiembro());
			}
		}
		return resultado;
	}
	
	@Override
	public String getTipoConvocatoria() throws Exception {
		return TipoConvocatoria.ISFPP.toString();
	}

	@Override
	public void setConvocados(Set<Convocado> nuevosPracticantes) throws MiembroExistenteException,Exception {
		for(Convocado c: nuevosPracticantes) {	
			if(!this.practicantes.add(c.getPersona())) throw new MiembroExistenteException(c.getPersona());
		}
		
	}

	@Override
	public boolean isAsignador(PersonaFisica persona) throws Exception {
		List<PersonaFisica> responsables = getResponsables();
		return responsables.contains(persona);
	}

	@Override
	public List<PersonaFisica> getMiembros() {
		List<PersonaFisica> miembros = new ArrayList<PersonaFisica>();
		for(MiembroStaffIsfpp  m : getStaff()){
			miembros.add(m.getMiembro());
		}
		miembros.addAll(practicantes);
		return miembros;
	}
	
}// fin de la clase
