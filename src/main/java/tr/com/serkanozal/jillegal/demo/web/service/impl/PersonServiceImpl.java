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
import tr.com.serkanozal.jillegal.demo.web.domain.PersonStats;
import tr.com.serkanozal.jillegal.demo.web.service.PersonService;
import tr.com.serkanozal.jillegal.offheap.service.OffHeapService;
import tr.com.serkanozal.jillegal.offheap.service.OffHeapServiceFactory;

@Service
public class PersonServiceImpl implements PersonService {

	private static final Logger logger = Logger.getLogger(PersonServiceImpl.class);
	
	private static final int SAVE_COUNT_IN_A_SCHEDULE = 
			Integer.getInteger("jillegal.demo.web.saveCountInASchedule", 1000);
	private static final int REMOVE_COUNT_IN_A_SCHEDULE = 
			Integer.getInteger("jillegal.demo.web.removeCountInASchedule", 10);
	private static final int GET_COUNT_IN_A_SCHEDULE = 
			Integer.getInteger("jillegal.demo.web.getCountInASchedule", 1000);
	private static final boolean IGNORE_STRINGS = 
			Boolean.getBoolean("jillegal.demo.web.ignoreStrings");
	private static final boolean USE_SCHEDULED_TASK = 
			Boolean.getBoolean("jillegal.demo.web.useScheduledTask");
	
	private static final Random RANDOM = new Random();
	private static volatile boolean initiallyLoaded = false;
	
