package me.restardev.simplethirst.listeners;

import me.restardev.simplethirst.SimpleThirst;
import me.restardev.simplethirst.utils.CraftingFile;
import me.restardev.simplethirst.utils.PlayerFile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CraftingListeners implements Listener {

    private CraftingFile config;
    private PlayerFile playerFile;
    private final SimpleThirst plugin;  // Assume che JavaPlugin sia l'import corretto per la tua classe principale del plugin

    private List<ItemStack> items = new ArrayList<>();


    // Costruttore che accetta un'istanza di ConfigFile
    public CraftingListeners(CraftingFile config, PlayerFile playerFile, SimpleThirst simpleThirst) {

        this.config = config;
        this.playerFile = playerFile;
        this.plugin = simpleThirst;
    }

    /**
     * Questo metodo serve a registrare tutti i crafting custom presenti nel file crafting.yml
     * In primo luogo inizia una iterazione su tutte le chiavi principali ovvero i custom_recipe_NUMERO
     * dopodichè vengono utilizzati i metodi get creati nella classe CraftingFile per ottenere le caratteristiche dell'oggetto
     * attraverso ShapedRecipe definisco la ricetta per ottenere l'oggetto, prendendo la shape e gli ingredienti
     *
     * @param allCustomRecipeKeys
     */
    public List<ItemStack> registerCrafting(List<String> allCustomRecipeKeys) {
        for (String customRecipeKey : allCustomRecipeKeys) {
            String materialName = config.getMaterial(customRecipeKey);

            if (materialName != null) {
                Material material = Material.getMaterial(materialName);

                if (material != null) {
                    ItemStack item = new ItemStack(material);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(config.getDisplayName(customRecipeKey));
                    meta.setLore(config.getLore(customRecipeKey));
                    item.setItemMeta(meta);
                    items.add(item);

                    ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, customRecipeKey), item);
                    List<String> shape = config.getShape(customRecipeKey);
                    Map<Character, Material> ingredients = config.getIngredients(customRecipeKey);
                    // Impostazione della forma della ricetta
                    recipe.shape(shape.get(0),shape.get(1),shape.get(2));

                    // Aggiunta degli ingredienti alla ricetta
                    for (Map.Entry<Character, Material> entry : ingredients.entrySet()) {
                        recipe.setIngredient(entry.getKey(), entry.getValue());
                    }

                    // Aggiunta della ricetta al server
                    plugin.getServer().addRecipe(recipe);


                } else {
                    System.out.println("Il nome del materiale " + materialName + " non è valido.");
                }
            } else {
                System.out.println("Il materiale non è specificato per la chiave " + customRecipeKey);
            }
        }

        System.out.println("Added " + items.size() + " Items"); //stampo sulla console il numero di oggetti aggiunti in game

        return items;
    }

}
