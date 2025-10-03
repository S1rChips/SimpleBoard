package me.chips.dev;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BoardManager {

    private final Main plugin;
    private final List<String> frames;
    private final List<String> lines;
    private final int delay;
    private int currentFrame = 0;
    private int taskId = -1;

    public BoardManager(Main plugin) {
        this.plugin = plugin;
        frames = plugin.getConfig().getStringList("title-frames");
        lines = plugin.getConfig().getStringList("lines");
        delay = plugin.getConfig().getInt("title-delay", 20);
    }

    public void start() {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new BoardTask(plugin, this), 0L, delay);
    }

    public void stop() {
        if (taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
        }
    }

    public String getNextFrame() {
        if (frames.isEmpty()) return "";
        String frame = frames.get(currentFrame);
        currentFrame = (currentFrame + 1) % frames.size();
        return frame;
    }

    public List<String> getLines() {
        return new ArrayList<>(lines);
    }

    public void updateAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            BoardTask.updateBoardFor(player, this);
        }
    }
}
