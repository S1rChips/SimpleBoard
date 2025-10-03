package me.chips.dev.commands;

import me.chips.dev.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final Main plugin;

    public ReloadCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("sboard reload")) {
            plugin.reload();
            sender.sendMessage("§a[SimpleBoard] Config reloaded!");
            return true;
        }
        sender.sendMessage("§cUsage: /sboard reload");
        return true;
    }
}
