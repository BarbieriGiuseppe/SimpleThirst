package me.restardev.simplethirst.utils;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;

import java.util.HashMap;
import java.util.Map;

public class ConfigFile {

    private String thirstUnicode;
    private String thirstTranslation;

    private String thirstBarColor;

    public ConfigFile() {
        loadFromYml("config.yml");
    }

    public void setThirstBarColor(String thirstBarColor) {
        this.thirstBarColor = thirstBarColor;
    }

    public String getThirstBarColor() {
        return thirstBarColor;
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
                setThirstBarColor((String) dati.get("thirst-bar-color"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void creaFileDefault(File file) {
        // Crea un oggetto Yaml
        Yaml yaml = new Yaml();

        // Crea una mappa per i valori predefiniti
        Map<String, Object> defaultValues = new HashMap<>();
        defaultValues.put("thirst-string", "Sete:");
        defaultValues.put("thirst-unicode", "\u2B24");
        defaultValues.put("thirst-bar-color","&9");
        // Aggiungi commenti usando DumperOptions
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setPrettyFlow(true);

        // Creare uno StringWriter per scrivere il risultato
        StringWriter stringWriter = new StringWriter();
        // Utilizzare un nuovo oggetto Yaml con DumperOptions
        Yaml yamlWithComments = new Yaml(dumperOptions);

        // Converti la mappa in YAML e scrivi su stringWriter
        yamlWithComments.dump(defaultValues, stringWriter);

        // Scrivi la stringa risultante nel file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Aggiungi commenti al file usando stringWriter
            writer.write("# In this file you can customize your thirst bar as you like\n");

            writer.write(stringWriter.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
