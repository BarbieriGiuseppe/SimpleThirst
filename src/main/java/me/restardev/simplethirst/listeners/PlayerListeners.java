package me.restardev.simplethirst.listeners;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListeners implements Listener {



    // Converti il colore esadecimale in un oggetto ChatColor

    String fullThirstBar = ChatColor.AQUA + "\u2B24\u2B24\u2B24\u2B24\u2B24\u2B24\u2B24\u2B24\u2B24\u2B24";

    String sete = ChatColor.AQUA + "Sete: ";

        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent playerEvent){
            Player player = playerEvent.getPlayer();
            showThirstBar(player);

        }

    public void showThirstBar(Player player){


        player.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                new TextComponent(sete + fullThirstBar));

    }


}
