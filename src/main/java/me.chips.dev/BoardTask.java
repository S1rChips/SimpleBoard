package me.chips.dev;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.List;

public class BoardTask implements Runnable {

    private final Main plugin;
    private final BoardManager manager;

    public BoardTask(Main plugin, BoardManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public void run() {
        manager.updateAll();
    }

    public static void updateBoardFor(Player player, BoardManager manager) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("simpleboard", "dummy");

        String title = color(PlaceholderAPI.setPlaceholders(player, manager.getNextFrame()));
        if (title.length() > 32) title = title.substring(0, 32);
        obj.setDisplayName(title);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<String> lines = manager.getLines();
        int score = lines.size();

        for (String line : lines) {
            String parsed = color(PlaceholderAPI.setPlaceholders(player, line));

            if (parsed.length() > 40) {
                parsed = parsed.substring(0, 40);
            }

            while (board.getEntries().contains(parsed)) {
                parsed = parsed + " ";
            }

            obj.getScore(parsed).setScore(score--);
        }

        player.setScoreboard(board);
    }

    private static String color(String text) {
        return text == null ? "" : text.replace("&", "§");
    }
}
