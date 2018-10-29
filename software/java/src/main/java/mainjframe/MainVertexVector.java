/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainjframe;

import api.ElementoExamen;
import api.Respuesta;
import api.Termino;
import com.google.common.collect.ImmutableList;
import conceptmanager.ConceptManager;
import conceptmanager.Concepto;
import java.util.ArrayList;
import java.util.Scanner;
import ortografia.LangTools;

/**
 *
 * @author filardo
 */
public class MainVertexVector {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try (Scanner sca = new Scanner(System.in)) {

            Respuesta p, r;

            LangTools lt = new LangTools();
            ConceptManager cm = new ConceptManager("remote:localhost/PPR", "admin", "admin");

            do {
                System.out.println("Ingrese respuesta 1:");
                p = new Respuesta(sca.nextLine());
                p = lt.CheckRespuesta(p, cm.getConceptosComplejos(), cm.getRelacionesComplejas());

                // Imprimo los errores, si no los hay no se imprime nada
                System.out.println(corregirRespuesta(p));

            } while (p.hasErrors());

            do {

                System.out.println("Ingrese respuesta 2:");
                r = new Respuesta(sca.nextLine());
                r = lt.CheckRespuesta(r, cm.getConceptosComplejos(), cm.getRelacionesComplejas());

                // Imprimo los errores, si no los hay no se imprime nada
                System.out.println(corregirRespuesta(r));

            } while (r.hasErrors());

            // evalúo las respuestas y encuentro las ruta, ya pueden ser comparadas
            p = cm.evaluarRespuesta(p);
            p = cm.findRouteRespuesta(p);

            r = cm.evaluarRespuesta(r);
            r = cm.findRouteRespuesta(r);

            // elimino las relaciones antes de obtener los deltas
            if (p != null && r != null) {
                p.eliminarRelaciones();
                r.eliminarRelaciones();
                
                p.calcularPeso();
                r.calcularPeso();
                
                System.out.println("Peso de r1:");
                System.out.println(r.getPeso());
                System.out.println("Peso de r2:");
                System.out.println(p.getPeso());

                Respuesta deltas1 = p.getDeltas(r);
                Respuesta deltas2 = r.getDeltas(p);

                System.out.println("Deltas entre 1 y 2: ");
                System.out.println(deltas1.toStringDelta());

                System.out.println("Deltas entre 2 y 1: ");
                System.out.println(deltas2.toStringDelta());
            } else {
                System.out.println("Alguna de las respuestas no se pudo evaluar. Imposible calcular los Deltas.");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Clase excepción: " + e.getClass().toString());
        }

    }

    /**
     * Recibe una respuesta y la corrige, ofreciendo sugerencias
     *
     * @param respuesta Respuesta previamente chequeada con LangTools
     * @return un StringBuilder con las sugerencias de corrección, en el caso
     * que las haya.
     */
    private static StringBuilder corregirRespuesta(Respuesta respuesta) {

        ArrayList<Termino> terminos = respuesta.getTerminos();

        StringBuilder bld = new StringBuilder();

        for (Termino termino : terminos) {
            if (termino.hasErrors()) {
                String item = String.format("Error en la palabra: %s\n  - Corrección sugerida: %s\n", termino.getNombre(), termino.getSugerenciasCorreccion());
                bld.append(item);
            }
        }
        return bld;
    }

}
