// src/App.js

import React, { useState } from 'react';
import './App.css';
import CreatePauta from './CreatePauta';
import OpenVotingSession from './OpenVotingSession';
import CastVote from './CastVote';
import VotingResults from './VotingResults';

const App = () => {
    const [activeTab, setActiveTab] = useState('createPauta');

    return (
        <div>
            <div className="tabs">
                <button onClick={() => setActiveTab('createPauta')}>Criar Pauta</button>
                <button onClick={() => setActiveTab('openSession')}>Abrir Sessão de Votação</button>
                <button onClick={() => setActiveTab('castVote')}>Votar</button>
                <button onClick={() => setActiveTab('votingResults')}>Resultados da Votação</button>
            </div>
            <div className="tab-content">
                {activeTab === 'createPauta' && <CreatePauta />}
                {activeTab === 'openSession' && <OpenVotingSession />}
                {activeTab === 'castVote' && <CastVote />}
                {activeTab === 'votingResults' && <VotingResults />}
            </div>
        </div>
    );
};

export default App;
