// src/Tabs.js

import React, { useState } from 'react';
import CreatePauta from './CreatePauta';
import OpenVotingSession from './OpenVotingSession';
import CastVote from './CastVote';
import VotingResults from './VotingResults'; // Vamos criar esse componente em breve

const Tabs = () => {
    const [activeTab, setActiveTab] = useState('createPauta');

    return (
        <div>
            <div className="tabs">
                <button onClick={() => setActiveTab('createPauta')}>Criar Pauta</button>
                <button onClick={() => setActiveTab('openVotingSession')}>Abrir Sessão de Votação</button>
                <button onClick={() => setActiveTab('castVote')}>Votar</button>
                <button onClick={() => setActiveTab('votingResults')}>Resultados da Votação</button>
            </div>

            <div className="tab-content">
                {activeTab === 'createPauta' && <CreatePauta />}
                {activeTab === 'openVotingSession' && <OpenVotingSession />}
                {activeTab === 'castVote' && <CastVote />}
                {activeTab === 'votingResults' && <VotingResults />}
            </div>
        </div>
    );
};

export default Tabs;
