## JAVA多线程

### 一、JAVA线程内存模型

1.  声明变量数据存储在主内存
2.  使用read()操作将数据从主内存读取出来
3.  使用load()操作将数据副本加载到线程的工作内存中
4.  使用use()操作来处理数据
5.  如果修改了数据，则会使用assign()操作来将计算好的值重新复制到工作内存中
6.  修改结束，会使用store()操作将工作内存数据写入主内存（未修改原数据）
7.  执行write()操作，将store()过来的变量值赋值给主内存中的变量（修改了主内存中原数据）

```volatile```缓存可见性实现原理：

​	底层实现主要是通过总线实现MESI缓存一致性协议和主存嗅探机制来保证数据一致。

​	当修改变量有volatile修饰时，汇编代码中会有lock前缀，CPU执行到lock前缀代码时，会将该赋值语句修改的数据 **立即**写回主存中，并且在使用该变量前必须从主存读取最新的数据到工作内存中。并可以通过内存屏障来防止指令重排序，也就是会使在此变量之后的指令无法重排序到内存屏障之前。

​	volatile会在store()前调用lock()，防止写回主存出现线程并发问题。执行完store()和write()完成之后unlock()

并发编程三大特性：可见性，原子性、有序性

```volatile```保证可见性与有序性，但不保证原子性，保证原子性需要借助 ```synchronized``` 这样的锁机制。

```volatile```修饰的关键字，只会在store()和write()操作过程中加锁，但是其他CPU在嗅探到主存数据变化时，重新读取数据覆盖工作内存中的数据，可能会导致其他CPU已经做的操作结果丢失，所以无法保证原子性。



### 一、基本概念

1. 并发：同时拥有两个或多个线程，如果程序在单核处理器上运行时，多个程序交替的还如换出内存，这些线程时同时存在的，每个线程处于执行的某个状态，如果运行在多核处理器是上时，程序的每个线程都分配到一个处理器核上，隐藏可以同时运行。
2. 高并发（High Concurrency）：是互联网分布式系统架构设计中必须考虑的因素之一，它通常指，通过设计保证系统**同时并行**处理很多请求。

### 二、并发基础

#### 1. CPU多级缓存一致性

- 为什么需要缓存CPU cache？

  CPU的频率太快了，快到主存跟不上，这样在处理器的时钟周期内，CPU常常需要等待主存，浪费资源，所以cache的出现时为了缓存CPU和主存之间的速度不匹配的问题。（结构：cpu -> cache ->  memory)

- CPU cache有什么意义？

  1. 时间局部性：如果某个数据被访问，那么在不久的将来他很可能在此被访问。
  2. 空间局部性：如果某个数据被访问，那么与他相邻的数据库很快也可能被访问。

- CPU多级缓存 - 缓存一致性（MESI - Modified Exclusive Shared Invalided）：

  MESI协议用于保证多个CPU cache之间缓存共享数据的一致

#### 2. CPU多级缓存 - 乱序执行优化

- 定义：处理器为了提高运算速度而做出违背代码原有顺序的优化。

#### 3. Java内存模型

- Java内存模型中线程的工作内存（本地内存）是CPU缓存和CPU寄存器的抽象，而主内存就是主存。
- 同步的八种操作：
  1. lock(锁定)：作用于主存的变量，把一个变量标识为一条线程独占状态
  2. unlock(解锁)：作用于主内存的变量，把一个处于锁定状态的变量释放出来，释放后的变量才可以被其他线程锁定。
  3. read(读取)：作用于主内存的变量，把一个变量值从主内存传输到线程的工作内存中，以便随后的load动作使用。
  4. load(载入)：作用于工作内存中的变量，它把read操作从主存中得到的变量值放入工作内存的变量副本中。
  5. use(使用)：作用于工作内存的变量，把工作内存中的一个变量值传递给执行引擎。
  6. assign(赋值)：作用于工作内存的变量，它把一个从执行引擎接收到的值赋值给工作内存的变量。
  7. store(存储)：作用于工作内存中的变量，它把工作内存中的一个变量的值传送到主存中，以便随后的write操作。
  8. write(写入)：作用于主存的变量，它把store操作从工作内存中的一个变量的值传送到主存的变量中。
