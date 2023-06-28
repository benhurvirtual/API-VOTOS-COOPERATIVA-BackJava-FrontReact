import React, { useState, useEffect } from 'react';

const OpenVotingSession = () => {
  const [pautas, setPautas] = useState([]);
  const [selectedPauta, setSelectedPauta] = useState(null);
  const [duration, setDuration] = useState('');

  useEffect(() => {
    // Fetch pautas when component mounts
    fetch('http://localhost:8080/pautas')
      .then(response => response.json())
      .then(data => setPautas(data))
      .catch(error => console.error('Error fetching pautas:', error));
  }, []);

  const handleOpenSession = () => {
    if (selectedPauta && duration) {
      fetch(`http://localhost:8080/sessoes-votacao?pautaId=${selectedPauta}&duracaoMinutos=${duration}`, { method: 'POST' })
        .then(response => alert('Sessão aberta com sucesso!'))
        .catch(error => console.error('Error opening session:', error));
    }
  };

  return (
    <div>
      <h2>Abrir Sessão de Votação</h2>
      <label>
        Selecione a Pauta:
        <select value={selectedPauta} onChange={(e) => setSelectedPauta(e.target.value)}>
          <option value="">Selecione...</option>
          {pautas.map(pauta => (
            <option key={pauta.id} value={pauta.id}>{pauta.titulo}</option>
          ))}
        </select>
      </label>
      <label>
        Duração (minutos):
        <input type="number" value={duration} onChange={(e) => setDuration(e.target.value)} />
      </label>
      <button onClick={handleOpenSession}>Abrir Sessão</button>
    </div>
  );
};

export default OpenVotingSession;
