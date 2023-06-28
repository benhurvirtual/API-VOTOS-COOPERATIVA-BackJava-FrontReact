package br.com.meta.apivotoscooperativa.repository;

import br.com.meta.apivotoscooperativa.model.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {
    List<SessaoVotacao> findByPautaId(Long pautaId);
}

