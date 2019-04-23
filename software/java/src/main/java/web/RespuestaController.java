package web;

import api.Respuesta;
import api.Termino;
import conceptmanager.ConceptManager;

import controlador.Controller;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ortografia.ManejoLang;
import ortografia.OrtographyManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
public class RespuestaController {



    @RequestMapping(path="/respuesta/evaluar", method=RequestMethod.POST)
    public ResponseEntity evaluarRespuesta(@RequestParam("respuestaAlumno") String respuestaAlumno, @RequestParam("respuestaBase") String respuestaBase){

        RespuestaHttp respuesta = new RespuestaHttp();

        try {
            Controller controller = new Controller();
            HttpStatus status = HttpStatus.OK;
            Double calificacion = -1.0;

            respuesta.agregarDato("calificacion", controller.calcularCalificaciónRespuesta(respuestaAlumno, respuestaBase));
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
        respuesta.setMensaje("Prueba de concepto");
        respuesta.agregarDato("version", "1.0.0");

        return respuesta.getRespuestaHttp();
    }

        @RequestMapping(path="/respuesta/corregir", method=RequestMethod.POST)
    public ResponseEntity corregirRespuesta(@RequestParam("respuesta") String respuestaText)  {

        RespuestaHttp respuestaHttp = new RespuestaHttp();

        ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");
        OrtographyManager ortografia = new OrtographyManager(cm);
        ManejoLang ml = new ManejoLang();
        HttpStatus status = HttpStatus.OK;

        HashMap<String, Object> respuestaMap = new HashMap<>();

        Respuesta respuesta = new Respuesta(respuestaText);



        try{
            respuesta = ortografia.corregirRespuesta(respuesta);
            respuesta = cm.evaluarRespuesta(respuesta);

            ArrayList<Termino> terminos = respuesta.getTerminos();
            ArrayList<HashMap<String, Object>> terminosMap = new ArrayList<HashMap<String, Object>>();

            for (Termino termino : terminos) {
                HashMap<String, Object> terminoMap = new HashMap<String, Object>();

                HashMap<String, Object> errorMap = new HashMap<String, Object>();

                //LisugerenciasErrorMap = new HashMap<String>();

                List<String> sugerencias = termino.getSugerenciasCorreccion();

                /*for (String sugerencia : sugerencias) {
                    sugerenciasErrorMap.put(sugerencia);
                } */

                errorMap.put("error", termino.hasErrors());
                errorMap.put("sugerencias", sugerencias);

                terminoMap.put("nombre",termino.getNombre());
                terminoMap.put("error", errorMap);

                String tipo = termino.getTipo();
                terminoMap.put("tipo", tipo);

                String sugerenciaTipo = tipo;
                if (sugerenciaTipo == ""){
                    sugerenciaTipo = ml.sugerenciaTipoConcepto(termino);
                }
                terminoMap.put("sugerenciaTipo", sugerenciaTipo);
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
