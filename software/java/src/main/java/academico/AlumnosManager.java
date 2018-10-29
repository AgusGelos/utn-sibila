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
 * Clase que concentra la gestión de alumnos. Alta, Baja, Modificacion y consultas.
 * @author Martin
 */
public class AlumnosManager
{
    String mConnectionString;
    String mUser;
    String mPassword;
    OrientGraphFactory factory;
    
    /**
     * Constructor. establece una conexión a la base de datos.
     * @param connection_string Cadena de conexión. Formato (protocolo:host/database)
     * @param user Nombre de usuario
     * @param password Contraseña
     */
    public AlumnosManager (String connection_string, String user, String password)
    {
        mConnectionString = connection_string;
        mUser = user;
        mPassword = password;
        
        factory = new OrientGraphFactory(mConnectionString, mUser, mPassword).setupPool(1, 10);
    }
    
    /**
     * Obtiene una lista de alumnos
     * @return Un ArrayList de objetos Alumno
     */
    public ArrayList <Alumno> getAlumnos ()
    {
        ArrayList<Alumno> result = new ArrayList<> ();
        OrientGraph db = factory.getTx();
        for (Vertex v : db.getVerticesOfClass("Alumno")) 
        {
//            System.out.println(v.getProperty("Nombre"));
            Alumno a = new Alumno();
            a.Nombre = v.getProperty("Nombre");
            a.Apellido = v.getProperty("Apellido");
            a.Legajo = v.getProperty("Legajo");
            a.Condicion = v.getProperty("Condicion");
            a.IdNodo = v.getId().toString();
            result.add(a);
        }
        
        return result;
    };
    
    /**
     * Obtiene un alumno dado un legajo.
     * @param legajo Legajo del alumno a buscar
     * @return Alumno, con todos los campos cargados desde la DB
     */
    public Alumno getAlumnoByLegajo (String legajo)
    {
        OrientGraph db = factory.getTx();
        try
        {
            Iterable<Vertex> vi = db.getVertices("Alumno.Legajo", legajo);
            if (vi.iterator().hasNext())
            {
                Vertex v = vi.iterator().next();
                Alumno a = new Alumno();
                a.Nombre = v.getProperty("Nombre");
                a.Apellido = v.getProperty("Apellido");
                a.Legajo = v.getProperty("Legajo");
                a.Condicion = v.getProperty("Condicion");
                a.IdNodo = v.getId().toString();
                return a;
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
     * Agregar un alumno a la base de datos
     * @param a Alumno que se desea agregar
     * @return true para alumno agregado, false si hubo error
     */
    public boolean addAlumno (Alumno a)
    {
        OrientGraph db = factory.getTx();
        try
        {
            Vertex v = db.addVertex("class:Alumno");
            v.setProperty("Nombre", a.Nombre);        
            v.setProperty("Apellido", a.Apellido);        
            v.setProperty("Condicion", a.Condicion);        
            v.setProperty("Legajo", a.Legajo);
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
     * Elimina un alumno (identificado por Id) de la base de datos
     * @param a El Alumno que se desea eliminar
     * @return true = alumno eliminado, false = error.
     */
    public boolean delAlumno (Alumno a)
    {
        OrientGraph db = factory.getTx();
        try
        {
            Vertex v = db.getVertex(a.IdNodo);
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
     * Actualiza la información de un alumno en la base de datos.
     * @param old Alumno existente. Se utiliza el Id para encontrarlo.
     * @param upd Alumno nuevo. Sus datos reemplazan a los anteriores.
     * @return true si se pudo actualizar el alumno, false en caso contrario.
     */
    public boolean updAlumno (Alumno old, Alumno upd)
    {
        OrientGraph db = factory.getTx();
        try
        {
            Vertex v = db.getVertex(old.IdNodo);
            if (v == null)
                return false;
            
            v.setProperty("Nombre", upd.Nombre);
            v.setProperty("Apellido", upd.Apellido);
            v.setProperty("Condicion", upd.Condicion);
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
