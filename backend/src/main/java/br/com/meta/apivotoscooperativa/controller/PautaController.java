package br.com.meta.apivotoscooperativa.controller;

import br.com.meta.apivotoscooperativa.dto.DadosDetalhadoPauta;
import br.com.meta.apivotoscooperativa.dto.DadosPauta;
import br.com.meta.apivotoscooperativa.model.Pauta;
import br.com.meta.apivotoscooperativa.model.Voto;
import br.com.meta.apivotoscooperativa.repository.PautaRepository;
import br.com.meta.apivotoscooperativa.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("pautas")
@CrossOrigin(origins = "*")
public class PautaController {

    @Autowired
    private PautaRepository pautaRepository;
    @Autowired
    private VotoRepository votoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosPauta dados, UriComponentsBuilder uriBuilder) {
        var pauta = new Pauta(dados);
        pautaRepository.save(pauta);

        var uri = uriBuilder.path("/pautas/{id}").buildAndExpand(pauta.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhadoPauta(pauta));
    }

    @GetMapping("/{pautaId}/resultado")
    public ResponseEntity<String> obterResultadoVotacao(@PathVariable Long pautaId) {
        Optional<Pauta> pautaOptional = pautaRepository.findById(pautaId);
        if (pautaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pauta não encontrada");
        }

        Pauta pauta = pautaOptional.get();
        List<Voto> votos = votoRepository.findByPautaId(pautaId);

        int votosSim = 0;
        int votosNao = 0;

        for (Voto voto : votos) {
            if (voto.isVoto()) {
                votosSim++;
            } else {
                votosNao++;
            }
        }

        String resultado = "Resultado da votação na pauta '" + pauta.getTitulo() + "':\n";
        resultado += "Descrição: " + pauta.getDescricao() + "\n"; // Adicionando a descrição da pauta
        resultado += "Votos 'Sim': " + votosSim + "\n";
        resultado += "Votos 'Não': " + votosNao + "\n";

        return ResponseEntity.status(HttpStatus.OK).body(resultado);
    }

    // Método adicionado para listar todas as pautas
    @GetMapping
    public ResponseEntity<List<DadosDetalhadoPauta>> listarPautas() {
        List<Pauta> pautas = pautaRepository.findAll();
        List<DadosDetalhadoPauta> dadosPautas = pautas.stream()
                .map(DadosDetalhadoPauta::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dadosPautas);
    }
}
