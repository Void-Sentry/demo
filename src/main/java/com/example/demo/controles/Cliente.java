package com.example.demo.controles;

import com.example.demo.dominio.Tabuleiro;
import com.example.demo.persistencia.TabuleiroDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequestMapping("/cliente")
public class Cliente {

    @RequestMapping
    public void cliente(HttpServletRequest request, HttpServletResponse response) throws IOException {

        TabuleiroDAO tdao = new TabuleiroDAO();

        response.getWriter().println("<h1>Cliente</h1>");
        response.getWriter().println("<table> <tr> <th>ID</th> <th>Nome</th> <th>Valor</th> <th>quantidade</th> <th>Descrição</th> <th>classificação</th> <th> </th> </tr>");

        for(Tabuleiro t : tdao.selectALL()){
            response.getWriter().println("<tr>");
            response.getWriter().println("<td>" + t.getId() + "</td>");
            response.getWriter().println("<td>" + t.getNome() + "</td>");
            response.getWriter().println("<td>" + t.getValor() + "</td>");
            response.getWriter().println("<td>" + t.getQuantidade() + "</td>");
            response.getWriter().println("<td>" + t.getDescricao() + "</td>");
            response.getWriter().println("<td>" + t.getClassificacao() + "</td>");
            response.getWriter().println("<td><a href = \'/cliente/adicionarCarrinho?id="+t.getId()+"\'>Adicionar</a></td>");
            response.getWriter().println("</tr>");
        }

        response.getWriter().println("</table>");
        response.getWriter().println("<p><a href = \'/cliente/verCarrinho\'>Ver carrinho</a></p>");

    }

    @RequestMapping("/adicionarCarrinho")
    public void adicionarCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException{

        TabuleiroDAO tdao = new TabuleiroDAO();

        HttpSession session = request.getSession();

        if(session.getAttribute("carrinho") == null){
            session.setAttribute("carrinho", new ArrayList<Tabuleiro>());
        }

        ArrayList<Tabuleiro> t = (ArrayList<Tabuleiro>)session.getAttribute("carrinho");

        t.add(tdao.select(Integer.parseInt(request.getParameter("id"))));

        response.sendRedirect("/cliente");
    }

    @RequestMapping("/verCarrinho")
    public void verCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.getWriter().println("<h1>Carrinho</h1>");

        HttpSession session = request.getSession();

        if(session.getAttribute("carrinho") != null){
            response.getWriter().println("<table> <tr> <th>ID</th> <th> nome </th> <th> valor </th> <th> quantidade </th> <th> descrição </th> <th> classificação </th> <th> </th>");
            ArrayList<Tabuleiro> listar = (ArrayList<Tabuleiro>)session.getAttribute("carrinho");

            for(Tabuleiro t : listar){
                response.getWriter().println("<tr>");
                response.getWriter().println("<td>" + t.getId() + "</td>");
                response.getWriter().println("<td>" + t.getNome() + "</td>");
                response.getWriter().println("<td>" + t.getValor() + "</td>");
                response.getWriter().println("<td>" + t.getQuantidade() + "</td>");
                response.getWriter().println("<td>" + t.getDescricao() + "</td>");
                response.getWriter().println("<td>" + t.getClassificacao() + "</td>");
                response.getWriter().println("</tr>");
            }
            response.getWriter().println("</table>");
            response.getWriter().println("<p><a href = \'/cliente/finalizarCompra\'>Finalizar Compra</a></p>");
        }
        else{
            response.sendRedirect("/cliente");
        }
    }

    @RequestMapping("/finalizarCompra")
    public void finalizarCompra(HttpServletRequest request, HttpServletResponse response) throws IOException{

        request.getSession().invalidate();
        response.sendRedirect("/");
    }
}
