/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academico;

import api.Pregunta;
import api.Respuesta;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import java.util.ArrayList;

/**
 *
 * @author Martin
 */
public class AcademicManager
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
    public AcademicManager (String Database, String Username, String Password)
    {
        this.DBName = Database;
        this.Username = Username;
        this.Password = Password;
        Factory = new OrientGraphFactory(Database, Username, Password).setupPool(1, 10);
    }
    
    public ArrayList<Pregunta> getPreguntas()
    {
        OrientGraph db = Factory.getTx();
        ArrayList<Pregunta> ret = new ArrayList<Pregunta>();
        try
        {
            Iterable<Vertex> result = db.getVerticesOfClass("Pregunta");
            for (Vertex v : result)
            {
                Pregunta p = new Pregunta();
                p.IdNodo = v.getId().toString();
                p.setTexto(v.getProperty("Texto").toString());
                ret.add(p);
            }
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
        return ret;
    }
    
    /**
     * Obtiene la cantidad de respuestas asociadas a la pregunta pasada por parámetro
     * @param p Pregunta a la que se quiere obtener la cantidad de respuestas
     * @return Cantidad de respuestas. 0 si la pregunta no tiene respuestas asociadas.
     */
    public int getRespuestasCount (Pregunta p) {
        // select from Respuesta where out('PertenecePregunta')[0].@rid = #31:4
        String qry = String.format("select count(*) from Respuesta where out('PertenecePregunta')[0].@rid = %s", p.IdNodo);
        OrientGraph db = Factory.getTx();
        OCommandSQL cmd = new OCommandSQL(qry); //+
        Iterable<Vertex> result = db.command(cmd).execute();
        ImmutableList<Vertex> result_list = ImmutableList.copyOf(result);
        int Cantidad = 0;
        if (result_list.size() > 0) {
            Vertex v = result_list.get(0);
            if (v != null) {
                Cantidad = Integer.parseInt(v.getProperty("count").toString());
            }
        }
        return Cantidad;
    }
    
    /**
     * Agrega una pregunta a la base de datos de grafos.
     * La pregunta se agrega como un nodo conteniendo texto (la pregunta en sí)
     * Su única finalidad (por el momento) es agrupar preguntas relacionadas
     * Más adelante se va a utilizar para la gestión de examenes y evaluaciones.
     * @param p Pregunta que se desea almacenar en la base de datos. Solamente
     * se utiliza el Texto.
     * @return La pregunta pasada por parámetro pero con el campo IdNodo 
     * (heredado de ElementoExamen) asignado desde el servidor
     */
    public Pregunta addPregunta (Pregunta p)
    {
        OrientGraph db = Factory.getTx();
        try
        {
            Vertex v = db.addVertex("class:Pregunta");
            v.setProperty("Texto", p.getTexto());
            db.commit();
            p.IdNodo = v.getId().toString();
            return p;
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
     * Elimina una pregunta de la base de datos. Las relaciones asociadas
     * también se eliminan (automáticamente por el servidor)
     * @param p La pregunta que se desea eliminar (se utiliza IdNodo para borrar)
     * @param cascade TRUE implica que se van a borrar automáticamente las respuestas asociadas, 
     * FALSE que no. Si existen respuestas asociadas la pregunta no se elimina.
     * @return true = Pregunta eliminada con éxito. false = Cualquier error
     */
    //public boolean delPregunta (Pregunta p, boolean cascade)
    public boolean delPregunta (Pregunta p, boolean cascade)
    {
        // Si cascade = false y la pregunta tiene respuestas volver con false
        int dependencies = getRespuestasCount(p);
        if ((cascade == false) && dependencies > 0)
            return false;
        
        //
        OrientGraph db = Factory.getTx();
        try
        {
            // TODO: MEJORAR CON UN DELETE BATCH DE TODAS LAS RESPUESTAS ASOCIADAS
            // Si hay que borrar en cascada y tiene dependencias
            if (cascade && dependencies > 0) {
                // Obtener la lista de respuestas asociadas a esta pregunta
                String qry = String.format("select from Respuesta where out('PertenecePregunta')[0].@rid = %s", p.IdNodo);
                OCommandSQL cmd = new OCommandSQL(qry); //+
                Iterable<Vertex> result = db.command(cmd).execute();
                for (Vertex v : result) {
                    db.removeVertex(v);
                }
            }
            // Ahora borrar la pregunta
            Vertex v = db.getVertex(p.IdNodo);
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
     * Modificar una pregunta almacenada en la base de datos
     * @param old Pregunta existente, se utiliza IdNodo para buscar el nodo
     * @param upd Pregunta nueva, se utiliza solo Texto para modificar
     * @return true = Modificación exitosa, false = cualquier error
     */
    public boolean updPregunta (Pregunta old, Pregunta upd)
    {
        OrientGraph db = Factory.getTx();
        try
        {
            Vertex v = db.getVertex(old.IdNodo);
            if (v == null)
                return false;
            
            v.setProperty("Texto", upd.getTexto());
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
     * Obtiene una pregunta basada en el texto. La búsqueda es EXACTA
     * @param texto Texto de la pregunta que se quiere obtener
     * @return Pregunta encontrada (con IdNodo asignado) o null si no se encuentra
     */
    public Pregunta getPreguntaByText (String texto)
    {
        OrientGraph db = Factory.getTx();
        try
        {
            Iterable<Vertex> vi = db.getVertices("Pregunta.Text", texto);
            if (vi.iterator().hasNext())
            {
                Vertex v = vi.iterator().next();
                Pregunta p = new Pregunta(texto);
                p.IdNodo = v.getId().toString();
                return p;
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
     * Obtiene una pregunta basada en el IdNodo pasado por parámetro
     * @param Id Id del nodo que contiene la pregunta
     * @return La pregunta encontrada, con el campo Texto cargado desde la 
     * DB. null en caso de que no se haya encontrado.
     */
    public Pregunta getPreguntaById (String Id)
    {
        OrientGraph db = Factory.getTx();
        try
        {
            Vertex vi = db.getVertex(Id);
            if (vi != null)
            {
                Pregunta p = new Pregunta (vi.getProperty("Texto").toString());
                p.IdNodo = Id;
                return p;
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
     * Agrega una respuesta a la base de datos de grafos.
     * La respuesta se agrega como un nodo conteniendo el texto de la respuesta
     * y el query que el sistema genera para encontrar las rutas.
     * NO SE GRABAN NODOS NI RUTAS para permitir que las rutas cambien dinámicamente
     * a medida que lo haga la base de conocimientos.
     * La respuesta se graba asociada (en ambas direcciones) a la pregunta.
     * @param r Respuesta que deseo grabar en la base
     * @param p Pregunta (ya debe estar almacenada en la base)
     * @return La respuesta pero con el IdNodo asignado por el servidor.
     */
    public Respuesta addRespuesta (Respuesta r, Pregunta p)
    {
        OrientGraph db = Factory.getTx();
        try
        {
            Vertex v = db.addVertex("class:Respuesta");
            v.setProperty("Texto", r.getTexto());
            v.setProperty("Query", r.getQuery());
            // Crear un nodo estableciendo la pregunta origen
            Vertex origen = db.getVertex(p.IdNodo);
            // La pregunta "tiene" una respuesta ->
            db.addEdge("class:TieneRespuesta", origen, v, null);
            // La respuesta "pertenece" a una pregunta <-
            db.addEdge("class:PertenecePregunta", v, origen, null);
            // Grabar todo en un comit
            db.commit();
            r.IdNodo = v.getId().toString();
            return r;
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
     * Elimina una Respuesta de la base de datos.
     * Se utiliza unicamente el campo IdNodo de la Respuesta
     * @param r Respuesta que deseo eliminar (se utiliza solamente IdNodo)
     * @return true = Respuesta eliminada, false = caso contrario
     */
    public boolean delRespuesta (Respuesta r)
    {
        OrientGraph db = Factory.getTx();
        try
        {
            Vertex v = db.getVertex(r.IdNodo);
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
     * Actualiza una respuesta grabada en la base de datos, se actualiza el Texto
     * y el query de busqueda. No se modifican relaciones ni el IdNodo
     * @param old Pregunta original. Se utiliza solo el IdNodo para reemplazar
     * @param upd Pregunta nueva. Se utilizan los campos Texto y Query.
     * @return true = Pregunta modificada, false = caso contrario
     */
    public boolean updRespuesta (Respuesta old, Respuesta upd)
    {
        OrientGraph db = Factory.getTx();
        try
        {
            Vertex v = db.getVertex(old.IdNodo);
            if (v == null)
                return false;
            
            v.setProperty("Texto", upd.getTexto());
            v.setProperty("Query", upd.getQuery());
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
     * Obtiene una respuesta buscandola por texto. La busqueda es EXACTA
     * @param texto Texto de la pregunta a buscar
     * @return Repuesta (con IdNodo asignado) que contiene ese texto
     */
    public Respuesta getRespuestaByText (String texto)
    {
        OrientGraph db = Factory.getTx();
        try
        {
            Iterable<Vertex> vi = db.getVertices("Respuesta.Text", texto);
            if (vi.iterator().hasNext())
            {
                Vertex v = vi.iterator().next();
                Respuesta r = new Respuesta(texto);
                r.IdNodo = v.getId().toString();
                r.setQuery(v.getProperty("Query").toString());
                return r;
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
     * Obtiene una respuesta buscandola por el IdNodo asociado.
     * @param Id Id del nodo que contiene la respuesta
     * @return Respuesta, con los campos Texto y Query cargados desde la DB.
     */
    public Respuesta getRespuestaById (String Id)
    {
        OrientGraph db = Factory.getTx();
        try
        {
            Vertex vi = db.getVertex(Id);
            if (vi != null)
            {
                Respuesta r = new Respuesta (vi.getProperty("Texto").toString());
                r.setQuery(vi.getProperty("Query").toString());
                r.IdNodo = Id;
                return r;
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
    
    public ArrayList<Respuesta> getRespuestas()
    {
        OrientGraph db = Factory.getTx();
        ArrayList<Respuesta> ret = new ArrayList<Respuesta>();
        try
        {
            Iterable<Vertex> result = db.getVerticesOfClass("Respuesta");
            for (Vertex v : result)
            {
                Respuesta p = new Respuesta();
                p.IdNodo = v.getId().toString();
                p.setTexto(v.getProperty("Texto").toString());
                p.setQuery(v.getProperty("Query").toString());
                ret.add(p);
            }
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
        return ret;
    }

    public ArrayList<Respuesta> getRespuestasByPregunta (Pregunta p)
    {
        ArrayList<Respuesta> respuestas = new ArrayList<Respuesta>();
        // select from Respuesta where out('PertenecePregunta')[0].@rid = #31:4
        String qry = String.format("select from Respuesta where out('PertenecePregunta')[0].@rid = %s", p.IdNodo);
        OrientGraph db = Factory.getTx();
        OCommandSQL cmd = new OCommandSQL(qry); //+
        Iterable<Vertex> result = db.command(cmd).execute();
        for (Vertex v : result)
        {
            Respuesta r = new Respuesta();
            r.IdNodo = v.getId().toString();
            r.setTexto(v.getProperty("Texto").toString());
            r.setQuery(v.getProperty("Query").toString());
            respuestas.add(r);
        }
        return respuestas;
    }
}
