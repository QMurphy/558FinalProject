package edu.iastate;

import java.io.File;
import java.util.List;
import java.util.Random;

//Just making this for testing so far
public class Main {
    private static final int MAX_TIME = 48;

    public static void main(String[] args){
        InputParser ip = new InputParser(new File("input.txt"));
        Scheduler s = new Scheduler();
        AsyncTaskServer server = new NoFilterServer(); // TODO Determine if async tasks should run with their own deadline or the deadline of the server
        List<Task> tasks = ip.parseInput();

        runSchedule(s, server, tasks);

        System.out.println("END SCHEDULE 1\n");
/*
        s = new Scheduler();
        server = new PollingServer(4, 8);
        FrequencyScaler f = new FrequencyScaler(.5f);
        for(Task t : tasks)
            t.calcNewTime(f);

        runSchedule(s, server, tasks);
        System.out.println("END SCHEDULE 2\n");

        VoltageScaler v = new VoltageScaler(1.2f);
        for(Task t : tasks)
            System.out.println("Task " + t.getID() + ": " + String.format("%.3g", t.calcEnergyUsed(v, f)));*/
    }

    private static void runSchedule(Scheduler s, AsyncTaskServer server, List<Task> tasks){
        PIDController pid = new PIDController(0.5f, 0.1f, 0.1f, 0.95f);
        AdmissionController controller = new AdmissionController(s, server);
        float cpuUtil = 1;
        float missRate = 0.05f;

        for (int i = 0; i < MAX_TIME; i++) {
            controller.admit(tasks, cpuUtil, i);

            s.schedule(i);

            // Get CPU Utilization
            cpuUtil -= pid.update(missRate);
        }

        // Results
        List<History> hist = s.getHistory();
        for (History h : hist) {
            System.out.println("Task " + h.task.getID() + ": " + History.eventToString(h.event) + " at time " + (h.time + 1));
        }
    }
}