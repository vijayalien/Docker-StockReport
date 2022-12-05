package threadObjects;

import pageObjects.Automation;

public class ThreadAutomation {
    private static ThreadLocal<Automation> threadAutomation = new ThreadLocal<>();

    public static synchronized void setThreadAutomation(Automation automation) {
        threadAutomation.set(automation);
    }

    public static synchronized Automation getThreadAutomation() {
        return threadAutomation.get();
    }
}
