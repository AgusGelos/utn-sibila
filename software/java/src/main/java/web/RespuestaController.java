package web;

import api.Respuesta;
import api.Termino;
import conceptmanager.ConceptManager;

import controlador.Controller;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ortografia.ManejoLang;
import ortografia.GrammarManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import web.RespuestaParams;
import web.RespuestaSimpleParams;

@RestController
public class RespuestaController {



    @RequestMapping(path="/respuesta/evaluar", method=RequestMethod.POST)
    public ResponseEntity evaluarRespuesta(
        //@RequestParam("respuestaAlumno") String respuestaAlumno, 
        //@RequestParam("respuestaBase") String respuestaBase,
        @RequestBody RespuestaParams params){

        String respuestaAlumno = params.respuestaAlumno;
        String respuestaBase = params.respuestaBase;

        RespuestaHttp respuesta = new RespuestaHttp();

        try {
            Controller controller = new Controller();
            HttpStatus status = HttpStatus.OK;
            //Double calificacion = -1.0;
            Double calificacion = controller.calcularCalificaciónRespuesta(respuestaAlumno, respuestaBase);   
            respuesta.agregarDato("calificacion", calificacion);
            respuesta.tipoOk();
        } catch (Exception e){
            respuesta.setMensaje(e.getMessage());
            respuesta.tipoError();
        }

        return respuesta.getRespuestaHttp();

    }


    @RequestMapping(path="/version", method=RequestMethod.GET)
    public ResponseEntity getVersion() {
        RespuestaHttp respuesta = new RespuestaHttp();
        respuesta.tipoOk();
        respuesta.setMensaje("Sibila API");
        respuesta.agregarDato("version", "0.7");

        return respuesta.getRespuestaHttp();
    }

        @RequestMapping(path="/respuesta/corregir", method=RequestMethod.POST)
    public ResponseEntity corregirRespuesta(
        @RequestBody RespuestaSimpleParams params){

        String respuestatxt = params.respuesta;

        RespuestaHttp respuestaHttp = new RespuestaHttp();

        String url = String.format("remote:%s/PPR",web.Config.HOST_ORIENTDB);
        System.out.println("Conectando a : "+url);
        //ConceptManager cm = new ConceptManager("remote:localhost/PPR", "admin", "admin");
        ConceptManager cm = new ConceptManager(url, "admin", "admin");
        GrammarManager grammarManager = new GrammarManager(cm);
        ManejoLang ml = new ManejoLang();
        HttpStatus status = HttpStatus.OK;

        HashMap<String, Object> respuestaMap = new HashMap<>();

        Respuesta respuesta = new Respuesta(respuestatxt);

        try{
            respuesta = grammarManager.corregirRespuesta(respuesta);
            respuesta = grammarManager.clasificarTerminos(respuesta);
            respuesta = cm.evaluarRespuesta(respuesta);

            ArrayList<Termino> terminos = respuesta.getTerminos();
            ArrayList<HashMap<String, Object>> terminosMap = new ArrayList<HashMap<String, Object>>();

            for (Termino termino : terminos) {

                HashMap<String, Object> terminoMap = new HashMap<String, Object>();

                HashMap<String, Object> errorMap = new HashMap<String, Object>();

                String tipo = termino.getTipo();
                terminoMap.put("tipo", tipo);


                // Si el término está en la base de datos no se corrige ortograficamente
                if (tipo != ""){
                    termino.setSugerenciasCorreccion(new ArrayList<>());
                }


                errorMap.put("tiene", termino.hasErrors());
                errorMap.put("sugerencias", termino.getSugerenciasCorreccion());

                terminoMap.put("nombre",termino.getNombre());
                terminoMap.put("error", errorMap);



                String sugerenciaTipo = "";
                if (tipo == "R"){
                    sugerenciaTipo = "Relacion";
                }
                else if (tipo == "C"){
                    sugerenciaTipo = "Concepto";
                }

                if (sugerenciaTipo == ""){
                    sugerenciaTipo = termino.getSugerenciaTipo();
                }

                terminoMap.put("sugerenciaTipo", sugerenciaTipo);

                terminoMap.put("raiz", termino.getRaiz());


                terminosMap.add(terminoMap);
            }

            respuestaHttp.agregarDato("terminos", terminosMap);

            if (respuesta.hasErrors()){
                respuestaHttp.tipoError();
                respuestaHttp.setMensaje("La respuesta tiene errores ortograficos");
            }
            else{
                respuestaHttp.tipoOk();
            }
        }
        catch(IOException e){
            respuestaHttp.tipoError();
            respuestaHttp.setMensaje(e.getMessage());
        }


        return respuestaHttp.getRespuestaHttp();

    }


}
