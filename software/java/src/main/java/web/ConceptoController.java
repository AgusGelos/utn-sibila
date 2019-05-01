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
    public ResponseEntity getConceptos()  {

        RespuestaHttp respuesta = new RespuestaHttp();

        try {
            ConceptManager cm = new ConceptManager("remote:localhost/PPR", "admin", "admin");
            ArrayList<Concepto> conceptos = cm.getConceptos();
            ArrayList<HashMap> conceptosBody = new ArrayList<>();


            for (Concepto concepto : conceptos) {
                HashMap<String, Object> map = new HashMap<>();

                map.put("id", concepto.getId());
                map.put("nombre", concepto.getNombre());
                map.put("equivalencias", cm.getEquivalencias(concepto.getNombre()));

                conceptosBody.add(map);
            }

            respuesta.agregarDato("conceptos", conceptosBody);
            respuesta.setMensaje("ok");
            respuesta.tipoOk();

        }
        catch (Exception e){

            respuesta.tipoError();
            respuesta.setMensaje(e.getMessage());
        }


        return respuesta.getRespuestaHttp();

    }



    @RequestMapping(path="/concepto", method=RequestMethod.POST)
    public ResponseEntity addConcepto(@RequestParam("nombre") String nombre){

        RespuestaHttp respuesta = new RespuestaHttp();

        ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");
        Concepto concepto = new Concepto("",nombre);


        try {
            cm.addConcepto(concepto);
            respuesta.tipoOk();
        } catch (Exception e){
            respuesta.tipoError();
            respuesta.setMensaje(e.getMessage());
        }

        return respuesta.getRespuestaHttp();

    }



    @RequestMapping(path="/concepto/{nombre}", method=RequestMethod.GET)
    public ResponseEntity<HashMap> getConcepto(@PathVariable("nombre") String nombre){

        RespuestaHttp respuesta = new RespuestaHttp();
        try {
            ConceptManager cm = new ConceptManager("remote:localhost/PPR", "admin", "admin");

            Concepto concepto = cm.getConceptoByName(nombre);
            HashMap<String, Object> conceptoMap = new HashMap<>();

            if (concepto != null) {

                conceptoMap.put("id", concepto.getId());
                conceptoMap.put("nombre", concepto.getNombre());
                conceptoMap.put("equivalencias", cm.getEquivalencias(concepto.getNombre()));

                respuesta.agregarDato("concepto", conceptoMap);
                respuesta.tipoOk();
            } else {
                respuesta.noEncontrado();
                respuesta.setMensaje("No existe un concepto con el nombre especificado");
            }
        }
        catch(Exception e){
            respuesta.tipoError();
            respuesta.setMensaje(e.getMessage());
        }

        return respuesta.getRespuestaHttp();

    }


    @RequestMapping(path="/concepto/{nombre}", method=RequestMethod.PUT)
    public ResponseEntity<String> updateConcepto(@PathVariable("nombre") String nombre, @RequestParam("nuevoNombre") String nuevoNombre){

        RespuestaHttp respuesta = new RespuestaHttp();


        try {
            ConceptManager cm = new ConceptManager("remote:localhost/PPR", "admin", "admin");


            Concepto concepto = cm.getConceptoByName(nombre);
            String mensaje = "";
            HttpStatus status;

            if (concepto != null) {

                cm.updNombreConcepto(concepto, nuevoNombre);
                respuesta.tipoOk();
            } else {
                respuesta.noEncontrado();
                respuesta.setMensaje("El concepto que se quiere actualizar no existe");
            }
        }
        catch(Exception e){
            respuesta.tipoError();
            respuesta.setMensaje(e.getMessage());
        }

        return respuesta.getRespuestaHttp();

    }


    @RequestMapping(path="/concepto/{nombre}", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteConcepto(@PathVariable("nombre") String nombre){

        RespuestaHttp respuesta = new RespuestaHttp();

        try {
            ConceptManager cm = new ConceptManager("remote:localhost/PPR", "admin", "admin");

            Concepto concepto = cm.getConceptoByName(nombre);
            String mensaje = "";
            HttpStatus status;

            if (concepto != null) {

                Boolean borrado = cm.delConceptoSafe(concepto);

                if (borrado) {
                    respuesta.tipoOk();
                    respuesta.setMensaje("Se ha borrado el concepto con éxito");
                } else {
                    respuesta.tipoError();
                    respuesta.setMensaje("Existen relaciones asociadas al concepto. No es posible borrarlo");
                }

            } else {
                respuesta.noEncontrado();
                respuesta.setMensaje("El concepto que se quiere actualizar no existe");

            }
        }
        catch(Exception e){
            respuesta.tipoError();
            respuesta.setMensaje(e.getMessage());
        }


        return respuesta.getRespuestaHttp();

    }


    // Desde acá no está documentado en Swagger

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



    @RequestMapping(path="/concepto/{nombreConcepto}/equivalencia", method=RequestMethod.POST)
    public ResponseEntity<HashMap> addEquivalencia(
            @PathVariable("nombreConcepto") String concepto,
            @RequestParam("nombreEquivalencia") String nombreEquivalencia,
            @RequestParam("pesoEquivalencia") Double pesoEquivalencia
    ){

        RespuestaHttp respuestaHttp = new RespuestaHttp();

        try {
            ConceptManager cm = new ConceptManager("remote:localhost/PPR", "admin", "admin");
            cm.addEquivalencia(concepto, nombreEquivalencia, pesoEquivalencia);
            respuestaHttp.tipoOk();

        }
        catch(Exception e){
            respuestaHttp.tipoError();
            respuestaHttp.setMensaje(e.getMessage());
        }

        return respuestaHttp.getRespuestaHttp();



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
