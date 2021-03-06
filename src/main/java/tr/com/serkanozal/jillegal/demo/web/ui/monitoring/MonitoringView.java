/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */
 
package tr.com.serkanozal.jillegal.demo.web.ui.monitoring;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import tr.com.serkanozal.jillegal.demo.web.domain.EnvironmentInfo;
import tr.com.serkanozal.jillegal.demo.web.domain.GCStats;
import tr.com.serkanozal.jillegal.demo.web.domain.MemoryStats;
import tr.com.serkanozal.jillegal.demo.web.domain.ObjectStats;
import tr.com.serkanozal.jillegal.demo.web.domain.PersonStats;
import tr.com.serkanozal.jillegal.demo.web.service.MonitoringService;
import tr.com.serkanozal.jillegal.demo.web.util.SpringContextProvider;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
public class MonitoringView extends VerticalLayout {

	private Timer monitoringRefresherTimer;
	
	@SuppressWarnings("deprecation")
	public MonitoringView(SpringContextProvider springContextProvider) {
		final MonitoringService monitoringService = springContextProvider.getBean(MonitoringService.class);
		
		VerticalLayout vl = new VerticalLayout();
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		Panel pnlEnvironmentInfo = new Panel();
		pnlEnvironmentInfo.setCaption("Environment Info");
		pnlEnvironmentInfo.addStyleName(Reindeer.LAYOUT_BLUE);
		pnlEnvironmentInfo.setWidth("100%");
		
		GridLayout lytEnvironmentInfo = new GridLayout(6,  4);
		lytEnvironmentInfo.setMargin(true);
		lytEnvironmentInfo.setSpacing(true);
		lytEnvironmentInfo.setSizeFull();
		lytEnvironmentInfo.addStyleName(Reindeer.LAYOUT_BLUE);
		
		EnvironmentInfo environmentInfo = monitoringService.getEnvironmentInfo();
		
		final Date startTime = monitoringService.getStartTime();
		final Label lblPassedTimeValue = new Label();
		
		lytEnvironmentInfo.addComponent(new Label("<b>Start Time: </b>", ContentMode.HTML), 0, 0);
		lytEnvironmentInfo.addComponent(new Label(startTime.toGMTString()), 1, 0);
		lytEnvironmentInfo.addComponent(new Label("<b>Passed Time: </b>", ContentMode.HTML), 2, 0);
		lytEnvironmentInfo.addComponent(lblPassedTimeValue, 3, 0);
		
		lytEnvironmentInfo.addComponent(new Label("<b>OS Name: </b>", ContentMode.HTML), 0, 1);
		lytEnvironmentInfo.addComponent(new Label(environmentInfo.getOsName()), 1, 1);
		lytEnvironmentInfo.addComponent(new Label("<b>OS Version: </b>", ContentMode.HTML), 2, 1);
		lytEnvironmentInfo.addComponent(new Label(environmentInfo.getOsVersion()), 3, 1);
		lytEnvironmentInfo.addComponent(new Label("<b>OS Architecture: </b>", ContentMode.HTML), 4, 1);
		lytEnvironmentInfo.addComponent(new Label(environmentInfo.getOsArch()), 5, 1);
		
		lytEnvironmentInfo.addComponent(new Label("<b>JVM Name: </b>", ContentMode.HTML), 0, 2);
		lytEnvironmentInfo.addComponent(new Label(environmentInfo.getJvmName()), 1, 2);
		lytEnvironmentInfo.addComponent(new Label("<b>JVM Version: </b>", ContentMode.HTML), 2, 2);
		lytEnvironmentInfo.addComponent(new Label(environmentInfo.getJvmVersion()), 3, 2);
		lytEnvironmentInfo.addComponent(new Label("<b>JVM Vendor: </b>", ContentMode.HTML), 4, 2);
		lytEnvironmentInfo.addComponent(new Label(environmentInfo.getJvmVendor()), 5, 2);
		
		lytEnvironmentInfo.addComponent(new Label("<b>Java Vendor: </b>", ContentMode.HTML), 0, 3);
		lytEnvironmentInfo.addComponent(new Label(environmentInfo.getJavaVendor()), 1, 3);
		lytEnvironmentInfo.addComponent(new Label("<b>Java Version: </b>", ContentMode.HTML), 2, 3);
		lytEnvironmentInfo.addComponent(new Label(environmentInfo.getJavaVersion()), 3, 3);
		/*
		lytEnvironmentInfo.addComponent(new Label("<b>Java Spec Version: </b>", ContentMode.HTML), 4, 3);
		lytEnvironmentInfo.addComponent(new Label(environmentInfo.getJavaSpecVersion()), 5, 3);
		lytEnvironmentInfo.addComponent(new Label("<b>Java Runtime Version: </b>", ContentMode.HTML), 6, 3);
		lytEnvironmentInfo.addComponent(new Label(environmentInfo.getJavaRuntimeVersion()), 7, 3);
		*/
		
		pnlEnvironmentInfo.setContent(lytEnvironmentInfo);
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		Panel pnlMemoryStats = new Panel();
		pnlMemoryStats.setCaption("Memory Stats");
		pnlMemoryStats.addStyleName(Reindeer.LAYOUT_BLUE);
		pnlMemoryStats.setWidth("100%");
		
		GridLayout lytMemoryStats = new GridLayout(6, 4);
		lytMemoryStats.setMargin(true);
		lytMemoryStats.setSpacing(true);
		lytMemoryStats.setSizeFull();
		lytMemoryStats.addStyleName(Reindeer.LAYOUT_BLUE);
		
		final MemoryStats memoryStats = monitoringService.getMemoryStats();
		
		final Label lblTotalPhysicalMemory = 
				new Label(String.valueOf(memoryStats.getTotalPhysicalMemory() / (1024 * 1024)));
		final Label lblFreePhysicalMemory = 
				new Label(String.valueOf(memoryStats.getFreePhysicalMemory() / (1024 * 1024)));
		
		lytMemoryStats.addComponent(new Label("<b>Total Physical Memory (MB): </b>", ContentMode.HTML), 0, 0);
		lytMemoryStats.addComponent(lblTotalPhysicalMemory, 1, 0);
		lytMemoryStats.addComponent(new Label("<b>Free Physical Memory (MB): </b>", ContentMode.HTML), 2, 0);
		lytMemoryStats.addComponent(lblFreePhysicalMemory, 3, 0);
		
		final Label lblTotalSwapSpace = 
				new Label(String.valueOf(memoryStats.getTotalSwapSpace() / (1024 * 1024)));
		final Label lblFreeSwapSpace = 
				new Label(String.valueOf(memoryStats.getFreeSwapSpace() / (1024 * 1024)));
		
		lytMemoryStats.addComponent(new Label("<b>Total Swap Space (MB): </b>", ContentMode.HTML), 0, 1);
		lytMemoryStats.addComponent(lblTotalSwapSpace, 1, 1);
		lytMemoryStats.addComponent(new Label("<b>Free Swap Space (MB): </b>", ContentMode.HTML), 2, 1);
		lytMemoryStats.addComponent(lblFreeSwapSpace, 3, 1);
		
		final Label lblTotalOffHeapMemory = 
				new Label(String.valueOf(memoryStats.getTotalOffHeapMemory() / (1024 * 1024)));
		final Label lblUsedOffHeapMemory = 
				new Label(String.valueOf(memoryStats.getUsedOffHeapMemory() / (1024 * 1024)));
		
		lytMemoryStats.addComponent(new Label("<b>Total Off-Heap Memory (MB): </b>", ContentMode.HTML), 0, 2);
		lytMemoryStats.addComponent(lblTotalOffHeapMemory, 1, 2);
		lytMemoryStats.addComponent(new Label("<b>Used Off-Heap Memory (MB): </b>", ContentMode.HTML), 2, 2);
		lytMemoryStats.addComponent(lblUsedOffHeapMemory, 3, 2);
		
		final Label lblMaxHeapMemory = 
				new Label(String.valueOf(memoryStats.getMaxHeapMemory() / (1024 * 1024)));
		final Label lblCommittedHeapMemory = 
				new Label(String.valueOf(memoryStats.getCommittedHeapMemory() / (1024 * 1024)));
		final Label lblUsedHeapMemory = 
				new Label(String.valueOf(memoryStats.getUsedHeapMemory() / (1024 * 1024)));
		
		lytMemoryStats.addComponent(new Label("<b>Max Heap Memory (MB): </b>", ContentMode.HTML), 0, 3);
		lytMemoryStats.addComponent(lblMaxHeapMemory, 1, 3);
		lytMemoryStats.addComponent(new Label("<b>Committed Heap Memory (MB): </b>", ContentMode.HTML), 2, 3);
		lytMemoryStats.addComponent(lblCommittedHeapMemory, 3, 3);
		lytMemoryStats.addComponent(new Label("<b>Used Heap Memory (MB): </b>", ContentMode.HTML), 4, 3);
		lytMemoryStats.addComponent(lblUsedHeapMemory, 5, 3);
		
		pnlMemoryStats.setContent(lytMemoryStats);
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		Panel pnlGCStats = new Panel();
		pnlGCStats.setCaption("GC Stats");
		pnlGCStats.addStyleName(Reindeer.LAYOUT_BLUE);
		pnlGCStats.setWidth("100%");
		
		GridLayout lytGCStats = new GridLayout(6, 3);
		lytGCStats.setMargin(true);
		lytGCStats.setSpacing(true);
		lytGCStats.setSizeFull();
		lytGCStats.addStyleName(Reindeer.LAYOUT_BLUE);
		
		final GCStats gcStats = monitoringService.getGCStats();

		final Label lblMinorCount = new Label(String.valueOf(gcStats.getMinorCount()));
		final Label lblMinorTime = new Label(String.valueOf(gcStats.getMinorTime()));
		
		lytGCStats.addComponent(new Label("<b>Minor Count: </b>", ContentMode.HTML), 0, 0);
		lytGCStats.addComponent(lblMinorCount, 1, 0);
		lytGCStats.addComponent(new Label("<b>Minor Time (ms): </b>", ContentMode.HTML), 2, 0);
		lytGCStats.addComponent(lblMinorTime, 3, 0);
		
		final Label lblMajorCount = new Label(String.valueOf(gcStats.getMajorCount()));
		final Label lblMajorTime = new Label(String.valueOf(gcStats.getMajorTime()));
		
		lytGCStats.addComponent(new Label("<b>Major Count: </b>", ContentMode.HTML), 0, 1);
		lytGCStats.addComponent(lblMajorCount, 1, 1);
		lytGCStats.addComponent(new Label("<b>Major Time (ms): </b>", ContentMode.HTML), 2, 1);
		lytGCStats.addComponent(lblMajorTime, 3, 1);
		
		final Label lblUnknownCount = new Label(String.valueOf(gcStats.getUnknownCount()));
		final Label lblUnknownTime = new Label(String.valueOf(gcStats.getUnknownTime()));
		
		lytGCStats.addComponent(new Label("<b>Unknown Count: </b>", ContentMode.HTML), 0, 2);
		lytGCStats.addComponent(lblUnknownCount, 1, 2);
		lytGCStats.addComponent(new Label("<b>Unknown Time (ms): </b>", ContentMode.HTML), 2, 2);
		lytGCStats.addComponent(lblUnknownTime, 3, 2);
		
		pnlGCStats.setContent(lytGCStats);
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		Panel pnlPersonStats = new Panel();
		pnlPersonStats.setCaption("Person Stats");
		pnlPersonStats.addStyleName(Reindeer.LAYOUT_BLUE);
		pnlPersonStats.setWidth("100%");
		
		GridLayout lytPersonStats = new GridLayout(6, 2);
		lytPersonStats.setMargin(true);
		lytPersonStats.setSpacing(true);
		lytPersonStats.setSizeFull();
		lytPersonStats.addStyleName(Reindeer.LAYOUT_BLUE);
		
		final PersonStats personStats = monitoringService.getPersonStats();

		final Label lblExisting = new Label(String.valueOf(personStats.getExisting()));
		final Label lblGot = new Label(String.valueOf(personStats.getGot()));
		final Label lblPut = new Label(String.valueOf(personStats.getPut()));
		final Label lblRemoved = new Label(String.valueOf(personStats.getRemoved()));
		
		lytPersonStats.addComponent(new Label("<b>Existing: </b>", ContentMode.HTML), 0, 0);
		lytPersonStats.addComponent(lblExisting, 1, 0);
		lytPersonStats.addComponent(new Label("<b>Put: </b>", ContentMode.HTML), 2, 0);
		lytPersonStats.addComponent(lblPut, 3, 0);
		lytPersonStats.addComponent(new Label("<b>Removed: </b>", ContentMode.HTML), 0, 1);
		lytPersonStats.addComponent(lblRemoved, 1, 1);
		lytPersonStats.addComponent(new Label("<b>Got: </b>", ContentMode.HTML), 2, 1);
		lytPersonStats.addComponent(lblGot, 3, 1);
		
		pnlPersonStats.setContent(lytPersonStats);
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		final ObjectStats objectStats = monitoringService.getObjectStats();
		
		Panel pnlObjectStats = new Panel();
		pnlObjectStats.setCaption("Object Stats");
		pnlObjectStats.addStyleName(Reindeer.LAYOUT_BLUE);
		pnlObjectStats.setWidth("100%");
		
		GridLayout lytObjectStats = new GridLayout(6, 1);
		lytObjectStats.setMargin(true);
		lytObjectStats.setSpacing(true);
		lytObjectStats.setSizeFull();
		lytObjectStats.addStyleName(Reindeer.LAYOUT_BLUE);
		
		final Label lblExistingObject = new Label(String.valueOf(objectStats.getExisting()));
		final Label lblCreatedObject = new Label(String.valueOf(objectStats.getCreated()));
		final Label lblDisposedObject = new Label(String.valueOf(objectStats.getDisposed()));
		
		lytObjectStats.addComponent(new Label("<b>Existing: </b>", ContentMode.HTML), 0, 0);
		lytObjectStats.addComponent(lblExistingObject, 1, 0);
		lytObjectStats.addComponent(new Label("<b>Created: </b>", ContentMode.HTML), 2, 0);
		lytObjectStats.addComponent(lblCreatedObject, 3, 0);
		lytObjectStats.addComponent(new Label("<b>Disposed: </b>", ContentMode.HTML), 4, 0);
		lytObjectStats.addComponent(lblDisposedObject, 5, 0);
		
		pnlObjectStats.setContent(lytObjectStats);
		
		/////////////////////////////////////////////////////////////////////////////////////////
				
		vl.addStyleName(Reindeer.LAYOUT_BLUE);
		vl.addComponent(pnlEnvironmentInfo);
		vl.addComponent(pnlMemoryStats);
		vl.addComponent(pnlGCStats);
		vl.addComponent(pnlPersonStats);
		vl.addComponent(pnlObjectStats);
		
		vl.setMargin(true);
		vl.setSpacing(true);
		
		addComponent(vl);
		addStyleName(Reindeer.LAYOUT_BLUE);
		setMargin(false);
		setSpacing(false);
		setSizeFull();
		
		final int MILLISECONDS_IN_A_MINUTE = 60 * 1000;
		final int MILLISECONDS_IN_A_HOUR = 60 * MILLISECONDS_IN_A_MINUTE;
		final int MILLISECONDS_IN_A_DAY = 24 * MILLISECONDS_IN_A_HOUR;
		
		monitoringRefresherTimer = new Timer();
		monitoringRefresherTimer.schedule(
				new TimerTask() {
					@Override
					public void run() {
						long passedMilis = System.currentTimeMillis() - startTime.getTime();
						int passedDays = (int) (passedMilis / MILLISECONDS_IN_A_DAY);
						int passedHours = (int) ((passedMilis - (passedDays * MILLISECONDS_IN_A_DAY)) 
												/ MILLISECONDS_IN_A_HOUR);
						int passedMinutes = (int) ((passedMilis - ((passedDays * MILLISECONDS_IN_A_DAY) 
																   + (passedHours * MILLISECONDS_IN_A_HOUR))) 
												  / MILLISECONDS_IN_A_MINUTE);
						
						lblPassedTimeValue.setValue(String.valueOf(
								passedDays + " day(s) " + 
								passedHours + " hour(s) " + 
								passedMinutes + " min(s)")); 
						lblTotalPhysicalMemory.
								setValue(String.valueOf(memoryStats.getTotalPhysicalMemory() / (1024 * 1024)));
						lblFreePhysicalMemory.
								setValue(String.valueOf(memoryStats.getFreePhysicalMemory() / (1024 * 1024)));
						lblTotalSwapSpace.
								setValue(String.valueOf(memoryStats.getTotalSwapSpace() / (1024 * 1024)));
						lblFreeSwapSpace.
								setValue(String.valueOf(memoryStats.getFreeSwapSpace() / (1024 * 1024)));
						lblTotalOffHeapMemory.
								setValue(String.valueOf(memoryStats.getTotalOffHeapMemory() / (1024 * 1024)));
						lblUsedOffHeapMemory.
								setValue(String.valueOf(memoryStats.getUsedOffHeapMemory() / (1024 * 1024)));
						lblMaxHeapMemory.
								setValue(String.valueOf(memoryStats.getMaxHeapMemory() / (1024 * 1024)));
						lblCommittedHeapMemory.
								setValue(String.valueOf(memoryStats.getCommittedHeapMemory() / (1024 * 1024)));
						lblUsedHeapMemory.
								setValue(String.valueOf(memoryStats.getUsedHeapMemory() / (1024 * 1024)));
						
						lblMinorCount.setValue(String.valueOf(gcStats.getMinorCount()));
						lblMinorTime.setValue(String.valueOf(gcStats.getMinorTime()));
						lblMajorCount.setValue(String.valueOf(gcStats.getMajorCount()));
						lblMajorTime.setValue(String.valueOf(gcStats.getMajorTime()));
						lblUnknownCount.setValue(String.valueOf(gcStats.getUnknownCount()));
						lblUnknownTime.setValue(String.valueOf(gcStats.getUnknownTime()));
						
						lblExisting.setValue(String.valueOf(personStats.getExisting()));
						lblGot.setValue(String.valueOf(personStats.getGot()));
						lblPut.setValue(String.valueOf(personStats.getPut()));
						lblRemoved.setValue(String.valueOf(personStats.getRemoved()));
						
						lblExistingObject.setValue(String.valueOf(objectStats.getExisting()));
						lblCreatedObject.setValue(String.valueOf(objectStats.getCreated()));
						lblDisposedObject.setValue(String.valueOf(objectStats.getDisposed()));
						
						UI.getCurrent().push();
					}
				}, 
				1000, 1000);
	}
	
	@Override
	public void detach() {
		super.detach();
		monitoringRefresherTimer.cancel();
	}
	
}
