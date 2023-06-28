package br.com.meta.apivotoscooperativa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long pautaId;
    private Long associadoId;
    private boolean voto;

    public Voto() {
    }

    public Voto(Long pautaId, Long associadoId, boolean voto) {
        this.pautaId = pautaId;
        this.associadoId = associadoId;
        this.voto = voto;
    }

    public Long getId() {
        return id;
    }

    public Long getPautaId() {
        return pautaId;
    }

    public Long getAssociadoId() {
        return associadoId;
    }

    public boolean isVoto() {
        return voto;
    }
}