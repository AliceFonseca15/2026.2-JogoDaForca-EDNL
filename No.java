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

