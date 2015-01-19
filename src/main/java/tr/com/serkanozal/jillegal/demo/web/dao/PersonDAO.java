/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */

package tr.com.serkanozal.jillegal.demo.web.dao;

import tr.com.serkanozal.jillegal.demo.web.domain.Person;

public interface PersonDAO {

	Person get(long id);
	Person save(Person person);
	Person remove(long id);
	
}
