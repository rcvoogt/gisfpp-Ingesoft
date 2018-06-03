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

				case "onLoadEntity":
					actualizarUltimaEntrada((String) evt.getData());
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
	private void actualizarUltimaEntrada(String nuevoTexto) {
		System.out.println("Entra a actualizar?" + nuevoTexto);
		ItemBreadCrumb actual = breadcrumb.get(breadcrumb.size() - 1);
		List<Component> hijos = Selectors.find(m_hlayout, "label[value='" + actual.getTitulo() + "']");

		Component ultimo = ((Component) m_hlayout).getLastChild(); // el primero es el separador

		m_hlayout.removeChild(ultimo);

		ultimo = ((Component) m_hlayout).getLastChild(); // el segundo es el label

		m_hlayout.removeChild(ultimo);
		System.out.println("En actualizar:" + ultimo.getAttribute("value") + " class:" + ultimo.getClass());
		System.out.println("atributos" + ultimo.getAttributes());
		ultimo.setAttribute("value", "nuevoTexto");
		m_hlayout.appendChild(ultimo);
		
		Space separador = new Space();
		separador.setBar(true);
		separador.setClass(" separador");
		m_hlayout.appendChild(separador);
		

		breadcrumb.remove(actual);
		actual.setTitulo(nuevoTexto);
		breadcrumb.add(actual);

	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
}
