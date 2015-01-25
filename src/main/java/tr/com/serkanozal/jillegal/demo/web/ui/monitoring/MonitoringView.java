/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */
 
package tr.com.serkanozal.jillegal.demo.web.ui.monitoring;

import tr.com.serkanozal.jillegal.demo.web.service.MonitoringService;
import tr.com.serkanozal.jillegal.demo.web.util.SpringContextProvider;

import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
public class MonitoringView extends VerticalLayout {

	private Panel pnlEnvironmentInfo;
	private Panel pnlMemoryStats;
	private Panel pnlGCStats;
	private Panel pnlPersonStats;
	
	public MonitoringView(SpringContextProvider springContextProvider) {
		final MonitoringService monitoringService = springContextProvider.getBean(MonitoringService.class);
		
		VerticalLayout vl = new VerticalLayout();
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		pnlEnvironmentInfo = new Panel();
		pnlEnvironmentInfo.setCaption("Environment Info");
		pnlEnvironmentInfo.addStyleName(Reindeer.LAYOUT_BLUE);
		pnlEnvironmentInfo.setWidth("100%");
		pnlEnvironmentInfo.setHeight("150px");
		
		
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		pnlMemoryStats = new Panel();
		pnlMemoryStats.setCaption("Memory Stats");
		pnlMemoryStats.addStyleName(Reindeer.LAYOUT_BLUE);
		pnlMemoryStats.setWidth("100%");
		pnlMemoryStats.setHeight("150px");
		
		
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		pnlGCStats = new Panel();
		pnlGCStats.setCaption("GC Stats");
		pnlGCStats.addStyleName(Reindeer.LAYOUT_BLUE);
		pnlGCStats.setWidth("100%");
		pnlGCStats.setHeight("150px");
		
		
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		pnlPersonStats = new Panel();
		pnlPersonStats.setCaption("Person Stats");
		pnlPersonStats.addStyleName(Reindeer.LAYOUT_BLUE);
		pnlPersonStats.setWidth("150%");
		pnlPersonStats.setHeight("150px");
		
		
		
		/////////////////////////////////////////////////////////////////////////////////////////
				
		vl.addStyleName(Reindeer.LAYOUT_BLUE);
		vl.addComponent(pnlEnvironmentInfo);
		vl.addComponent(pnlMemoryStats);
		vl.addComponent(pnlGCStats);
		vl.addComponent(pnlPersonStats);
		
		vl.setMargin(true);
		vl.setSpacing(true);
		
		addComponent(vl);
		addStyleName(Reindeer.LAYOUT_BLUE);
		setMargin(false);
		setSpacing(false);
		setSizeFull();
	}
	
}
