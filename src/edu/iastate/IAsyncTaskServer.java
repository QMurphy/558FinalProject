package edu.iastate;

import java.util.List;

/**
 * Created by qmurp on 12/4/2016.
 */
public interface IAsyncTaskServer {
    List<Task> run(int curTime);
    void refresh();
    void addTask(Task t);
    boolean canRun();
    boolean shouldRefresh(int curTime);
}
