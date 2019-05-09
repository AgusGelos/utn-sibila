/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ortografia;

import api.Termino;
import conceptmanager.ConceptManager;
import conceptmanager.ConceptoComplejo;
import conceptmanager.RelacionCompleja;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.language.Spanish;
import org.languagetool.rules.Rule;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.spelling.SpellingCheckRule;

/**
 *
 * @author user
 */
public class ManejoLang {

    Boolean tErrores;
    private JLanguageTool langT;

    /**
     * Retorna un objeto que representa una capa por encima de la herramienta
     * LanguageTool, agregando nuevas funcionalidades o facilitando la
     * interacción con la misma
     */
    public ManejoLang() {
        tErrores = true;
        langT = new JLanguageTool(new Spanish());
    }

    /**
     * Retorna un ArrayList de String con las palabras encontradas dentro del
     * texto previamente pasando por un filtro de palabras validas.
     *
     * @param text
     * @return words
     */
    public ArrayList<String> getWords(String text) {
        ArrayList<String> res = new ArrayList<String>();

        for (AnalyzedToken reading : this.getReadings(text)) {
            if (esPalabraValida(reading.getToken())) {
                res.add(reading.getToken());
            }
        }

        return res;
    }

    /**
     * Retorna un ArrayList de Termino con los terminos encontrados dentro del
     * texto previamente pasando por un filtro de palabras validas.
     *
     * @param text
     * @return termino
     */
    public ArrayList<Termino> getWordsTerminos(String text) {
        ArrayList<Termino> res = new ArrayList<Termino>();
        ArrayList<String> tokens = new ArrayList<>();
        for (AnalyzedToken reading : this.getReadings(text)) {
            if (esPalabraValida(reading.getToken())) {
//                Pattern word = Pattern.compile(reading.getToken());
//                Matcher match = word.matcher(text);
//                System.out.println(match.toString());
//                while (match.find()) {
                Termino term = new Termino(reading.getToken(), reading.getToken());
                term.setMensajeDeError("");
                term.setSugerenciasCorreccion(tokens);
//                    term.setPosicionInicioEnTexto(match.start());
//                    term.setPosicionFinEnTexto(match.end());

                res.add(term);
//                }

            }
        }

        return res;
    }

