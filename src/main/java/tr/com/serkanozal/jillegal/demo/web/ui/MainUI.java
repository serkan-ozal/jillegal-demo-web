/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */
 
package tr.com.serkanozal.jillegal.demo.web.ui;

import javax.servlet.annotation.WebServlet;

import tr.com.serkanozal.jillegal.demo.web.util.SpringContextProvider;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class MainUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MainUI.class, widgetset = "tr.com.serkanozal.jillegal.demo.web.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    	
    }

    @Override
    protected void init(VaadinRequest request) {
    	setSizeFull();
        setContent(new MainView(new SpringContextProvider(VaadinServlet.getCurrent().getServletContext())));   
    }

}
