import React, { useState, useEffect } from 'react';

const CastVote = () => {
    const [pautas, setPautas] = useState([]);
    const [selectedPauta, setSelectedPauta] = useState(null);
    const [associadoId, setAssociadoId] = useState('');
    const [voto, setVoto] = useState('');
    const [message, setMessage] = useState(null);

    useEffect(() => {
        fetch('http://localhost:8080/pautas')
            .then(response => response.json())
            .then(data => setPautas(data))
            .catch(error => console.error('Error fetching pautas:', error));
    }, []);

    const handleCastVote = () => {
        if (selectedPauta && associadoId && voto) {
            fetch(`http://localhost:8080/votos?pautaId=${selectedPauta}&associadoId=${associadoId}&voto=${voto}`, { method: 'POST' })
                .then(response => {
                    if (response.status === 200) {
                        return response.text();
                    } else {
                        return response.text().then(text => {
                            throw new Error(text);
                        });
                    }
                })
                .then(data => {
                    setMessage('Voto registrado com sucesso!');
                })
                .catch(error => {
                    setMessage(error.message);
                });
        }
    };

    return (
        <div>
            <h2>Votar</h2>
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
                CPF do Associado:
                <input type="text" value={associadoId} onChange={(e) => setAssociadoId(e.target.value)} />
            </label>
            <label>
                Voto (Sim/Não):
                <select value={voto} onChange={(e) => setVoto(e.target.value)}>
                    <option value="">Selecione...</option>
                    <option value="true">Sim</option>
                    <option value="false">Não</option>
                </select>
            </label>
            <button onClick={handleCastVote}>Votar</button>
            {message && (
                <div>
                    <p>{message}</p>
                </div>
            )}
        </div>
    );
};

export default CastVote;
