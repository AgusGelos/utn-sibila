package web;

import api.Respuesta;
import conceptmanager.ConceptManager;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class EstructuraController {



    @RequestMapping(path="/estructura", method=RequestMethod.POST)
    public ResponseEntity addEstructura(@RequestParam("conceptoOrigen") String conceptoOrigen,
                                        @RequestParam("conceptoDestino") String conceptoDestino,
                                        @RequestParam("relacion") String relacion){

        RespuestaHttp respuesta = new RespuestaHttp();



        try {
            ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");
            cm.addStruct(conceptoOrigen, conceptoDestino, relacion);
            respuesta.tipoOk();

        } catch (Exception e){
            respuesta.tipoError();
            respuesta.setMensaje(e.getMessage());
        }

        return respuesta.getRespuestaHttp();

    }



}
