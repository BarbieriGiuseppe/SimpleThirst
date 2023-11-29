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
                // Crea un esempio di custom_recipe
                String exampleKey = "custom_recipe_1";
                String keyPath = exampleKey + ".";

                config.set(keyPath + "material", "DIAMOND_SWORD");
                config.set(keyPath + "display_name", "&bSpada Magica");
                config.set(keyPath + "lore", java.util.Arrays.asList("&7Una spada potente creata con magia"));
                config.set(keyPath + "potion_effects", java.util.Arrays.asList("type: INCREASE_DAMAGE"));
                config.set(keyPath + "duration", 300);
                config.set(keyPath + "amplifier", 1);
                config.set(keyPath + "thirst", 5.0);
                config.set(keyPath + "custom_id", 1);

                // Aggiungi la sezione recipe
                config.set(keyPath + "recipe.shape", java.util.Arrays.asList("ABC", "DEF", "GHI"));
                Map<String, String> ingredients = new HashMap<>();
                ingredients.put("A", "IRON_INGOT");
                ingredients.put("B", "STICK");
                ingredients.put("C", "AIR");
                ingredients.put("D", "IRON_INGOT");
                ingredients.put("E", "STICK");
                ingredients.put("F", "AIR");
                ingredients.put("G", "AIR");
                ingredients.put("H", "STICK");
                ingredients.put("I", "AIR");
                config.set(keyPath + "recipe.ingredients", ingredients);
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

    public List<String> getShape(String customRecipeKey) {
        ConfigurationSection customRecipeSection = config.getConfigurationSection(customRecipeKey + ".recipe.");
        if (customRecipeSection != null && customRecipeSection.isList("shape")) {
            return customRecipeSection.getStringList("shape");
        }
        return new ArrayList<>();
    }

    public Map<Character, Material> getIngredients(String customRecipeKey) {
        ConfigurationSection customRecipeSection = config.getConfigurationSection(customRecipeKey + ".recipe.");
        Map<Character, Material> ingredientsMap = new HashMap<>();

        if (customRecipeSection != null && customRecipeSection.isConfigurationSection("ingredients")) {
            ConfigurationSection ingredientsSection = customRecipeSection.getConfigurationSection("ingredients");

            for (String key : ingredientsSection.getKeys(false)) {
                char ingredientChar = key.charAt(0);
                Material ingredientMaterial = Material.getMaterial(ingredientsSection.getString(key));
                ingredientsMap.put(ingredientChar, ingredientMaterial);
            }
        }

        return ingredientsMap;
    }

    public File getFile() {
        return file;
    }


    public FileConfiguration getConfig() {
        return config;
    }

}
