package me.chips.dev;

import me.chips.dev.commands.ReloadCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    private BoardManager boardManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().severe("PlaceholderAPI not found! Disabling plugin...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        boardManager = new BoardManager(this);
        boardManager.start();

        getCommand("sboard").setExecutor(new ReloadCommand(this));
        getLogger().info("SimpleBoard enabled!");
    }

    @Override
    public void onDisable() {
        if (boardManager != null) {
            boardManager.stop();
        }
    }

    public static Main getInstance() {
        return instance;
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }

    public void reload() {
        reloadConfig();
        if (boardManager != null) {
            boardManager.stop();
        }
        boardManager = new BoardManager(this);
        boardManager.start();
    }
}
