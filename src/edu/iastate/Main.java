package edu.iastate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//Just making this for testing so far
public class Main {
    private static final int MAX_TIME = 32;

    public static void main(String[] args){
        InputParser ip = new InputParser(new File("input.txt"));
        Scheduler s = new Scheduler();
        IAsyncTaskServer server = new NoFilterServer(); // TODO Determine if async tasks should run with their own deadline or the deadline of the server
        List<Task> tasks = ip.parseInput();

        runSchedule(s, server, tasks);
        System.out.println("END SCHEDULE 1\n");

        s = new Scheduler();
        server = new PollingServer(4, 8);
        FrequencyScaler f = new FrequencyScaler(.5f);
        for(Task t : tasks)
            t.calcNewTime(f);

        runSchedule(s, server, tasks);
        System.out.println("END SCHEDULE 2\n");

        VoltageScaler v = new VoltageScaler(1.2f);
        for(Task t : tasks)
            System.out.println("Task " + t.getID() + ": " + String.format("%.3g", t.calcEnergyUsed(v, f)));
    }

    private static void runSchedule(Scheduler s, IAsyncTaskServer server, List<Task> tasks){
        // Admission Controller
        // TODO Change this based on CPU Utilization
        for (int i = 0; i < MAX_TIME; i++) {
            // Add tasks
            for (Task t : tasks) {
                if (t.getSync() && i % t.getPeriod() == 0) {
                    t.setDeadline(i + t.getPeriod());
                    t.reset();
                    s.addTask(t);
                }
                if (!t.getSync() && t.getArrivalTime() == i) {
                    // Add async task to server
                    server.addTask(t);
                }
            }

            if (server.shouldRefresh(i)) {
                server.refresh();
            }

            if (server.canRun()) {
                List<Task> newAsyncTasks = server.run(i);
                s.addTasks(newAsyncTasks);
            }

            s.schedule(i);
        }

        // Results
        List<History> hist = s.getHistory();
        for (History h : hist) {
            System.out.println("Task " + h.task.getID() + ": " + History.eventToString(h.event) + " at time " + h.time);
        }
    }
}