	private static final char[] DIGIT_ONES = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
	};
	
	private static final char[] DIGIT_TENS = {
        '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
        '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
        '2', '2', '2', '2', '2', '2', '2', '2', '2', '2',
        '3', '3', '3', '3', '3', '3', '3', '3', '3', '3',
        '4', '4', '4', '4', '4', '4', '4', '4', '4', '4',
        '5', '5', '5', '5', '5', '5', '5', '5', '5', '5',
        '6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
        '7', '7', '7', '7', '7', '7', '7', '7', '7', '7',
        '8', '8', '8', '8', '8', '8', '8', '8', '8', '8',
        '9', '9', '9', '9', '9', '9', '9', '9', '9', '9',
	};

	private static final OffHeapService offHeapService = OffHeapServiceFactory.getOffHeapService();
	
	private final CharArrayThreadLocal USERNAME_CHAR_ARRAY_BUFFER = new CharArrayThreadLocal("Username-");
	private final CharArrayThreadLocal FIRSTNAME_CHAR_ARRAY_BUFFER = new CharArrayThreadLocal("Firstname-");
	private final CharArrayThreadLocal LASTNAME_CHAR_ARRAY_BUFFER = new CharArrayThreadLocal("Lastname-");
	
	@Autowired
	private PersonDAO personDAO;
	private volatile PersonStats personStats = new PersonStats();
	
	private class CharArray {
		
		private char[] chars;
		private int actualLength;
		
		private CharArray(String prefix, int maxLength) {
			this.chars = new char[maxLength];
			this.actualLength = prefix.length();
			for (int i = 0; i < prefix.length(); i++) {
				chars[i] = prefix.charAt(i);
			}
		}
		
	}
	
	private class CharArrayThreadLocal extends ThreadLocal<CharArray> {
		
		private static final int DEFAULT_MAX_LENGTH = 30;
		
		private final String prefix;
		private final int maxLength;
		
		private CharArrayThreadLocal(String prefix) {
			this(prefix, DEFAULT_MAX_LENGTH);
		}
		
		private CharArrayThreadLocal(String prefix, int maxLength) {
			this.prefix = prefix;
			this.maxLength = maxLength;
		}
		
		@Override
		protected CharArray initialValue() {
			return new CharArray(prefix, maxLength);
		}
		
		private CharArray getFor(long l) {  
	        int size = (l < 0) ? stringSize(-l) + 1 : stringSize(l);
	        int offset = prefix.length();
	        CharArray charArray = get();
	        getChars(l, size, charArray.chars, offset);
	        charArray.actualLength = size + offset;
	        return charArray;
	    }
		
		private void getChars(long i, int index, char[] buf, int offset) {
	        long q;
	        int r;
	        int charPos = index;
	        char sign = 0;

	        if (i < 0) {
	            sign = '-';
	            i = -i;
	        }

	        // Get 2 digits/iteration using longs until quotient fits into an int
	        while (i > Integer.MAX_VALUE) {
	            q = i / 100;
	            // really: r = i - (q * 100);
	            r = (int)(i - ((q << 6) + (q << 5) + (q << 2)));
	            i = q;
	            buf[offset + --charPos] = DIGIT_ONES[r];
	            buf[offset + --charPos] = DIGIT_TENS[r];
	        }

	        // Get 2 digits/iteration using ints
	        int q2;
	        int i2 = (int)i;
	        while (i2 >= 65536) {
	            q2 = i2 / 100;
	            // really: r = i2 - (q * 100);
	            r = i2 - ((q2 << 6) + (q2 << 5) + (q2 << 2));
	            i2 = q2;
	            buf[offset + --charPos] = DIGIT_ONES[r];
	            buf[offset + --charPos] = DIGIT_TENS[r];
	        }

	        // Fall thru to fast mode for smaller numbers
	        // assert(i2 <= 65536, i2);
	        for (;;) {
	            q2 = (i2 * 52429) >>> (16+3);
	            r = i2 - ((q2 << 3) + (q2 << 1));  // r = i2-(q2*10) ...
	            buf[offset + --charPos] = (char) ('0' + r);
	            i2 = q2;
	            if (i2 == 0) break;
	        }
	        if (sign != 0) {
	            buf[offset + --charPos] = sign;
	        }
	    }
		
		// Requires positive x
	    private int stringSize(long x) {
	        long p = 10;
	        for (int i = 1; i < 19; i++) {
	            if (x < p) {
	                return i;
	            }    
	            p = 10 * p;
	        }
	        return 19;
	    }
		
	}
	
	@PostConstruct
	private void init() {
		doInitialLoad();
		if (!USE_SCHEDULED_TASK) {
			runProcess(); 
		}	
	}
	
	private synchronized void doInitialLoad() {
		if (!initiallyLoaded) {
			logger.info("Started doing initial load ...");
	
			for (int i = 0; i < Person.MAX_PERSON_COUNT; i++) {
				Person person = randomizePerson(i, newPerson());
				saveInternal(person, false);
			}
			
			logger.info("Finished doing initial load ...");
			initiallyLoaded = true;
		}	
	}
	
	private Person randomizePerson(int key, Person person) {
		person.setId(key);
		if (!IGNORE_STRINGS) {
			CharArray usernameCharArray = USERNAME_CHAR_ARRAY_BUFFER.getFor(key);
			person.setUsername(offHeapService.newString(usernameCharArray.chars, 0, usernameCharArray.actualLength));
			//person.setUsername(offHeapService.newString("Username-" + key));
			CharArray firstNameCharArray = FIRSTNAME_CHAR_ARRAY_BUFFER.getFor(key);
			person.setFirstName(offHeapService.newString(firstNameCharArray.chars, 0, firstNameCharArray.actualLength));
			//person.setFirstName(offHeapService.newString("Firstname-" + key));
			CharArray lastNameCharArray = LASTNAME_CHAR_ARRAY_BUFFER.getFor(key);
			person.setLastName(offHeapService.newString(lastNameCharArray.chars, 0, lastNameCharArray.actualLength));
			//person.setLastName(offHeapService.newString("Lastname-" + key));
		}	
		person.setBirthDate(
				(Person.MILLI_SECONDS_IN_A_YEAR * RANDOM.nextInt(30)) + 	// Any year between 1970 and 2000
				(Person.MILLI_SECONDS_IN_A_MONTH * (RANDOM.nextInt(12))) +	// Any month between 0 and 11 (Jan and Dec)
				(Person.MILLI_SECONDS_IN_A_DAY * (RANDOM.nextInt(29)))); 	// Any day between 0 and 28
		person.setAccountNo((int) (RANDOM.nextInt(1000000)));
		person.setDebt(RANDOM.nextInt(1000));
		
		return person;
	}
	
	private void runProcess() {
		new Thread() {
			public void run() {
				while (true) {
					try {
						for (int i = 0; i < SAVE_COUNT_IN_A_SCHEDULE; i++) {
							int id = RANDOM.nextInt(Person.MAX_PERSON_COUNT);
							Person person = randomizePerson(id, newPerson());
							saveInternal(person, false);
						}	
						for (int i = 0; i < REMOVE_COUNT_IN_A_SCHEDULE; i++) {
							int id = RANDOM.nextInt(Person.MAX_PERSON_COUNT);
							remove(id);
						}	
						for (int i = 0; i < GET_COUNT_IN_A_SCHEDULE; i++) {
							int id = RANDOM.nextInt(Person.MAX_PERSON_COUNT);
							get(id);
						}
						Thread.sleep(1000);
					} 
					catch (Throwable t) {
						t.printStackTrace();
					}
				}
			};
		}.start();
	}
	
	@Scheduled(initialDelay = 60 * 1000, fixedRate = 1000)
	public synchronized void process() {
		if (USE_SCHEDULED_TASK) {
			for (int i = 0; i < SAVE_COUNT_IN_A_SCHEDULE; i++) {
				int id = RANDOM.nextInt(Person.MAX_PERSON_COUNT);
				Person person = randomizePerson(id, newPerson());
				saveInternal(person, false);
			}	
			for (int i = 0; i < REMOVE_COUNT_IN_A_SCHEDULE; i++) {
				int id = RANDOM.nextInt(Person.MAX_PERSON_COUNT);
				remove(id);
			}	
			for (int i = 0; i < GET_COUNT_IN_A_SCHEDULE; i++) {
				int id = RANDOM.nextInt(Person.MAX_PERSON_COUNT);
				get(id);
			}
		}	
	}
	
	//@Scheduled(initialDelay = 60 * 1000, fixedRate = 100)
	public void saveRandomPerson() {
		for (int i = 0; i < SAVE_COUNT_IN_A_SCHEDULE; i++) {
			int id = RANDOM.nextInt(Person.MAX_PERSON_COUNT);
			Person person = randomizePerson(id, newPerson());
			saveInternal(person, false);
		}	
	}
	
	//@Scheduled(initialDelay = 60 * 1000, fixedRate = 100)
	public void removeRandomPerson() {
		for (int i = 0; i < REMOVE_COUNT_IN_A_SCHEDULE; i++) {
			int id = RANDOM.nextInt(Person.MAX_PERSON_COUNT);
			remove(id);
		}	
	}
	
	//@Scheduled(initialDelay = 60 * 1000, fixedRate = 100)
	public void getRandomPerson() {
		for (int i = 0; i < GET_COUNT_IN_A_SCHEDULE; i++) {
			int id = RANDOM.nextInt(Person.MAX_PERSON_COUNT);
			get(id);
		}	
	}
	
	@Override
	public Person newPerson() {
		Person person = offHeapService.newObject(Person.class);
		personStats.increaseCreated();
		return person;
	}
	
	@Override
	public Person get(long id) {
		Person person = personDAO.get(id);
		if (person != null) {
			personStats.increaseGot();
		}	
		return person;
	}

	@Override
	public void save(Person person) {
		saveInternal(person, true);
	}
	
	private void saveInternal(Person person, boolean checksEnable) {
		if (checksEnable && (person.getId() < 0 || person.getId() > Person.MAX_PERSON_COUNT)) {
			throw new IllegalArgumentException("ID can be between 0 and " + Person.MAX_PERSON_COUNT);
		}
		
		if (!IGNORE_STRINGS) {
			if (checksEnable) {
				if (!offHeapService.isInOffHeap(person.getUsername())) {
					person.setUsername(offHeapService.newString(person.getUsername()));
				}
				if (!offHeapService.isInOffHeap(person.getFirstName())) {
					person.setFirstName(offHeapService.newString(person.getFirstName()));
				}
				if (!offHeapService.isInOffHeap(person.getLastName())) {
					person.setLastName(offHeapService.newString(person.getLastName()));
				}
			}
		}
		
		Person oldPerson = personDAO.save(person);
		if (oldPerson != null) {
			String username = null;
			String firstName = null;
			String lastName = null;
			
			if (!IGNORE_STRINGS) {
				username = oldPerson.getUsername();
				firstName = oldPerson.getFirstName();
				lastName = oldPerson.getLastName();
			}
			
			offHeapService.freeObject(oldPerson);
			
			if (!IGNORE_STRINGS) {
				if (checksEnable) {
					if (username != person.getUsername()) {
						offHeapService.freeString(username);
					}
					if (firstName != person.getFirstName()) {
						offHeapService.freeString(firstName);
					}
					if (lastName != person.getLastName()) {
						offHeapService.freeString(lastName);
					}
				}
				else {
					offHeapService.freeString(username);
					offHeapService.freeString(firstName);
					offHeapService.freeString(lastName);
				}
			}	
		}
		
		personStats.increasePut();
	}

	@Override
	public boolean remove(long id) {
		Person removedPerson = personDAO.remove(id);
		if (removedPerson != null) {
			String username = null;
			String firstName = null;
			String lastName = null;
			
			if (!IGNORE_STRINGS) {
				username = removedPerson.getUsername();
				firstName = removedPerson.getFirstName();
				lastName = removedPerson.getLastName();
			}
			
			offHeapService.freeObject(removedPerson);
			
			if (!IGNORE_STRINGS) {
				offHeapService.freeString(username);
				offHeapService.freeString(firstName);
				offHeapService.freeString(lastName);
			}
			
			personStats.increaseRemoved();
			
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public PersonStats getPersonStats() {
		return personStats;
	}

}
