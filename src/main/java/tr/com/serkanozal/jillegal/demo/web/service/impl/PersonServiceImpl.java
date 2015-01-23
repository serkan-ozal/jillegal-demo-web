/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */

package tr.com.serkanozal.jillegal.demo.web.service.impl;

import java.util.Random;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tr.com.serkanozal.jillegal.demo.web.dao.PersonDAO;
import tr.com.serkanozal.jillegal.demo.web.domain.Person;
import tr.com.serkanozal.jillegal.demo.web.service.PersonService;
import tr.com.serkanozal.jillegal.offheap.service.OffHeapService;
import tr.com.serkanozal.jillegal.offheap.service.OffHeapServiceFactory;

@Service
public class PersonServiceImpl implements PersonService {

	private static final Logger logger = Logger.getLogger(PersonServiceImpl.class);
	
	private static final int SAVE_COUNT_IN_A_SCHEDULE = 100;
	private static final Random RANDOM = new Random();
	private static volatile boolean initiallyLoaded = false;
	
	@Autowired
	private PersonDAO personDAO;
	private OffHeapService offHeapService = OffHeapServiceFactory.getOffHeapService();
	
	@PostConstruct
	private void init() {
		doInitialLoad();
	}
	
	private synchronized void doInitialLoad() {
		if (!initiallyLoaded) {
			logger.info("Started doing initial load ...");
	
			for (int i = 0; i < Person.MAX_PERSON_COUNT; i++) {
				Person person = newPerson();
				personDAO.save(randomizePerson(i, person));
			}
			
			logger.info("Finished doing initial load ...");
			initiallyLoaded = true;
		}	
	}
	
	private Person randomizePerson(int key, Person person) {
		person.setId(key);
		person.setUsername(offHeapService.newString("Username-" + key));
		person.setFirstName(offHeapService.newString("Firstname-" + key));
		person.setLastName(offHeapService.newString("Lastname-" + key));
		person.setBirthDate(
				(Person.MILLI_SECONDS_IN_A_YEAR * RANDOM.nextInt(30)) + 	// Any year between 1970 and 2000
				(Person.MILLI_SECONDS_IN_A_MONTH * (RANDOM.nextInt(12))) +	// Any month between 0 and 11 (Jan and Dec)
				(Person.MILLI_SECONDS_IN_A_DAY * (RANDOM.nextInt(29)))); 	// Any day between 0 and 28
		person.setAccountNo((int) (RANDOM.nextInt(1000000)));
		person.setDebt(RANDOM.nextInt(1000));
		
		return person;
	}
	
	@Scheduled(initialDelay = 60 * 1000, fixedRate = 100)
	public void saveRandomPerson() {
		for (int i = 0; i < SAVE_COUNT_IN_A_SCHEDULE; i++) {
			int id = RANDOM.nextInt(Person.MAX_PERSON_COUNT);
			Person person = newPerson();
			personDAO.save(randomizePerson(id, person));
		}	
	}
	
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
		if (person.getId() < 0 || person.getId() > Person.MAX_PERSON_COUNT) {
			throw new IllegalArgumentException("ID can be between 0 and " + Person.MAX_PERSON_COUNT);
		}
		
		if (!offHeapService.isInOffHeap(person.getUsername())) {
			person.setUsername(offHeapService.newString(person.getUsername()));
		}
		if (!offHeapService.isInOffHeap(person.getFirstName())) {
			person.setFirstName(offHeapService.newString(person.getFirstName()));
		}
		if (!offHeapService.isInOffHeap(person.getLastName())) {
			person.setLastName(offHeapService.newString(person.getLastName()));
		}
		
		Person oldPerson = personDAO.save(person);
		if (oldPerson != null) {
			if (oldPerson.getUsername() != person.getUsername()) {
				offHeapService.freeString(oldPerson.getUsername());
			}
			if (oldPerson.getFirstName() != person.getFirstName()) {
				offHeapService.freeString(oldPerson.getFirstName());
			}
			if (oldPerson.getLastName() != person.getLastName()) {
				offHeapService.freeString(oldPerson.getLastName());
			}
			offHeapService.freeObject(oldPerson);
		}
	}

	@Override
	public boolean remove(long id) {
		Person removedPerson = personDAO.remove(id);
		if (removedPerson != null) {
			offHeapService.freeString(removedPerson.getUsername());
			offHeapService.freeString(removedPerson.getFirstName());
			offHeapService.freeString(removedPerson.getLastName());
			offHeapService.freeObject(removedPerson);
			return true;
		}
		else {
			return false;
		}
	}

}
