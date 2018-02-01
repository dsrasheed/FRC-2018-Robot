package com.robodogs.lib.loops;

import java.util.List;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.Notifier;

public class Looper {
	
	private List<Loop> loops = new ArrayList<>();
	private Object loopLock = new Object();
	private Runnable onLoop = new Runnable() {
		public void run() {
			synchronized(loopLock) {
				for (Loop loop: loops) {
					loop.onLoop();
				}
			}
		}
	};
	private Notifier notifier = new Notifier(onLoop);
	private double period;
	private boolean hasStarted;
	
	/*
	 * @param period Number of times to call the runnable in a second
	 */
	public Looper(float period) {
		this.period = 1.0 / period;
	}
	
	public void start() {
		synchronized(loopLock) {
			for (Loop loop: loops) {
				loop.onStart();
			}
			notifier.startPeriodic(period);
			hasStarted = true;
		}
	}
	
	public void stop() {
		synchronized(loopLock) {
			for (Loop loop: loops) {
				loop.onStop();
			}
			notifier.stop();
			hasStarted = false;
		}
	}
	
	public void register(Loop loop) {
		synchronized(loopLock) {
			loops.add(loop);
		}
	}
	
	public boolean hasStarted() {
		return hasStarted;
	}
}
