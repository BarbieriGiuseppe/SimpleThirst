package me.restardev.simplethirst;

import me.restardev.simplethirst.listeners.PlayerListeners;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleThirst extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("\r\n"
                + "___  ___          _       ______        ______     _____ _                     \r\n"
                + "|  \\/  |         | |      | ___ \\       | ___ \\   /  ___| |                    \r\n"
                + "| .  . | __ _  __| | ___  | |_/ /_   _  | |_/ /___\\ `--.| |_ __ _ _ __         \r\n"
                + "| |\\/| |/ _` |/ _` |/ _ \\ | ___ \\ | | | |    // _ \\`--. \\ __/ _` | '__|        \r\n"
                + "| |  | | (_| | (_| |  __/ | |_/ / |_| | | |\\ \\  __/\\__/ / || (_| | |           \r\n"
                + "\\_|  |_/\\__,_|\\__,_|\\___| \\____/ \\__, | \\_| \\_\\___\\____/ \\__\\__,_|_|           \r\n"
                + "                                  __/ |                          ______ ______ \r\n"
                + "                                 |___/                          |______|______|\r\n"
                + "");

        //registro l'evento onplayerjoin
        getServer().getPluginManager().registerEvents(new PlayerListeners(),this);


    }

    @Override
    public void onDisable() {
        System.out.println("SimpleThirst disabilitato");


    }
}
