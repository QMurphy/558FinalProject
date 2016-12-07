package edu.iastate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qmurp on 12/4/2016.
 * This server has a set amount of time that it can assign async tasks to per call
 */
public class PollingServer extends AsyncTaskServer {
    public PollingServer(int runTime, int period) {
        this.runTime = runTime;
        tasks = new ArrayList<>();
        lastRefresh = 0;
        this.period = period;
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
        timeUsed = runTime; // Make it so that it can't run again
        return ret;
    }
}
