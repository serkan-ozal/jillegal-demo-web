/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */

package tr.com.serkanozal.jillegal.demo.web.service;

import java.util.Date;

import tr.com.serkanozal.jillegal.demo.web.domain.EnvironmentInfo;
import tr.com.serkanozal.jillegal.demo.web.domain.GCStats;
import tr.com.serkanozal.jillegal.demo.web.domain.MemoryStats;
import tr.com.serkanozal.jillegal.demo.web.domain.PersonStats;

public interface MonitoringService {
	
	Date getStartTime();
	EnvironmentInfo getEnvironmentInfo();
	PersonStats getPersonStats();
	MemoryStats getMemoryStats();
	GCStats getGCStats();
	
}
