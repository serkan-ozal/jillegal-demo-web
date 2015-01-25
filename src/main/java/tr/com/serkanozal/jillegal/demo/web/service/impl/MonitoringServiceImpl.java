/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */

package tr.com.serkanozal.jillegal.demo.web.service.impl;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tr.com.serkanozal.jillegal.demo.web.domain.EnvironmentInfo;
import tr.com.serkanozal.jillegal.demo.web.domain.GCStats;
import tr.com.serkanozal.jillegal.demo.web.domain.MemoryStats;
import tr.com.serkanozal.jillegal.demo.web.domain.PersonStats;
import tr.com.serkanozal.jillegal.demo.web.service.PersonService;
import tr.com.serkanozal.jillegal.demo.web.service.MonitoringService;
import tr.com.serkanozal.jillegal.offheap.memory.allocator.MemoryAllocator;
import tr.com.serkanozal.jillegal.offheap.service.OffHeapServiceFactory;
import tr.com.serkanozal.jillegal.util.JvmUtil;

@Service
public class MonitoringServiceImpl implements MonitoringService {

	private static final Logger logger = Logger.getLogger(MonitoringServiceImpl.class);
	
	private static final Runtime RUNTIME = Runtime.getRuntime();
	private static final OperatingSystemMXBean OPERATING_SYSTEM_MX_BEAN = 
			ManagementFactory.getOperatingSystemMXBean();
    private static final double PERCENTAGE_MULTIPLIER = 100D;
    
    private static final long TOTAL_PHYSICAL_MEMORY = readLongAttribute("TotalPhysicalMemorySize", -1L);
    private static final long TOTAL_SWAP_SPACE = readLongAttribute("TotalSwapSpaceSize", -1L);
    
    private static final Method FREE_PHYSICAL_MEMORY_SIZE_ATTRIBUTE_GETTER_METHOD = 
    		getMBeanAttributeGetterMethod("FreePhysicalMemorySize");
    private static final Method FREE_SWAP_SPACE_SIZE_ATTRIBUTE_GETTER_METHOD = 
    		getMBeanAttributeGetterMethod("FreeSwapSpaceSize");
    
	private static final Set<String> YOUNG_GC = new HashSet<String>(3);
    private static final Set<String> OLD_GC = new HashSet<String>(3);

    static {
        YOUNG_GC.add("PS Scavenge");
        YOUNG_GC.add("ParNew");
        YOUNG_GC.add("G1 Young Generation");

        OLD_GC.add("PS MarkSweep");
        OLD_GC.add("ConcurrentMarkSweep");
        OLD_GC.add("G1 Old Generation");
    }
	
    private static final MemoryAllocator MEMORY_ALLOCATOR = 
    		OffHeapServiceFactory.getOffHeapService().getDirectMemoryService().getMemoryAllocator();
    
	private final EnvironmentInfo environmentInfo = findEnvironmentInfo();
	private final MemoryStats memoryStats = new MemoryStats();
	private final GCStats gcStats = new GCStats();
	
	@Autowired
	private PersonService personService;
	
	private static Method getMBeanAttributeGetterMethod(String attributeName) {
		try {
			String methodName = "get" + attributeName;
			Method method = OPERATING_SYSTEM_MX_BEAN.getClass().getMethod(methodName);
			method.setAccessible(true);
			return method;
		}
		catch (Throwable t) {
			throw new RuntimeException("Error occured while getting getter method of attribute " + attributeName + 
					" from OperationgSystemMXBean", t);
		} 
	}
	
	private static long readLongAttribute(String attributeName, long defaultValue) {
		try {
			return readLongAttribute(getMBeanAttributeGetterMethod(attributeName), defaultValue);
		} catch (Throwable t) {
			throw new RuntimeException("Error occured while getting attribute " + attributeName + 
					" from OperationgSystemMXBean", t);
		} 
	}
	
