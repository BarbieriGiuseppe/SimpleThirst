package me.restardev.simplethirst.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CraftingFile {
    private File file;
    private FileConfiguration config;

    public CraftingFile(File file) {
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
        // Carica il file e imposta i valori predefiniti se non esiste
        if (!file.exists()) {
            saveResource();
        }
    }

    private void saveResource() {
        try {
            // Itera su tutte le chiavi presenti nel file YAML
            for (String customRecipeKey : config.getKeys(false)) {
                String keyPath = customRecipeKey + ".";

                config.set(keyPath + "material", "DIAMOND_SWORD");
                config.set(keyPath + "display_name", "&bSpada Magica");
                config.set(keyPath + "lore", java.util.Arrays.asList("&7Una spada potente creata con magia"));
                config.set(keyPath + "potion_effects", java.util.Arrays.asList("type: INCREASE_DAMAGE"));
                config.set(keyPath + "duration", 300);
                config.set(keyPath + "amplifier", 1);
                config.set(keyPath + "thirst", 5.0);
                config.set(keyPath + "custom_id", 1);
                config.set(keyPath + "recipe", java.util.Arrays.asList("IRON_INGOT", "STICK", "AIR", "IRON_INGOT", "STICK", "AIR", "AIR", "STICK", "AIR"));
            }

            // Salva il resource nel file
            file.getParentFile().mkdirs();
            file.createNewFile();
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void reload() {
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> getCraftingData(String customRecipeKey) {
        ConfigurationSection customRecipeSection = config.getConfigurationSection(customRecipeKey);
        if (customRecipeSection != null) {
            return customRecipeSection.getValues(true);
        }
        return new HashMap<>();
    }

    public File getFile() {
        return file;
    }

    public void printAllCraftingData() {
        for (String customRecipeKey : config.getKeys(true)) {
            Map<String, Object> craftingData = getCraftingData(customRecipeKey);
            System.out.println("Crafting Data for " + customRecipeKey + ":");
            for (Map.Entry<String, Object> entry : craftingData.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            System.out.println("------");
        }
    }
}
