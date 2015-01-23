/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */

package tr.com.serkanozal.jillegal.demo.web.service;

import tr.com.serkanozal.jillegal.demo.web.domain.Person;

public interface PersonService {
	
	Person newPerson();
	Person get(long id);
	void save(Person person);
	boolean remove(long id);
	
}
