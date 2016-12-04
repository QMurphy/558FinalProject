/**
 * CPRE 558 Final Project
 * Date: 11/28/16
 * Author: Colin Ward
 */
package edu.iastate;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class InputParse {
    private File input;

    public InputParse(File i){
        input = i;
    }

    public ArrayList<Task> parseInput() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            Scanner sc = new Scanner(input);

            int numSync = sc.nextInt();
            for (int i = 0; i < numSync; i++) {
                String line = sc.nextLine();
                double compTime = Double.parseDouble(line.substring(0, line.indexOf(" ")));
                double period = Double.parseDouble(line.substring(line.indexOf(" ") + 1));
                tasks.add(new Task(i, compTime, period));
            }

            int numAsync = sc.nextInt();
            for (int i = 0; i < numAsync; i++) {
                String line = sc.nextLine();
                double compTime = Double.parseDouble(line.substring(0, line.indexOf(" ")));
                double deadline = Double.parseDouble(line.substring(line.indexOf(" ") + 1));

                String temp = line.substring(line.indexOf(" ") + 1);
                double arrivalTime = Double.parseDouble(temp.substring(line.indexOf(" ") + 1));
                tasks.add(new Task(numSync + i, compTime, deadline, arrivalTime));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
