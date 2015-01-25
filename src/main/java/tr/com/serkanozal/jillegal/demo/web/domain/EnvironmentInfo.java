/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */

package tr.com.serkanozal.jillegal.demo.web.domain;

public class EnvironmentInfo {

	private String osArch;
	private String osName;
	private String osVersion;
	private String javaVersion;
	private String javaSpecVersion;
	private String javaRuntimeVersion;
	private String javaVendor;
	private String jvmVendor;
	private String jvmVersion;
	private String jvmName;
	
	public String getOsArch() {
		return osArch;
	}
	
	public void setOsArch(String osArch) {
		this.osArch = osArch;
	}
	
	public String getOsName() {
		return osName;
	}
	
	public void setOsName(String osName) {
		this.osName = osName;
	}
	
	public String getOsVersion() {
		return osVersion;
	}
	
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	
	public String getJavaVersion() {
		return javaVersion;
	}
	
	public void setJavaVersion(String javaVersion) {
		this.javaVersion = javaVersion;
	}
	
	public String getJavaSpecVersion() {
		return javaSpecVersion;
	}
	
	public void setJavaSpecVersion(String javaSpecVersion) {
		this.javaSpecVersion = javaSpecVersion;
	}
	
	public String getJavaRuntimeVersion() {
		return javaRuntimeVersion;
	}
	
	public void setJavaRuntimeVersion(String javaRuntimeVersion) {
		this.javaRuntimeVersion = javaRuntimeVersion;
	}
	
	public String getJavaVendor() {
		return javaVendor;
	}
	
	public void setJavaVendor(String javaVendor) {
		this.javaVendor = javaVendor;
	}
	
	public String getJvmVendor() {
		return jvmVendor;
	}
	
	public void setJvmVendor(String jvmVendor) {
		this.jvmVendor = jvmVendor;
	}
	
	public String getJvmVersion() {
		return jvmVersion;
	}
	
	public void setJvmVersion(String jvmVersion) {
		this.jvmVersion = jvmVersion;
	}
	
	public String getJvmName() {
		return jvmName;
	}
	
	public void setJvmName(String jvmName) {
		this.jvmName = jvmName;
	}

	@Override
	public String toString() {
		return "EnvironmentInfo [osArch=" + osArch + ", osName=" + osName
				+ ", osVersion=" + osVersion + ", javaVersion=" + javaVersion
				+ ", javaSpecVersion=" + javaSpecVersion
				+ ", javaRuntimeVersion=" + javaRuntimeVersion
				+ ", javaVendor=" + javaVendor + ", jvmVendor=" + jvmVendor
				+ ", jvmVersion=" + jvmVersion + ", jvmName=" + jvmName + "]";
	}

}
