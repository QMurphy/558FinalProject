package edu.iastate;

import java.io.File;
import java.util.List;

//Just making this for testing so far
public class Main {

    public static void main(String[] args){
        InputParser ip = new InputParser(new File("input.txt"));
        List<Task> tasks = ip.parseInput();
        for(Task t : tasks){
            System.out.println(t.getID() + " " + t.getCompTime() + " " + t.getPeriod());
        }
    }
}