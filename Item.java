import java.util.Objects;

public class Item implements Comparable<Item>, Cloneable {

    private String nome;
    private String descricao;

    public enum Efeito {
        CURA, BUFF_ATAQUE, BUFF_DEFESA, BUFF_CRITICO, DANO, INSTA_MANA
    }

    private Efeito efeito;
    private int quantidade;

    public Item(String nome, String descricao, Efeito efeito, int quantidade) {
        setNome(nome);
        setDescricao(descricao);
        setEfeito(efeito);
        setQuantidade(quantidade);
    }

    public Item(Item outro) {
        this(outro.nome, outro.descricao, outro.efeito, outro.quantidade);
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Efeito getEfeito() {
        return efeito;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        if (descricao == null || descricao.isBlank())
            throw new IllegalArgumentException("Descrição não pode ser nula ou vazia.");
        this.descricao = descricao;
    }

    public void setEfeito(Efeito efeito) {
        if (efeito == null)
            throw new IllegalArgumentException("Efeito não pode ser nulo.");
        this.efeito = efeito;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade < 0)
            throw new IllegalArgumentException("Quantidade não pode ser negativa.");
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Item)) return false;
        Item outro = (Item) obj;
        return nome.equalsIgnoreCase(outro.nome) &&
               efeito == outro.efeito;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome.toLowerCase(), efeito);
    }

    @Override
    public Item clone() {
        return new Item(this);
    }

    @Override
    public String toString() {
        return String.format("%s x%d (%s): %s", nome, quantidade, efeito, descricao);
    }

    @Override
    public int compareTo(Item outro) {
        int cmp = this.nome.compareToIgnoreCase(outro.nome);
        if (cmp != 0) return cmp;
        return this.efeito.compareTo(outro.efeito);
    }
}