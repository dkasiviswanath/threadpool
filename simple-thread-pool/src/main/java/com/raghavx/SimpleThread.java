package com.raghavx;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleThread extends Thread {

	private final AtomicBoolean threadPoolShutdown;

	private final BlockingQueue<Runnable> tasks;

	public SimpleThread(AtomicBoolean threadPoolShutdown, BlockingQueue<Runnable> tasks) {
		this.threadPoolShutdown = threadPoolShutdown;
		this.tasks = tasks;
	}

	public void run() {
		while (!this.threadPoolShutdown.get()) {
			if (!tasks.isEmpty()) {
				try {
					tasks.take().run();
				} catch (InterruptedException e) {
				}
			}
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
			}
		}
	}
}
