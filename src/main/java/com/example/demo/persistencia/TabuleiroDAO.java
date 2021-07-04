package com.example.demo.persistencia;

import com.example.demo.dominio.Tabuleiro;
import java.sql.*;
import java.util.ArrayList;

public class TabuleiroDAO {

    private final Conexao conex;

    private final String SELECTALL = "SELECT * FROM public.tabuleiro;";

    private final String SELECT = "SELECT valor, quantidade, nome, id, descricao, classificacao\n" +
            "\tFROM public.tabuleiro;";

    private final String INSERT = "INSERT INTO public.tabuleiro(\n" +
            "\tid, nome, valor, descricao, classificacao)\n" +
            "\tVALUES (?, ?, ?, ?, ?, ?);";

    private final String CREATE = "CREATE TABLE public.tabuleiro\n" +
            "(\n" +
            "    valor integer,\n" +
            "    quantidade integer,\n" +
            "    nome text COLLATE pg_catalog.\"default\",\n" +
            "    id integer NOT NULL DEFAULT nextval('tabuleiro_id_seq'::regclass),\n" +
            "    descricao text COLLATE pg_catalog.\"default\",\n" +
            "    classificacao text COLLATE pg_catalog.\"default\",\n" +
            "    CONSTRAINT tabuleiro_pkey PRIMARY KEY (id)\n" +
            ")";

    private final String EXISTS = "SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_name = 'tabuleiro')";

    public TabuleiroDAO(){
        conex = new Conexao(System.getenv("DATABASE_HOST"), System.getenv("DATABASE_PORT"), System.getenv("DATABASE_NAME"), System.getenv("DATABASE_USERNAME"), System.getenv("DATABASE_PASSWORD"));
    }

    public boolean exists(){
        boolean existe = false;

        try{
            conex.Conectar();

            Statement st = conex.getCon().createStatement();
            ResultSet rs = st.executeQuery(EXISTS);

            if(rs.next()){
                existe = rs.getBoolean("exists");
            }

            conex.Desconectar();
        }
        catch(SQLException e){
            System.out.println("Erro na criação da tabela: " + e.getMessage());
        }

        return existe;
    }

    public Tabuleiro select(int id){
        Tabuleiro t = null;

        try{
            conex.Conectar();
            PreparedStatement st = conex.getCon().prepareStatement(SELECT);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if(rs.next()){
                t = new Tabuleiro(rs.getInt("id"), rs.getString("nome"), rs.getInt("valor"), rs.getString("descricao"), rs.getInt("quantidade"), rs.getString("classificacao"));
            }
            conex.Desconectar();
        }
        catch (SQLException e){
            System.out.println("Erro na busca:" + e.getMessage());
        }

        return t;
    }

    public ArrayList<Tabuleiro> selectALL(){

        ArrayList<Tabuleiro> tabuleiros = new ArrayList<>();

        try{
            conex.Conectar();
            Statement st = conex.getCon().createStatement();
            ResultSet rs = st.executeQuery(SELECTALL);

            while(rs.next()){
                tabuleiros.add(new Tabuleiro(rs.getInt("id"), rs.getString("nome"), rs.getInt("valor"), rs.getString("descricao"), rs.getInt("quantidade"), rs.getString("classificacao")));
            }
        }
        catch(SQLException e){
            System.out.println("Erro no relatorio:" + e.getMessage());
        }

        return tabuleiros;
    }

    public void create(){
        try{
            conex.Conectar();

            Statement st = conex.getCon().createStatement();
            st.execute(CREATE);

            conex.Desconectar();
        }
        catch (SQLException e){
            System.out.println("Erro na criação da tabela" + e.getMessage());
        }

    }

    public void insert(Tabuleiro p){
        try{
            conex.Conectar();

            PreparedStatement ps = conex.getCon().prepareStatement(INSERT);
            ps.setInt(1, p.getID());
            ps.setString(2, p.getNome());
            ps.setInt(3, p.getValor());
            ps.setString(4, p.getDescrição());
            ps.setInt(5, p.getQuantidade());
            ps.setString(5, p.getClassificação());
            ps.execute();
        }
        catch (SQLException e){
            System.out.println("");
        }
    }

}
