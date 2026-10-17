public class ArvoreBinaria<T extends Comparable<T>> {
	No<T> raiz;

	public ArvoreBinaria() {
		this.raiz = null;
	}

	public ArvoreBinaria(T dadoRaiz){
		this.raiz = new No<T>(dadoRaiz);
	}

	public boolean arvoreVazia() {
		return this.raiz == null;
	}

	public void inserir(T dado){
		if(this.arvoreVazia())
			this.raiz = new No<T>(dado);
		else
			this.raiz.inserir(dado);
	}

	public No<T> buscar(T dado){
		if(this.arvoreVazia()){
			return null;
		}else {
			return this.raiz.buscarNo(dado);
		}
	}

	public void imprimirArvore() {
		imprimirArvore(raiz, 0);
	}

	private void imprimirArvore(No<T> no, int nivel) {
		if (no == null) return;

		for (int i = 0; i < nivel; i++) {
			System.out.print(" ");
		}
		System.out.println("+- " + no.exibir());

		imprimirArvore(no.esquerda, nivel + 1);
		imprimirArvore(no.direita, nivel + 1);
	}

	public void revelarTudo() {
		if (raiz != null) {
			raiz.revelarTodos();
		}
	}
}