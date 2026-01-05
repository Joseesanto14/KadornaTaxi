# Fluxo de execução das telas do app

## Tela Menu (MainActivity)
Possui três (3) botões, **"Solicitação de Táxi"**, **"Viagens"** e **"Gerar Relatório"**, 
eles tem a função de redirecionar o usuário para as outras telas.

**Solicitação de Táxi** -> *SolicitacaoActivity*

**Viagens** -> *ViagensActivity*

**Gerar Relatório** -> *GerarRelatorioActivity*

## Tela Solicitação de Táxi (SolicitacaoActivity)
Tela com sete (7) campos para preenchimento de dados da viagem e um botão (**"Gerar Viagem"**) para 
criação da ficha da viagem.

Os campos da tela e seus respectivos nomes na classe Viagem são:

| Tela          | Classe        |
|---------------|---------------|
| Origem        | origem        |
| Data          | dataOrigem    |
| Hora          | horaOrigem    |
| Destino       | destino       |
| Data          | dataDestino   |
| Hora          | horaDestino   |
| Justificativa | justificativa |

Os dados inseridos nesses campos são salvos num objeto da classe Viagem após o clique do botão, 
sendo possível vê-los na tela de viagens.

## Tela Viagens (ViagensActivity)
Tela com uma lista de viagens salvas e seus respectivos detalhes, separadas por mês.
Cada item da lista pode ser expandido ao ser clicado, mostrando os detalhes das viagens do mês e um 
botão para gerar relatório.

## Tela Gerar Relatório (GerarRelatorioActivity)
Tela possuindo um campo que ao ser clicado mostra uma lista com os meses salvos e com viagens para seleção,
logo abaixo um botão para gerar relatório com base no mês selecionado.