package web;

import conceptmanager.ConceptManager;
import conceptmanager.Relacion;
import org.codehaus.jettison.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;


@RestController
public class RelacionController {


    @RequestMapping(path="/relaciones", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<HashMap> getRelaciones() throws JSONException {
        ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");
        ArrayList<Relacion> relaciones = cm.getRelaciones();

        ArrayList<HashMap> response = new ArrayList<>();

        for (Relacion relacion : relaciones) {

            HashMap<String, Object> map = new HashMap<>();

            map.put("id", relacion.getId());
            map.put("nombre", relacion.getNombre());
            map.put("tipo", relacion.getTipo());


            response.add(map);
        }

        return response;

    }



    @RequestMapping(path="/relacion", method=RequestMethod.POST)
    public ResponseEntity addRelacion(@RequestParam("nombre") String nombre){
        ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");
        Relacion relacion = new Relacion("",nombre);

        HttpStatus status = HttpStatus.OK;

        // Relación origen y destino?
        // Pregunta: Agregamos al modelo de relación el concepto de origen y el concepto de destino?
        //cm.addRelacion();

        return new ResponseEntity(status);

    }



    @RequestMapping(path="/relacion/{nombre}", method=RequestMethod.GET)
    public ResponseEntity<HashMap> getRelacion(@PathVariable("nombre") String nombre){
        ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");

        Relacion relacion = cm.getRelacionByName(nombre);
        HashMap<String, Object> relacionMap = new HashMap<>();

        HttpStatus status = HttpStatus.OK;

        try {
            if (relacion != null) {

                relacionMap.put("id", relacion.getId());
                relacionMap.put("nombre", relacion.getNombre());
                relacionMap.put("tipo", relacion.getTipo());
            } else {
                relacionMap.put("mensaje", "No existe una relacion con el nombre especificado");
                status = HttpStatus.NOT_FOUND;
            }
        }
        catch(Exception e){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            relacionMap.put("mensaje", e.getMessage());
        }

        return new ResponseEntity<HashMap>(relacionMap, status);

    }

}
