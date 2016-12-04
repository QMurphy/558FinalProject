package edu.iastate;

import java.util.ArrayList;

/**
 * Created by qmurp on 12/4/2016.
 */
public interface IAsyncTaskServer {
    void run(int curTime);
    void addTask(Task t);
    ArrayList<History> getHistory();
}
