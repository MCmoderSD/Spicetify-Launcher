import java.io.IOException;

public class SpicetifyLauncher {
    public static void main(String[] args) {
        String[] commands = {"powershell.exe spicetify.exe upgrade", "powershell.exe spicetify.exe apply"};
        for (int i = 0; i < commands.length; i++) {
            String command = commands[i];
            Process powerShellProcess;
            try {
                powerShellProcess = Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                powerShellProcess.getOutputStream().close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
