package me.restardev.simplethirst.listeners;

import me.restardev.simplethirst.utils.ConfigFile;
import me.restardev.simplethirst.utils.PlayerFile;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Bukkit.getLogger;

public class PlayerListeners implements Listener {

    private ConfigFile config;
    private PlayerFile playerFile;

    // Costruttore che accetta un'istanza di ConfigFile
    public PlayerListeners(ConfigFile config,PlayerFile playerFile) {

        this.config = config;
        this.playerFile = playerFile;
    }

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

    @EventHandler
    public void onPlayerRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            // Verifica se il giocatore sta rigenerando la vita
            if (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) {
                double amount = event.getAmount();

                // Verifica se il giocatore ha rigenerato esattamente 1.5 cuori (3.0 di vita)
                if (amount == config.getHeartRestoreThirstDecrease()) {

                    // Decrementa la sete di 1
                    double currentThirst = playerFile.getThirst();
                    playerFile.setThirst(Math.max(0, currentThirst - 1));
                    playerFile.updatePlayerFile(player.getUniqueId().toString());

                    // Mostra la nuova barra della sete
                    setThirstBar(player);
                    showThirstBar(player);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent playerDeathEvent) {
        playerFile.setThirst(10.0);
        playerFile.updatePlayerFile(playerDeathEvent.getPlayer().getUniqueId().toString());

    }

    public void setPlayerFile(Player player){
        String uuid = String.valueOf(player.getUniqueId());
        String playerName = player.getName();

        playerFile.setName(playerName);
        playerFile.setUuid(uuid);

        playerFile.createPlayerFile(uuid,playerName);


    }

    public void setThirstBar(Player player) {
        thirstBar = new StringBuilder(); // Azzera la thirstBar
        thirstBarString = config.getThirstTranslation();
        thirstBarUnicode = config.getThirstUnicode();
        fullThirstBar = thirstBarString + thirstBarUnicode;

        thirstLevel = playerFile.getThirst();
        thirstLevel = Math.min(10, Math.max(0, thirstLevel)); // Ensure thirstLevel is between 0 and 10


        for (int i = 0; i < thirstLevel; i++) {
            thirstBar.append(thirstBarUnicode);
        }

        // aggiungo i pallini restanti fino a 10 di colore grigio solo se thirstLevel < 10
        if (thirstLevel < 10) {
            for (int i = (int) thirstLevel; i < 10; i++) {
                thirstBar.append("&7").append(thirstBarUnicode); // &7 rappresenta il colore grigio
            }
        }



        // Ottieni il colore per la Action Bar dalla configurazione
        thirstBarColor = config.getThirstBarColor();

        String coloredThirstBar = thirstBarColor + thirstBarString + thirstBar;

        // Converti il codice di formato del testo di Minecraft
        formattedText = ChatColor.translateAlternateColorCodes('&', coloredThirstBar);

        //se il player entra in creative imposta la sete al massimo e nasconde la barra
        if(player.getGameMode().equals(GameMode.CREATIVE)){
            playerFile.setThirst(10.0);
            playerFile.updatePlayerFile(String.valueOf(player.getUniqueId()));
            formattedText = "";
            showThirstBar(player);

        }
        emptyThirst(player);
        showThirstBar(player);

    }




    public void showThirstBar(Player player) {

        String leftPadding = "                               ";
        String leftAlignedText = formattedText + leftPadding;

        // Invia la Action Bar al giocatore
            player.spigot().sendMessage(
                    ChatMessageType.ACTION_BAR,
                    new TextComponent(leftAlignedText));
        }



    public void updateThirstBar(Player player){

            if(player.isSprinting()){
                sprintTime++;

                if(sprintTime >= config.getSprintThirstDecrease()){ //se il tempo di corsa è >= dello sprint-time specificato nel config.yml
                    playerFile.setThirst(thirstLevel - 1 ); //tolgo una pallina di sete
                    playerFile.updatePlayerFile(String.valueOf(player.getUniqueId())); //aggiorno il file yml
                    sprintTime = 0; //resetto il timer

                }
            }else{
                sprintTime = 0;
            }

        }

    public void emptyThirst(Player player) {

        double thirstLevelCheck = playerFile.getThirst();

        if (thirstLevelCheck == 0 || thirstLevelCheck == -1) {
            // Notify the player
            String message = ChatColor.RED + config.getThirstMessage();
            player.spigot().sendMessage(ChatMessageType.SYSTEM, TextComponent.fromLegacyText(message));

            // Apply effects
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 40, 0)); // 40 ticks = 2 seconds
            player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 40, 0)); // 40 ticks = 2 seconds
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 0)); // 40 ticks = 2 seconds
        }
    }
}



