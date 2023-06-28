package br.com.meta.apivotoscooperativa.controller;

import br.com.meta.apivotoscooperativa.model.Pauta;
import br.com.meta.apivotoscooperativa.model.SessaoVotacao;
import br.com.meta.apivotoscooperativa.repository.PautaRepository;
import br.com.meta.apivotoscooperativa.repository.SessaoVotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sessoes-votacao")
@CrossOrigin(origins = "*")
public class SessaoVotacaoController {

    private final SessaoVotacaoRepository sessaoVotacaoRepository;
    private final PautaRepository pautaRepository;

    @Autowired
    public SessaoVotacaoController(SessaoVotacaoRepository sessaoVotacaoRepository, PautaRepository pautaRepository) {
        this.sessaoVotacaoRepository = sessaoVotacaoRepository;
        this.pautaRepository = pautaRepository;
    }

    @PostMapping
    public ResponseEntity<?> abrirSessaoVotacao(
            @RequestParam Long pautaId,
            @RequestParam(required = false) Long duracaoMinutos) {

        Optional<Pauta> pautaOptional = pautaRepository.findById(pautaId);
        if (pautaOptional.isEmpty()) {
            String mensagem = "Pauta não existe";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
        }

        Pauta pauta = pautaOptional.get();
        LocalDateTime dataAbertura = LocalDateTime.now();
        Duration duracao = Duration.ofMinutes(duracaoMinutos != null ? duracaoMinutos : 1);
        SessaoVotacao sessaoVotacao = new SessaoVotacao(pautaId, dataAbertura, duracao);
        SessaoVotacao novaSessaoVotacao = sessaoVotacaoRepository.save(sessaoVotacao);

        return ResponseEntity.status(HttpStatus.CREATED).body(novaSessaoVotacao.getId());
    }

    // Adicione este método para obter sessões de votação por pautaId
    @GetMapping
    public ResponseEntity<List<SessaoVotacao>> getSessoesByPautaId(@RequestParam Long pautaId) {
        List<SessaoVotacao> sessoes = sessaoVotacaoRepository.findByPautaId(pautaId);
        return ResponseEntity.ok(sessoes);
    }
}
