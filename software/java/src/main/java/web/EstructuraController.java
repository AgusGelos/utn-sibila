package web;

import api.Respuesta;
import api.Termino;
import conceptmanager.ConceptManager;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ortografia.ManejoLang;

import java.util.ArrayList;
import java.util.Optional;


@RestController
public class EstructuraController {



    @RequestMapping(path="/estructura", method=RequestMethod.POST)
    public ResponseEntity addEstructura(@RequestParam("conceptoOrigen") Optional<String> conceptoOrigenOp,
                                        @RequestParam("conceptoDestino") Optional<String> conceptoDestinoOp,
                                        @RequestParam("relacion") Optional<String> relacionOp,
                                        @RequestParam("estructura") Optional<String> estructuraOp
                                        ){

        RespuestaHttp respuesta = new RespuestaHttp();


        if ((!estructuraOp.isPresent()) && (!conceptoDestinoOp.isPresent() || !conceptoOrigenOp.isPresent() || !relacionOp.isPresent() )) {
            respuesta.setMensaje("Faltan datos obligatorios");
            respuesta.tipoError();
        }
        else {
            try {

                // Si existe el parámetro estructura entocnces obtenemos los conceptos y la relación de ahí
                // Estructura = Concepto - Relacion - Concepto

                String conceptoOrigen = "";
                String conceptoDestino = "";
                String relacion = "";

                if (estructuraOp.isPresent()){
                    String estructura = estructuraOp.get();
                    String[] estructuraArray = estructura.split("-");

                    if (estructuraArray.length != 3) throw new Exception("La estructura provista no cumple con tiene el patrón c-r-c");

                    conceptoOrigen = estructuraArray[0];
                    relacion = estructuraArray[1];
                    conceptoDestino = estructuraArray[2];
                }
                else {
                    conceptoOrigen = conceptoOrigenOp.get();
                    relacion = relacionOp.get();
                    conceptoDestino = conceptoDestinoOp.get();
                }



                ConceptManager cm = new ConceptManager("remote:localhost/PPR", "admin", "admin");


                ArrayList<Termino> co = this.cambiarASingular(conceptoOrigen.toUpperCase().split(" "));
                conceptoOrigen = this.aString(co);

                ArrayList<Termino> cd = this.cambiarASingular(conceptoDestino.toUpperCase().split(" "));
                conceptoDestino = this.aString(cd);


                ArrayList<Termino> relaciones = this.cambiarASingular(relacion.split(" "));
                relacion = this.aString(relaciones);


                cm.addStruct(conceptoOrigen, conceptoDestino, relacion);
                respuesta.tipoOk();

            } catch (Exception e) {
                respuesta.tipoError();
                respuesta.setMensaje(e.getMessage());
            }
        }
        return respuesta.getRespuestaHttp();

    }


    private ArrayList<Termino> cambiarASingular(String [] terminos){
        ArrayList<Termino> term = new ArrayList<Termino>();
        for (int i = 0; i < terminos.length; i++) {
            term.add(new Termino(terminos[i]));
        }

        ManejoLang ml = new ManejoLang();
        return ml.cambiarPluralPorSingular(term);

    }

    private String aString(ArrayList<Termino> terminos){
        String cadena = "";
        for (int i = 0; i < terminos.size(); i++) {
            cadena += terminos.get(i).getNombre();
            if ((i+1) != terminos.size()) cadena += " ";
        }
        return cadena;
    }



}
