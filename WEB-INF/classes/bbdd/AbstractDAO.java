package bbdd;

import java.sql.Connection;

public abstract class AbstractDAO {

    private Connection conexion;

    protected java.sql.Connection getConexion(){
        return this.conexion;
    }
    
    protected void setConexion(java.sql.Connection conexion){
        this.conexion = conexion;
    }

}