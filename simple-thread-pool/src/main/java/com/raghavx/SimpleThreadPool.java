package com.raghavx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleThreadPool {

	private List<Thread> threads = new ArrayList<Thread>();
	private final BlockingQueue<Runnable> tasks;
	private final AtomicBoolean threadPoolShutdown;

	/**
	 * We don't want our constructor to be public
	 * 
	 * @param numberOfThreads
	 */
	private SimpleThreadPool(int numberOfThreads) {
		this.threadPoolShutdown = new AtomicBoolean(false);
		this.tasks = new LinkedBlockingQueue<Runnable>();
		for (int i = 0; i < numberOfThreads; i++) {
			this.threads.add(new SimpleThread(threadPoolShutdown, tasks));
		}
	}

	/**
	 * Method to give a new thread pool
	 * 
	 * @param numberOfThreads
	 * @return
	 */
	public static SimpleThreadPool getInstance(int numberOfThreads) {
		return new SimpleThreadPool(numberOfThreads);
	}

	public void execute(Runnable runnable) {
		if (this.threadPoolShutdown.get()) {
			throw new IllegalStateException("Thread pool is shutting down");
		}
		try {
			this.tasks.put(runnable);
		} catch (InterruptedException e) {

		}
	}

	public void shutDownAfterCompletingExecutingTasks() {
		this.threadPoolShutdown.set(true);
	}

}
