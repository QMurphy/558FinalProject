package edu.iastate;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by qmurp on 12/7/2016.
 */
public class AdmissionController {
    private Scheduler s;
    private AsyncTaskServer server;
    private List<Task> accepted;

    public AdmissionController(Scheduler s, AsyncTaskServer server, List<Task> accepted) {
        this.s = s;
        this.server = server;
        this.accepted = accepted;
    }

    public int admit(List<Task> tasks, float cpuUtil, int curTime) {
        // For now, just push everything through
        int admitted = 0;
        for (Task t : tasks) {
            float curCPUUtil = 0;
            for (Task t2 : accepted) {
                curCPUUtil += t2.getCompTimeLeft() * 1.0f / (t2.getDeadline() - curTime);
            }
            float deltaCPUUtil = cpuUtil - curCPUUtil;
            if (deltaCPUUtil > 0) { // We can actually admit something, if not admit nothing
                if (t.getSync() && curTime % t.getPeriod() == 0) {
                    t.setDeadline(curTime + t.getPeriod());
                    t.reset();
                    s.addTask(t);
                    admitted++;
                }
                if (!t.getSync() && t.getArrivalTime() == curTime) {
                    // Add async task to server
                    server.addTask(t);
                    admitted++;
                }
            }
        }

        if (server.shouldRefresh(curTime)) {
            server.refresh();
        }

        if (server.canRun()) {
            List<Task> newAsyncTasks = server.run(curTime);
            s.addTasks(newAsyncTasks);
        }
        return admitted;
    }
}
