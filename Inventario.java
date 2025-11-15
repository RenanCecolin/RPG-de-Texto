import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventario implements Cloneable {

    private List<Item> itens;

    public Inventario() {
        this.itens = new ArrayList<>();
    }

    // Construtor de cópia profunda
    public Inventario(Inventario outro) {
        this.itens = new ArrayList<>();
        for (Item i : outro.itens) {
            this.itens.add(i.clone()); // Item também tem clone
        }
    }

    // Adicionar item → soma quantidade se já existir
    public void adicionar(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Item não pode ser nulo.");

        int index = itens.indexOf(item); // usa equals(nome + efeito)
        if (index >= 0) {
            Item existente = itens.get(index);
            existente.setQuantidade(existente.getQuantidade() + item.getQuantidade());
        } else {
            itens.add(item.clone()); // adiciona cópia para evitar compartilhamento
        }
    }

    // Remover quantidade → retorna true se conseguiu remover
    public boolean remover(String nome, int quantidade) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        if (quantidade <= 0)
            throw new IllegalArgumentException("Quantidade deve ser positiva.");

        for (int i = 0; i < itens.size(); i++) {
            Item atual = itens.get(i);
            if (atual.getNome().equalsIgnoreCase(nome)) {
                if (atual.getQuantidade() < quantidade) {
                    return false; // não há quantidade suficiente
                }

                atual.setQuantidade(atual.getQuantidade() - quantidade);

                if (atual.getQuantidade() == 0) {
                    itens.remove(i); // remove completamente
                }
                return true;
            }
        }
        return false; // item não encontrado
    }

    // Retorna lista ordenada via compareTo de Item
    public List<Item> listarOrdenado() {
        List<Item> copia = new ArrayList<>();
        for (Item i : itens) {
            copia.add(i.clone());
        }
        Collections.sort(copia); // usa compareTo de Item
        return copia;
    }

    @Override
    public Inventario clone() {
        return new Inventario(this); // usa construtor de cópia profunda
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Inventário:\n");
        for (Item i : itens) {
            sb.append(" - ").append(i).append("\n");
        }
        return sb.toString();
    }
}