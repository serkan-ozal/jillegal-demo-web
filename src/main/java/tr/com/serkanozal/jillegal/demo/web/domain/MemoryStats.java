/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */

package tr.com.serkanozal.jillegal.demo.web.domain;

public class MemoryStats {

	private static final int BYTES_IN_A_MB = 1024 * 1024;
	
	private volatile long totalPhysicalMemory;
    private volatile long freePhysicalMemory;
    
    private volatile long totalSwapSpace;
    private volatile long freeSwapSpace;

    private volatile long totalOffHeapMemory;
    private volatile long usedOffHeapMemory;

    private volatile long maxHeapMemory;
    private volatile long committedHeapMemory;
    private volatile long usedHeapMemory;
    
	public long getTotalPhysicalMemory() {
		return totalPhysicalMemory;
	}
	
	public void setTotalPhysicalMemory(long totalPhysicalMemory) {
		this.totalPhysicalMemory = totalPhysicalMemory;
	}
	
	public long getFreePhysicalMemory() {
		return freePhysicalMemory;
	}
	
	public void setFreePhysicalMemory(long freePhysicalMemory) {
		this.freePhysicalMemory = freePhysicalMemory;
	}
	
	public long getTotalSwapSpace() {
		return totalSwapSpace;
	}
	
	public void setTotalSwapSpace(long totalSwapSpace) {
		this.totalSwapSpace = totalSwapSpace;
	}
	
	public long getFreeSwapSpace() {
		return freeSwapSpace;
	}
	
	public void setFreeSwapSpace(long freeSwapSpace) {
		this.freeSwapSpace = freeSwapSpace;
	}
	
	public long getTotalOffHeapMemory() {
		return totalOffHeapMemory;
	}
	
	public void setTotalOffHeapMemory(long totalOffHeapMemory) {
		this.totalOffHeapMemory = totalOffHeapMemory;
	}
	
	public long getUsedOffHeapMemory() {
		return usedOffHeapMemory;
	}
	
	public void setUsedOffHeapMemory(long usedOffHeapMemory) {
		this.usedOffHeapMemory = usedOffHeapMemory;
	}
	
	public long getMaxHeapMemory() {
		return maxHeapMemory;
	}
	
	public void setMaxHeapMemory(long maxHeapMemory) {
		this.maxHeapMemory = maxHeapMemory;
	}
	
	public long getCommittedHeapMemory() {
		return committedHeapMemory;
	}
	
	public void setCommittedHeapMemory(long committedHeapMemory) {
		this.committedHeapMemory = committedHeapMemory;
	}
	
	public long getUsedHeapMemory() {
		return usedHeapMemory;
	}
	
	public void setUsedHeapMemory(long usedHeapMemory) {
		this.usedHeapMemory = usedHeapMemory;
	}

	@Override
	public String toString() {
		return "MemoryStats [totalPhysicalMemory=" + (totalPhysicalMemory / BYTES_IN_A_MB + " MB")
				+ ", freePhysicalMemory=" + (freePhysicalMemory / BYTES_IN_A_MB + " MB")
				+ ", totalSwapSpace=" + (totalSwapSpace / BYTES_IN_A_MB + " MB") 
				+ ", freeSwapSpace=" + (freeSwapSpace / BYTES_IN_A_MB + " MB") 
				+ ", totalOffHeapMemory=" + (totalOffHeapMemory / BYTES_IN_A_MB + " MB") 
				+ ", usedOffHeapMemory=" + (usedOffHeapMemory / BYTES_IN_A_MB + " MB") 
				+ ", maxHeapMemory=" + (maxHeapMemory / BYTES_IN_A_MB + " MB")
				+ ", committedHeapMemory=" + (committedHeapMemory / BYTES_IN_A_MB + " MB")
				+ ", usedHeapMemory=" + (usedHeapMemory / BYTES_IN_A_MB + " MB") + "]";
	}
	
}
