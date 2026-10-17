public class No<T extends Comparable<T>> {
	T dado;
	No<T> esquerda;
	No<T> direita;
	No<T> pai;
	boolean revelado;

	// construtor
	public No(T dado){
		this.dado = dado;
		this.esquerda = null;
		this.direita = null;
        this.revelado = false;
	}

	public void inserir(T dado){		
		if(this.dado.compareTo(dado) >= 1){ // sub-árvore direita
			if(this.esquerda == null) {
				No<T> no_novo = new No<T>(dado);
				this.esquerda = no_novo;
				no_novo.pai = this;
			}else {
				this.esquerda.inserir(dado);
			}
		}else { // sub-árvore esquerda
			if(this.direita == null) {
				No<T> no_novo = new No<T>(dado);
				this.direita = no_novo;
				no_novo.pai = this;
			}else {
				this.direita.inserir(dado);
			}
		}
	}
	
	public No<T> buscarNo(T dado){
		if(this.dado.equals(dado)){ // nó contém o dado
			return this;
		}else { // procurar o dado nos filhos do nó atual
			if(this.dado.compareTo(dado) >= 1 && this.esquerda != null) {
				return this.esquerda.buscarNo(dado);
			}else if(this.dado.compareTo(dado) < 1 && this.direita != null) {
				return this.direita.buscarNo(dado);
			}
			else 
				return null; // não encontrado!
		}
	}

    public String exibir() {
        if (revelado) {
    		return String.valueOf(this.dado);
		} else {
    		return "[ ? ]";
	}

    }
    public void imprimir() {
        System.out.print(exibir());
    }

    public void revelarTodos() {
        this.revelado = true;
        if (esquerda != null) esquerda.revelarTodos();
        if (direita != null) direita.revelarTodos();
    }

    @Override
    public String toString() {
        return exibir();
    }

	public void imprimirNo(String recuo){
		System.out.println(recuo + "+- " + this.exibir());
		
		if(this.esquerda != null)
			this.esquerda.imprimirNo(recuo + " ");

		if(this.direita != null)
			this.direita.imprimirNo(recuo + " ");
	}
}
