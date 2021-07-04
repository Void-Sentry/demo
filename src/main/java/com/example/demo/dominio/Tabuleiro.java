package com.example.demo.dominio;

import jdk.jfr.DataAmount;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tabuleiro {

    Integer ID;
    String nome;
    int valor;
    String descrição;
    int quantidade;
    String classificação;

}
