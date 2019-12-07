package bbdd;

import java.sql.*;

public final class ArtigoDAO extends AbstractDAO {

    public ArtigoDAO(Connection conexion){
        super.setConexion(conexion);
    }

}