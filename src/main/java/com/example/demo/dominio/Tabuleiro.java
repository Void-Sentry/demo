package com.example.demo.dominio;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tabuleiro {
    Integer id;
    String nome;
    int valor;
    int quantidade;
    String descricao;
    String classificacao;

}
