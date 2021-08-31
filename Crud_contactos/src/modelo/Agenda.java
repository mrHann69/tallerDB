
package modelo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Agenda {    
    // Relacion de tiene-un
    private ConexionBD conexion;
    private ArrayList<Contacto> lista;

    public ArrayList<Contacto> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Contacto> lista) {
        this.lista = lista;
    }

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
        } catch (SQLException e) {
            System.out.println("Error addContact: "+ e.getMessage());
        }
        conexion.ejecutarSQL(sql);
    }
    
    public void modificarContacto(String n, String a, int e, char g, int  id)
    { 
        PreparedStatement sentencia = null;
        try {
            String sql = "UPDATE contactos SET nombre = ?, apellido = ?, edad = ?, genero = ? WHERE id = ?;";
            sentencia = conexion.getConexion().prepareStatement(sql);
            sentencia.setString(1, n);
            sentencia.setString(2, a);
            sentencia.setInt(3, e);
            sentencia.setString(4, String.valueOf(g));
            sentencia.setInt(5, id);
            sentencia.executeQuery();

        } catch (SQLException ee) {
            System.out.println("Error mC: "+ ee.getSQLState()+" "+ee.getLocalizedMessage());
        }   
    }

    
    public Contacto getContacto(int pos) {
        return lista.get(pos);
    }
    
    
    public void borrarContacto(int id)
    {
        PreparedStatement psmt =null;
        try {
            String sql = "DELETE FROM contactos WHERE id= ?";
            psmt = conexion.getConexion().prepareStatement(sql);
            psmt.setInt(1, id);
            psmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error bC: "+e.getLocalizedMessage());
        }
    }

    public void mostrarDatos(JTable tabla) {
        DefaultTableModel modeloDatos = (DefaultTableModel)tabla.getModel(); 
        while (modeloDatos.getRowCount() > 0) {
            modeloDatos.removeRow(modeloDatos.getRowCount()-1);
        }
        String sql = "SELECT * FROM contactos ORDER BY id ASC;";
        ArrayList<String[]> otraLista = conexion.consultar(sql, 5);   
        this.lista= new ArrayList(); 
        for (int i = 0; i < otraLista.size(); i++) {
            String[] row =  otraLista.get(i);
            Object[] fila = new Object[]{row[0], row[1], row[2], row[3],row[4]};
            this.lista.add(new Contacto(Integer.parseInt(row[0]), //ID
                                        row[1], // nombre
                                        row[2], // apellido
                                        Integer.parseInt(row[3]), //edad
                                        row[4].charAt(0))); // genero
            modeloDatos.addRow(fila);
        }
    }
    
}
