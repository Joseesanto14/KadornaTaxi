# Fluxo de execução das telas do app

## Tela Viagens (ViagensActivity)
Tela principal do app, possui uma lista de viagens salvas e seus respectivos detalhes, separadas por mês.
Cada item da lista pode ser expandido ao ser clicado, mostrando os detalhes das viagens do mês e um
botão para gerar relatório do respectivo mês.

Botão flutuante: Presente no canto inferior direito da tela, servindo para enviar o usuário diretamente à tela de criação de viagens.

Botão de Configuração: Presente no canto superior direito da tela, servindo para abrir a tela de configurações tem ícone de engrenagem.

Barra de pesquisa: Presente no canto superior central da tela, para filtrar viagens.

## Tela Solicitação de Táxi (SolicitacaoActivity)
Tela com campos para o preenchimento de dados para a criação de viagens.

Os campos a serem preenchidos para a criação da viagem são:
- Origem
- Data
- Hora
- Destino
- Descrição
- Km rodados
- Hora Espera
- Valor Serviço
- Motorista
- Viagem Separada (checkbox para separar viagens por tipo de serviço)

Os dados inseridos nesses campos são salvos num objeto da classe Viagem após o clique do botão, 
sendo possível vê-los na tela de viagens.

## Tela de Configurações (ConfiguracaoActiviy)
Se o usuário tentar criar uma viagem sem configurar o app, ele será enviado para a tela de configurações, onde preencherá informações referentes a:
- Valor por quilometro rodado
- Valor por hora de espera
- Motorista padrão
- Nome dado as viagens separadas pela checkbox na tela de criação de viagens.