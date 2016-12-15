package edu.iastate;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

//Just making this for testing so far
public class Main {
    private static final int MAX_TIME = 48;
    private static Scheduler completeSchedule;
    public static void main(String[] args) {
        InputParser ip = new InputParser(new File("input.txt"));
        List<Task> tasks = ip.parseInput();
        completeSchedule = runSchedule(tasks);
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());


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

    private static Scheduler runSchedule(List<Task> tasks) {
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
            if (admittedCount.size() > 1000)
                admittedCount.remove(0);
            missedCount.add(s.schedule(i));
            if (missedCount.size() > 1000)
                missedCount.remove(0);
            int totalMissed = 0;
            int totalAdmitted = 0;
            for (Integer k : missedCount) {
                totalMissed += k;
            }
            for (Integer k : admittedCount) {
                totalAdmitted += k;
            }

            // Get CPU Utilization
            desiredCpuUtil -= pid.update((1.0f * totalMissed) / totalAdmitted);
        }


        // Results
        List<History> hist = s.getHistory();
        for (History h : hist) {
            System.out.println("Task " + h.task.getID() + ": " + History.eventToString(h.event) + " at time " + (h.time + 1));
        }
        return s;
    }
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Project Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));
        JPanel panel = new JPanel(){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                List<History> hist = completeSchedule.getHistory();
                int numTasks = hist.size();
                int currentTime = 0;
                int totalTime = hist.get(numTasks - 1).time + 1;
                HashMap<Integer, Color> colors = setColors(hist);
                ArrayList<Integer> uniqueIds = new ArrayList<>();
                for(History h: hist){
                    if(!uniqueIds.contains(h.task.getID()))
                        uniqueIds.add(h.task.getID());
                    g.setColor(colors.get(h.task.getID()));
                    g.fillRect((int) ((double) currentTime / totalTime * frame.getWidth()), frame.getHeight() / 3, (int) (frame.getWidth() * ((double) ((h.time + 1) - currentTime) / totalTime)), (int) (frame.getHeight() * .1));
                    g.setColor(Color.BLACK);
                    g.fillRect((int) ((double) currentTime / totalTime * frame.getWidth()), frame.getHeight() / 3, 2, (int) (frame.getHeight() * .1));
                    g.drawString(Integer.toString(currentTime), (int)((double)currentTime/totalTime * frame.getWidth()), (frame.getHeight() / 3)-5);
                    currentTime += ((h.time + 1) - currentTime);
                }


                for(int i = 0; i < uniqueIds.size(); i++){
                    g.setColor(Color.BLACK);
                    g.drawString("Task: " + uniqueIds.get(i), (int)((double)i/uniqueIds.size() * frame.getWidth()) + 20, (int)(frame.getHeight()*.8));
                    g.setColor(colors.get(uniqueIds.get(i)));
                    g.fillRect((int)((double)i/uniqueIds.size() * frame.getWidth()) + 30, (int)(frame.getHeight()*.8)+10, 20, 20);
                }
            }
        };


        frame.getContentPane().add(panel, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static HashMap<Integer, Color> setColors(List<History> hist){
        HashMap<Integer, Color> c = new HashMap<>();
        float hStep = .2f;
        for(int i = 0; i < hist.size(); i++) {
            Color col = Color.getHSBColor(i*hStep + 1, .7f, .7f);
            c.put(hist.get(i).task.getID(), col);
        }
        return c;
    }
}

