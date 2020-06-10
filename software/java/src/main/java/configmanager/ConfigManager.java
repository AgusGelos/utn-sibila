package configmanager;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

public class ConfigManager {
    String odb_host;
    String spcy_host;

    public ConfigManager (String[] command_args) {
        odb_host = System.getenv("SIBILA_ORIENT_HOST");
        spcy_host = System.getenv("SIBILA_SPACY_HOST");
        boolean existeOdbHost = !StringUtils.isEmpty(odb_host);
        boolean existeSpcyHost = !StringUtils.isEmpty(spcy_host);
        if (!existeOdbHost || !existeSpcyHost) {
            System.err.println(
                    "No se encontró la variable de entorno SIBILA_ORIENT_HOST o SIBILA_SPACY_HOST.\n"+
                    "Intentando con los parámetros de línea de comandos...\n"
                );
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
            // create the parser
            CommandLineParser parser = new DefaultParser();
            try {
                // parse the command line arguments
                CommandLine line = parser.parse(options, command_args);// has the buildfile argument been passed?
                if (line.hasOption("hodb")) {
                    // initialise the member variable
                    odb_host = line.getOptionValue("hodb");
                    System.out.format("Host OrientDB (Param): %s\n",odb_host);
                } else {
                    odb_host = null;
                }
                if (line.hasOption("hspcy")) {
                    spcy_host = line.getOptionValue("hspcy");
                    System.out.format("Host Spacy (Param): %s\n",spcy_host);
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
            System.out.format("Host OrientDB (Env): %s\n",odb_host);
            System.out.format("Host Spacy (Env): %s\n",spcy_host);
        }
    }

    public boolean validConfig() {
        boolean existeOdbHost = !StringUtils.isEmpty(odb_host);
        boolean existeSpcyHost = !StringUtils.isEmpty(spcy_host);
        return existeOdbHost && existeSpcyHost;
    }
    
    public String getODBHost () {
        return odb_host;
    }

    public String getSpacyHost() {
        return spcy_host;
    }
}