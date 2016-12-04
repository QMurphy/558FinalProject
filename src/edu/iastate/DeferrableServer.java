package edu.iastate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qmurp on 12/4/2016.
 * This server has a set amount of time that it can assign async tasks to per call
 * but
 */
public class DeferrableServer implements IAsyncTaskServer {
    private int runTime;
    private int timeUsed;
    private int period;
    private int lastRefresh;
    private ArrayList<Task> tasks;

    public DeferrableServer(int runTime, int period) {
        this.runTime = runTime;
        tasks = new ArrayList<>();
        this.period = period;
        lastRefresh = 0;
    }

    @Override
    public void refresh() {
        timeUsed = 0;
    }

    @Override
    public boolean canRun() {
        return timeUsed < runTime;
    }

    @Override
    public boolean shouldRefresh(int curTime) {
        if (lastRefresh / period < curTime / period) {
            lastRefresh = curTime;
            return true;
        }
        return false;
    }

    @Override
    public List<Task> run(int curTime) {
        // Check for missed deadlines
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.getDeadline() - t.getCompTime() < curTime) {
                tasks.remove(t);
                i--;
                //hist.add(new History(curTime, t, false));
            }
        }

        ArrayList<Task> ret = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.getCompTime() + timeUsed < runTime) {
                ret.add(t);
                tasks.remove(t);
                i--;
                timeUsed += t.getCompTime();
            }
            if (timeUsed == runTime) {
                break;
            }
        }
        return ret;
    }

    @Override
    public void addTask(Task t) {
        tasks.add(t);
    }
}
