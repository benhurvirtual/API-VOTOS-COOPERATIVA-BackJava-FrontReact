import React, { useState } from 'react';

const CreatePauta = () => {
    const [titulo, setTitulo] = useState('');
    const [descricao, setDescricao] = useState('');
    const [errors, setErrors] = useState({});

    const handleSubmit = (event) => {
        event.preventDefault();

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
                setErrors({});
            } else {
                response.json().then(data => {
                    const errorMessages = {};
                    data.forEach(error => {
                        if (!errorMessages[error.campo]) {
                            errorMessages[error.campo] = [];
                        }
                        errorMessages[error.campo].push(error.mensagem);
                    });
                    setErrors(errorMessages);
                });
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
            <div style={{ marginBottom: '10px' }}>
                {errors.titulo && errors.titulo.map((error, index) => (
                    <p key={`titulo-${index}`} style={{ color: 'red' }}>{error}</p>
                ))}
                {errors.descricao && errors.descricao.map((error, index) => (
                    <p key={`descricao-${index}`} style={{ color: 'red' }}>{error}</p>
                ))}
            </div>
            <button type="submit">Criar</button>
        </form>
    );
};

export default CreatePauta;
