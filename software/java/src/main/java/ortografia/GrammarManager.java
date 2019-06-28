/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ortografia;

import api.Respuesta;
import api.Termino;
import conceptmanager.ConceptManager;
import org.json.JSONArray;
import org.json.JSONObject;
import web.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// import scala.util.parsing.json.JSONObject;

public class GrammarManager {
    
    private ConceptManager cm;
    
    public GrammarManager(ConceptManager cm){
        this.cm = cm;
    }
    
    /**
     * Valida el texto de la respuesta pasada como parámetro.
     * 
     * Separa términos
     * Analiza ortográficamente la respuesta
     * Carga los errores encontrados en la respuesta

     * 
     * @param respuesta
     * @return respuesta con los términos cargados  con los errores
     * cargados (si se encuentran)
     * @throws IOException 
     */
    public Respuesta corregirRespuesta(Respuesta respuesta) throws IOException {

        // Reemplazamos esto por Hunspell en Python
        // LangTools lt = new LangTools();
        // respuesta = lt.CheckRespuesta(respuesta, cm.getConceptosComplejos(), cm.getRelacionesComplejas());
        try {
            String recurso = "correccion";
            URL url = new URL(Config.WEB_SERVER_PYTHON + recurso);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");

            String datajson =  "{\"txt\": \""+respuesta.getTexto()+"\"}";


            OutputStream os = con.getOutputStream();
            os.write(datajson.getBytes("UTF-8"));
            os.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);

            System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            org.json.JSONObject jsonObject = null;

            jsonObject = new org.json.JSONObject(response.toString());


            //print result
            System.out.println(jsonObject.toString());

            JSONArray terminosJson = jsonObject.getJSONArray("datos");
            ArrayList<Termino> terminos = new ArrayList<Termino>();
            for(int i = 0 ; i < terminosJson.length() ; i++){
                Termino term = new Termino(terminosJson.getJSONObject(i).getString("original"));
                JSONArray sugerenciasJson = terminosJson.getJSONObject(i).getJSONArray("sugerencias");
                //String[] sugerenciasJson = sugerenciasCadena.split(",", -1);
                // String tieneError = terminosJson.getJSONObject(i).getString("error");
                List<String> sugerenciasList = new ArrayList<String>();

                if (sugerenciasJson.toString() != "\"\""){
                    for (int j= 0; j < sugerenciasJson.length(); j++){
                        sugerenciasList.add(sugerenciasJson.getString(j).replace("\"", "").replace("[", "").replace("]", "").replace("{", "").replace("}", "")); }
                }

                term.setSugerenciasCorreccion(sugerenciasList);
                terminos.add(term);
            }


            respuesta.setTerminos(terminos);
        }
        catch(Exception e){
            System.out.print(e);
        }
        
        return respuesta;
    }



    public Respuesta clasificarTerminos(Respuesta respuesta) throws IOException {


        try {
            String recurso = "tokenizacion";
            URL url = new URL(Config.WEB_SERVER_PYTHON + recurso);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");

            String datajson =  "{\"txt\": \""+respuesta.getTexto()+"\"}";


            OutputStream os = con.getOutputStream();
            os.write(datajson.getBytes("UTF-8"));
            os.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);

            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            org.json.JSONObject jsonObject = null;

            jsonObject = new org.json.JSONObject(response.toString());

            //print result
            System.out.println(jsonObject.toString());

            org.json.JSONObject datos = jsonObject.getJSONObject("datos");
            JSONArray tokens = datos.getJSONArray("tokens");

            ArrayList<Termino> terminos = respuesta.getTerminos();
            for(int i = 0 ; i < tokens.length() ; i++){

                terminos.get(i).setRaiz(tokens.getJSONObject(i).getString("raiz"));
                String tipo = tokens.getJSONObject(i).getString("type");

                if (tipo.equalsIgnoreCase("VERB") || tipo.equalsIgnoreCase("CONJ") || tipo.equalsIgnoreCase("AUX")){
                    terminos.get(i).setSugerenciaTipo("Relación");
                }
                else if (tipo.equalsIgnoreCase("NOUN")){
                    terminos.get(i).setSugerenciaTipo("Concepto");
                }
                else {
                    terminos.get(i).setSugerenciaTipo("Ignorar");
                }

            }


            respuesta.setTerminos(terminos);
        }
        catch(Exception e){
            System.out.print(e);
        }

        return respuesta;
    }

    
}
