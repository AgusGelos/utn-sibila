/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ortografia;

/**
 *
 * @author ricky
 */
public class CommonsFunctions {

    /**
     * Recibe un string separado por espacios y lo convierte en camel case
     *
     * @param input El string que se desea convertir en camel case
     * @return String en camel case 
     */
    public static String toCamelCase(String input) {
        String result = "";
        char firstChar = input.charAt(0);
        result = result + Character.toUpperCase(firstChar);
        for (int i = 1; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (currentChar != ' ') {
                char previousChar = input.charAt(i - 1);
                if (previousChar == ' ') {
                    result = result + Character.toUpperCase(currentChar);
                } else {
                    result = result + Character.toLowerCase(currentChar);
                }
            }
        }
        return result;
    }
 /**
     * Recibe un string en camel case y lo convierte a un string separado por espacios
     *
     * @param input El string camel case que se desea convertir
     * @return String con espacios 
     */
    public static String fromCamelCase(String input) {
        return input.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }
}


