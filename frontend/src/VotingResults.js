import React, { useState, useEffect } from 'react';

const VotingResults = () => {
    const [pautas, setPautas] = useState([]);
    const [selectedPautaId, setSelectedPautaId] = useState(null);
    const [votingResults, setVotingResults] = useState(null);
    const [votingSessions, setVotingSessions] = useState([]);
    const [pautaDescription, setPautaDescription] = useState(null);

    useEffect(() => {
        fetch('http://localhost:8080/pautas')
            .then(response => response.json())
            .then(data => setPautas(data));
    }, []);

    const handlePautaChange = (event) => {
        const pautaId = event.target.value;
        setSelectedPautaId(pautaId);

        fetch(`http://localhost:8080/pautas/${pautaId}/resultado`)
            .then(response => response.text())
            .then(data => setVotingResults(data));

        fetch(`http://localhost:8080/sessoes-votacao?pautaId=${pautaId}`)
            .then(response => response.json())
            .then(data => setVotingSessions(data));

        const selectedPauta = pautas.find(pauta => pauta.id === pautaId);
        if (selectedPauta) {
            setPautaDescription(selectedPauta.descricao);
        }
    };

    return (
        <div>
            <h2>Resultados da Votação</h2>
            <label>Selecione uma Pauta: </label>
            <select onChange={handlePautaChange}>
                <option value="">Selecione...</option>
                {pautas.map(pauta => (
                    <option key={pauta.id} value={pauta.id}>{pauta.titulo}</option>
                ))}
            </select>

            {pautaDescription && (
                <div>
                    <h3>Descrição da Pauta:</h3>
                    <p>{pautaDescription}</p>
                </div>
            )}

            {votingResults && (
                <div>
                    <h3>Resultados:</h3>
                    <pre>{votingResults}</pre>
                </div>
            )}

            {votingSessions.length > 0 && (
                <div>
                    <h3>Sessões de Votação:</h3>
                    <ul>
                        {votingSessions.map(session => {
                            // Calcula a duração em minutos
                            const duracaoMinutos = (new Date(session.dataEncerramento) - new Date(session.dataAbertura)) / 60000;
                            return (
                                <li key={session.id}>
                                    Sessão ID: {session.id}, Data de Abertura: {new Date(session.dataAbertura).toLocaleString()}, Duração: {duracaoMinutos} minutos
                                </li>
                            );
                        })}
                    </ul>
                </div>
            )}
        </div>
    );
};

export default VotingResults;
