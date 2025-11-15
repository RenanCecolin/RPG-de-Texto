# Como compilar e executar o programa?
  - javac *.java
  - java Jogo

# Descrição das classes principais:
  - Jogo: Classe principal, exibe o menu e gerencia o fluxo geral do jogo.
  - Historia: Controla as fases principais, as batalhas contra chefes e o roteiro da aventura.
  - Personagem (abstract): Base para todas as entidades jogáveis, armazena status, XP, métodos de combate e evolução.
  - Guerreiro, Mago, Arqueiro: Classes filhas de Personagem, com estilos próprios de ataque.
  - Inimigo: Extende Personagem, representa adversários comuns e chefes, define XP concedida ao ser derrotado.
  - Inventario: Lista de itens do personagem, permite adicionar/remover/usar itens.
  - Item: Modela um item consumível (poção, elixir, escudo, etc).
  - Dado: Utilitário para sorteios aleatórios.

# Decisões de design:
  - Evolução: Derrotar inimigos concede XP; ao atingir o necessário, o personagem "upa" (nível, ataque, defesa e HP crescem).
  - Explorar: Sempre que vence 1 inimigo em exploração, o personagem recebe XP e um item aleatório.
  - Chefes: Ao tentar fugir de chefes, aparece uma mensagem especial e volta ao menu – a história não avança.
  - Fluxo desacoplado: Cada fase só avança se o chefe daquela fase realmente for derrotado.
  - Inventário: Itens são consumíveis, podem melhorar status ou curar o personagem durante as batalhas.
