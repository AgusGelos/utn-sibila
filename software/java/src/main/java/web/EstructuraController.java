package web;

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

        ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");

        HttpStatus status = HttpStatus.OK;
        try {
            cm.addStruct(conceptoOrigen, conceptoDestino, relacion);
        } catch (Exception e){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity(status);

    }



}
