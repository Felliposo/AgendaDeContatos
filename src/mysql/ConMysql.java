/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fellipe
 */
public class ConMysql {

    //Declaração dos atributos da classe
    public Connection conn = null;
    public String connURL = null;
    public String userName = null;
    public String password = null;
    public String dbName = null;

    public ResultSet rs = null;
    public Statement stmt = null;

    /*
     Construtor da Classe
     */
    public ConMysql() {
        connURL = "jdbc:mysql://localholst:3306/";
        userName = "root";
        password = "fatec";
        dbName = "bd_fatec";

    }

    public ConMysql(String url, String port, String user, String password, String dbName) {
        this.connURL = "jdbc:mysql://" + url + ";" + port + "/";
        this.userName = user;
        this.password = password;
        this.dbName = dbName;
    }

    /*
     Abre uma conexão com o S.G.B.D.
     */
    public void abreConexao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConMysql.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            conn = DriverManager.getConnection(this.connURL + this.dbName, this.userName, this.password);
        } catch (SQLException ex) {
            Logger.getLogger(ConMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     Faz as operações insert, delete e update no banco de dados
     */
    public boolean execSql(String sql) {
        try {
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.commit();
            conn.setAutoCommit(true);

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /*
     Faz as pesquisas select no banco de dados
     */
    //select * from nome_da_tabela;
    public void selectSql(String sql) {
        rs = null;
        try {
            conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ConMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ConMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     Fecha a conexão com o S.G.B.D.
     */
    public void fechaConexao() {

        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
