/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */

package tr.com.serkanozal.jillegal.demo.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.serkanozal.jillegal.demo.web.dao.PersonDAO;
import tr.com.serkanozal.jillegal.demo.web.domain.Person;
import tr.com.serkanozal.jillegal.demo.web.service.PersonService;
import tr.com.serkanozal.jillegal.offheap.service.OffHeapService;
import tr.com.serkanozal.jillegal.offheap.service.OffHeapServiceFactory;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonDAO personDAO;
	private OffHeapService offHeapService = OffHeapServiceFactory.getOffHeapService();
	
	@Override
	public Person newPerson() {
		return offHeapService.newObject(Person.class);
	}
	
	@Override
	public Person get(long id) {
		return personDAO.get(id);
	}

	@Override
	public void save(Person person) {
		Person oldPerson = personDAO.save(person);
		if (oldPerson != null) {
			offHeapService.freeObject(oldPerson);
		}
	}

	@Override
	public boolean remove(long id) {
		Person removedPerson = personDAO.remove(id);
		if (removedPerson != null) {
			offHeapService.freeObject(removedPerson);
			return true;
		}
		else {
			return false;
		}
	}

}
