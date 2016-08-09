package ru.minecrafting.recipes.util;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServerSyncScheduler {
	private static final List<ScheduledTask> tasks = new ArrayList<>();

	public static void addTask(ScheduledTask task) {
		tasks.add(task);
	}

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent e) {
		if (e.side == Side.SERVER)
			for (Iterator<ScheduledTask> $i = tasks.iterator(); $i.hasNext(); ) {
				ScheduledTask task = $i.next();
				if (task.getRemainingDelayInTicks() <= 0) $i.remove();
				task.tick();
			}
	}

	public static class ScheduledTask {
		private final Runnable runnable;
		private final int delayInTicks;
		private int leftTicks;

		public ScheduledTask(Runnable runnable, int delayInTicks) {

			this.runnable = runnable;
			this.delayInTicks = delayInTicks;
			leftTicks = delayInTicks;
		}

		public Runnable getRunnable() {
			return runnable;
		}

		public int getDelayInTicks() {
			return delayInTicks;
		}

		public int getRemainingDelayInTicks() {
			return leftTicks;
		}

		public void tick() {
			if (leftTicks-- <= 0)
				runnable.run();
		}
	}
}
