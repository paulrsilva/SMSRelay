/*
 * Conectar.java
 *
 * Created on 17 de Mar�o de 2006, 11:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package smsrelay;

import java.io.Serializable;
import java.sql.*;
import javax.swing.JOptionPane;
import java.util.*;

/**
 *
 * @author Paulino
 */
public class Conectar implements Serializable {
    //private boolean conectado;
    public static boolean conectado; //para o caso de querer usar o valor conectado em outra classe
    public static boolean ConexaoLocal; //para alternar entre BD local e externo
    //private static boolean conectado;
    public static boolean Proxy;
  
    private ClassLoader classLoader;  //implementacao do classloader para o WebStart
    Connection conn = null;
    private String userName=null;
    private String password=null;
    private String url=null;    
    
    private Driver driver;
    
    String driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    Class driverClass;

    /*
     * Verificar implementacaoo de leitura do MySQL e MSSQL para JavaWebStart
     */
    
    //Cria nova instancia de Conectar
    public Conectar(String username, String password, String url, String driverClassName){
        this.userName=username;
        this.password=password;
        this.url=url;
        this.driverClassName=driverClassName;
    }
    
        
    protected ClassLoader getClassLoader() {
        if (classLoader == null) {
        classLoader = this.getClass().getClassLoader();
        }
        return classLoader;
    }
    
    /** Creates a new instance of Conectar */
    public Conectar() {
        conectado=false;
    }
    
    public String tipoConexao(){
        if (ConexaoLocal){
           return "LOCAL"; 
        } else {
           return "REMOTA";  
        } 
    }    
    
    public String ProxyAtivo(){
        //return Proxy;
        if (Proxy){
            return "Proxy Ativo";
        } else{
            return "";
        }
    }
    
    public boolean isConectado(){
        return conectado;
    }
    
    Driver getDriver()  {
        try {
            if (driver == null) {
                Class driverClass = Class.forName(driverClassName, true,
                Conectar.class.getClassLoader());
                driver = (Driver) driverClass.newInstance();
            }
        }catch (Exception e) {
 
        }
        return driver;
    }
    
    
    
    
    public void Conecte(){
       
       //Connection conn = null;
        
        /*
         *Rever configura��es de Proxy
         */
        
        if (Proxy){
            System.setProperty("proxySet", "true" );
            System.setProperty("http.proxyHost", "172.19.1.1"); //configura��o de proxy
            System.setProperty("http.proxyPort", "3128");     //configura��o de proxy
            System.out.println("Proxy Ativado");
        }
        
           try
           {         
              if (ConexaoLocal) {
                   userName = "sa";
                   password = "asfadas@2014";
                   url = "jdbc:sqlserver://br.urbicell.com:1433;"+"databaseName=messages;user=sa;password=asfadas@2014";  
              } else {
                   userName = "sa";
                   password = "asfadas@2014";
                   url = "jdbc:sqlserver://br.urbicell.com:1433";   
              }
               
               //driverClass=Class.forName("com.mysql.jdbc.Driver",true,Database.class.getClassLoader());
               //driver = (Driver) driverClass.newInstance();
               
                driverClass=Class.forName(driverClassName,true,Conectar.class.getClassLoader());
                driver = (Driver) driverClass.newInstance();
                Properties connectProperties = new Properties();
                connectProperties.put("user",userName);
                connectProperties.put("password",password);
                conn = getDriver().connect(url,connectProperties);
                //conn = DriverManager.getConnection (url, connectProperties);
                conectado=true;
                
                
                /* 
                 * Conexao original - Nao rola no JavaWebStart
                 * Class.forName ("com.mysql.jdbc.Driver").newInstance();
                 * conn = DriverManager.getConnection (url, userName, password);
                 * System.out.println ("Conexao efetuada com sucesso");
                 * conectado=true; 
               */

           }
           catch (Exception e){
                //System.err.println ("hahahaha... Nao conecta no server");
                JOptionPane.showMessageDialog(null, "Problema de Conexao: "+e, "FALHA DE ACESSO AO BD",JOptionPane.WARNING_MESSAGE);
           }

    }
    
    public void Desconecte(){
        
        if (conn !=null){
            try {
                conn.close();
                System.out.println ("Conexao fechada");
                conectado=false;
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println ("Erro na Desconex�o: "+ex);
            }             
        } else {
            System.out.println("A conex�o j� estava fechada.");
        }
        this.conectado=false;
        
    }
    
    public void MostraColecao(){
        
        Collection c=new HashSet();
        c.add("BancoUm");
        c.add("BancoDois");
        c.add("BancoTres");
        c.add("BancoQuatro");
        
        Iterator i=c.iterator();
               
        while (i.hasNext()){
            
            System.out.println(i.next());
        }
            
    }
    
    
}
