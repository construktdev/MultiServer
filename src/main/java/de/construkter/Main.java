package de.construkter;

public class Main {
    public static void main(String[] args) {
        String jarPath1 = "server.jar";
        String jarPath2 = "proxy.jar";

        String maxMem = "8g";

        Thread t1 = new Thread(() -> executeJar(jarPath1, maxMem));
        Thread t2 = new Thread(() -> executeJar(jarPath2, maxMem));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void executeJar(String jarPath, String maxMem) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-Xmx" + maxMem, "-jar", jarPath);
            processBuilder.inheritIO();
            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}