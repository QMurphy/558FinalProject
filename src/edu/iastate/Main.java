package edu.iastate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Just making this for testing so far
public class Main {
    private static final int MAX_TIME = 48;

    public static void main(String[] args){
        InputParser ip = new InputParser(new File("input.txt"));
        List<Task> tasks = ip.parseInput();

        runSchedule(tasks);

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

    private static void runSchedule(List<Task> tasks){
        ArrayList<Integer> admittedCount = new ArrayList<>();
        ArrayList<Integer> missedCount = new ArrayList<>();
        ArrayList<Task> accepted = new ArrayList<>();
        Scheduler s = new Scheduler(accepted);
        AsyncTaskServer server = new NoFilterServer();
        PIDController pid = new PIDController(0.5f, 0.1f, 0.1f, 0.95f);
        AdmissionController controller = new AdmissionController(s, server, accepted);
        float desiredCpuUtil = 1;

        for (int i = 0; i < MAX_TIME; i++) {
            admittedCount.add(controller.admit(tasks, desiredCpuUtil, i));
            if(admittedCount.size() > 1000)
                admittedCount.remove(0);
            missedCount.add(s.schedule(i));
            if(missedCount.size() > 1000)
                missedCount.remove(0);
            int totalMissed = 0;
            int totalAdmitted = 0;
            for(Integer k : missedCount){
                totalMissed+=k;
            }
            for(Integer k : admittedCount){
                totalAdmitted+=k;
            }

            // Get CPU Utilization
            desiredCpuUtil -= pid.update((1.0f*totalMissed)/totalAdmitted);
        }

        // Results
        List<History> hist = s.getHistory();
        for (History h : hist) {
            System.out.println("Task " + h.task.getID() + ": " + History.eventToString(h.event) + " at time " + (h.time + 1));
        }
    }
}