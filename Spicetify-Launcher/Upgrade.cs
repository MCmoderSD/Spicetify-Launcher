using System.Management.Automation;

namespace Spicetify_Launcher;

public class Upgrade
{
    private static string[] commands = new string[] { "spicetify.exe upgrade" };

    public Upgrade()
    {
        foreach (var command in commands)
        {
            PowerShell ps = PowerShell.Create();
            ps.AddScript(command).Invoke();
        }
    }
}