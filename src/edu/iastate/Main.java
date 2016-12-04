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

        int nextSchedulePoint = 0;
        for (int i = 0; i < MAX_TIME; i++) {
            for (Task t : tasks) {
                if (t.getSync() && i % t.getPeriod() == 0) {
                    s.addTask(t);
                }
            }
            if (i >= nextSchedulePoint) {
                nextSchedulePoint = s.schedule(i);
            }
        }

        List<History> hist = s.getHistory();
        for (History h : hist) {
            System.out.println(h.time + ": " + h.task.getID());
        }
    }
}