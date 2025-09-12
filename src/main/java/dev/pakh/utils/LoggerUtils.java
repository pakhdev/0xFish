package dev.pakh.utils;

public class LoggerUtils {
    public static void logCall(String format, Object... args) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement current = stackTrace[2];
        StackTraceElement caller = stackTrace[3];
        String callerMethod = caller.getMethodName();
        String currentMethod = current.getMethodName();
        String extraInfo = String.format(format, args);
        System.out.println(
                callerMethod + "() -> " + currentMethod + "(): " + extraInfo
        );
    }

    public static void logBlock(Object[][] nameValuePairs, int perLine) {
        if (nameValuePairs == null || nameValuePairs.length == 0) return;

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement current = stackTrace[2];
        StackTraceElement caller = stackTrace[3];

        String callerMethod = caller.getMethodName();
        String currentMethod = current.getMethodName();

        String border = "===============================";
        System.out.println(border);
        System.out.println(callerMethod + "() -> " + currentMethod + "():");

        for (int i = 0; i < nameValuePairs.length; i++) {
            System.out.printf("%s: %s", nameValuePairs[i][0], nameValuePairs[i][1]);
            if (i < nameValuePairs.length - 1) {
                System.out.print(" | ");
            }
            if ((i + 1) % perLine == 0 || i == nameValuePairs.length - 1) {
                System.out.println();
            }
        }

        System.out.println(border);
    }
}

