package web;

import conceptmanager.ConceptManager;
import conceptmanager.Concepto;
import org.codehaus.jettison.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;


@RestController
public class ConceptoController {


    @RequestMapping(path="/conceptos", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<HashMap> getConceptos() throws JSONException {
        ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");
        ArrayList<Concepto> conceptos = cm.getConceptos();

        ArrayList<HashMap> response = new ArrayList<>();

        for (Concepto concepto : conceptos) {
            HashMap<String, Object> map = new HashMap<>();

            map.put("id", concepto.getId());
            map.put("nombre", concepto.getNombre());
            map.put("equivalencias", cm.getEquivalencias(concepto.getNombre()));

            response.add(map);
        }

        return response;

    }



    @RequestMapping(path="/concepto", method=RequestMethod.POST)
    public ResponseEntity addConcepto(@RequestParam("nombre") String nombre){
        ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");
        Concepto concepto = new Concepto("",nombre);

        HttpStatus status = HttpStatus.OK;
        try {
            cm.addConcepto(concepto);
        } catch (Exception e){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity(status);

    }



    @RequestMapping(path="/concepto/{nombre}", method=RequestMethod.GET)
    public ResponseEntity<HashMap> getConcepto(@PathVariable("nombre") String nombre){
        ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");

        Concepto concepto = cm.getConceptoByName(nombre);
        HashMap<String, Object> conceptoMap = new HashMap<>();

        if (concepto != null){

            conceptoMap.put("id", concepto.getId());
            conceptoMap.put("nombre", concepto.getNombre());
            conceptoMap.put("equivalencias", cm.getEquivalencias(concepto.getNombre()));
        }
        else{
            conceptoMap.put("mensaje", "No existe un concepto con el nombre especificado");
        }


        return new ResponseEntity<HashMap>(conceptoMap, HttpStatus.OK);

    }

}
