package us.blockcade.fusebox.registration;

import org.bukkit.command.CommandExecutor;

public class CommandData {

    private String label;
    private CommandExecutor executor;

    public CommandData(String label, CommandExecutor executor) {
        this.label = label;
        this.executor = executor;
    }

    public String getLabel() { return label; }
    public CommandExecutor getExecutor() { return executor; }

}
