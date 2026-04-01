# Fluxo de execução das telas do app

## 1. Tela Viagens (ViagensActivity)
Esta é a tela principal do aplicativo, onde o motorista gerencia seu histórico de corridas.

### Funcionalidades:
- **Listagem Agrupada**: As viagens são exibidas em uma lista organizada por Mês/Ano. Cada cabeçalho de mês pode ser clicado para expandir/recolher as viagens daquele período.
- **Resumo Mensal**: Ao expandir um mês, o app exibe a somatória total de valores (KM, Espera e Serviços) daquele período.
- **Geração de Relatório**: Cada grupo mensal possui um botão para gerar um relatório profissional em PDF. O app valida se as configurações de tarifas existem antes de permitir a geração.
- **Filtragem Dinâmica**:
  - **Barra de Pesquisa**: Filtra em tempo real por Destino, Origem, Data, Motorista, Descrição ou Classificação.
  - **Filtro por Chip**: Um botão (Chip) "Separadas" permite filtrar rapidamente apenas as viagens que possuem uma classificação diferente de "Comum" (ideal para convênios).
- **Navegação**:
  - **Botão Flutuante (FAB)**: Direciona para a criação de nova viagem (valida se o app já foi configurado).
  - **Ícone de Engrenagem**: Abre a tela de configurações.

---

## 2. Tela Solicitação de Táxi (SolicitacaoActivity)
Tela dedicada ao registro de uma nova corrida, com foco em automação para agilizar o trabalho do motorista.

### Preenchimento Automático:
- **Data e Hora**: Carregadas automaticamente do sistema no momento da abertura da tela.
- **Dados Padrão**: Motorista e Classificação de Cliente são carregados das configurações salvas.

### Cálculos em Tempo Real:
- **Máscaras Inteligentes**: Campos de Data (DD/MM/AAAA), Hora (HH:MM) e Hora de Espera (HH:MM) possuem formatação automática durante a digitação.
- **Valor da Viagem**: Calculado instantaneamente multiplicando os KM rodados pela tarifa definida nas configurações.
- **Valor de Espera**: Calculado convertendo o tempo (HH:MM) em valor monetário com base na tarifa de espera.
- **Valor de Serviço**: Campo para adicionar taxas extras ou serviços manuais.
- **Valor Total**: Soma automática de (Viagem + Espera + Serviço).

### Regras de Validação:
- Todos os campos básicos (Origem, Destino, Data, Hora, Motorista) são obrigatórios.
- É necessário informar **ou** a Quilometragem **ou** um Valor de Serviço para que a viagem seja considerada válida.

---

## 3. Tela de Configurações (ConfiguracaoActivity)
Tela essencial para o funcionamento do app, onde são definidas as bases para todos os cálculos financeiros.

### Parâmetros Configuráveis:
- **Valor por KM rodado**: Base para o cálculo da viagem.
- **Valor por Hora de Espera**: Base para o cálculo do tempo parado.
- **Motorista Padrão**: Nome que aparecerá nos relatórios e será sugerido em novas solicitações.
- **Nome para Viagens Separadas**: Define o nome da categoria especial (ex: "Convênio X", "Empresa Y"). Se deixado em branco, o sistema assume "Comum".

### Regras de Segurança:
- O app impede salvar configurações com valores zerados ou campos de texto vazios.
- Caso o usuário tente usar o app sem essas definições, ele será automaticamente redirecionado para esta tela com um aviso de orientação.
