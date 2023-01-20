import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class SpicetifyLauncher {

    private int amoutOfNetTries, amoutOfTries;

    // Run
    private void run() {
        if (netIsAvailable()) {
            executeCommands();
            if (isSpotifyRunning()) {
                System.out.println("Spotify is currently running. Shutting down...");
                System.exit(0);
            } else {
                retry();
                System.out.println("Spotify is not running. Trying again");
            }
        } else {
            String msg = "No internet connection detected. Trying again in 10 seconds...";
            JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(msg);
            if (amoutOfNetTries >= 6) {
                msg = "No internet connection after 2 minutes detected! Please check your internet connection and restart the program. \nShutting Down...";
                JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println(msg);
                System.exit(404);
            } else {
                try {
                    Thread.sleep(10000);
                    amoutOfNetTries++;
                    System.out.println("Try restarting");
                    run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // Main [PSVM]
    public static void main(String[] args) {
        new SpicetifyLauncher().run();
    }

    // Spicetify apply
    private void executeCommands() {
        String[] commands = {"powershell.exe spicetify.exe upgrade", "powershell.exe spicetify.exe apply"};
        exec(commands);
        System.out.println("Spicetify commands executed");
        if (isSpotifyRunning()) {
            System.out.println("Spotify is now running after applying. Shutting down...");
            System.exit(0);
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            retry();
        }
    }
    // Troubleshooting
    private void executeTroubleshooting() {
        String[] commands = {"powershell.exe spicetify.exe backup apply", "powershell.exe spicetify.exe restore backup apply"};
        exec(commands);
        System.out.println("Spicetify Troubleshooting commands executed");
        if (isSpotifyRunning()) {
            System.out.println("Spotify is now running after Troubleshooting. Shutting down");
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(null, "Something went wrong maybe retry or check your Spicetify Installation \nhttps://spicetify.app/docs/getting-started/", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Something went wrong maybe retry or check your Spicetify Installation \nhttps://spicetify.app/docs/getting-started/");
            System.exit(2);
        }
    }
    // Retry
    private void retry(){
        if (isSpotifyRunning()) {
            System.out.println("Spotify is running after retry. Shutting down");
            System.exit(0);
        } else {
            if (amoutOfTries > 5) {
                executeTroubleshooting();
                System.out.println("Spotify is not running after 5 retries. Running Troubleshooting");
            }
            else {
                System.out.println("Spotify is not running after retry. Trying again 5 times");
                amoutOfTries++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                executeCommands();
            }
        }
    }

    // Check if internet is available
    private boolean netIsAvailable() {
        System.out.println("Checking internet connection");
        try {
            final URL testURL = new URL("https://guthib.com/");
            final URLConnection conn = testURL.openConnection();
            conn.connect();
            conn.getInputStream().close();
            System.out.println("Internet connection detected!");
            return true;
        } catch (IOException e) {
            System.out.println("No internet connection detected!");
            return false;
        }
    }

    // Check if Spotify is running
    private boolean isSpotifyRunning() {
        try {
            Process process = Runtime.getRuntime().exec("tasklist /fi \"imagename eq Spotify.exe\"");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Spotify.exe")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Execute commands
    private void exec(String[] commands) {
        for (String command : commands) {
            try {
                Runtime.getRuntime().exec(command).getOutputStream().close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Executed " + command);
        }
    }
}