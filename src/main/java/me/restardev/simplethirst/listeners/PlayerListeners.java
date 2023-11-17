package me.restardev.simplethirst.listeners;

import me.restardev.simplethirst.utils.ConfigFile;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListeners implements Listener {

    private ConfigFile config;

    // Costruttore che accetta un'istanza di ConfigFile
    public PlayerListeners(ConfigFile config) {

        this.config = config;
    }

    // Converti il colore esadecimale in un oggetto ChatColor

    String fullThirstBar;
    String thirstBarUnicode;
    String sete;

    String thirstBarColor;
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent playerEvent){
            Player player = playerEvent.getPlayer();
            showThirstBar(player);

        }

    public void showThirstBar(Player player) {
        sete = config.getThirstTranslation();
        thirstBarUnicode = config.getThirstUnicode();
        fullThirstBar = sete + thirstBarUnicode;

        // Ottieni il colore per la Action Bar dalla configurazione
        thirstBarColor = config.getThirstBarColor();

        // Aggiungi il prefisso del colore al testo
        String coloredText = thirstBarColor + sete;
        String coloredUnicode = thirstBarColor + thirstBarUnicode;

        // Converti il codice di formato del testo di Minecraft
        String formattedText = ChatColor.translateAlternateColorCodes('&', coloredText + coloredUnicode);

        player.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                new TextComponent(formattedText));
    }


}
