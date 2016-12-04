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