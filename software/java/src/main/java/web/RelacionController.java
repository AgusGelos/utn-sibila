package web;

import api.Respuesta;
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
    public ResponseEntity getRelaciones() throws JSONException {

        RespuestaHttp respuesta  = new RespuestaHttp();

        try {

            ConceptManager cm = new ConceptManager("remote:localhost/PPR", "admin", "admin");
            ArrayList<Relacion> relaciones = cm.getRelaciones();

            ArrayList<HashMap> relacionesBody = new ArrayList<>();

            for (Relacion relacion : relaciones) {

                HashMap<String, Object> map = new HashMap<>();

                map.put("id", relacion.getId());
                map.put("nombre", relacion.getNombre());
                map.put("tipo", relacion.getTipo());


                relacionesBody.add(map);
            }

            respuesta.agregarDato("relaciones", relacionesBody);
            respuesta.tipoOk();
        }
        catch (Exception e){
            respuesta.setMensaje(e.getMessage());
            respuesta.tipoError();
        }
        return respuesta.getRespuestaHttp();

    }


/*
    @RequestMapping(path="/relacion", method=RequestMethod.POST)
    public ResponseEntity addRelacion(@RequestParam("nombre") String nombre){

        try{
            ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");
            Relacion relacion = new Relacion("",nombre);

            HttpStatus status = HttpStatus.OK;

            // Relación origen y destino?
            // Pregunta: Agregamos al modelo de relación el concepto de origen y el concepto de destino?
            //cm.addRelacion();

        return new ResponseEntity(status);

    }*/



    @RequestMapping(path="/relacion/{nombre}", method=RequestMethod.GET)
    public ResponseEntity getRelacion(@PathVariable("nombre") String nombre){

        RespuestaHttp respuesta = new RespuestaHttp();


        try {

            ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");

            Relacion relacion = cm.getRelacionByName(nombre);
            HashMap<String, Object> relacionMap = new HashMap<>();

            HttpStatus status = HttpStatus.OK;


            if (relacion != null) {

                relacionMap.put("id", relacion.getId());
                relacionMap.put("nombre", relacion.getNombre());
                relacionMap.put("tipo", relacion.getTipo());

                respuesta.agregarDato("relacion", relacionMap);
                respuesta.tipoOk();

            } else {
                respuesta.setMensaje("No existe una relacion con el nombre especificado");
                respuesta.noEncontrado();

            }
        }
        catch(Exception e){
            respuesta.tipoError();
            respuesta.setMensaje(e.getMessage());
        }

        return respuesta.getRespuestaHttp();

    }

}
