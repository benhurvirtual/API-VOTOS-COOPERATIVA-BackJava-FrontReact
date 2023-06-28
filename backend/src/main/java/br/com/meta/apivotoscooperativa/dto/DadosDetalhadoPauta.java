package br.com.meta.apivotoscooperativa.dto;

import br.com.meta.apivotoscooperativa.model.Pauta;

public record DadosDetalhadoPauta(Long id, String titulo, String descricao) {


    public  DadosDetalhadoPauta(Pauta pauta) {
        this(pauta.getId(), pauta.getTitulo(), pauta.getDescricao());
    }

}
