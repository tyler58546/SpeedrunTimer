package com.tyler58546.speedruntimer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimerCommand implements CommandExecutor, TabCompleter {
    private final TimerPlugin plugin;

    TimerCommand(TimerPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1) return false;
        switch (args[0].toLowerCase()) {
            case "start":
                sender.sendMessage(ChatColor.GREEN + "The timer has started.");
                plugin.startTimer();
                return true;
            case "stop":
                sender.sendMessage(ChatColor.RED + "The timer has been stopped.");
                plugin.stopTimer();
                return true;
            case "reset":
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "The timer has been reset.");
                plugin.resetTimer();
                return true;
            default:
                return false;
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        ArrayList<String> completions = new ArrayList<>();
        if (args.length == 1) {
            for (String arg : new String[]{"start", "stop", "reset"}) {
                if (arg.startsWith(args[0])) {
                    completions.add(arg);
                }
            }
        }
        return completions;
    }
}
