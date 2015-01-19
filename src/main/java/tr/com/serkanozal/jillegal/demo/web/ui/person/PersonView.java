/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */
 
package tr.com.serkanozal.jillegal.demo.web.ui.person;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PersonView extends VerticalLayout {

	public PersonView() {
		Label lbl = new Label("<center>" + "Person Panel" + "</center>", ContentMode.HTML);
		
		addComponent(lbl);
		setComponentAlignment(lbl, Alignment.MIDDLE_CENTER);
		
		setSizeFull();
	}
	
}
