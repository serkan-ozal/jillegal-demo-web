/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */

package tr.com.serkanozal.jillegal.demo.web.dao.impl;

import org.springframework.stereotype.Repository;

import tr.com.serkanozal.jillegal.demo.web.dao.PersonDAO;
import tr.com.serkanozal.jillegal.demo.web.domain.Person;
import tr.com.serkanozal.jillegal.offheap.collection.map.OffHeapJudyHashMap;

@Repository
public class OffHeapPersonDAO implements PersonDAO {

	private final OffHeapJudyHashMap<Long, Person> personMap = 
			new OffHeapJudyHashMap<Long, Person>(Long.class, Person.class);
	
	@Override
	public Person get(long id) {
		return personMap.get(id);
	}
	
	@Override
	public Person save(Person person) {
		return personMap.put(person.getId(), person);
	}
	
	@Override
	public Person remove(long id) {
		return personMap.remove(id);
	}
	
}
