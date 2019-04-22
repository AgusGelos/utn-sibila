package web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;

public class RespuestaHttp {

    private static final String ESTADO_OK = "ok";
    private static final String ESTADO_ERROR = "error";
    private static final String ESTADO_NOT_FOUND = "no encontrado";

    private String mensaje;
    private ArrayList<HashMap<String, Object>> datos;
    private String estado;

    public RespuestaHttp(){
        this.mensaje = "";
        this.estado = this.ESTADO_ERROR;
        this.datos = new ArrayList<>();
    }

    public void setMensaje(String mensaje){
        this.mensaje = mensaje;
    }

    public void tipoOk(){
        this.estado = this.ESTADO_OK;
    }

    public void tipoError(){
        this.estado = this.ESTADO_ERROR;
    }

    public void noEncontrado(){
        this.estado = this.ESTADO_NOT_FOUND;
    }

    public void agregarDato(HashMap<String, Object> dato){
        this.datos.add(dato);
    }

    public void agregarDato(String nombre, Object dato){
        HashMap data = new HashMap();
        data.put(nombre, dato);
        this.datos.add(data);
    }


    public ResponseEntity getRespuestaHttp(){

        HashMap<String, Object> body = new HashMap<>();

        body.put("estado", this.estado);
        body.put("mensaje", this.mensaje);
        body.put("datos", this.datos);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (this.estado == this.ESTADO_OK){
            status = HttpStatus.OK;
        }
        else if (this.estado == this.ESTADO_NOT_FOUND){
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity(body, status);
    }

}
