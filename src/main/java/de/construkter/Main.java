package de.construkter;

public class Main {
    public static void main(String[] args) {
        // Pfade zu den JAR-Dateien
        String jarPath1 = "server.jar";
        String proxypath = "wasserfall.jar";

        // Arbeitsverzeichnis für die proxy.jar
        String workingDir = "./proxy";

        // Maximaler Speicher, den der JVM-Prozess nutzen darf
        String maxMem = "8g";

        // Erstellen und Starten der Threads
        Thread t1 = new Thread(() -> executeJar(jarPath1, maxMem));
        Thread t2 = new Thread(() -> runProxy(proxypath, maxMem, workingDir));

        t1.start();
        t2.start();

        // Warten, bis beide Threads abgeschlossen sind
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Methode zum Ausführen einer JAR-Datei ohne Verzeichniswechsel
    private static void executeJar(String jarPath, String maxMem) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-Xmx" + maxMem, "-jar", jarPath);
            processBuilder.inheritIO(); // Ausgabe und Fehler auf der Konsole anzeigen
            Process process = processBuilder.start();
            process.waitFor(); // Warten, bis der Prozess abgeschlossen ist
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Methode zum Ausführen der proxy.jar im spezifizierten Verzeichnis
    private static void runProxy(String proxypath, String maxMem, String workingDir) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-Xmx" + maxMem, "-jar", proxypath);
            processBuilder.directory(new java.io.File(workingDir)); // Setzt das Arbeitsverzeichnis
            processBuilder.inheritIO(); // Ausgabe und Fehler auf der Konsole anzeigen
            Process process = processBuilder.start();
            process.waitFor(); // Warten, bis der Prozess abgeschlossen ist
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
