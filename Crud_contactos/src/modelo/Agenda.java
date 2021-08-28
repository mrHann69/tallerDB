
package modelo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Agenda {    
    // Relacion de tiene-un
    private ConexionBD conexion;
    private ArrayList<Contacto> lista;

    public Agenda() {        
        conexion = new ConexionBD();
    }
    
    public void addContacto(Contacto c)
    {
        String sql = "INSERT INTO contactos (nombre, apellido, edad, genero) ";
        sql += " VALUES ( ?, ?, ?, ?) ";
        
        try {
            PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
            sentencia.setString(1 , c.getNombre() );
            sentencia.setString(2 , c.getApellido());
            sentencia.setInt(3 , c.getEdad());
            sentencia.setString(4 , String.valueOf(c.getGenero()));
            sentencia.executeQuery();
        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
        conexion.ejecutarSQL(sql);
    }
    
    public void modificarContacto(String n, String a, int e, char g, int  pos)
    { 
        String sql = "UPDATE contactos SET nombre = ?,"
                                    + " apellido = ?,"
                                    + "edad = ?,"
                                    + "genero = ?";
        sql +=" WHERE contactos.id = ? ";

        try {
            PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
            sentencia.setString(1, n);
            sentencia.setString(2, a);
            sentencia.setInt(3, e);
            sentencia.setString(4, String.valueOf(g));
            sentencia.setInt(5, pos);
            sentencia.executeQuery();
        } catch (SQLException ee) {
            System.out.println("Error: "+ ee.getMessage());
        }
        conexion.ejecutarSQL(sql);
    }
    
    public Contacto getContacto(int pos) {
        return lista.get(pos);
    }
    
    
    public void borrarContacto(int pos)
    {
        String sql = "DELETE ... ";
        
        conexion.ejecutarSQL(sql);
    }

    public void mostrarDatos(JTable tabla) {
        DefaultTableModel modeloDatos = (DefaultTableModel)tabla.getModel();
        
        while (modeloDatos.getRowCount() > 0) {
            modeloDatos.removeRow(modeloDatos.getRowCount()-1);
        }
        
        String sql = "SELECT * FROM contactos";
        ArrayList<String[]> lista = conexion.consultar(sql, 5);   
        this.lista= new ArrayList();
        
        for (int i = 0; i < lista.size(); i++) {
            String[] row =  lista.get(i);
            Object[] fila = new Object[]{row[1], row[2], row[3], row[4]};
            this.lista.add(new Contacto(row[1], // nombre
                                        row[2], // apellido
                                        Integer.parseInt(row[3]), // edad 
                                        row[4].charAt(0))); // genero
            modeloDatos.addRow(fila);
        }
    }
    
}
