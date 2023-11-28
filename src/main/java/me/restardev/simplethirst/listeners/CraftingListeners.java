package me.restardev.simplethirst.listeners;

import me.restardev.simplethirst.utils.ConfigFile;
import me.restardev.simplethirst.utils.CraftingFile;
import me.restardev.simplethirst.utils.PlayerFile;
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

    private CraftingFile config;
    private PlayerFile playerFile;

    // Costruttore che accetta un'istanza di ConfigFile
    public CraftingListeners(CraftingFile config,PlayerFile playerFile) {

        this.config = config;
        this.playerFile = playerFile;
    }



}
