package edu.iastate;

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

    public void admit(List<Task> tasks, float cpuUtil, int curTime) {
        // For now, just push everything through
        for (Task t : tasks) {
            if (t.getSync() && curTime % t.getPeriod() == 0) {
                t.setDeadline(curTime + t.getPeriod());
                t.reset();
                s.addTask(t);
            }
            if (!t.getSync() && t.getArrivalTime() == curTime) {
                // Add async task to server
                server.addTask(t);
            }
        }

        if (server.shouldRefresh(curTime)) {
            server.refresh();
        }

        if (server.canRun()) {
            List<Task> newAsyncTasks = server.run(curTime);
            s.addTasks(newAsyncTasks);
        }
    }
}
