package edu.iastate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qmurp on 12/4/2016.
 * This server has send any async tasks that come through
 */
public class NoFilterServer extends AsyncTaskServer {
    public NoFilterServer() {
        tasks = new ArrayList<>();
        timeUsed = 0;
        runTime = 1;
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

        ArrayList<Task> ret = new ArrayList<>(tasks);
        tasks = new ArrayList<>();
        return ret;
    }
}
