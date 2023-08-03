package cn.heshiqian.lycoris.core.channel;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/8/2
 */
public abstract class TickChannel extends AbstractLycorisChannel implements Runnable{

    private static final Object waiter = new Object();

    protected final int tick;
    protected final int timePerSecond;
    protected final Thread channel;
    protected final Lock lock = new ReentrantLock();

    protected abstract void onStartBefore();

    protected abstract void tick();

    public TickChannel(int tick) {
        if (1000 % tick != 0) throw new IllegalArgumentException("The tick: [" + tick + "] cannot be divided equally in one second.");
        this.tick = tick;
        this.timePerSecond = 1000 / tick;

        channel = new Thread(this);
        channel.setName(channelId());
        channel.setDaemon(true);
        onStartBefore();
        channel.start();
    }

    public int getTick() {
        return tick;
    }

    @Override
    public void run() {
        // follow the main thread state
        while (channel.isAlive() || !channel.isInterrupted()) {
            try {
                // 1.trigger tick
                lock.lock();
                int offset = timerRun(this::tick);

                // 1-1. if run tick is over time, next tick will immediately trigger
                boolean overTime = offset > timePerSecond;

                if (overTime) continue;

            } catch (Exception e) {
                // something wrong
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

            // 2.wait next tick
            waitNextTick();
        }
    }

    private void waitNextTick() {
        synchronized (waiter) {
            try {
                waiter.wait(timePerSecond);
            } catch (InterruptedException ignore) {}
        }
    }

    private int timerRun(Runnable execute) {
        long startTime = System.currentTimeMillis();
        execute.run();
        return (int) (System.currentTimeMillis() - startTime);
    }
}
