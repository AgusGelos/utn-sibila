package web;

import api.Respuesta;
import conceptmanager.ConceptManager;

import controlador.Controller;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ortografia.OrtographyManager;

import java.util.HashMap;


@RestController
public class RespuestaController {



    @RequestMapping(path="/respuesta/evaluar", method=RequestMethod.POST)
    public ResponseEntity evaluarRespuesta(@RequestParam("respuestaAlumno") String respuestaAlumno, @RequestParam("respuestaBase") String respuestaBase){

        Controller controller = new Controller();
        HttpStatus status = HttpStatus.OK;
        Double calificacion = -1.0;
        try {
             calificacion = controller.calcularCalificaciónRespuesta(respuestaAlumno, respuestaBase);
        } catch (Exception e){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Double>(calificacion, status);

    }



}
