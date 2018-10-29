/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmanager;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;

/**
 *
 * @author Martin
 */
public class DBManager
{
    OrientGraphFactory Factory;
    String DBName ;
    String Username;
    String Password;
    /**
     * Constructor
     * 
     * @param Database Nombre de la base de datos a la que se desea acceder (ej: remote:localhost/PPR)
     * @param Username Nombre de usuario que está autorizado para conectarse a la DB
     * @param Password Password del usuario
     */
    public void DBManager (String Database, String Username, String Password)
    {
        this.DBName = Database;
        this.Username = Username;
        this.Password = Password;
        Factory = new OrientGraphFactory(Database, Username, Password).setupPool(1, 10);
    }

    /**
     * Obtiene todos los vérticas de un determinado tipo (clase) y devuelve una lista
     * @param className Nombre de la clase de Vértices que debo obtener
     * @return Lista iterable de objetos de tipo Vertex (que coinciden con la clase pasada por parámetro)
     */
    public Iterable<Vertex> getVerticesOfClass (String className)
    {
        OrientGraph db = Factory.getTx();
        Iterable<Vertex> result = db.getVerticesOfClass(className);
        db.shutdown();
        return result;
    }
}
