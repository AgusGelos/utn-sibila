/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academico;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que administra los docentes
 * @author Martin
 */
public class DocentesManager
{
    String Database;
    String Username;
    String Password;
    OrientGraphFactory factory;
    
    /**
     * Constructor. Establece una conexión a la base de datos.
     * @param database Cadena de conexión, en formato protocolo:host/database
     * @param user Nombre de usuario
     * @param password Cadena de conexión
     */
    public DocentesManager (String database, String user, String password)
    {
        Database = database;
        Username = user;
        Password = password;
        
        factory = new OrientGraphFactory(Database, Username, Password).setupPool(1, 10);
    }
    
    /**
     * Obtener un lista con los docentes de la base
     * @return ArrayList con objetos Docente
     */
    public ArrayList <Docente> getDocentes ()
    {
        ArrayList<Docente> result = new ArrayList<> ();
        OrientGraph db = factory.getTx();
        for (Vertex v : db.getVerticesOfClass("Docente")) 
        {
//            System.out.println(v.getProperty("Apellido"));
            Docente d = new Docente();
            d.Apellido = v.getProperty("Apellido");
            d.Nombre = v.getProperty("Nombre");
            d.Legajo = v.getProperty("Legajo");
            d.IdNodo = v.getId().toString();
            result.add(d);
        }
        
        return result;
    };
    
    /**
     * Obtiene un Docente, a partir de su legajo.
     * @param legajo Legajo del docente a buscar
     * @return Docente encontrado, null si no se encontró nada.
     */
    public Docente getDocenteByLegajo (String legajo)
    {
        OrientGraph db = factory.getTx();
        try
        {
            Iterable<Vertex> vi = db.getVertices("Docente.Legajo", legajo);
            if (vi.iterator().hasNext())
            {
                Vertex v = vi.iterator().next();
                Docente d = new Docente();
                d.Apellido = v.getProperty("Apellido");
                d.Nombre = v.getProperty("Nombre");
                d.Legajo = v.getProperty("Legajo");
                d.IdNodo = v.getId().toString();
                return d;
            }
            else
                return null;
        }
        catch (Exception e)
        {
            db.rollback();
            e.printStackTrace();
            return null;
        }
        finally
        {
            db.shutdown();
        }
    }
    
    /**
     * Agrega un Docente a la base de datos.
     * @param d Docente que se desea agregar a la base de datos
     * @return true=alta correcta, false=no se pudo dar de alta
     */
    public boolean addDocente (Docente d)
    {
        OrientGraph db = factory.getTx();
        try
        {
            Vertex v = db.addVertex("class:Docente");
            v.setProperty("Nombre", d.Nombre);        
            v.setProperty("Apellido", d.Apellido);        
            v.setProperty("Legajo", d.Legajo);
            db.commit();
            return true;
        }
        catch (Exception e)
        {
            db.rollback();
            e.printStackTrace();
            return false;
        }
        finally
        {
            db.shutdown();
        }
    };
    
    /**
     * Elimina un docente de la base de datos
     * @param d Docente que se desea eliminar
     * @return true=baja exitosa, false=error
     */
    public boolean delDocente (Docente d)
    {
        OrientGraph db = factory.getTx();
        try
        {
            Vertex v = db.getVertex(d.IdNodo);
            if (v == null)
                return false;
            db.removeVertex(v);
            db.commit();
            return true;
        }
        catch (Exception e)
        {
            db.rollback();
            e.printStackTrace();
            return false;
        }
        finally
        {
            db.shutdown();
        }
    };
    
    /**
     * Actualiza un docente en la base de datos
     * @param old Docente original. Se utiliza Id para encontrarlo.
     * @param upd Nuevo docente, sus datos reemplazan a los originales.
     * @return true si se pudo modificar, false en caso contrario
     */
    public boolean updDocente (Docente old, Docente upd)
    {
        OrientGraph db = factory.getTx();
        try
        {
            Vertex v = db.getVertex(old.IdNodo);
            if (v == null)
                return false;
            
            v.setProperty("Nombre", upd.Nombre);
            v.setProperty("Apellido", upd.Apellido);
            v.setProperty("Legajo", upd.Legajo);
            db.commit();
            return true;
        }
        catch (Exception e)
        {
            db.rollback();
            e.printStackTrace();
            return false;
        }
        finally
        {
            db.shutdown();
        }
    };
}
