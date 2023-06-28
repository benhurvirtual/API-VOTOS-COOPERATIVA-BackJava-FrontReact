package br.com.meta.apivotoscooperativa.model;

import br.com.meta.apivotoscooperativa.dto.DadosPauta;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "pauta")
@Entity(name = "Pauta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;



    public Pauta(DadosPauta dados) {
        this.id = dados.id();
        this.titulo = dados.titulo();
        this.descricao = dados.descricao();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }
}
