package me.restardev.simplethirst.utils;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PlayerFile {
    private String uuid;
    private String name;
    private double thirst;

    public PlayerFile() {
        createPlayerFolder();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getThirst() {
        return thirst;
    }

    public void setThirst(double thirst) {
        this.thirst = thirst;
    }

    public void createPlayerFolder() {
        // Ottenere la cartella del plugin
        File cartellaPlugin = new File("plugins/SimpleThirst/PlayerData");

        // Se la cartella del plugin non esiste, crearla
        if (!cartellaPlugin.exists()) {
            cartellaPlugin.mkdirs();
        }


    }

    public void createPlayerFile(String uuid, String name) {
        // Creare la cartella del plugin se non esiste
        File cartellaPlugin = new File("plugins/SimpleThirst/PlayerData");
        if (!cartellaPlugin.exists()) {
            cartellaPlugin.mkdirs();
        }

        // Creare il percorso completo del file YAML
        File fileYml = new File(cartellaPlugin, uuid + ".yml");

        // Controllare se il file esiste
        if (!fileYml.exists()) {
            // Se il file non esiste, crealo con i valori forniti
            try {
                fileYml.createNewFile();
                DumperOptions options = new DumperOptions();
                options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
                options.setPrettyFlow(true);

                Yaml yaml = new Yaml(options);

                // Creare la mappa con i dati
                // Qui puoi aggiungere i dati che desideri salvare nel file
                // In questo esempio, sto salvando l'UUID, il nome e la sete
                // Modifica questo blocco in base alle tue esigenze
                HashMap<String, Object> playerData = new HashMap<>();
                playerData.put("uuid", uuid);
                playerData.put("name", name);
                playerData.put("thirst", 10.00);

                // Scrivere la mappa nel file YAML
                try (FileWriter writer = new FileWriter(fileYml)) {
                    yaml.dump(playerData, writer);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try (FileInputStream input = new FileInputStream(fileYml)) {
                Yaml yaml = new Yaml();
                // Carica i dati dal file YAML in una mappa
                @SuppressWarnings("unchecked")
                Map<String, Object> playerData = yaml.load(input);

                if (playerData != null) {
                    //chiamo gli opportuni set per impostare i valori letti dal file
                    setName((String) playerData.get("name"));
                    setUuid((String) playerData.get("uuid"));
                    setThirst((double) playerData.get("thirst"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updatePlayerFile(String uuid) {
        // Creare la cartella del plugin se non esiste
        File cartellaPlugin = new File("plugins/SimpleThirst/PlayerData");
        if (!cartellaPlugin.exists()) {
            cartellaPlugin.mkdirs();
        }
        File fileYml = new File(cartellaPlugin, uuid + ".yml");

        try {
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            options.setPrettyFlow(true);

            Yaml yaml = new Yaml(options);

            // Creare la mappa con i dati
            // Qui puoi aggiungere i dati che desideri salvare nel file
            // In questo esempio, sto salvando l'UUID, il nome e la sete
            // Modifica questo blocco in base alle tue esigenze
            HashMap<String, Object> playerData = new HashMap<>();
            playerData.put("uuid", uuid);
            playerData.put("name", name);
            playerData.put("thirst", thirst);

            // Scrivere la mappa nel file YAML
            try (FileWriter writer = new FileWriter(fileYml)) {
                yaml.dump(playerData, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





}
