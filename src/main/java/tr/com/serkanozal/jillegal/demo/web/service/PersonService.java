/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */

package tr.com.serkanozal.jillegal.demo.web.service;

import tr.com.serkanozal.jillegal.demo.web.domain.ObjectStats;
import tr.com.serkanozal.jillegal.demo.web.domain.Person;
import tr.com.serkanozal.jillegal.demo.web.domain.PersonStats;

public interface PersonService {
	
	Person newPerson();
	
	Person get(long id);
	void save(Person person);
	boolean remove(long id);
	
	PersonStats getPersonStats();
	ObjectStats getObjectStats();
	
}
