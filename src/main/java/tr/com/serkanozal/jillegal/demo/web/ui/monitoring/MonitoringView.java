/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */
 
package tr.com.serkanozal.jillegal.demo.web.ui.monitoring;

import tr.com.serkanozal.jillegal.demo.web.util.SpringContextProvider;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
public class MonitoringView extends VerticalLayout {

	public MonitoringView(SpringContextProvider springContextProvider) {
		Label lbl = new Label("<center>" + "Monitoring Panel" + "</center>", ContentMode.HTML);
		
		addComponent(lbl);
		setComponentAlignment(lbl, Alignment.MIDDLE_CENTER);
		
		setStyleName(Reindeer.LAYOUT_BLUE);
		setSizeFull();
	}
	
}
