package me.restardev.simplethirst.utils;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public List<String> getAllCustomRecipeKeys() {

        return new ArrayList<>(config.getKeys(false));
    }

    public List<String> getRecipe(String customRecipeKey) {
        ConfigurationSection customRecipeSection = config.getConfigurationSection(customRecipeKey);
        if (customRecipeSection != null && customRecipeSection.isList("recipe")) {
            return customRecipeSection.getStringList("recipe");
        }
        return new ArrayList<>();
    }

    public String getMaterial(String customRecipeKey){
        ConfigurationSection customRecipeSection = config.getConfigurationSection(customRecipeKey);
        if (customRecipeSection != null) {
            return customRecipeSection.getString("material"); // 0 è il valore di default se "amplifier" non è presente
        }
        return null;
    }
    public String getDisplayName(String customRecipeKey) {
        ConfigurationSection customRecipeSection = config.getConfigurationSection(customRecipeKey);
        if (customRecipeSection != null) {
            return customRecipeSection.getString("display_name", "");
        }
        return "";
    }

    public List<String> getLore(String customRecipeKey) {
        ConfigurationSection customRecipeSection = config.getConfigurationSection(customRecipeKey);
        if (customRecipeSection != null && customRecipeSection.isList("lore")) {
            return customRecipeSection.getStringList("lore");
        }
        return new ArrayList<>();
    }

    public String getPotionEffectType(String customRecipeKey) {
        ConfigurationSection customRecipeSection = config.getConfigurationSection(customRecipeKey);
        if (customRecipeSection != null && customRecipeSection.isList("potion_effects")) {
            List<String> potionEffects = customRecipeSection.getStringList("potion_effects");
            if (!potionEffects.isEmpty()) {
                String firstPotionEffect = potionEffects.get(0);
                ConfigurationSection effectSection = YamlConfiguration.loadConfiguration(new StringReader(firstPotionEffect));
                return effectSection.getString("type", "");
            }
        }
        return "";
    }

    public int getDuration(String customRecipeKey) {
        ConfigurationSection customRecipeSection = config.getConfigurationSection(customRecipeKey);
        if (customRecipeSection != null) {
            return customRecipeSection.getInt("duration", 0);
        }
        return 0;
    }

    public int getAmplifier(String customRecipeKey) {
        ConfigurationSection customRecipeSection = config.getConfigurationSection(customRecipeKey);
        if (customRecipeSection != null) {
            return customRecipeSection.getInt("amplifier", 0);
        }
        return 0;
    }

    public double getThirst(String customRecipeKey) {
        ConfigurationSection customRecipeSection = config.getConfigurationSection(customRecipeKey);
        if (customRecipeSection != null) {
            return customRecipeSection.getDouble("thirst", 0.0);
        }
        return 0.0;
    }

    public int getCustomId(String customRecipeKey) {
        ConfigurationSection customRecipeSection = config.getConfigurationSection(customRecipeKey);
        if (customRecipeSection != null) {
            return customRecipeSection.getInt("custom_id", 0);
        }
        return 0;
    }

    public File getFile() {
        return file;
    }


    public FileConfiguration getConfig() {
        return config;
    }

}
