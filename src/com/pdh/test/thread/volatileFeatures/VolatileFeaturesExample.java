package com.pdh.test.thread.volatileFeatures;
/**
 * 
 * @ClassName:VolatileFeaturesExample
 * @Function: volatile特性
 * @version
 *
 * @author pengdh
 * @date: 2017年2月23日 下午1:31:53
 */
public class VolatileFeaturesExample {
	volatile long v1 = 0L;	// 使用volatile声明64位的long型变量
	
	public void set(long l) {
		v1 = l;	// 单个volatile变量写
	}
	
	public long get() {
		return v1;	// 单个volatile变量读
	}
	
	public void getAndIncrement() {
		v1++;	// 复合（多个）volatile变量的读/写
	}
	
	int a;
    volatile int v2 = 1;
    volatile int v3 = 2;

    void readAndWrite() {
        int i = v2;           //第一个volatile读
        int j = v3;           // 第二个volatile读
        a = i + j;            //普通写
        v2 = i + 1;          // 第一个volatile写
        v3 = j * 2;          //第二个 volatile写
    }
}
