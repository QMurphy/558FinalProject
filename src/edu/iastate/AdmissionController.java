package edu.iastate;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by qmurp on 12/7/2016.
 */
public class AdmissionController {
    private Scheduler s;
    private AsyncTaskServer server;

    public AdmissionController(Scheduler s, AsyncTaskServer server) {
        this.s = s;
        this.server = server;
    }

    public int admit(List<Task> tasks, float desiredCPUUtil, int curTime) {
        // TODO Figure out which tasks best fit the utilization hole
        int admitted = 0;
        float curCPUUtil = s.getCPUUtil();
        float deltaCPUUtil = desiredCPUUtil - curCPUUtil;

        if (server.shouldRefresh(curTime)) {
            server.refresh();
        }

        for (Task t : tasks) {
            if (deltaCPUUtil > 0) { // We can actually admit something, if not admit nothing
                if (t.getSync() && curTime % t.getPeriod() == 0) {
                    t.setDeadline(curTime + t.getPeriod());
                    t.reset();
                    s.addTask(t);
                    admitted++;
                    deltaCPUUtil -= t.getCompTime() / t.getPeriod();
                }
                if (!t.getSync() && t.getArrivalTime() == curTime) {
                    // Add async task to server
                    server.addTask(t);
                    if (server.canRun()) {
                        List<Task> newAsyncTasks = server.run(curTime);
                        s.addTasks(newAsyncTasks);
                        admitted++;
                        deltaCPUUtil -= t.getCompTime() / (t.getDeadline() - curTime);
                    }
                }
            }
        }

        return admitted;
    }
}
