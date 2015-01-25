/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */
 
package tr.com.serkanozal.jillegal.demo.web.util;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class UiUtil {

	private UiUtil() {
		
	}
	
	public static void showInfoMessage(String message) {
		showInfoMessage("Info", message);
	}
	
	public static void showInfoMessage(String caption, String message) {
		showMessage(caption, message, Type.HUMANIZED_MESSAGE);
	}
	
	public static void showWarningMessage(String message) {
		showWarningMessage("Warning", message);
	}
	
	public static void showWarningMessage(String caption, String message) {
		showMessage(caption, message, Type.WARNING_MESSAGE);
	}
	
	public static void showErrorMessage(String message) {
		showErrorMessage("Error", message);
	}
	
	public static void showErrorMessage(String caption, String message) {
		showMessage(caption, message, Type.ERROR_MESSAGE);
	}
	
	private static void showMessage(String caption, String message, Type type) {
		Notification notification = 
				new Notification(caption, message, type);
		notification.show(Page.getCurrent());
	}
	
}
