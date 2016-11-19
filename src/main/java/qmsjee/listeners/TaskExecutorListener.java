/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 
 */
package qmsjee.listeners;

/**
 *
 * @author Tomek
 */
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class TaskExecutorListener implements ServletContextListener {

    private static ExecutorService executor;

    @Override
    public void contextInitialized(ServletContextEvent cs) {
        createExecutor();
        cs.getServletContext().log("Executor service started !");
    }

    @Override
    public void contextDestroyed(ServletContextEvent cs) {

        executor.shutdown();

        cs.getServletContext().log("Executor service shutdown !");
    }

    public static synchronized void submitTask(Runnable runnable) {
        if (executor == null) {
            createExecutor();
        }
        executor.submit(runnable);
    }

    public static synchronized Future<String> submitTask(Callable callable) {
        if (executor == null) {
            createExecutor();
        }
        return executor.submit(callable);
    }

    static void createExecutor() {
        executor = new ThreadPoolExecutor(
                1,
                3,
                100L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

    }
}
