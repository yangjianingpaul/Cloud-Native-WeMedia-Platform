# 1.Scheduled release of article
* redis implementation: The zset data type's de-ordering (fractional sorting) feature is delayed. For example, the timestamp is sorted as a score
* [SpringBoot Redis](redis.java)

# 2.redis implementation of delayed tasks:
![](/resources/delayTask.png)

* Redis implements future data periodic refresh, add the enable task scheduling annotation in the boot class: @EnableScheduling

# 3.Distributed lock resolves function preemption execution in a cluster
* Start two heima-leadnews-schedule services, each of which executes the refresh scheduled task method.
* Distributed lock: Controls distributed systems to perform operations on shared resources in an orderly manner and ensures data consistency through mutual exclusion.
* redis distributed lock: The sexnx (SET if Not eXists) command sets the specified value for a key if the specified key does not exist.

![](/resources/distributedLock.png)

* If the key does not exist, a value is set for the key. If the key already exists, the SETNX command does nothing.
    - Client A requests the server to set the key value. If the key value is set successfully, the lock is successfully added.
    - Client B also requests the server to set the value of the key. If the return fails, it means that the lock has failed.
    - Client A executes the code and deletes the lock.
    - Client B waits for a period of time and then requests to set the key value. The setting succeeds.
    - Client B executes the code and deletes the lock.

# 4.Delay queue to resolve precise time release
* In the heima-leadnews-feign-api, a remote feign interface is provided.
* The corresponding implementation is provided under the heima-leadnews-schedule microservice.