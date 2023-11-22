package me.restardev.simplethirst;

import me.restardev.simplethirst.listeners.CraftingListeners;
import me.restardev.simplethirst.listeners.PlayerListeners;
import me.restardev.simplethirst.utils.ConfigFile;
import me.restardev.simplethirst.utils.CraftingFile;
import me.restardev.simplethirst.utils.PlayerFile;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class SimpleThirst extends JavaPlugin {
    private int actionBarTaskId; // Variabile per memorizzare l'ID della task periodica
    private int updateActionBarTaskId; // Variabile per memorizzare l'ID della task periodica
    FileConfiguration craftingConfig;
     CraftingFile craftingFile;

    @Override
    public void onEnable() {
        System.out.println("SimpleThirst abilitato");

        // Istanzia la classe CraftingListeners e passa l'istanza del plugin
        // Carica o crea il file di configurazione


        craftingFile = new CraftingFile(new File(getDataFolder(), "craftings.yml"));

        // Esempio di come ottenere i dati di un custom recipe
        craftingFile.printAllCraftingData();

        // Registro l'evento onPlayerJoin
        ConfigFile config = new ConfigFile();
        PlayerFile playerFile = new PlayerFile();
        PlayerListeners playerListeners = new PlayerListeners(config, playerFile);
        startActionBarTask(playerListeners);

        // Avvia la task periodica per l'aggiornamento della sete dopo 5 secondi di sprint
        startUpdateActionBarTask(playerListeners);

        // Registra l'evento di crafting
       // Bukkit.getPluginManager().registerEvents(craftingListeners, this);
        Bukkit.getPluginManager().registerEvents(playerListeners, this);
    }


    @Override
    public void onDisable() {
        System.out.println("SimpleThirst disabilitato");
        stopActionBarTask();
        stopUpdateActionBarTask();
    }

    public void startActionBarTask(PlayerListeners playerListeners) {
        // Avvia una task periodica per l'Action Bar
        actionBarTaskId = new BukkitRunnable() {
            @Override
            public void run() {
                // Chiamato ogni secondo (20 ticks)
                for (Player player : Bukkit.getOnlinePlayers()) {
                        playerListeners.setThirstBar(player);

                }
            }
        }.runTaskTimer(this, 0L, 20L).getTaskId(); // 20L equivale a 1 secondo (20 ticks)
    }

    public void stopActionBarTask() {
        // Arresta la task periodica dell'Action Bar
        Bukkit.getScheduler().cancelTask(actionBarTaskId);
    }

    public void startUpdateActionBarTask(PlayerListeners playerListeners) {
        // Avvia una task periodica per l'aggiornamento della sete dopo 5 secondi di sprint
        updateActionBarTaskId = new BukkitRunnable() {
            @Override
            public void run() {
                // Chiamato ogni secondo (20 ticks)
                for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.isSprinting()) {
                            playerListeners.updateThirstBar(player);
                        }

                }
            }
        }.runTaskTimer(this, 0L, 20L).getTaskId(); // 20L equivale a 1 secondo (20 ticks)
    }

    public void stopUpdateActionBarTask() {
        // Arresta la task periodica per l'aggiornamento della sete dopo 5 secondi di sprint
        Bukkit.getScheduler().cancelTask(updateActionBarTaskId);
    }
}
