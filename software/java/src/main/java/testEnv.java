import configmanager.ConfigManager;
/*
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
*/
public class testEnv {
    public static void main(String[] args) {
        System.out.println("Iniciando servidor Sibila...");

        ConfigManager cf = new ConfigManager(args);
        System.out.println (cf.getODBHost());
        System.out.println  (cf.getSpacyHost());
        return;
        /*
        Map<String, String> environmentMap = System.getenv();
        SortedMap<String, String> sortedEnvMap = new TreeMap<String, String>(environmentMap);
        Set<String> keySet = sortedEnvMap.keySet();
        for (String key : keySet) {
            String value = environmentMap.get(key);
            System.out.println("[" + key + "] " + value);
        }
        */
        /*
        Options options = new Options();
        Option host_orientdb   = Option.builder("hodb")
                                    .longOpt("host-orientdb")
                                    .desc("Nombre o IP del host OrientDB")
                                    .hasArg(true)
                                    .build();
        Option host_spacy   = Option.builder("hspcy")
                                    .longOpt("host-spacy")
                                    .desc("Nombre o IP del host Spacy")
                                    .hasArg(true)
                                    .build();

        options.addOption(host_orientdb);
        options.addOption(host_spacy);

        String odb_host = System.getenv("SIBILA_ORIENT_HOST");
        String spcy_host = System.getenv("SIBILA_SPACY_HOST");

        boolean existeOdbHost = !StringUtils.isEmpty(odb_host);
        boolean existeSpcyHost = !StringUtils.isEmpty(spcy_host);

        if (!existeOdbHost || !existeSpcyHost) {
            System.out.println(
                    "No se encontró la variable de entorno SIBILA_ORIENT_HOST o SIBILA_SPACY_HOST.\nIntentando con los parámetros de línea de comandos...");

            // create the parser
            CommandLineParser parser = new DefaultParser();
            try {
                // parse the command line arguments
                CommandLine line = parser.parse(options, args);// has the buildfile argument been passed?
                if (line.hasOption("hodb")) {
                    // initialise the member variable
                    odb_host = line.getOptionValue("hodb");
                    System.out.format("Host OrientDB: %s\n",odb_host);
                } else {
                    odb_host = null;
                }
                if (line.hasOption("hspcy")) {
                    spcy_host = line.getOptionValue("hspcy");
                    System.out.format("Host Spacy: %s\n",spcy_host);
                } else {
                    spcy_host = null;
                }
                
                if (odb_host == null || spcy_host == null) {
                    System.out.println("No se suministró un parámetro válido:");
                    HelpFormatter formatter = new HelpFormatter();
                    formatter.printHelp("sibila-server", options);
                }
            } catch (ParseException exp) {
                // oops, something went wrong
                System.err.println("Parsing failed.  Reason: " + exp.getMessage());
            }
        } else {
            System.out.format(
                "Utilizando las variables de entorno:\n"+
                "SIBILA_ORIENT_HOST : "+odb_host+"\n"+
                "SIBILA_SPACY_HOST : "+spcy_host+"\n"
                );
        }
        */
    }
}