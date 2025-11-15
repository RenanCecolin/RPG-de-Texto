public abstract class Personagem {
    private String nome;
    private int HP;
    private int ataque;
    private int defesa;
    private int nivel;
    private int experiencia; 
    private Inventario inventario;
    
    // Construtor padrão (valores neutros)
    public Personagem() {
        this("SemNome", 50, 5, 5, 1);
    }
    
    public Personagem(String nome) {
        this(nome, 50, 5, 5, 1);
    }
    
    public Personagem(String nome, int HP, int ataque, int defesa, int nivel){
        setNome(nome);
        setHP(HP);
        setAtaque(ataque);
        setDefesa(defesa);
        setNivel(nivel);
        setExperiencia(0); // XP inicia em zero
        setInventario(new Inventario());
    }
    
    public Personagem(Personagem outro) {
        this( outro.nome, outro.HP, outro.ataque, outro.defesa, outro.nivel );
        this.experiencia = outro.experiencia; // copia XP
        this.inventario = new Inventario(outro.inventario);
    }

    public String getNome() { 
        return nome; 
        
    }
    
    public void setNome(String nome) { 
        this.nome = nome; 
        
    }
    
    public int getHP() { 
        return HP; 
        
    }
    
    public void setHP(int HP) { 
        this.HP = Math.max(0, HP); 
        
    }
    
    public int getAtaque() { 
        return ataque; 
        
    }
    
    public void setAtaque(int ataque) { 
        this.ataque = ataque; 
        
    }
    
    public int getDefesa() { 
        return defesa; 
        
    }
    
    public void setDefesa(int defesa) { 
        this.defesa = defesa; 
        
    }
    
    public int getNivel() { 
        return nivel; 
        
    }
    
    public void setNivel(int nivel) { 
        this.nivel = nivel; 
        
    }
    
    public Inventario getInventario() { 
        return inventario; 
        
    }
    
    public void setInventario(Inventario inventario) { 
        this.inventario = inventario; 
        
    }
    
    public int getExperiencia() { 
        return experiencia; 
        
    }
    
    public void setExperiencia(int experiencia) { 
        this.experiencia = Math.max(0, experiencia); 
        
    }

    public void ganharExperiencia(int xp) {
        setExperiencia(this.experiencia + xp);
        while (experiencia >= xpParaUpar()) {
            experiencia -= xpParaUpar();
            nivelUp();
        }
    }
    
    // XP necessária para próxima evolução
    public int xpParaUpar() {
        return 20 + nivel * 10; // Escala de XP progressiva
    }
    
    // UP de nível: aumenta status!
    private void nivelUp() {
        setNivel(this.nivel + 1);
        setAtaque(this.ataque + 3); // Aumento fixo de ataque
        setDefesa(this.defesa + 2); // Aumento fixo de defesa
        setHP(this.HP + 10);        // Aumento leve do HP
        System.out.println("Você subiu para o nível " + this.nivel + "! Status aumentados!");
    }

    public abstract void atacar(Personagem alvo);
    public void receberDano(int dano) {
        int danoFinal = Math.max(0, dano - defesa);
        HP = Math.max(0, HP - danoFinal);
        System.out.println(nome + " recebeu " + danoFinal + " de dano! HP restante: " + HP);
    }
    
    public boolean estaVivo() { 
        return HP > 0; 
        
    }

    @Override
    public String toString() {
        return String.format(
            "%s (Nível %d) - HP: %d | Ataque: %d | Defesa: %d | XP: %d/%d",
            nome, nivel, HP, ataque, defesa, experiencia, xpParaUpar()
        );
    }
}