import java.util.Random;
public class Arqueiro extends Personagem {
    private double chanceAcertoDuplo;
    private Random random = new Random();
    
    public Arqueiro() { 
        this("Arqueiro", 100, 10, 6, 1, 0.20); 
    }
    
    public Arqueiro(String nome) { 
        this(nome, 100, 10, 6, 1, 0.20); 
    }
    
    public Arqueiro(String nome, int pontosVida, int ataque, int defesa, int nivel, double chanceAcertoDuplo) {
        super(nome, pontosVida, ataque, defesa, nivel);
        this.chanceAcertoDuplo = chanceAcertoDuplo;
    }
    
    public Arqueiro(Arqueiro outro) {
        this(outro.getNome(), outro.getHP(), outro.getAtaque(), outro.getDefesa(), outro.getNivel(), outro.chanceAcertoDuplo);
        setInventario(new Inventario(outro.getInventario()));
    }
    
    @Override
    public void atacar(Personagem alvo) {
        int dano = getAtaque();
        System.out.println(getNome() + " dispara uma flecha causando " + dano + " de dano!");
        alvo.receberDano(dano);
        if (random.nextDouble() < chanceAcertoDuplo && alvo.estaVivo()) {
            System.out.println(getNome() + " acerta um segundo disparo!");
            alvo.receberDano(dano / 2);
        }
    }
}