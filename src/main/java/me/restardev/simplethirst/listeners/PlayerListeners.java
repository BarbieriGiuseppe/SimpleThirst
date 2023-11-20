package me.restardev.simplethirst.listeners;

import me.restardev.simplethirst.utils.ConfigFile;
import me.restardev.simplethirst.utils.PlayerFile;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListeners implements Listener {

    private ConfigFile config;
    private PlayerFile playerFile;

    // Costruttore che accetta un'istanza di ConfigFile
    public PlayerListeners(ConfigFile config,PlayerFile playerFile) {

        this.config = config;
        this.playerFile = playerFile;
    }

    // Converti il colore esadecimale in un oggetto ChatColor

    String fullThirstBar;
    String thirstBarUnicode;
    String thirstBarString;
    String formattedText;
    double thirstLevel;
    int sprintTime = 0;

    StringBuilder thirstBar = new StringBuilder();

    String thirstBarColor;
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent playerEvent){
            Player player = playerEvent.getPlayer();

            setPlayerFile(player);
            setThirstBar(player);
            showThirstBar(player);

        }


    public void setPlayerFile(Player player){
        String uuid = String.valueOf(player.getUniqueId());
        String playerName = player.getName();

        playerFile.setName(playerName);
        playerFile.setUuid(uuid);

        playerFile.createPlayerFile(uuid,playerName);


    }

    public void setThirstBar(Player player){
        thirstBar = new StringBuilder(); // Azzera la thirstBar
        thirstBarString = config.getThirstTranslation();
        thirstBarUnicode = config.getThirstUnicode();
        fullThirstBar = thirstBarString + thirstBarUnicode;

        thirstLevel = playerFile.getThirst();

        if (thirstLevel > 0) {

            for (int i = 0; i < thirstLevel; i++) {
                thirstBar.append(thirstBarUnicode);
            }
            // Ottieni il colore per la Action Bar dalla configurazione
            thirstBarColor = config.getThirstBarColor();

            String coloredThirstBar = thirstBarColor + thirstBarString + thirstBar;

            // Converti il codice di formato del testo di Minecraft
            formattedText = ChatColor.translateAlternateColorCodes('&', coloredThirstBar);



        }

        showThirstBar(player);
    }
    public void showThirstBar(Player player) {

            // Invia la Action Bar al giocatore
            player.spigot().sendMessage(
                    ChatMessageType.ACTION_BAR,
                    new TextComponent(formattedText));
        }



    public void updateThirstBar(Player player){

            if(player.isSprinting()){
                sprintTime++;
                System.out.println(sprintTime);
                if(sprintTime == 20){
                    playerFile.setThirst(thirstLevel - 1 );

                    //qui devo chiamare il metodo setThirstBar e devo far iterare e mettere i pallini grigi fino a 10 per quanti ne mancano

                }
            }else{
                sprintTime = 0;
            }

        }
    }

