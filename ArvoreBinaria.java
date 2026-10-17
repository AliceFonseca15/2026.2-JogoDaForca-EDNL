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

	public void imprimir() {
		if(this.arvoreVazia()){
			System.out.println("Árvore vazia!");	
		}else {
			this.raiz.imprimirNo("");
		}
		System.out.println("");
	}

	public void revelarTudo() {
		if (raiz != null) {
			raiz.revelarTodos();
		}
	}
}