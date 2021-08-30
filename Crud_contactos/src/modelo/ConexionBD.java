
package modelo;

import java.sql.*;
import java.util.ArrayList;

public class ConexionBD {
    private String username = "msp";
    private String password = "123456";
    private String dbname = "agenda";
    private String host = "localhost";
    private int puerto = 5432;
    private Connection conexion;
    
    /* constructor se conecta a la db */
    public ConexionBD()
    {        
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            String url = "jdbc:postgresql://"+host+":"+puerto+"/"+dbname;
            
            conexion = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            System.out.println("Error conexion: " + ex.getMessage());
        }
    }
    
    // consultar (select)
    public ArrayList<String[]> consultar(String sql, int numCol)
    {
        ArrayList<String[]> tabla = new ArrayList<>();
        try {
            Statement s =  conexion.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while (rs.next()){
                String[] fila = new String[numCol];
                for (int i = 0; i < numCol; i++) {
                    fila[i] = rs.getString(i+1);
                }
                System.out.println("consultar: "+fila[0]+" "+fila[1]);
                tabla.add(fila);
            }
            
        } catch (SQLException ex) {
            System.out.println("Error consulta: " + ex.getMessage());
        }
        return tabla;
    }
    
    // modificar (inserto, actualizo, borro)
    public void ejecutarSQL (String sql){    
        try {
            Statement s =  conexion.createStatement();
            int numFilas = s.executeUpdate(sql);
            System.out.println("eSQL NumFilas: " + numFilas);
            
        } catch (SQLException ex) {
            System.out.println("Error eSQL: " + ex.getMessage());
        }
    }

    public Connection getConexion() {
        return conexion;
    }
    
    
}
