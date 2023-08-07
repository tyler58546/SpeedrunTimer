package com.tyler58546.speedruntimer;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class TimerPlugin extends JavaPlugin implements Listener {
    private long ticksElapsed = 0L;
    private boolean timerRunning = false;

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("timer")).setExecutor(new TimerCommand(this));

        getServer().getPluginManager().registerEvents(this, this);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (timerRunning) {
                    ticksElapsed++;
                }
            }
        }.runTaskTimer(this, 0L, 1L);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (ticksElapsed <= 0) return;
                for (Player player : getServer().getOnlinePlayers()) {
                    if (timerRunning) {
                        player.sendActionBar(ChatColor.GREEN + getTimeElapsed());
                    } else {
                        player.sendActionBar(ChatColor.BLUE + getTimeElapsed());
                    }
                }
            }
        }.runTaskTimer(this, 0L, 10L);
    }

    void startTimer() {
        timerRunning = true;
    }

    void stopTimer() {
        timerRunning = false;
    }

    void resetTimer() {
        timerRunning = false;
        ticksElapsed = 0L;
    }

    String getTimeElapsed() {
        long seconds = ticksElapsed / 20L;
        return String.format("%02d:%02d", seconds / 60L, seconds % 60L);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (!timerRunning) return;
        if (e.getTo().getWorld() != getServer().getWorld("world_the_end")) return;
        if (e.getTo().getBlock().getType() != Material.END_PORTAL) return;
        for (Player player : getServer().getOnlinePlayers()) {
            player.sendTitle(ChatColor.BLUE + getTimeElapsed(), "", 0, 1000, 20);
        }
        stopTimer();
    }
}
