# API-VOTOS-COOPERATIVA-BackJava-FrontReact
O projeto API-VOTOS-COOPERATIVA foi desenvolvido com o objetivo de gerenciar sessões de votação no contexto do cooperativismo, onde cada associado possui um voto e as decisões são tomadas em assembleias. A solução foi desenvolvida para ser executada na nuvem e oferece uma série de funcionalidades através de uma API REST.

A API permite o cadastro de novas pautas e a abertura de uma sessão de votação para cada pauta. Cada sessão de votação pode ficar aberta por um tempo pré-determinado, que é definido na chamada de abertura, ou por padrão, por um minuto. Os associados podem registrar seus votos em cada pauta - apenas as opções 'Sim'/'Não' são permitidas. Cada associado é identificado por um ID único e só pode votar uma vez por pauta.

A API também contabiliza os votos e fornece o resultado da votação em cada pauta. Para simplificar o projeto, a segurança das interfaces foi abstraída, o que significa que qualquer chamada para as interfaces pode ser considerada autorizada.

Importante ressaltar que as pautas e os votos são persistidos, garantindo que eles não sejam perdidos no caso de uma reinicialização da aplicação. A escolha da linguagem de programação, frameworks e bibliotecas para a implementação do projeto foi deixada livre, desde que não infrinjam direitos de uso.

Além da implementação da API, também foi desenvolvido um frontend em React para interagir com esta API, proporcionando uma interface de usuário amigável para manipulação e visualização das pautas e votações. Esse front-end permitiria que os usuários acessassem as funcionalidades de maneira intuitiva e visual, ampliando a usabilidade da aplicação.
