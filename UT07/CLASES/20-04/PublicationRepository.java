package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PublicationRepository {
    
    //La colección de libros de la base de datos
    private ArrayList<Publication> repositorio = 
        new ArrayList<Publication>();

    public PublicationRepository() {
        // nos conectamos a la base de datos
        CrearConexion miConexion = new CrearConexion();

        try (Connection conexion = miConexion.hazConnection();
        Statement sentencia = conexion.createStatement();) {
            //sentencia e ejecutar
            String query = "select * from publication";
            //ejecuto la sentencia y guardo el resultado en rs
            ResultSet rs = sentencia.executeQuery(query);

            while (rs.next()) {
                //Creo un objeto con el resultado actual
                Publication p = Publication.fromResulSet(rs);
                //Lo agregamos a la colección
                repositorio.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar");
        }
    }


    public Publication buscarPorId(Integer id) {
        Publication p = null;
        for (Publication pub : repositorio) {
            if (pub.getId() == id) {
                p = pub;
                break;
            }
        }
        return p;
    }

    public ArrayList<Publication> buscarPorTitulo (String titulo) {
        // nos conectamos a la base de datos
        CrearConexion miConexion = new CrearConexion();

        var devolver = new ArrayList<Publication>();

        try (Connection conexion = miConexion.hazConnection();
        Statement sentencia = conexion.createStatement();) {
            //sentencia e ejecutar
            String query = "select * from publication where " +
                "book_title like %" + titulo + "%";
            //ejecuto la sentencia y guardo el resultado en rs
            ResultSet rs = sentencia.executeQuery(query);

            while (rs.next()) {
                //Creo un objeto con el resultado actual
                Publication p = Publication.fromResulSet(rs);
                //Lo agregamos a la colección
                devolver.add(p);
            }
            

        } catch (SQLException e) {
            System.out.println("Error al conectar");
        }
        
        return devolver;

    }
}