- 同步规则：
  1. 如果要把一个变量从主存中复制到工作内存，就需要按顺序的执行read和load操作，如果把变量从工作内存中同步回主存，就要按照顺序的执行store和write操作。但java内存模型只要求上述操作必须按顺序执行，而没有保证必须是连续执行。
  2. 不允许read和load、store和write操作之一单独出现。
  3. 不允许一个线程丢弃它的最近assign操作，即变量在工作内存中改变以后必须同步到主存中。
  4. 不允许一个线程无原因的（没有发生任何assign操作）把数据从工作内存中同步回主存。
  5. 一个新的变量只能在主存中诞生，不允许在工作内存中直接使用一个未被初始化（load或assign）的变量，即就是对一个变量实施use和store操作之前，必须先执行过了assign和load操作。
  6. 一个变量在同一时刻只允许一条线程对其进行lock操作，但lock操作可以被同一条线程重复执行多次，多次执行lock后，只有执行相同次数的unlock操作，变量才会被解锁。lock和unlock必须成对出现。
  7. 如果对一个变量执行lock操作，将会清空工作内存中此变量的值，在执行引擎使用和这个变量前需要重新执行load或assign操作初始化变量的值。
  8. 如果一个变量事先没有被lock操作锁定，则不允许对他执行unlock操作，也不允许unlock一个被其他线程锁定的变量。
  9. 对一个变量执行unlock操作之前，必须先把此变量同步到主存中（执行store和write操作）

#### 4. 并发的优势与风险

优势：

- 速度
- 设计：某些情况设计简单，选择更多
- 资源利用

风险：

- 安全性：多个线程存在线程安全问题
- 活跃性：你某个操作无法继续进行下去就会发生活跃性问题，如死锁、饥饿等。
- 性能：线程过多时，CPU频繁切换，调度时间增多；同步机制，消耗过多内存。

### 三、并发模拟

#### 1. 并发模拟

- Postman ： Http请求模拟工具

- Apache Bench（AB）：Apache附带的工具，测试网站性能----**未学习**

- JMeter：Apache阻止开发的压力测试工具----**未学习**
- 代码：Semaphore、CountDownLatch等

演示代码：

```java
@NotThreadSafe
public class ConcurrencyTest {
    private Logger logger = LoggerFactory.getLogger(TestController.class);

    public static int clientTotal = 5000;
    public static int threadTotal = 50;
    public static int count = 0;

    private static void add(){
        count++;
    }

    @Test
    public void test() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                add();
                semaphore.release();
            });
            countDownLatch.countDown();
        }
        countDownLatch.await();
        logger.info("count:{}",count);
        executorService.shutdown();
    }
}
```

### 四、线程安全性

#### 1. 原子性

- 定义：**当多个线程访问某个类时，不管运行时环境采用何种调度方式，或者这些进程将如何交替执行，并且在主调代码中不需要任何额外的同步或协同，这个类都能表现出正确的行为，那么久称这个类时线程安全的。**

- 线程安全三要素：

  1. 原子性：提供互斥访问，统一时刻只有一个线程来对他进行操作。

  2. 可见性：一个线程对主内存的修改可以及时的被其他线程观察到。

  3. 有序性：一个线程观察其他线程中的指令执行顺序，由于指令重排序的存在，该观察结果一般杂乱无序。

- Atomic包

  AtomicXXX：CAS、Unsafe.compareAndSwapInt

  AtomicInteger.incrementAndGet()调用unsafe中getAndAddInt()方法：

  ```java
  public final int getAndAddInt(Object var1, long var2, int var4) {//第一个参数var1表示当前对象，第二个参数var2表示当前对象的值，第三个参数var3表示增加的值
      int var5;
      do {
          var5 = this.getIntVolatile(var1, var2);//调用native方法获取底层var1对象中的真实值
      } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));//如果var2==var5则返回true并将底层更新为var5+var4，退出循环，否则一直循环判断
      return var5;
  }
  ```

  LongAdder：线程安全；

  数据分离，value()核心数据分离成数组，被线程访问时，通过hash等算法将值映射到其中一个数字进行计数，最终计数结果为所有数字的计数求和，热点数据会被分离成多个cell，每个cell维护自己的值，分离热点，提高并行度。并发更新时可能会有错误。

  AtomicReference：

  ```java
  public class AtomicExample4 {
      private static Logger logger = LoggerFactory.getLogger(AtomicExample4.class);
      private static AtomicReference<Integer> count = new AtomicReference<>(0);
  
      public static void main(String[] args) {
          count.compareAndSet(0,2);//2
          count.compareAndSet(0,1);//no
          count.compareAndSet(1,3);//no
          count.compareAndSet(2,4);//4
          count.compareAndSet(3,5);//no
          logger.info("count:{}",count.get());//count:4
      }
  }
  ```

  AtomicIntegerFieldUpdater：更新字段必须为volatile修饰，compareAndSet(var1,var2.,var3)判断var1的指定字段是否为var2，是则更新为var3。更新一个类的指定字段，只能更新volatile且非static的字段

  AtomicStampReference：CAS的ABA问题

  参数A被其他线程修改为B又修改回A，CAS会认为数据没有变化从而修改数据，这与设计思想不符合，AtomicStampReference通过加入版本信息来解决此问题。

  AtomicBoolean：compareAndSet()可以确保代码只会执行一次。

