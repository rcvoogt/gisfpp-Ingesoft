package unpsjb.fipm.gisfpp.controladores;

import java.util.LinkedList;
import java.util.List;

import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Space;

import unpsjb.fipm.gisfpp.entidades.ItemBreadCrumb;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class ControllerBreadcrumb extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	List<ItemBreadCrumb> breadcrumb;

	@Wire
	private Hlayout m_hlayout;

	private Label nodo;

	public ControllerBreadcrumb() {
		// TODO Auto-generated constructor stub
		breadcrumb = new LinkedList();
		EventQueues.lookup("breadcrumb", EventQueues.DESKTOP, true).subscribe(new EventListener() {
			public void onEvent(Event evt) {
				switch (evt.getName()) {
				case "onNavigate":
					// para obtener los datos del evento: evt.getData()
					ItemBreadCrumb llamado = (ItemBreadCrumb) evt.getData();
					agregarABreadCrumb(llamado);
					break;
				case "onExit":
					limpiarBreadCrumb();
					break;

				case "volver":
					volver();
					break;
				default:
					break;
				}

			}
		});

	}

	@NotifyChange("m_hlayout")
	public void agregarABreadCrumb(ItemBreadCrumb llamado) {

		if (llamado.getArgsLlamada() == null) {
			limpiarBreadCrumb();
		}

		nodo = new Label();
		nodo.setValue(llamado.getTitulo());
		nodo.setClass("breadcrumb-item");
		breadcrumb.add(llamado);

		m_hlayout.appendChild(nodo);

		Space separador = new Space();
		separador.setBar(true);
		separador.setClass(" separador");
		m_hlayout.appendChild(separador);

		System.out.println("lo llama?");
	}
	
	@NotifyChange("m_hlayout")
	private void limpiarBreadCrumb() {
		List<Component> hijos = Selectors.find(m_hlayout, ".breadcrumb-item");
		hijos.addAll(Selectors.find(m_hlayout, ".separador"));

		for (Component component : hijos) {
			m_hlayout.removeChild(component);
		}
		breadcrumb.clear();

	}

	@NotifyChange("m_hlayout")
	private void volver() {
		//Volver consta de borrar las ultimas dos entradas, ya que como llama al constructor nuevamente,
		//	manda un agregar de la pantalla a la que esta yendo
		//
		m_hlayout.getLastChild().detach();
		m_hlayout.getLastChild().detach();
		breadcrumb.remove(breadcrumb.size()-1);
		breadcrumb.remove(breadcrumb.size()-1);

	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
}
