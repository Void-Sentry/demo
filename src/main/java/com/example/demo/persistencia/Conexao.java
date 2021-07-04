package com.example.demo.persistencia;

import java.sql.*;

public class Conexao {

    String url;
    String usuario;
    String senha;
    Connection conex;

    public Conexao(String host, String porta, String db, String usuario, String senha){

        this.url = "jdbc:postgresql://" + host + ':' + porta + "/" + db + "?serverTimezone=UTC";
        this.usuario = usuario;
        this.senha = senha;
    }

    public void Conectar(){

        try {
            conex = DriverManager.getConnection(url, usuario, senha);
        }
        catch (Exception e){
            System.out.println("Erro ao conectar-se" + e.getMessage());
        }
    }

    public void Desconectar(){

        try{
            conex.close();
        }
        catch (Exception e){

            System.out.println("Erro ao desconectar-se!!" + e.getMessage());
        }
    }

    public Connection getCon(){
        return conex;
    }
}
