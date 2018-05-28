package unpsjb.fipm.gisfpp.controladores.convocatoria;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocable;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocado;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.servicios.convocatoria.IServiciosConvocable;
import unpsjb.fipm.gisfpp.servicios.convocatoria.IServiciosConvocado;
import unpsjb.fipm.gisfpp.servicios.convocatoria.IServiciosConvocatoria;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;
import unpsjb.fipm.gisfpp.util.MiembroExistenteException;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

public class MVDlgAsignarConvocados {
	private IServiciosConvocado servConvocado;
	private IServiciosConvocatoria servConvocatoria;
	private IServicioUsuario servUsuario;
	private IServiciosConvocable servConvocable;
	private ListModelList list;
	private String titulo;
	private Convocable item;
	private Convocatoria convocatoria;
	private String tipoConvocable;
	private List<Convocado> convocados;
	private Convocado convocado;
	private Set practicantes;
	private Boolean checked;
	private Map<String, Object> args;

	@Init
	@NotifyChange("convocados")
	public void init() {
		// String titulo = item.getTituloConvocable();
		// servConvocado = (IServiciosConvocado) SpringUtil.getBean("servConvocado");
		// practicantes = new HashSet<Convocado>();
		args = (Map<String, Object>) Executions.getCurrent().getArg();
		servConvocatoria = (IServiciosConvocatoria) SpringUtil.getBean("servConvocatoria");
		servUsuario = (IServicioUsuario) SpringUtil.getBean("servUsuario");
		servConvocable = (IServiciosConvocable) SpringUtil.getBean("servConvocable");
		convocatoria = (Convocatoria) args.get("convocatoria");
		// convocable = args.get("convocable");
		try {
			convocados = servConvocatoria.getConvocadosAceptadores(convocatoria.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// convocados = new ArrayList<Convocado>();
		// PersonaFisica p1 = new PersonaFisica("Jose Arza");
		// p1.setId(1);
		// convocados.add(new Convocado(null, p1));
		// PersonaFisica p2 = new PersonaFisica("Juan Arza");
		// p2.setId(2);
		// convocados.add(new Convocado(null, p2));
		// PersonaFisica p3 = new PersonaFisica("Pablo Martinez");
		// p3.setId(3);
		// convocados.add(new Convocado(null, p3));
		list = new ListModelList<Convocado>();
		list.addAll(convocados);
		list.setMultiple(true);
	}

	public Convocable getItem() {
		return item;
	}

	public Convocatoria getConvocatoria() {
		return convocatoria;
	}

	public String getTipoConvocable() {
		return tipoConvocable;
	}

	public List<Convocado> getConvocados() {
		return convocados;
	}

	public Convocado getConvocado() {
		return convocado;
	}

	@SuppressWarnings("unchecked")
	@Command("asignar")
	public void asignar() {
		Set<Convocado> nuevosPracticantes = (Set<Convocado>) getPracticantes();
		Messagebox.show("Desea realmente asignar esta persona al staff?", "Gisfpp: Eliminando Persona", 
				Messagebox.YES+Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
					
		@Override
		public void onEvent(Event event) throws MiembroExistenteException,Exception {
			try {
				servConvocatoria.asignar(nuevosPracticantes,convocatoria.getConvocable());
				Clients.showNotification("Convocados "+ practicantes + " Asignados", Clients.NOTIFICATION_TYPE_INFO,
						null, "top_right", 3500);
			} catch(MiembroExistenteException me) {
				Clients.showNotification(me.getMessage(), Clients.NOTIFICATION_TYPE_INFO,
						null, "top_right", 3500);
			}catch (Exception e) {
				Clients.showNotification("No se pudieron asignar los convocados "+ practicantes + " Asignados", Clients.NOTIFICATION_TYPE_ERROR,
					null, "top_right", 3500);
			}	
		}
		});
	}

	public Set getPracticantes() {
		return practicantes;
	}

	@NotifyChange
	public void setPracticantes(Set practicantes) {
		this.practicantes = practicantes;
	}

	@NotifyChange
	public ListModelList getList() {
		return list;
	}

	public void setList(ListModelList list) {
		this.list = list;
	}

	@Command
	@NotifyChange("practicantes")
	public void pick(@BindingParam("checked") boolean isPicked, @BindingParam("picked") Convocado item) {
		if (isPicked) {
			practicantes.add((Convocado) item);
		} else {
			practicantes.remove((Convocado) item);
		}
	}

	@NotifyChange({ "list", "checked" })
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	@Command
	public void show() {
		if (practicantes != null)
			System.out.println("select item count = " + practicantes.size());
	}
	@NotifyChange("item")
	public boolean isConvocador() {
		PersonaFisica personaFisica;
		Convocable convocable;
		try {
			personaFisica = servUsuario.getUsuario(UtilSecurity.getNickName()).getPersona();
			return item.isAsignador(personaFisica);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
