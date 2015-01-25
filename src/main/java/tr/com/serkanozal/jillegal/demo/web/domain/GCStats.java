/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */

package tr.com.serkanozal.jillegal.demo.web.domain;

public class GCStats {

	private volatile long minorCount;
	private volatile long minorTime;
	private volatile long majorCount;
	private volatile long majorTime;
	private volatile long unknownCount;
	private volatile long unknownTime;
	
	public long getMinorCount() {
		return minorCount;
	}
	
	public void setMinorCount(long minorCount) {
		this.minorCount = minorCount;
	}
	
	public long getMinorTime() {
		return minorTime;
	}
	
	public void setMinorTime(long minorTime) {
		this.minorTime = minorTime;
	}
	
	public long getMajorCount() {
		return majorCount;
	}
	
	public void setMajorCount(long majorCount) {
		this.majorCount = majorCount;
	}
	
	public long getMajorTime() {
		return majorTime;
	}
	
	public void setMajorTime(long majorTime) {
		this.majorTime = majorTime;
	}
	
	public long getUnknownCount() {
		return unknownCount;
	}
	
	public void setUnknownCount(long unknownCount) {
		this.unknownCount = unknownCount;
	}
	
	public long getUnknownTime() {
		return unknownTime;
	}
	
	public void setUnknownTime(long unknownTime) {
		this.unknownTime = unknownTime;
	}
	
	public long getTotalCount() {
		return minorCount + majorCount + unknownCount;
	}
	
	public long getTotalTime() {
		return minorTime + majorTime + unknownTime;
	}

	@Override
	public String toString() {
		return "GCStats [minorCount=" + minorCount + ", minorTime=" + minorTime + " ms"
				+ ", majorCount=" + majorCount + ", majorTime=" + majorTime + " ms"
				+ ", unknownCount=" + unknownCount + ", unknownTime="
				+ unknownTime + "]";
	}
	
}
