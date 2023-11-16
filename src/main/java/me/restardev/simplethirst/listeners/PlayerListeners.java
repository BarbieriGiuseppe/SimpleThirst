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

        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent playerEvent){
            Player player = playerEvent.getPlayer();
            showThirstBar(player);

        }

    public void showThirstBar(Player player){

            sete = config.getThirstTranslation();
            thirstBarUnicode = config.getThirstUnicode();

            String fullThirstBar = ChatColor.AQUA + thirstBarUnicode;


        player.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                new TextComponent(sete + fullThirstBar));

    }


}
