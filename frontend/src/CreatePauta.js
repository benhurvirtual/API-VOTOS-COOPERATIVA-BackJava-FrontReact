// src/CreatePauta.js

import React, { useState } from 'react';

const CreatePauta = () => {
    const [titulo, setTitulo] = useState('');
    const [descricao, setDescricao] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();

        // Atenção para a estrutura do objeto pauta aqui
        const pauta = {
            titulo: titulo,
            descricao: descricao
        };

        fetch('http://localhost:8080/pautas', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(pauta)
        }).then(response => {
            if (response.ok) {
                alert('Pauta criada com sucesso!');
                setTitulo('');
                setDescricao('');
            } else {
                alert('Erro ao criar pauta');
            }
        });
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Criar Pauta</h2>
            <input
                type="text"
                placeholder="Título"
                value={titulo}
                onChange={(e) => setTitulo(e.target.value)}
            />
            <input
                type="text"
                placeholder="Descrição"
                value={descricao}
                onChange={(e) => setDescricao(e.target.value)}
            />
            <button type="submit">Criar</button>
        </form>
    );
};

export default CreatePauta;
