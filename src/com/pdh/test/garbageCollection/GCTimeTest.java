package com.pdh.test.garbageCollection;

import java.util.HashMap;

/**
 * 
 * @ClassName:GCTimeTest
 * @Function: 垃圾回收，收集器对系统性能的影响	
 * 代码运行 1 万次循环，每次分配 512*100B 空间，采用不同的垃圾回收器，输出程序运行所消耗的时间
 * @version
 *
 * @author pengdh
 * @date: 2017年3月1日 下午4:17:17
 * GC 相关参数总结
1. 与串行回收器相关的参数
-XX:+UseSerialGC:在新生代和老年代使用串行回收器。
-XX:+SuivivorRatio:设置 eden 区大小和 survivor 区大小的比例。
-XX:+PretenureSizeThreshold:设置大对象直接进入老年代的阈值。当对象的大小超过这个值时，将直接在老年代分配。
-XX:MaxTenuringThreshold:设置对象进入老年代的年龄的最大值。每一次 Minor GC 后，对象年龄就加 1。任何大于这个年龄的对象，一定会进入老年代。
2. 与并行 GC 相关的参数
-XX:+UseParNewGC: 在新生代使用并行收集器。
-XX:+UseParallelOldGC: 老年代使用并行回收收集器。
-XX:ParallelGCThreads：设置用于垃圾回收的线程数。通常情况下可以和 CPU 数量相等。但在 CPU 数量比较多的情况下，设置相对较小的数值也是合理的。
-XX:MaxGCPauseMills：设置最大垃圾收集停顿时间。它的值是一个大于 0 的整数。收集器在工作时，会调整 Java 堆大小或者其他一些参数，尽可能地把停顿时间控制在 MaxGCPauseMills 以内。
-XX:GCTimeRatio:设置吞吐量大小，它的值是一个 0-100 之间的整数。假设 GCTimeRatio 的值为 n，那么系统将花费不超过 1/(1+n) 的时间用于垃圾收集。
-XX:+UseAdaptiveSizePolicy:打开自适应 GC 策略。在这种模式下，新生代的大小，eden 和 survivor 的比例、晋升老年代的对象年龄等参数会被自动调整，以达到在堆大小、吞吐量和停顿时间之间的平衡点。
3. 与 CMS 回收器相关的参数
-XX:+UseConcMarkSweepGC: 新生代使用并行收集器，老年代使用 CMS+串行收集器。
-XX:+ParallelCMSThreads: 设定 CMS 的线程数量。
-XX:+CMSInitiatingOccupancyFraction:设置 CMS 收集器在老年代空间被使用多少后触发，默认为 68%。
-XX:+UseFullGCsBeforeCompaction:设定进行多少次 CMS 垃圾回收后，进行一次内存压缩。
-XX:+CMSClassUnloadingEnabled:允许对类元数据进行回收。
-XX:+CMSParallelRemarkEndable:启用并行重标记。
-XX:CMSInitatingPermOccupancyFraction:当永久区占用率达到这一百分比后，启动 CMS 回收 (前提是-XX:+CMSClassUnloadingEnabled 激活了)。
-XX:UseCMSInitatingOccupancyOnly:表示只在到达阈值的时候，才进行 CMS 回收。
-XX:+CMSIncrementalMode:使用增量模式，比较适合单 CPU。
4. 与 G1 回收器相关的参数
-XX:+UseG1GC：使用 G1 回收器。
-XX:+UnlockExperimentalVMOptions:允许使用实验性参数。
-XX:+MaxGCPauseMills:设置最大垃圾收集停顿时间。
-XX:+GCPauseIntervalMills:设置停顿间隔时间。
5. 其他参数
-XX:+DisableExplicitGC: 禁用显示 GC。
 */
public class GCTimeTest {
	static HashMap<Long, byte[]> map = new HashMap<Long, byte[]>();
	public static void main(String[] args) {
		long beginTime = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			if(map.size() * 512 / 1024 /1024 >= 400) {
				map.clear();// 保护内存不溢出
				System.out.println("clean up");
			}
			byte[] b1;
			for (int j = 0; j < 100; j++) {
				b1 = new byte[512];
				map.put(System.nanoTime(), b1);	// 不断消耗内存
			}
		}
		long endtime = System.currentTimeMillis();
		 System.out.println("cost time:" + (endtime - beginTime));
	}
}
