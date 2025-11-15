import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Historia {
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final Random rand = new Random();

    public void iniciar(Personagem jogador) throws IOException {
        System.out.println("O vento sopra entre as ruínas de Eldoria...");
        System.out.println("Herói " + jogador.getNome() + ", sua jornada começa agora!\n");
        if (!fase1(jogador)) return;
        if (!fase2(jogador)) return;
        if (!fase3(jogador)) return;
    }

    // Fases agora retornam boolean: true se passou, false se morreu/fugiu
    private boolean fase1(Personagem jogador) throws IOException {
        System.out.println("=== FASE 1: A FLORESTA SOMBRIA ===");
        System.out.println("A névoa cobre as árvores, e passos rápidos se aproximam...\n");
        Inimigo goblin = new Inimigo("Goblin Errante", "Goblin", 60, 10, 3, 1, 15);
        if (!combateComChefao(jogador, goblin, true)) return false;
        System.out.println("O Goblin Errante foi derrotado!");
        jogador.ganharExperiencia(goblin.getExperienciaConcedida());
        encontrarItem(jogador);
        System.out.println("Fase 1 concluída.\n");
        return true;
    }

    private boolean fase2(Personagem jogador) throws IOException {
        System.out.println("=== FASE 2: AS RUÍNAS ANTIGAS ===");
        System.out.println("As paredes antigas sussurram ecos de batalhas passadas...\n");
        Inimigo espectro = new Inimigo("Espectro das Ruínas", "Fantasma", 85, 14, 5, 2, 25);
        if (!combateComChefao(jogador, espectro, true)) return false;
        System.out.println("Você purificou o Espectro!");
        jogador.ganharExperiencia(espectro.getExperienciaConcedida());
        encontrarItem(jogador);
        System.out.println("Fase 2 concluída.\n");
        return true;
    }

    private boolean fase3(Personagem jogador) throws IOException {
        System.out.println("=== FASE 3: O TRONO DO CAOS ===");
        System.out.println("O castelo em ruínas estremece com sua presença...");
        System.out.println("Diante de você está o temido Rei das Sombras!\n");
        Inimigo rei = new Inimigo("Rei das Sombras", "Chefe Final", 120, 18, 8, 3, 50);
        if (!combateComChefao(jogador, rei, true)) return false;
        System.out.println("O Rei das Sombras cai diante de você!");
        jogador.ganharExperiencia(rei.getExperienciaConcedida());
        System.out.println("PARABÉNS, HERÓI! VOCÊ RESTAUROU A LUZ DE ELDORIA!");
        return true;
    }

    // Agora retorna true se venceu, false se fugiu ou morreu!
    private boolean combateComChefao(Personagem jogador, Inimigo inimigo, boolean ehChefao) throws IOException {
        System.out.println("\n⚔ Início do combate contra " + inimigo.getNome() + "!\n");
        while (jogador.estaVivo() && inimigo.estaVivo()) {
            System.out.println("Ações:");
            System.out.println("1) Atacar");
            System.out.println("2) Fugir");
            System.out.println("3) Ver status");
            System.out.println("4) Usar item");
            int escolha = lerInteiroEntre(1, 4);
            switch (escolha) {
                case 1:
                    jogador.atacar(inimigo);
                    if (!inimigo.estaVivo())
                        break;
                    inimigo.atacar(jogador);
                    break;
                case 2:
                    if (tentarFugir()) {
                        if (ehChefao) {
                            System.out.println("O chefe era muito forte para você! Para derrotá-lo aumente seu nível para ficar mais forte e o derrote! ");
                            return false; // volta p/ menu principal imediatamente
                        }
                        return false;
                    }
                    inimigo.atacar(jogador);
                    break;
                case 3:
                    System.out.println("\n" + jogador);
                    System.out.println(inimigo + "\n");
                    continue;
                case 4:
                    usarItem(jogador);
                    continue;
            }
            if (!jogador.estaVivo()) {
                System.out.println("Você foi derrotado...");
                return false;
            }
        }
        if (!inimigo.estaVivo()) {
            System.out.println(inimigo.getNome() + " foi derrotado!\n");
            return true;
        }
        return false;
    }

    // USAR ITEM, ENCONTRAR ITEM, UTILITÁRIOS (esses permanecem iguais ao da sua versão anterior)
    // ...

    private void usarItem(Personagem jogador) throws IOException {
        Inventario inv = jogador.getInventario();
        if (inv.listarOrdenado().isEmpty()) {
            System.out.println("Seu inventário está vazio!");
            return;
        }
        System.out.println("\n=== SEU INVENTÁRIO ===");
        for (Item i : inv.listarOrdenado()) {
            System.out.println("- " + i.getNome() + " (" + i.getDescricao() + ") x" + i.getQuantidade());
        }
        System.out.print("\nDigite o nome do item que deseja usar: ");
        String nomeItem = in.readLine().trim();
        for (Item item : inv.listarOrdenado()) {
            if (item.getNome().equalsIgnoreCase(nomeItem)) {
                aplicarEfeitoItem(jogador, item);
                inv.remover(item.getNome(), 1);
                return;
            }
        }
        System.out.println("Item não encontrado.");
    }

    private void aplicarEfeitoItem(Personagem jogador, Item item) {
        switch (item.getEfeito()) {
            case CURA: jogador.setHP(jogador.getHP() + 30); System.out.println("Você usou " + item.getNome() + " e recuperou 30 de HP!"); break;
            case BUFF_ATAQUE: jogador.setAtaque(jogador.getAtaque() + 5); System.out.println("Seu ataque aumentou em 5 pontos!"); break;
            case BUFF_DEFESA: jogador.setDefesa(jogador.getDefesa() + 3); System.out.println("Sua defesa aumentou em 3 pontos!"); break;
            default: System.out.println("Nada aconteceu...");
        }
    }

    private void encontrarItem(Personagem jogador) throws IOException {
        if (rand.nextInt(100) < 70) { // 70% de chance de encontrar item
            Item item = gerarItemAleatorio();
            System.out.println("Você encontrou um item: " + item.getNome() + " (" + item.getDescricao() + ")");
            System.out.println("Deseja pegar o item?");
            System.out.println("1) Sim\n2) Não");
            int opc = lerInteiroEntre(1, 2);
            if (opc == 1) {
                jogador.getInventario().adicionar(item);
                System.out.println(item.getNome() + " foi adicionado ao inventário!");
            } else {
                System.out.println("Você deixou o item para trás.");
            }
        }
    }

    private Item gerarItemAleatorio() {
        int tipo = rand.nextInt(3);
        switch (tipo) {
            case 0: return new Item("Poção de Cura", "Restaura 30 de HP", Item.Efeito.CURA, 1);
            case 1: return new Item("Elixir de Força", "Aumenta o ataque", Item.Efeito.BUFF_ATAQUE, 1);
            default: return new Item("Escudo Antigo", "Aumenta a defesa", Item.Efeito.BUFF_DEFESA, 1);
        }
    }

    private boolean tentarFugir() {
        int rolagem = rand.nextInt(6) + 1;
        if (rolagem >= 4) {
            System.out.println("Você conseguiu escapar!");
            return true;
        } else {
            System.out.println("A fuga falhou!");
            return false;
        }
    }

    private int lerInteiroEntre(int min, int max) throws IOException {
        while (true) {
            System.out.print("Escolha (" + min + "-" + max + "): ");
            try {
                int valor = Integer.parseInt(in.readLine().trim());
                if (valor >= min && valor <= max) return valor;
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
            System.out.println("Opção inválida!");
        }
    }
}