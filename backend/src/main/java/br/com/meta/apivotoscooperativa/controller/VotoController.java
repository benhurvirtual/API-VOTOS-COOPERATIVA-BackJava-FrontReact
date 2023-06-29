package br.com.meta.apivotoscooperativa.controller;

import br.com.meta.apivotoscooperativa.model.Pauta;
import br.com.meta.apivotoscooperativa.model.SessaoVotacao;
import br.com.meta.apivotoscooperativa.model.Voto;
import br.com.meta.apivotoscooperativa.repository.PautaRepository;
import br.com.meta.apivotoscooperativa.repository.SessaoVotacaoRepository;
import br.com.meta.apivotoscooperativa.repository.VotoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/votos")
@CrossOrigin(origins = "*")
public class VotoController {

    private final VotoRepository votoRepository;
    private final PautaRepository pautaRepository;
    private final SessaoVotacaoRepository sessaoVotacaoRepository;

    private final String EXTERNAL_SERVICE_URL = "https://api.nfse.io/validate/NaturalPeople/taxNumber/";

    @Autowired
    public VotoController(VotoRepository votoRepository, PautaRepository pautaRepository, SessaoVotacaoRepository sessaoVotacaoRepository) {
        this.votoRepository = votoRepository;
        this.pautaRepository = pautaRepository;
        this.sessaoVotacaoRepository = sessaoVotacaoRepository;
    }

    @PostMapping
    public ResponseEntity<String> registrarVoto(@RequestParam Long pautaId, @RequestParam Long associadoId, @RequestParam boolean voto) {

        Optional<Pauta> pautaOptional = pautaRepository.findById(pautaId);
        if (pautaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pauta não encontrada");
        }

        List<SessaoVotacao> sessoes = sessaoVotacaoRepository.findByPautaId(pautaId);
        if (sessoes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não há sessão criada para esta pauta.");
        }

        LocalDateTime now = LocalDateTime.now();
        boolean sessaoAberta = false;
        for (SessaoVotacao sessao : sessoes) {
            if (now.isAfter(sessao.getDataAbertura()) && now.isBefore(sessao.getDataEncerramento())) {
                sessaoAberta = true;
                break;
            }
        }

        if (!sessaoAberta) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A sessão expirou.");
        }

        boolean associadoJaVotou = votoRepository.existsByPautaIdAndAssociadoId(pautaId, associadoId);
        if (associadoJaVotou) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Associado já votou nesta pauta");
        }

        if (!verificarElegibilidadeAssociado(associadoId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Associado não é elegível para votar");
        }

        Voto votoNovo = new Voto(pautaId, associadoId, voto);
        votoRepository.save(votoNovo);

        return ResponseEntity.status(HttpStatus.OK).body("Voto registrado com sucesso");
    }


    // Novo método para listar todas as pautas
    @GetMapping("/pautas")
    public ResponseEntity<List<Pauta>> listarPautas() {
        List<Pauta> pautas = pautaRepository.findAll();
        return ResponseEntity.ok(pautas);
    }

    private boolean verificarElegibilidadeAssociado(Long associadoId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = EXTERNAL_SERVICE_URL + associadoId;

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode responseJson = objectMapper.readTree(response.getBody());
                boolean valid = responseJson.get("valid").asBoolean();
                boolean exists = responseJson.get("exists").asBoolean();

                if (valid && exists) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