	private static long readLongAttribute(Method attributeGetterMethod, long defaultValue) {
		try {
			Object value = attributeGetterMethod.invoke(OPERATING_SYSTEM_MX_BEAN);
			if (value == null) {
				return defaultValue;
			}

			if (value instanceof Long) {
				return (Long) value;
			}

			if (value instanceof Double) {
				double v = (Double) value;
				return Math.round(v * PERCENTAGE_MULTIPLIER);
			}

			if (value instanceof Number) {
				return ((Number) value).longValue();
			}

			return defaultValue;
		} catch (Throwable t) {
			throw new RuntimeException("Error occured while getting associated attribute from " + 
					attributeGetterMethod.getName() + " method over OperationgSystemMXBean", t);
		} 
	}
	
	private EnvironmentInfo findEnvironmentInfo() {
		EnvironmentInfo environmentInfo = new EnvironmentInfo();
		
		environmentInfo.setOsArch(JvmUtil.OS_ARCH);
		environmentInfo.setOsName(JvmUtil.OS_NAME);
		environmentInfo.setOsVersion(JvmUtil.OS_VERSION);
		environmentInfo.setJavaVersion(JvmUtil.JAVA_VERSION);
		environmentInfo.setJavaSpecVersion(JvmUtil.JAVA_SPEC_VERSION);
		environmentInfo.setJavaRuntimeVersion(JvmUtil.JAVA_RUNTIME_VERSION);
		environmentInfo.setJavaVendor(JvmUtil.JAVA_VENDOR);
		environmentInfo.setJvmVendor(JvmUtil.JVM_VENDOR);
		environmentInfo.setJvmVersion(JvmUtil.JVM_VERSION);
		environmentInfo.setJvmName(JvmUtil.JVM_NAME);
		
		logger.info(environmentInfo);
		
		return environmentInfo;
	}
	
	@Override
	public EnvironmentInfo getEnvironmentInfo() {
		return environmentInfo;
	}
	
	@Override
	public PersonStats getPersonStats() {
		return personService.getPersonStats();
	}
		
	@Scheduled(initialDelay = 1000, fixedRate = 1000)
	private void updateMemoryStats() {
		memoryStats.setTotalPhysicalMemory(TOTAL_PHYSICAL_MEMORY);
		memoryStats.setFreePhysicalMemory(
				readLongAttribute(FREE_PHYSICAL_MEMORY_SIZE_ATTRIBUTE_GETTER_METHOD, -1L));
		memoryStats.setTotalSwapSpace(TOTAL_SWAP_SPACE);
		memoryStats.setFreeSwapSpace(
				readLongAttribute(FREE_SWAP_SPACE_SIZE_ATTRIBUTE_GETTER_METHOD, -1L));
		memoryStats.setTotalOffHeapMemory(MEMORY_ALLOCATOR.totalMemory());
		memoryStats.setUsedOffHeapMemory(MEMORY_ALLOCATOR.usedMemory());
		memoryStats.setMaxHeapMemory(RUNTIME.maxMemory());
		memoryStats.setCommittedHeapMemory(RUNTIME.totalMemory());
		memoryStats.setUsedHeapMemory(RUNTIME.totalMemory() - RUNTIME.freeMemory());
		
		logger.info(memoryStats);
	}

	@Override
	public MemoryStats getMemoryStats() {
		return memoryStats;
	}
	
	@Scheduled(initialDelay = 1000, fixedRate = 1000)
	private void updateGCStats() {
		long minorCount = 0;
        long minorTime = 0;
        long majorCount = 0;
        long majorTime = 0;
        long unknownCount = 0;
        long unknownTime = 0;

        for (GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {
            long count = gc.getCollectionCount();
            if (count >= 0) {
                if (YOUNG_GC.contains(gc.getName())) {
                    minorCount += count;
                    minorTime += gc.getCollectionTime();
                } else if (OLD_GC.contains(gc.getName())) {
                    majorCount += count;
                    majorTime += gc.getCollectionTime();
                } else {
                    unknownCount += count;
                    unknownTime += gc.getCollectionTime();
                }
            }
        }

        gcStats.setMajorCount(majorCount);
        gcStats.setMajorTime(majorTime);
        gcStats.setMinorCount(minorCount);
        gcStats.setMinorTime(minorTime);
        gcStats.setUnknownCount(unknownCount);
        gcStats.setUnknownTime(unknownTime);
        
        logger.info(gcStats);
	}

	@Override
	public GCStats getGCStats() {
		return gcStats;
	}
	
}
