# KadornaTaxi
***
## Objetivos
Projeto para registrar corridas, valores, horários e descrições para controle simples de viagens de táxi.

## Usuários
- Motorista (uso individual).

## Funcionalidades planejadas
- Registrar solicitação de táxi com cálculos automáticos.
- Visualizar dados de viagens organizados por período.
- Gerar relatórios de viagens profissionais em PDF.
- Configurações personalizadas de tarifas e motorista.

## Tecnologias utilizadas
- Android (Java e Kotlin).
- XML para Layouts.
- SQLite para persistência de dados.

---

## Estado atual do projeto

### Atualmente o projeto conta com: 

- **Configurações Completas:** Personalização dos valores de KM rodado, valor da hora de espera e dados do motorista para cálculos e cabeçalhos de relatórios.
- **Solicitação de Viagens:** Registro completo com preenchimento automático de data/hora e cálculo em tempo real de KM, espera e total.
- **Valor de Serviço:** Possibilidade de adicionar taxas ou serviços extras além da quilometragem.
- **Classificação de Viagens:** Organização inteligente entre viagens "Comuns" e "Separadas" (ideal para controle de convênios ou clientes específicos).
- **Persistência em Banco de Dados:** Gerenciamento via SQLite com suporte a migrações de versão.
- **Visualização Organizada:** Exibição das viagens agrupadas por mês/ano e por classificação de cliente.
- **Relatórios PDF Avançados:** Geração de documentos detalhados com quebra automática de texto, somatória de totais (KM, Espera, Serviço e Geral) e cabeçalho profissional.
- **Filtragem Dinâmica:** Busca por texto (origem, destino, motorista, etc.) e filtros rápidos via Chips.
- **Suporte a múltiplas páginas nos relatórios PDF:** Caso a lista de viagens seja muito extensa, o relatório quebra páginas automáticamente, criando um documento com mais de uma página

### Em desenvolvimento:
- Deletar e editar viagens.
- Transferir viagens entre empresas/clientes.
- Salvar formulário não finalizado em cache para recuperação (rascunho).

### Fluxo do app:
[Fluxo de Execução](docs/fluxo-app.md)
