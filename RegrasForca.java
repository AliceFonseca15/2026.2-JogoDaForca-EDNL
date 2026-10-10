import java.util.Random;

public class RegrasForca {
    private ArvoreBinaria<Integer> arvore;
    private int quantidadeNos;
    private int tentativas;
    private int acertos;

    // Construtor
    public RegrasForca() {
        this.arvore = new ArvoreBinaria<>();
        this.tentativas = 0;
        this.acertos = 0;
    }

    // Gera Arvore com númeroa aleatórios
    public void gerarArvore(int qtdNos) {
        this.quantidadeNos = qtdNos;
        Random gerador = new Random();
        int inseridos = 0;

        while (inseridos < qtdNos) {
            int valorSorteado = gerador.nextInt(51); // Gera entre 0 e 50

            if (this.arvore.buscar(valorSorteado) == null) {
                this.arvore.inserir(valorSorteado);
                inseridos++;
            }
        }
    }

    // Recebe o palpite e verifica se ta certo
    public boolean verificarPalpite(int palpite) {
        this.tentativas++;

        No<Integer> noEncontrado = this.arvore.buscar(palpite);

        if (noEncontrado != null) { // Achou o número na árvore
    
            if (!noEncontrado.revelado) {
                noEncontrado.revelado = true; 
                this.acertos++;
            }
            return true; 
        }
        return false; // Não achou na árvore
    }

    // Controlar a condição de vitória
    public boolean verificarVitoria() {
        return this.acertos == this.quantidadeNos;
    }

    // Get's pra puxar os dados pelo terminal
    public int getTentativas() { return tentativas; }
    public int getAcertos() { return acertos; }
    public ArvoreBinaria<Integer> getArvore() { return arvore; }

}