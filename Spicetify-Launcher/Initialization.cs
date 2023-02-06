using System.Management.Automation;

namespace Spicetify_Launcher;

public class Initialization
{
    private string[] commands = new[] { "spicetify.exe apply" };
    public Initialization()
    {
        foreach (var command in commands)
        { 
            PowerShell ps = PowerShell.Create(); 
            ps.AddScript("spicetify.exe apply").Invoke();
        }
    }
}