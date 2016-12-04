/**
 * CPRE 558 Final Project
 * Date: 11/28/16
 * Author: Colin Ward
 */
package edu.iastate;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class InputParser {
    private File input;

    public InputParser(File i){
        input = i;
    }

    public List<Task> parseInput() {
        List<Task> tasks = new ArrayList<>();
        try {
            Scanner sc = new Scanner(input);

            int numSync = Integer.parseInt(sc.nextLine());
            for (int i = 0; i < numSync; i++) {
                String line = sc.nextLine();
                int compTime = Integer.parseInt(line.substring(0, line.indexOf(" ")));
                int period = Integer.parseInt(line.substring(line.indexOf(" ") + 1));
                tasks.add(new Task(i, compTime, period));
            }

            int numAsync = Integer.parseInt(sc.nextLine());
            for (int i = 0; i < numAsync; i++) {
                String line = sc.nextLine();
                int compTime = Integer.parseInt(line.substring(0, line.indexOf(" ")));

                String temp = line.substring(line.indexOf(" ") + 1);
                int deadline = Integer.parseInt(temp.substring(0, temp.indexOf(" ")));
                int arrivalTime = Integer.parseInt(temp.substring(temp.indexOf(" ") + 1));
                tasks.add(new Task(numSync + i, compTime, deadline, arrivalTime));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
