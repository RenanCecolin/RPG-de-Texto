public class Mago extends Personagem {
    private int mana;
    private int custoFeitico;
    private int poderMagico;

    public Mago() { 
        this("Mago", 80, 8, 5, 1, 100, 20, 10); 
    }
    
    public Mago(String nome) { 
        this(nome, 80, 8, 5, 1, 100, 20, 10); 
    }
    
    public Mago(String nome, int pontosVida, int ataque, int defesa, int nivel, int mana, int poderMagico, int custoFeitico) {
        super(nome, pontosVida, ataque, defesa, nivel);
        this.mana = mana; this.poderMagico = poderMagico; this.custoFeitico = custoFeitico;
    }
    
    public Mago(Mago outro) {
        this(outro.getNome(), outro.getHP(), outro.getAtaque(), outro.getDefesa(), outro.getNivel(), outro.mana, outro.poderMagico, outro.custoFeitico);
        setInventario(new Inventario(outro.getInventario()));
    }
    
    // Getters e Setters
    public int getMana() { 
        return mana; 
    }
    
    public void setMana(int mana) { 
        this.mana = mana; 
    }
    
    public int getPoderMagico() { 
        return poderMagico; 
    }
    
    public void setPoderMagico(int poderMagico) { 
        this.poderMagico = poderMagico; 
    }
    
    public int getCustoFeitico() { 
        return custoFeitico; 
    }
    
    public void setCustoFeitico(int custoFeitico) { 
        this.custoFeitico = custoFeitico; 
        
    }
    
    @Override
    public void atacar(Personagem alvo) {
        if (mana < custoFeitico) {
            System.out.println(getNome() + " tentou lançar magia, mas está sem mana!");
            return;
        }
        mana -= custoFeitico;
        int dano = getAtaque() + poderMagico;
        System.out.println(getNome() + " lança uma bola de fogo causando " + dano + " de dano!");
        alvo.receberDano(dano);
    }
}
