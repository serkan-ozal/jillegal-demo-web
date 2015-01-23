/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */

package tr.com.serkanozal.jillegal.demo.web.util;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringContextProvider {

	private final ApplicationContext applicationContext;

	public SpringContextProvider(ServletContext servletContext) {
		applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(final String beanRef) {
	       return (T) applicationContext.getBean(beanRef);
	} 
	   
	public <T> T getBean(Class<T> clazz) {
		if (applicationContext == null) {
			return null;
		}
		else {
			return (T)applicationContext.getBean(clazz);
		}    
	}
	    
}
