package me.restardev.simplethirst.utils;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;

import java.util.HashMap;
import java.util.Map;

public class ConfigFile {

    private String thirstUnicode;
    private String thirstTranslation;

    public ConfigFile() {
        loadFromYml("config.yml");
    }

    public void setThirstTranslation(String thirstTranslation) {
        this.thirstTranslation = thirstTranslation;
    }

    public String getThirstTranslation() {
        return thirstTranslation;
    }

    public String getThirstUnicode() {
        return thirstUnicode;
    }

    public void setThirstUnicode(String thirstUnicode) {
        this.thirstUnicode = thirstUnicode;
    }

    private void loadFromYml(String percorso) {
        // Ottenere la cartella del plugin
        File cartellaPlugin = new File("plugins/SimpleThirst");

        // Se la cartella del plugin non esiste, crearla
        if (!cartellaPlugin.exists()) {
            cartellaPlugin.mkdirs();
        }

        // Creare il percorso completo del file YAML
        File fileYml = new File(cartellaPlugin, percorso);

        // Controllare se il file esiste
        if (!fileYml.exists()) {
            // Se il file non esiste, crealo con valori predefiniti
            creaFileDefault(fileYml);
        }

        // Caricare i dati dal file YAML
        try (InputStream input = new FileInputStream(fileYml)) {
            Yaml yaml = new Yaml();
            // Converte il contenuto del file YAML in una mappa di stringhe e oggetti
            @SuppressWarnings("unchecked")
            Map<String, Object> dati = yaml.load(input);

            // Imposta i valori della classe utilizzando i dati dal file YAML
            if (dati != null) {
                setThirstTranslation((String) dati.get("thirst-string"));
                setThirstUnicode((String) dati.get("thirst-unicode"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void creaFileDefault(File fileYml) {
        try {
            // Crea un nuovo file con i valori predefiniti
            Map<String, Object> defaultValues = new HashMap<>();
            defaultValues.put("# Change the string below in order to translate the word in your language",null);
            defaultValues.put("thirst-string", "Sete");

            defaultValues.put("# Change the string below in order to change the symbol",null);
            defaultValues.put("thirst-unicode", "\u2B24");

            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

            Yaml yaml = new Yaml(options);

            try (Writer output = new FileWriter(fileYml)) {
                yaml.dump(defaultValues, output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
