package br.com.meta.apivotoscooperativa.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Table(name = "sessao_votacao")
@Entity(name = "SessaoVotacao")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class SessaoVotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long pautaId;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataEncerramento;


    public SessaoVotacao(Long pautaId, LocalDateTime dataAbertura, Duration duracao) {
        this.pautaId = pautaId;
        this.dataAbertura = dataAbertura;
        this.dataEncerramento = dataAbertura.plus(duracao);
    }

    public Long getId() {
        return id;
    }

    public Long getPautaId() {
        return pautaId;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public LocalDateTime getDataEncerramento() {
        return dataEncerramento;
    }
}
