package edu.iastate;

import java.io.File;
import java.util.List;

//Just making this for testing so far
public class Main {
    private static final int MAX_TIME = 32;

    public static void main(String[] args){
        InputParser ip = new InputParser(new File("input.txt"));
        Scheduler s = new Scheduler(new PollingServer());
        List<Task> tasks = ip.parseInput();

        runSchedule(s, tasks);
        System.out.println("END SCHEDULE 1\n");

        s = new Scheduler(new PollingServer());
        FrequencyScaler f = new FrequencyScaler(.5f);
        for(Task t : tasks)
            t.calcNewTime(f);

        runSchedule(s, tasks);
        System.out.println("END SCHEDULE 2\n");

        VoltageScaler v = new VoltageScaler(1.2f);
        for(Task t : tasks)
            System.out.println("Task " + t.getID() + ": " + String.format("%.3g", t.calcEnergyUsed(v, f)));
    }

    private static void runSchedule(Scheduler s, List<Task> tasks){
        // Admission Controller
        // TODO Change this based on CPU Utilization
        int nextSchedulePoint = 0;
        for (int i = 0; i < MAX_TIME; i++) {
            for (Task t : tasks) {
                if (t.getSync() && i % t.getPeriod() == 0) {
                    t.setDeadline(i + t.getPeriod());
                    s.addTask(t);
                }
            }
            if (i >= nextSchedulePoint) {
                nextSchedulePoint = s.schedule(i);
            }
        }

        // Results
        List<History> hist = s.getHistory();
        for (History h : hist) {
            System.out.println("Task " + h.task.getID() + (h.success ? " ran " : " missed ") + "at time " + h.time);
        }
    }
}