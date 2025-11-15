import java.util.Random;
public class Inimigo extends Personagem {
    private String tipo;
    private int experienciaConcedida;
    private Random random = new Random();

    public Inimigo() { 
        this("Inimigo", "Genérico", 50, 8, 4, 1, 20); 
        
    }
    
    public Inimigo(String nome, String tipo) { 
        this(nome, tipo, 50, 8, 4, 1, 20); 
        
    }
    
    public Inimigo(String nome, String tipo, int HP, int ataque, int defesa, int nivel, int experienciaConcedida) {
        super(nome, HP, ataque, defesa, nivel);
        this.tipo = tipo;
        this.experienciaConcedida = experienciaConcedida;
    }
    
    public Inimigo(Inimigo outro) {
        this(outro.getNome(), outro.tipo, outro.getHP(), outro.getAtaque(), outro.getDefesa(), outro.getNivel(), outro.experienciaConcedida);
        setInventario(new Inventario(outro.getInventario()));
    }
    
    public String getTipo() { 
        return tipo; 
        
    }
    
    public int getExperienciaConcedida() { 
        return experienciaConcedida; 
        
    }
    
    @Override
    public void atacar(Personagem alvo) {
        int dano = getAtaque() + random.nextInt(5);
        System.out.println(getNome() + " (" + tipo + ") ataca causando " + dano + " de dano!");
        alvo.receberDano(dano);
    }
    
    @Override
    public String toString() {
        return super.toString() + " | Tipo: " + tipo;
    }
}