/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */

package tr.com.serkanozal.jillegal.demo.web.domain;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

public class PersonStats {

	private static final AtomicLongFieldUpdater<PersonStats> EXISTING_UPDATER = 
			AtomicLongFieldUpdater.newUpdater(PersonStats.class, "existing");
	private static final AtomicLongFieldUpdater<PersonStats> GOT_UPDATER = 
			AtomicLongFieldUpdater.newUpdater(PersonStats.class, "got");
	private static final AtomicLongFieldUpdater<PersonStats> PUT_UPDATER = 
			AtomicLongFieldUpdater.newUpdater(PersonStats.class, "put");
	private static final AtomicLongFieldUpdater<PersonStats> REMOVED_UPDATER = 
			AtomicLongFieldUpdater.newUpdater(PersonStats.class, "removed");
	
	private volatile long existing;
	private volatile long got;
	private volatile long put;
	private volatile long removed;
	
	public long getExisting() {
		return existing;
	}

	public void increaseExisting() {
		EXISTING_UPDATER.incrementAndGet(this);
	}
	
	public void decreaseExisting() {
		EXISTING_UPDATER.decrementAndGet(this);
	}
	
	public long getGot() {
		return got;
	}

	public void increaseGot() {
		GOT_UPDATER.incrementAndGet(this);
	}
	
	public long getPut() {
		return put;
	}

	public void increasePut() {
		PUT_UPDATER.incrementAndGet(this);
	}
	
	public long getRemoved() {
		return removed;
	}

	public void increaseRemoved() {
		REMOVED_UPDATER.incrementAndGet(this);
	}

	@Override
	public String toString() {
		return "PersonStats [existing=" + existing + ", got=" + got + ", put="
				+ put + ", removed=" + removed + "]";
	}
	
}
