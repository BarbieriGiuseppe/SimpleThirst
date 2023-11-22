package me.restardev.simplethirst.listeners;

import me.restardev.simplethirst.utils.CraftingFile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.util.List;
import java.util.Map;

public class CraftingListeners implements Listener {

    private final JavaPlugin plugin;

    public CraftingListeners(JavaPlugin plugin) {
        this.plugin = plugin;


    }



}