#### 2. synchronized

锁：synchronized：依赖JVM，不可中断锁，适合竞争不激烈，可读性好

Lock：依赖特殊的CPU指令，代码实现，可中断锁，多样化同步，竞争激烈时能维持常态

Atomic：竞争激烈时能维持常态，比Lock性能好，只能同步一个值

1. synchronized：

   - 修饰代码块：大括号括起来的代码，作用于调用的对象，不同对象调用时仍会乱序执行
   - 修饰方法：整个方法，作用于调用的对象，不同对象调用时仍会乱序执行
   - 修饰静态方法：整个静态方法，作用于所有对象，不同对象调用时仍会顺序执行
   - 修饰类：括号括起来的部分，作用于所有对象，不同对象调用时仍会顺序执行

   **父类方法带有synchronized关键字时，子类方法继承时是不会携带synchronized关键字的。**

#### 3. 可见性

定义：一个线程对主内存的修改可以及时的被其他线程观察到。

导致共享变量在线程间不可见的原因：

- 线程交叉执行
- 重排序结合线程交叉执行
- 共享变量更新后的值没有在工作内存与主存之间及时更新

JMM关于synchronized的两条规定：

- 线程解锁前，必须把共享变量的最新值刷新到主内存
- 线程加锁时，将清空工作内存中共享变量的值，从而使用共享变量时需要从主存中重新读取最新的值（注意：加锁和解锁是同一把锁）

volatile：通过加入内存屏障和禁止重排序优化来实现。

- 对volatile变量写操作时，会在写操作后加入一条store屏障指令，将本地内存中的共享变量值刷新到主内存。
- 对volatile变量读操作时：会在读操作前加入一条load屏障指令，从主存中读取共享变量。

volatile使用：使用volatile来修饰标记量（double check）

```java
volatile boolean inited = false;
//线程1
context = loadContext();
inited = true;
//线程2
while(!inited){
    sleep();
}
doSomething(context);
```

#### 4. 有序性

java内存模型中，允许编译器和处理器对指令进行重排序，但是重排序过程中不会影响到单线程程序的执行，却会影响到多线程程序并发执行的正确性。

happens-before原则：

- 程序次序规则：一个线程内，按照代码顺序，书写在前面的操作现行发生于书写后的操作。
- 锁定规则：一个unlock操作先行发生于后面对同一个锁的lock操作。
- volatile变量规则：对一个变量的写操作先行发生于后面对这个变量的读操作。
- 传递规则：如果操作A先行发生于操作B，而操作B又先行发生于操作C，则可以得出操作A先行发生于操作C。
- 线程启动规则：Thread对象的start()方法先行发生于此线程的每一个动作。
- 线程中断规则：对线程interrupt()方法的调用先行发生于发生于被中断线程的代码检测到中断事件的发生。
- 线程终结规则：线程中所有的操作都先行发生于线程的终止检测，我们可以通过Thread.join()方法结束、Thread.isAlive()的返回值手段检测到线程已经终止执行。
- 对象终结规则：一个对象的初始化完成先行发生于它的finalize()方法的开始。

如果一段代码的执行顺序无法通过happens-before原则推导出来，那么虚拟机可以随意进行重排序。

### 五、安全发布对象

#### 1. 发布与逸出

- 发布对象：是一个对象能够被当前代码范围之外的代码所使用。
- 对象逸出：一种错误的发布，当一个对象还没有构造完成时，就使他被其他线程所见。（不太理解）

#### 2. 安全发布对象的四种方法

- 在静态初始化函数中初始化一个对象的引用。
- 将对象的引用保存到volatile类型域或AtomicReference对象中。
- 讲对象的引用保存到某个正确构造对象的final类型域中。
- 将对象的引用保存到一个有锁保护的域中。























