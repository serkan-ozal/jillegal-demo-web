/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */

package tr.com.serkanozal.jillegal.demo.web.domain;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

public class ObjectStats {

	private static final AtomicLongFieldUpdater<ObjectStats> EXISTING_UPDATER = 
			AtomicLongFieldUpdater.newUpdater(ObjectStats.class, "existing");
	private static final AtomicLongFieldUpdater<ObjectStats> CREATED_UPDATER = 
			AtomicLongFieldUpdater.newUpdater(ObjectStats.class, "created");
	private static final AtomicLongFieldUpdater<ObjectStats> DISPOSED_UPDATER = 
			AtomicLongFieldUpdater.newUpdater(ObjectStats.class, "disposed");
	
	private volatile long existing;
	private volatile long created;
	private volatile long disposed;
	
	public long getExisting() {
		return existing;
	}

	public void increaseExisting() {
		EXISTING_UPDATER.incrementAndGet(this);
	}
	
	public void decreaseExisting() {
		EXISTING_UPDATER.decrementAndGet(this);
	}
	
	public long getCreated() {
		return created;
	}

	public void increaseCreated() {
		CREATED_UPDATER.incrementAndGet(this);
	}
	
	public long getDisposed() {
		return disposed;
	}

	public void increaseDisposed() {
		DISPOSED_UPDATER.incrementAndGet(this);
	}

	@Override
	public String toString() {
		return "ObjectStats [existing=" + existing + ", created=" + created
				+ ", disposed=" + disposed + "]";
	}

}
