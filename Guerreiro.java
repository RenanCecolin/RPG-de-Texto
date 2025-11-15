import java.util.Random;
public class Guerreiro extends Personagem {
    private double chanceCritico;
    private Random random = new Random();
    
    public Guerreiro() { 
        this("Guerreiro", 120, 12, 8, 1, 0.25); 
        
    }
    
    public Guerreiro(String nome) { 
        this(nome, 120, 12, 8, 1, 0.25); 
        
    }
    
    public Guerreiro(String nome, int pontosVida, int ataque, int defesa, int nivel, double chanceCritico) {
        super(nome, pontosVida, ataque, defesa, nivel);
        this.chanceCritico = chanceCritico;
    }
    
    //Construtor
    public Guerreiro(Guerreiro outro) {
        this(outro.getNome(), outro.getHP(), outro.getAtaque(), outro.getDefesa(), outro.getNivel(), outro.chanceCritico);
        setInventario(new Inventario(outro.getInventario()));
    }
    
    @Override
    public void atacar(Personagem alvo) {
        int dano = getAtaque();
        if (random.nextDouble() < chanceCritico) {
            dano *= 2;
            System.out.println(getNome() + " realiza um GOLPE CRÍTICO!");
        }
        System.out.println(getNome() + " ataca com sua espada causando " + dano + " de dano!");
        alvo.receberDano(dano);
    }
}