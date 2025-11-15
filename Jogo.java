import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Jogo {
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final Random rand = new Random();

    public static void main(String[] args) throws Exception {
        System.out.println("===== BEM-VINDO AO MUNDO DE ELDORIA =====\n");
        Personagem jogador = criarPersonagem();
        Historia historia = new Historia();
        boolean jogando = true;
        while (jogando && jogador.estaVivo()) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1) Iniciar história");
            System.out.println("2) Explorar");
            System.out.println("3) Ver status");
            System.out.println("4) Usar item");
            System.out.println("5) Sair do jogo");
            int opc = lerInteiroEntre(1, 5);
            switch (opc) {
                case 1: historia.iniciar(jogador); break;
                case 2: explorar(jogador); break;
                case 3: System.out.println("\n STATUS DO JOGADOR:"); System.out.println(jogador); break;
                case 4: usarItemDireto(jogador); break;
                case 5: jogando = false; System.out.println("\n Até a próxima aventura!"); break;
            }
        }
    }

    private static Personagem criarPersonagem() throws IOException {
        System.out.print("Digite o nome do seu herói: ");
        String nome = in.readLine().trim();
        System.out.println("\nEscolha sua classe:");
        System.out.println("1) Guerreiro");
        System.out.println("2) Mago");
        System.out.println("3) Arqueiro");
        int opc = lerInteiroEntre(1, 3);
        switch (opc) {
            case 1: return new Guerreiro(nome);
            case 2: return new Mago(nome);
            default: return new Arqueiro(nome);
        }
    }
    
    private static void explorar(Personagem jogador) throws IOException {
        System.out.println("\n Você adentra uma área desconhecida...");
        int evento = rand.nextInt(100);
        if (evento < 45) {
            encontrarInimigo(jogador);
        } else if (evento < 80) {
            encontrarItem(jogador);
        } else {
            System.out.println(" Nada aconteceu... mas você sente que algo te observa.");
        }
    }
    
    private static void encontrarInimigo(Personagem jogador) throws IOException {
        Inimigo ini = gerarInimigoAleatorio();
        System.out.println("\n Você encontrou um inimigo: " + ini.getNome());
        combateLivre(jogador, ini);
    }
    
    private static Inimigo gerarInimigoAleatorio() {
        int tipo = rand.nextInt(3);
        switch (tipo) {
            case 0: return new Inimigo("Lobo Faminto", "Fera", 50, 8, 2, 1, 12);
            case 1: return new Inimigo("Bandido", "Humano", 70, 10, 4, 2, 20);
            default: return new Inimigo("Slime Verde", "Monstro", 40, 6, 1, 0, 8);
        }
    }
    
    private static void combateLivre(Personagem jogador, Inimigo inimigo) throws IOException {
        System.out.println("\n⚔ Combate iniciado!");
        while (jogador.estaVivo() && inimigo.estaVivo()) {
            System.out.println("\nAções:");
            System.out.println("1) Atacar");
            System.out.println("2) Fugir");
            System.out.println("3) Ver status");
            System.out.println("4) Usar item");
            int escolha = lerInteiroEntre(1, 4);
            switch (escolha) {
                case 1:
                    jogador.atacar(inimigo);
                    if (!inimigo.estaVivo()) break;
                    inimigo.atacar(jogador);
                    break;
                case 2:
                    if (rand.nextInt(6) + 1 >= 4) {
                        System.out.println(" Você escapou!");
                        return;
                    } else {
                        System.out.println(" Falha na fuga!");
                        inimigo.atacar(jogador);
                    }
                    break;
                case 3:
                    System.out.println("\n" + jogador);
                    System.out.println(inimigo);
                    continue;
                case 4:
                    usarItemDireto(jogador);
                    continue;
            }
        }
        if (!jogador.estaVivo()) {
            System.out.println(" Você foi derrotado...");
        } else {
            System.out.println(inimigo.getNome() + " foi derrotado!");
            jogador.ganharExperiencia(inimigo.getExperienciaConcedida()); // Ganha XP
            encontrarItem(jogador);
        }
    }
    
    private static void encontrarItem(Personagem jogador) {
        Item item;
        int t = rand.nextInt(3);
        if (t == 0) item = new Item("Poção de Cura", "Restaura 30 HP", Item.Efeito.CURA, 1);
        else if (t == 1) item = new Item("Elixir de Força", "Aumenta ataque", Item.Efeito.BUFF_ATAQUE, 1);
        else item = new Item("Escudo Rústico", "Aumenta defesa", Item.Efeito.BUFF_DEFESA, 1);
        System.out.println("\n Você encontrou: " + item.getNome());
        jogador.getInventario().adicionar(item);
    }
    
    private static void usarItemDireto(Personagem jogador) throws IOException {
        Inventario inv = jogador.getInventario();
        if (inv.listarOrdenado().isEmpty()) {
            System.out.println("Inventário vazio!");
            return;
        }
        System.out.println("\n=== INVENTÁRIO ===");
        for (Item i : inv.listarOrdenado()) {
            System.out.println("- " + i.getNome() + " (x" + i.getQuantidade() + ")");
        }
        System.out.print("Digite o nome do item: ");
        String nome = in.readLine().trim();
        for (Item item : inv.listarOrdenado()) {
            if (item.getNome().equalsIgnoreCase(nome)) {
                aplicarEfeito(jogador, item);
                inv.remover(item.getNome(), 1); return;
            }
        }
        System.out.println("Item não encontrado!");
    }
    
    private static void aplicarEfeito(Personagem jogador, Item item) {
        switch (item.getEfeito()) {
            case CURA: jogador.setHP(jogador.getHP() + 30); System.out.println(" Você recuperou 30 HP!"); break;
            case BUFF_ATAQUE: jogador.setAtaque(jogador.getAtaque() + 5); System.out.println(" Seu ataque aumentou!"); break;
            case BUFF_DEFESA: jogador.setDefesa(jogador.getDefesa() + 3); System.out.println(" Sua defesa aumentou!"); break;
            default: System.out.println("Nada aconteceu...");
        }
    }
    
    private static int lerInteiroEntre(int min, int max) throws IOException {
        while (true) {
            try {
                System.out.print("Escolha (" + min + "-" + max + "): ");
                int v = Integer.parseInt(in.readLine().trim());
                if (v >= min && v <= max) return v;
            } catch (NumberFormatException ignore) { }
            System.out.println("Opção inválida!");
        }
    }
}
