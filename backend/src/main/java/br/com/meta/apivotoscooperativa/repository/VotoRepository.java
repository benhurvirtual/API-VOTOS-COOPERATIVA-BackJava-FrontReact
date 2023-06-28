package br.com.meta.apivotoscooperativa.repository;

import br.com.meta.apivotoscooperativa.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface VotoRepository extends JpaRepository<Voto, Long> {
    boolean existsByPautaIdAndAssociadoId(Long pautaId, Long associadoId);

    List<Voto> findByPautaId(Long pautaId);

}
