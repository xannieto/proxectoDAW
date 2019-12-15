package bbdd;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDAO {

    private Connection conexion;

    protected java.sql.Connection getConexion(){
        try {
			conexion.setAutoCommit(true);
        } catch (SQLException ex) {
			ex.printStackTrace();
        }
        
        return this.conexion;
    }
    
    protected void setConexion(java.sql.Connection conexion){
        this.conexion = conexion;
    }

}