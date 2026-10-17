public class TesteArvore {
    public static void main(String[] args) {
        ArvoreBinaria<Integer> arv = new ArvoreBinaria<>();

        arv.inserir(20);
        arv.inserir(10);
        arv.inserir(30);
        arv.inserir(5);
        arv.inserir(15);

        System.out.println("Árvore após inserir (tudo oculto):");
        arv.imprimir();

        // Busca de valores que existem
        System.out.println("\nBusca de valores existentes:");
        System.out.println("buscar(20): " + (arv.buscar(20) != null));
        System.out.println("buscar(10): " + (arv.buscar(10) != null));
        System.out.println("buscar(30): " + (arv.buscar(30) != null));
        System.out.println("buscar(5): "  + (arv.buscar(5) != null));
        System.out.println("buscar(15): " + (arv.buscar(15) != null));

        // Busca de valor que não existe
        System.out.println("\nBusca de valor inexistente:");
        System.out.println("buscar(42) é null: " + (arv.buscar(42) == null));

        // Revelar um nó e conferir o exibir()
        arv.buscar(10).revelado = true;
        System.out.println("\nDepois de revelar o 10:");
        arv.imprimir();

        // Revelar tudo
        arv.revelarTudo();
        System.out.println("\nDepois de revelarTudo():");
        arv.imprimir();

        // Inserir e buscar de novo após as mudanças
        arv.inserir(25);
        System.out.println("\nDepois de inserir o 25:");
        arv.imprimir();
        System.out.println("buscar(25): " + (arv.buscar(25) != null));
        System.out.println("buscar(20): " + (arv.buscar(20) != null));
    }
}