import java.io.IOException;
public class Upgrade {
    static String[] command = {"powershell.exe cd $env:APPDATA", "powershell.exe spicetify.exe upgrade"};
    public Upgrade() {
        for (String s : command) {
            Process powerShellProcess;
            try {
                powerShellProcess = Runtime.getRuntime().exec(s);
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
