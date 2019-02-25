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


    @RequestMapping(path="/concepto/{nombre}", method=RequestMethod.PUT)
    public ResponseEntity<String> updateConcepto(@PathVariable("nombre") String nombre, @RequestParam("nuevoNombre") String nuevoNombre){
        ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");

        Concepto concepto = cm.getConceptoByName(nombre);
        String mensaje = "";
        HttpStatus status;

        if (concepto != null){

            cm.updNombreConcepto(concepto, nuevoNombre);
            status = HttpStatus.OK;
        }
        else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            mensaje = "El concepto que se quiere actualizar no existe";
        }


        return new ResponseEntity<String>(mensaje, status);

    }


    @RequestMapping(path="/concepto/{nombre}", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteConcepto(@PathVariable("nombre") String nombre){
        ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");

        Concepto concepto = cm.getConceptoByName(nombre);
        String mensaje = "";
        HttpStatus status;

        if (concepto != null){

            Boolean borrado = cm.delConceptoSafe(concepto);

            if (borrado){
                status = HttpStatus.OK;
                mensaje = "Se ha borrado el concepto con éxito";
            }
            else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                mensaje = "Existen relaciones asociadas al concepto. No es posible borrarlo";
            }

        }
        else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            mensaje = "El concepto que se quiere actualizar no existe";
        }


        return new ResponseEntity<String>(mensaje, status);

    }


    @RequestMapping(path="/concepto/{nombreConcepto}/equivalencias", method=RequestMethod.GET)
    public ResponseEntity<HashMap> getEquivalenciasByConcepto(@PathVariable("nombreConcepto") String nombre){
        ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");

        Concepto concepto = cm.getConceptoByName(nombre);
        HashMap<String, Object> equivalencias = new HashMap();

        String mensaje = "";
        HttpStatus status;

        if (concepto != null){
            equivalencias.put("concepto", concepto.getNombre());
            equivalencias.put("equivalencias", cm.getEquivalencias(concepto.getNombre()));
            status = HttpStatus.OK;
        }
        else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;

        }


        return new ResponseEntity<HashMap>(equivalencias, status);

    }



    @RequestMapping(path="/concepto/{nombreConcepto}/equivalencias/{nombreEquivalencia}", method=RequestMethod.GET)
    public ResponseEntity<HashMap> getEquivalenciasByName(@PathVariable("nombreConcepto") String nombreConcepto, @PathVariable("nombreEquivalencia") String nombreEquivalencia){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(path="/equivalencia/{nombre}", method=RequestMethod.GET)
    public ResponseEntity<HashMap> getConceptoByEquivalencia(@PathVariable("nombre") String nombre){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @RequestMapping(path="/conceptos/{nombreConcepto}/relaciones/{tipoRelacion}", method=RequestMethod.GET)
    public ResponseEntity<HashMap> countReferences(@PathVariable("nombreConcepto") String nombreConcepto, @PathVariable("tipoRelacion") String tipoRelacion){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
