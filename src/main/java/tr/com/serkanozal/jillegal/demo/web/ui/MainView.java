/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */
 
package tr.com.serkanozal.jillegal.demo.web.ui;

import tr.com.serkanozal.jillegal.demo.web.ui.monitoring.MonitoringView;
import tr.com.serkanozal.jillegal.demo.web.ui.person.PersonView;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
public class MainView extends VerticalLayout {

	public MainView() {
		TabSheet tabSheet = new TabSheet();
		
		tabSheet.setStyleName(Reindeer.LAYOUT_BLUE);
		tabSheet.addTab(new PersonView(), "Person View");
		tabSheet.addTab(new MonitoringView(), "Monitoring View");
		tabSheet.setSizeFull();
		
		addComponent(tabSheet);
		setStyleName(Reindeer.LAYOUT_BLUE);
		setSizeFull();
	}
	
}