    private List<AnalyzedToken> getReadings(String text) {
        List<AnalyzedToken> readings = new ArrayList<AnalyzedToken>();

        try {
            List<AnalyzedSentence> ana = langT.analyzeText(text);
            for (AnalyzedSentence ana1 : ana) {
                // ana1.getTokens() nos devuelve los tokens de todas las palabras
                AnalyzedTokenReadings[] tokens = ana1.getTokens();

                // Recorremos los tokens de cada palabra
                for (AnalyzedTokenReadings token : tokens) {

                    AnalyzedToken tokenPriorizado = token.getAnalyzedToken(0);

                    // Comienzo indice 1
                    // Si no existe más de un token, entonces no hay nada que priorizar
                    for (int i = 1; i < token.getReadingsLength();i++){
                        AnalyzedToken tokenAnalizado = token.getAnalyzedToken(i);
                        if (tokenAnalizado.getPOSTag().charAt(0) == 'V'){
                            tokenPriorizado = tokenAnalizado;
                        }
                        //System.out.println(tokenAnalizado.getToken());
                    }

                    readings.add(tokenPriorizado);





                }
            }
        }
        catch (IOException ex) {
            Logger.getLogger(ManejoLang.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readings;

    }

    private boolean esPalabraValida(String palabra) {

        if (palabra.matches("([a-zA-ZñáéíóúÑÁÉÍÓÚ0-9]+)")) {
            return true;

        }
        return false;

    }

    /**
     * Retorna True si la palabra es sustantivo y False en el caso de que la
     * palabra no sea sustantivo;
     *
     * @param palabra
     * @return
     */
    public boolean esSustantivo(String palabra) {

        for (String tag : this.getCodigoClasificacion(palabra)) {
            if (tag.charAt(0) == 'N' || tag.charAt(0) == 'n') {
                return true;
            }

        }
        return false;
    }

    public String palabrasParaItem(String sin) throws IOException, BadLocationException {

        //langT.check(sin);
        List<RuleMatch> matches = null;
        StringBuilder acum = new StringBuilder("");
        try {

            matches = langT.check(sin);
            for (RuleMatch match : matches) {

                acum.append("").append(match.getSuggestedReplacements()).append("\n ");

                tErrores = true;
                acum.toString();
            }
            if (matches.isEmpty()) {
                tErrores = false;
            }

        }
        catch (IOException ex) {
            System.out.println(ex);
        }

        return acum.toString();

    }

    private ArrayList<String> getCodigoClasificacion(String texto) {

        ArrayList<String> tags = new ArrayList<String>();
        for (AnalyzedToken reading : this.getReadings(texto)) {
            tags.add(reading.getPOSTag());

        }

        return tags;
    }

    public void corregirArea(JTextArea sin, JTextArea cor) throws IOException, BadLocationException {
        cor.setText("");
        sin.setOpaque(false);
        int p0, p1;
        //sin.setHighlighter(null);
        Highlighter highlighter = sin.getHighlighter();

        Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
        //Highlighter.HighlightPainter painter2 = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
        //HighlightPainter painter3 = new DefaultHighlighter.DefaultHighlightPainter(Color.WHITE);
        //highlighter.addHighlight(0, sin.getText().length(), painter3);
        highlighter.removeAllHighlights();
        langT = new JLanguageTool(new Spanish());
//        langT.activateDefaultPatternRules();
        List<RuleMatch> matches = null;
        StringBuilder acum = new StringBuilder("");
        try {
            matches = langT.check(sin.getText());
            for (RuleMatch match : matches) {
                acum.append("Error en la linea " + match.getLine() + ", Columna " + match.getColumn() + ": ");
                acum.append(" Contexto(");
                int aux = match.getToPos() - match.getFromPos();
                acum.append(sin.getText(match.getFromPos(), aux) + ")");
                acum.append(match.getMessage());
                acum.append(" Corrección Sugerida: " + match.getSuggestedReplacements() + "\n ");
                p0 = match.getFromPos();
                p1 = match.getToPos();

                System.out.println(match.getShortMessage());

                if (match.getShortMessage() != null) {
                    sin.setOpaque(false);
                    highlighter.addHighlight(p0, p1, painter);
                    sin.append(" ");
                    //highlighter.changeHighlight(painter, p0, p1);

                }
                else {
                    sin.setOpaque(true);
                }
//                tErrores = true;
//                cor.setText(acum.toString());
            }
//            if (matches == null) {
////                tErrores = false;
////                cor.setText("No hay errores");
//                sin.setOpaque(true);
//                System.out.println("entre al if empty");
//
//            }

        }
        catch (IOException ex) {
            System.out.println(ex);
        }

    }

    /**
     * Retorna una lista con las clasficaciones gramáticas posibles para una
     * palabra
     *
     * @param palabra Palabra que se debe analizar
     * @return lista de clasificacioens
     */
    public ArrayList<String> getClasificacionesPalabra(String palabra) {
        /*
         1. ADJETIVOS
         Pos. 	Atributo 	Valor 	Código
         1 	Categoría 	Adjetivo 	A
         2 	Tipo 	Calificativo 		Q
         Ordinal 		O
         -			0
         3 	Grado 	- 			0
         Apreciativo 			A
         4 	Género 	Masculino 		M
         Femenino 			F
         Común 				C
         5 	Número 	Singular 		S
         Plural 				P
         Invariable 			N
         6 	Función 	- 		0
         Participio 			P

         Ejemplo: Verde, AQ0CS0

         2. ADVERBIOS
         Pos. 	Atributo 	Valor 	Código
         1 	Categoría 	Adverbio 	R
         2 	Tipo 	General 		G
         Negativo 		N
		
         Ejemplo: Todavía, RG

         3. DETERMINANTES
         Pos. 	Atributo 	Valor 	Código
         1 	Categoría 	Determinante 	D
         2 	Tipo 	Demostrativo 		D
         Posesivo 		P
         Interrogativo 		T
         Exclamativo 		E
         Indefinido 		I
         Artículo 		A
         Numeral 		N
         3 	Persona 	Primera 	1
         Segunda 	2
         Tercera 	3
         4 	Género 	Masculino 		M
         Femenino 		F
         Común 			C
         Neutro 			N
         5 	Número 	Singular 		S
         Plural 			P
         Invariable 		N
         6 	Poseedor 	Singular 	S
         Plural 		P
			
         Ejemplo: Estas, DD3FP0

         4. NOMBRES
         Pos. 	Atributo 	Valor 	Código
         1 	Categoría 	Nombre 	N
         2 	Tipo 	Común 		C
         Propio 		P
         3 	Género 	Masculino 	M
         Femenino 	F
         Común		C
         4 	Número 	Singular 	S
         Plural		P
         Invariable 	N
         5-6 	Clasificación 
         semántica 	- 	0
         7 	Grado 	Apreciativo 	A

         Ejemplo: Sillas, NCFP00

         5. VERBOS
         Pos. 	Atributo 	Valor 	Código
         1 	Categoría 	Verbo 	V
         2 	Tipo 	Principal 	M
         Auxiliar 	A
         Semiauxiliar 	S
         3 	Modo 	Indicativo 	I
         Subjuntivo 	S
         Imperativo 	M
         Infinitivo 	N
         Gerundio 	G
         Participio 	P
         4 	Tiempo 	Presente 	P
         Imperfecto 	I
         Futuro		F
         Pasado		S
         Condicional	C
         -		0
         5 	Persona 
         Primera 	1
         Segunda 	2
         Tercera 	3
         6 	Número 	Singular 	S
         Plural		P
         7 	Género 	Masculino 	M
         Femenino 	F

         Ejemplo: Escribisteis, VMIS2P0

         6. PRONOMBRES
         Pos. 	Atributo 	Valor 	Código
         1 	Categoría 	Pronombre 	P
         2 	Tipo 		Personal 	P
         Demostrativo 	D
         Posesivo 	X
         Indefinido 	I
         Interrogativo 	T
         Relativo 	R
         Numeral 	N
         Exclamativo 	E
         3 	Persona 	Primera 	1
         Segunda 	2
         Tercera 	3
         4 	Género 		Masculino 	M
         Femenino 	F
         Común 	C
         Neutro 	N
         5 	Número	 	Singular 	S
         Plural	 	P
         Invariable 	N
         6 	Caso 		Nominativo 	N
         Acusativo 	A
         Dativo	 	D
         Oblicuo 	O
         7 	Poseedor 	Singular 	S
         Plural	 	P
         8 	Politeness 	Polite	 	P

         Ejemplo: Ella, PP3FS000

         7. CONJUNCIONES
         Pos. 	Atributo 	Valor 	Código
         1 	Categoría 	Conjunción 	C
         2 	Tipo 		Coordinada 	C
         Subordinada 	S
			
         Ejemplo: Pero CC

         8. INTERJECCIONES
         Pos. 	Atributo 	Valor 	Código
         1 	Categoría 	Interjección 	I

         Ejemplo: Ay, I

         9. PREPOSICIONES
         Pos. 	Atributo 	Valor 	Código
         1 	Categoría 	Adposición 	S
         2 	Tipo 		Preposición 	P
         3 	Forma 		Simple	 	S
         Contraída 	C
         3 	Género 		Masculino 	M
         4 	Número 		Singular 	S

         Ejemplo: Contra; PS00

         10. SIGNOS DE PUNTUACIÓN
         Sin uso actualmente.

         11. NUMERALES
         Pos. 	Atributo 	Valor 	Código
         1		Categoría	Cifra	Z

         12. FECHAS Y HORAS
         Sin uso actualmente.*/
        ArrayList<String> tipos = new ArrayList<String>();
        List<String> codigos = this.getCodigoClasificacion(palabra);
        for (String codigo : codigos) {
            int letras = codigo.length();
            StringBuilder st = new StringBuilder();
            st.append("Categoría: ");
            switch (codigo.charAt(0)) {

                case 'A':
                    st.append("Adjetivo-");
                    st.append("Tipo: ");

                    switch (codigo.charAt(1)) {

                        case 'Q':
                            st.append("Calificativo-");
                            break;
                        case 'O':
                            st.append("Ordinal-");
                            break;
                        case '0':
                            st.append(" -");
                            break;
                    }
                    st.append("Grado: ");
                    switch (codigo.charAt(2)) {
                        case 'A':
                            st.append("Apreciativo-");
                            break;
                        case '0':
                            st.append(" -");

                            break;
                    }
                    st.append(getGenero(codigo.charAt(3)));
                    st.append(getNumero(codigo.charAt(4)));
                    st.append("Funcion: ");
                    switch (codigo.charAt(5)) {
                        case '0':
                            st.append(" ");
                            break;
                        case 'P':
                            st.append("Participio");
                            break;
                    }
                    break;
                case 'R':
                    st.append("Adverbio-");
                    st.append("Tipo: ");
                    switch (codigo.charAt(1)) {
                        case 'G':
                            st.append("General");
                            break;
                        case 'N':
                            st.append("Negativo");
                            break;

                    }
                case 'D':
                    st.append("Determinante");
                    st.append("Tipo: ");

                    switch (codigo.charAt(1)) {
                        case 'D':
                            st.append("Demostrativo-");
                            break;
                        case 'P':
                            st.append("Posesivo-");
                            break;
                        case 'T':
                            st.append("Interrogativo-");
                            break;
                        case 'E':
                            st.append("Exclamativo-");
                            break;
                        case 'I':
                            st.append("Indefinido-");
                            break;
                        case 'A':
                            st.append("Artículo-");
                            break;
                        case 'N':
                            st.append("Numeral-");
                            break;

                    }
                    st.append("Persona: ");
                    switch (codigo.charAt(2)) {
                        case '1':
                            st.append("Primera-");
                            break;
                        case '2':
                            st.append("Segunda-");
                            break;
                        case '3':
                            st.append("Tercera-");
                            break;

                    }
                    st.append(this.getGenero(codigo.charAt(3)));
                    st.append(getNumero(codigo.charAt(4)));
                    st.append("Poseedor: ");
                    switch (codigo.charAt(5)) {
                        case 'S':
                            st.append("Singular");
                            break;
                        case 'P':
                            st.append("Plural");
                            break;

                    }
                case 'N':
                    st.append("Nombre-");
                    st.append("Tipo: ");
                    switch (codigo.charAt(1)) {
                        case 'C':
                            st.append("Común-");
                            break;
                        case 'P':
                            st.append("Propio-");
                            break;

                    }
                    st.append(getGenero(codigo.charAt(2)));
                    st.append(getNumero(codigo.charAt(3)));
                    st.append("Grado: ");
                    switch (codigo.charAt(6)) {
                        case 'A':
                            st.append("Apreciativo");
                            break;

                    }

            }
            tipos.add(st.toString());
        }
        return tipos;
    }

    private String getNumero(char g) {
        StringBuilder st = new StringBuilder();

        st.append("Número: ");
        switch (g) {
            case 'S':
                st.append("Singular-");
                break;
            case 'P':
                st.append("Plural-");
                break;
            case 'I':
                st.append("Invariable-");
                break;
        }
        return st.toString();
    }

    private String getGenero(char g) {
        StringBuilder st = new StringBuilder();
        st.append("Género: ");
        switch (g) {
            case 'M':
                st.append("Masculino-");
                break;
            case 'F':
                st.append("Femenino-");
                break;
            case 'C':
                st.append("Comun-");
                break;
            case 'N':
                st.append("Neutro-");
        }
        return st.toString();
    }

    public ArrayList<String> getLemma(String texto) {
        ArrayList<String> lemmas = new ArrayList<String>();
        for (AnalyzedToken lemma : this.getReadings(texto)) {
            lemmas.add(lemma.getLemma());
        }
        return lemmas;
    }

    /**
     * Retorna un ArrayList de terminos, asignando a cada uno los errores
     * encontrados en estos
     *
     * @param text
     * @param terminos
     * @throws IOException
     * @return
     */
    public ArrayList<Termino> getListaDeTerminos(String text) throws IOException {
        ArrayList<Termino> term = getWordsTerminos(text);
        langT = new JLanguageTool(new Spanish());

        //TODO: OJO CON ESTO. NO ESTA BIEN DISEÑADO Y NO ES CLARO.
        ignorarPalabras();

//        langT.activateDefaultPatternRules();
        List<RuleMatch> matches = null;
        StringBuilder acum = new StringBuilder("");

        try {
            matches = langT.check(text);
            for (RuleMatch match : matches) {
                acum.append("Error en la linea ").append(match.getLine()).append(", Columna ").append(match.getColumn()).append(": ");
                acum.append(" Contexto(");
                String textoConError = text.substring(match.getFromPos(), match.getToPos());
                acum.append(textoConError).append(")");
                acum.append(match.getMessage());
                acum.append(" Corrección Sugerida: ").append(match.getSuggestedReplacements()).append("\n ");
                int p0 = match.getFromPos();
                int p1 = match.getToPos();
                for (Termino termi : term) {
                    if (termi.getNombre().equalsIgnoreCase(textoConError)) {
                        termi.setMensajeDeError(match.getMessage());
                        termi.setSugerenciasCorreccion(match.getSuggestedReplacements());

                        termi.setPosicionInicioEnTexto(p0);
                        termi.setPosicionFinEnTexto(p1);
//                        System.out.println("se encontro palabra " + term.getNombre() + " en " + p0 + " - " + p1);

                    }
                }
            }

        }
        catch (IOException ex) {
            System.out.println(ex);
        }

        return term;
    }

    /**
     * Retorna un ArrayList de terminos, asignando a cada uno los errores
     * encontrados en estos y si no encuentra arma los terminos complejos
     *
     * @param text
     * @param terminos
     * @throws IOException
     * @return
     */
    public ArrayList<Termino> getListaDeTerminosConComplejos(String text, ArrayList<ConceptoComplejo> conceptos, ArrayList<RelacionCompleja> relaciones) throws IOException {
        ArrayList<Termino> term = getWordsTerminos(text);
        langT = new JLanguageTool(new Spanish());

        //TODO: OJO CON ESTO. NO ESTA BIEN DISEÑADO Y NO ES CLARO.
        ignorarPalabras();

//        langT.activateDefaultPatternRules();
        List<RuleMatch> matches = null;
        StringBuilder acum = new StringBuilder("");

        try {
            matches = langT.check(text);
            for (RuleMatch match : matches) {
                acum.append("Error en la linea ").append(match.getLine()).append(", Columna ").append(match.getColumn()).append(": ");
                acum.append(" Contexto(");
                String textoConError = text.substring(match.getFromPos(), match.getToPos());
                acum.append(textoConError).append(")");
                acum.append(match.getMessage());
                acum.append(" Corrección Sugerida: ").append(match.getSuggestedReplacements()).append("\n ");
                int p0 = match.getFromPos();
                int p1 = match.getToPos();
                for (Termino termi : term) {
                    if (termi.getNombre().equalsIgnoreCase(textoConError)) {
                        termi.setMensajeDeError(match.getMessage());
                        termi.setSugerenciasCorreccion(match.getSuggestedReplacements());

                        termi.setPosicionInicioEnTexto(p0);
                        termi.setPosicionFinEnTexto(p1);
//                        System.out.println("se encontro palabra " + term.getNombre() + " en " + p0 + " - " + p1);

                    }
                }
            }
            if (matches.size() < 1) {
                term = this.getTerminosConComplejos(text, conceptos, relaciones);
            }

        }
        catch (IOException ex) {
            System.out.println(ex);
        }

        return term;
    }

    /**
     * ignora las palabras que nosotros le ponemos, seria una forma rapida de
     * "agregar al diccionario", ya que si la escriben mal, se la toma como
     * error
     */
    public void ignorarPalabras() {
        for (Rule rule : langT.getAllActiveRules()) {
            if (rule instanceof SpellingCheckRule) {
                List<String> wordsToIgnore = Arrays.asList("POO", "poo", "tupla", "caracteristica", "implementación", "ES UN");
                ((SpellingCheckRule) rule).addIgnoreTokens(wordsToIgnore);
            }
        }
    }

    /**
     * Recibe un ArrayList de Termino con los terminos encontrados y analiza el
     * nombre de cada termino obteniendo el codigo y de cada codigo el lemma
     * correspondiente. Retorna un ArrayList de Termino con los nombres de cada
     * termino de forma singular
     *
     * @param terminos
     * @return termino
     */
    public ArrayList<Termino> cambiarPluralPorSingular(ArrayList<Termino> terminos) {
        int indice = 0;
        for (Termino term : terminos) {
            if (term.getNombre().split("\\s+").length <= 1) {
                //obtengo los codigos de cada termino
                ArrayList<String> a = getCodigoClasificacion(term.getNombre());
                //obtengo el lemma corrrespondiente de cada termino
                ArrayList<String> b = getLemma(term.getNombre());
                //verifico a que codigo pertenece y aplico el cambio si corresponde o no
                for (String codigos : a) {

                    if (codigos != null) {

                        switch (codigos.charAt(0)) {
                            // case 'V': 
                            case 'A': // Adjetivos
                            case 'N': // Nombres
                                //terminos.get(indice).setNombre(b.get(1).toUpperCase());
                                term.setNombre(b.get(1).toUpperCase());
                                break;
                            case 'D':
                                break;
                            case 'P':
                                break;
                        }

                    }

                }
                //Comentado para solucionar un problema que hace que el indice
                //sobreescriba partes del vector
                //indice++;
            }
        }
        return terminos;
    }

    
    public String sugerenciaTipoConcepto(Termino termino) {
        String salida = "";
        ArrayList<String> codigo = getCodigoClasificacion(termino.getNombre());

        for (String codigos : codigo) {

            if (codigos != null) {

                switch (codigos.charAt(0)) {

                    
                    case 'N': // Nombres
                    case 'A': // Adjetivos
                        salida = "Concepto";
                        break;
                    case 'V': // Verbos   
                    case 'C': // Conjunciones
                        salida = "Relacion";
                        break;
                    case 'D': // Determinantes
                    case 'S': // Pronombres
                    case 'I': // Interjecciones
                    case 'R': // Adverbios
                    case 'P': // Pronombres
                        salida = "Ignorar";
                        break;

                }

            }

        }
        return salida;
    }

    public ArrayList<Termino> getTerminosConComplejos(String text, ArrayList<ConceptoComplejo> conceptos, ArrayList<RelacionCompleja> relaciones) {
        ArrayList<Termino> res = new ArrayList<Termino>();
        StringBuilder st = new StringBuilder();
        for (ConceptoComplejo cc : conceptos) {
            st.append(cc.getConcepto().getNombre().toUpperCase()).append("|");
        }
        for (RelacionCompleja rel : relaciones) {
            st.append(CommonsFunctions.fromCamelCase(rel.getRelacion().getTipo()).toUpperCase()).append("|");
        }
        Pattern pattern = Pattern.compile(st.toString() + "([a-zA-ZñáéíóúÑÁÉÍÓÚ0-9]+)");
        Matcher matcher = pattern.matcher(text.toUpperCase());
        while (matcher.find()) {
            res.add(new Termino(matcher.group(), matcher.group()));
        }
        return res;
    }

}
