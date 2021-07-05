package com.example.demo.controles;

import com.example.demo.dominio.Tabuleiro;
import com.example.demo.persistencia.TabuleiroDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/admin")
public class Admin {

    @RequestMapping
    public void admin(HttpServletRequest request, HttpServletResponse response) throws IOException {

        TabuleiroDAO tdao = new TabuleiroDAO();

        response.getWriter().println("<h1> Adm </h1>");

        if(tdao.exists()) {
            response.getWriter().println("<form method = \'POST\' action = \'/admin/cadastrar\' >");
            //response.getWriter().println("<label for = \'id\'> ID: </label> <br>");
            //response.getWriter().println("<input type = \'number\' id = \'id\' name = \'id\'><br>");
            response.getWriter().println("<label for = \'nome\'> Nome do tabuleiro: </label> <br>");
            response.getWriter().println("<input type = \'text\' id = \'nome\' name = \'nome\'><br>");
            response.getWriter().println("<label for = \'nome\'> Valor: </label> <br>");
            response.getWriter().println("<input type = \'number\' id = \'valor\' name = \'valor\'><br>");
            response.getWriter().println("<label for = \'number\'> Quantidade: </label> <br>");
            response.getWriter().println("<input type = \'text\' id = \'quantidade\' name = \'quantidade\'><br>");
            response.getWriter().println("<label for = \'nome\'> Descricao: </label> <br>");
            response.getWriter().println("<input type = \'text\' id = \'descricao\' name = \'descricao\'><br>");
            response.getWriter().println("<label for = \'nome\'> Classificação: </label> <br>");
            response.getWriter().println("<input type = \'text\' id = \'classificacao\' name = \'classificacao\'><br>");
            response.getWriter().println("<input type = \'submit\' value = \'cadastrar\'>");
            response.getWriter().println("</form>");
        }
        else{
            response.getWriter().println("<p><a href = \'/config\'></a></p>");
        }

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        Date date = new Date();
        Cookie c = new Cookie("visita", dateFormat.format(date));

        c.setMaxAge(60 * 60 * 24);

        response.addCookie(c);
    }

    @RequestMapping("/config")
    public void config(HttpServletRequest request, HttpServletResponse response) throws IOException{
        TabuleiroDAO tdao = new TabuleiroDAO();

        tdao.create();
        tdao.insert(new Tabuleiro());

        response.sendRedirect("/admin");
    }

    @RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
    public void cadastrar(HttpServletRequest request, HttpServletResponse response) throws IOException{
        TabuleiroDAO tdao = new TabuleiroDAO();
        Tabuleiro t = new Tabuleiro();

        //t.setId(Integer.parseInt(request.getParameter("id")));
        t.setNome(request.getParameter("nome"));
        t.setValor(Integer.parseInt(request.getParameter("valor")));
        t.setQuantidade(Integer.parseInt(request.getParameter("quantidade")));
        t.setDescricao(request.getParameter("descricao"));
        t.setClassificacao(request.getParameter("classificacao"));

        tdao.insert(t);

        response.sendRedirect("/admin");
    }
}
