Jillegal-Demo-Web
=================

Jillegal Demo Web Application

HOW TO INSTALL
--------------

* Open a EC2 instance. 

```
	On Demand Instance:
		Type		vCPU	ECU		Memory (GiB)	Instance Storage (GB)	Linux/UNIX Usage
		====================================================================================
		r3.large	2		6.5		15				1 x 32 SSD				$0.175 per Hour
		r3.xlarge	4		13		30.5			1 x 80 SSD				$0.350 per Hour
		r3.2xlarge	8		26		61				1 x 160 SSD				$0.700 per Hour
		r3.4xlarge	16		52		122				1 x 320 SSD				$1.400 per Hour
		r3.8xlarge	32		104		244				2 x 320 SSD				$2.800 per Hour
							
	Spot Instance:
		Type			Linux/UNIX Usage	Windows Usage
		====================================================
		r3.large		$0.0161 per Hour	$0.1741 per Hour
		r3.xlarge		$0.0322 per Hour	$0.2821 per Hour
		r3.2xlarge		$0.0641 per Hour	$0.4441 per Hour
		r3.4xlarge		$0.1284 per Hour	$0.6721 per Hour
		r3.8xlarge		$0.2567 per Hour	$0.9561 per Hour
```

* Connect to instance.

For example, over SSH with `jillegal-demo-web.pem` PEM file.

```
ssh -i ~/.ssh/jillegal-demo-web.pem <username>@<instance_public_dns_name>
``` 
	
For example: `http://ec2-12-34-567-89.compute-1.amazonaws.com:8080/jillegal-demo-web/`
		
* Install **"JDK 8"**.

```
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer
```

* Install **"Git"**.

```
sudo apt-get install git
```

* Install **"Maven"**. 

```
sudo apt-get install maven
```

* Set environment variables.

```
export JAVA_HOME=/usr/lib/jvm/java-8-oracle
export JAVA8_HOME=$JAVA_HOME
```

* Clone and build Jillegal.

```	
git clone https://github.com/serkan-ozal/jillegal.git
cd jillegal
mvn clean install -DskipTests=true
```

* Clone and build Jillegal-Demo-Web

```
git clone https://github.com/serkan-ozal/jillegal-demo-web.git
cd jillegal-demo-web
mvn clean install	
```

* Set Maven arguments to run sample web application uses Jillegal

**Requiered Configuration**:

```
export MAVEN_OPTS="-XX:-UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+StartAttachListener" 
```
  
**Example Configuration**:

```
export MAVEN_OPTS="-XX:-UseCompressedOops -XX:+UseConcMarkSweepGC -verbose:gc -XX:+PrintGCDetails -Xms2g -Xmx4g -XX:+StartAttachListener -Djillegal.demo.web.maxPersonCount=20000000 -Djillegal.offheap.pool.objectCount=20480 -Djillegal.offheap.pool.estimatedStringCount=20480"
```
  
* Run web application and connect to it

```
mvn jetty:run
```

For local access, go to url `localhost:8080/jillegal-demo-web/`

For remote access, go to url `<instance_public_dns_name>:8080/jillegal-demo-web/`

CONFIGURATIONS
--------------

* **`jillegal.offheap.pool.objectCount`:** Defines how much memory will be allocated for how many object while creating a single object pool. Default value is `1024`.

* **`jillegal.offheap.pool.estimatedStringCount`:** Defines how much memory will be allocated for how many string while creating a single string pool. Default value is `1024`.

* **`jillegal.offheap.pool.estimatedStringLength`:** Defines avarage size of string (including object header) object in string pool. This estimated length is used for calculating allocaton size in string pool. Default value is `32`.

* **`jillegal.demo.web.disableMonitoringLogs`:** Disables printing monitoring logs to console. Default value is `false`.

* **`jillegal.demo.web.disableOffHeapStorage`:** Disables off-heap storage in off-heap based map and uses normal JDK **ConcurrentHashMap**. But the objects are still off-heap objects. So in case of off-heap storage disabled mode, heap objects (ConcurrentHashMap's entries) points to off-heap objects. Default value is `false`.

* **`jillegal.demo.web.maxPersonCount`:** Defines how many person can be stored in memory at most. Default value is `1.000.000`.

* **`jillegal.demo.web.saveCountInASchedule`:** Defines how many person are put to off-heap storage as random in a schedule period (1 second). Default value is `1.000`.

* **`jillegal.demo.web.removeCountInASchedule`:**  Defines how many person are removed from off-heap storage as random in a schedule period (1 second). Default value is `10`.

* **`jillegal.demo.web.getCountInASchedule`:** Defines how many person are got (queried) from off-heap storage as random in a schedule period (1 second). Default value is `1.000`.

* **`jillegal.demo.web.ignoreStrings`:** Ignores string typed fields (`username`, `firstName` and `lastName`) in `Person` object and these fields will be `null`. So, instead of string, more memory can be consumed by `Person` objects. Default value is `false`.

* **`jillegal.demo.web.useScheduledTask`:** Enables scheduled tasks to put, remove and get from off-heap storage in parallel from multiple threads. Default value is `false`.

