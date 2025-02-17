package lista_de_nodos;

import java.util.Iterator;
import tad_pilha_lse.NodeStack;
public class NodePositionList<TIPO> implements PositionList<TIPO>{

	protected int numElementos;
	protected DNode<TIPO> header, trailer;
	
	public NodePositionList() {		
		
		numElementos = 0;
		header = new DNode<TIPO>(null, null, null);
		trailer = new DNode<TIPO>(header, null, null);
		header.setNext(trailer);
	}
	
	protected DNode<TIPO> checkPosition(Position<TIPO> position) throws InvalidPositionException{
		
		if (position == null) throw new InvalidPositionException("A posi��o informada tem valor null");
		if (position == header) throw new InvalidPositionException("A cabe�a da lista n�o � uma posi��o valida");
		if (position == trailer) throw new InvalidPositionException("A cabe�a da lista n�o � uma posi��o valida");		
		
		//exercicio 13
		
		/*Para verificar se uma dada posi��o pertence ou n�o a lista desejada.
		 * No metodo checkPosition que verifica se a posi��o informada � valida,
		 * crie um contador para marcar quantas vezes a posi��o se repete dentro daquela lista ap�s isso
		 * crie um la�o que percorrer� a lista e comparar� se os nodos dentro dela tem tal posi��o informada semelhante,
		 * para cada vez que alguma posi��o for igual a posi��o do nodo soma-se um ao contador, se o la�o chegar
		 * ao final e o contador tiver registrado mais de 0 vezes que a posi��o se repetiu ent�o a posi��o � valida. Agora
		 * caso o la�o chegue a sua ultima repeti��o e o contador for igual a zero ent�o a classe de exce��o ser� chamada,
		 * indicando que a posi��o n�o pertence a lista.
		 */
		
		int conta = 0; //Criar uma variavel contadora para contar quantas vezes a position informada aparece na lista.
		for(DNode<TIPO> inicio = header.getNext(); inicio != trailer; inicio = inicio.getNext()) { //Percorrer a lista de nodos.
			
			//Verificar se a position informada em algum momento � igual a posi��o de algum nodo da lista.
			if(position == inicio) {conta++;} //Caso seja soma-se mais um a variavel contadora.
			if(inicio.getNext() == trailer && conta == 0) { //Caso seja a ultima repeti��o do la�o e contador ainda for igual a zero.
				
				//Chama-se a exce��o de erro, pois ent�o a posi��o informada n�opertence a lista.
				throw new InvalidPositionException("Essa posi��o n�o pertence a essa lista!");				
			}
		}
		
		try {
			
			DNode<TIPO> temp = (DNode<TIPO>) position;			
			if ((temp.getPrev() == null) || (temp.getNext() == null)) {throw new InvalidPositionException("A posi��o n�o pertence a uma NodeList v�lida");}
			
			return temp;
			
		} catch (ClassCastException elemento) {throw new InvalidPositionException("A posi��o � do tipo errado para esta lista");}		
	}
	
	public int size() {return numElementos;}
	
	public boolean isEmpty() { return (numElementos == 0); }
	
	public Position<TIPO> first() throws EmptyListException {

		if (isEmpty()) throw new EmptyListException("A lista est� vazia!");

		return header.getNext();
	}
	
	public Position<TIPO> prev(Position<TIPO> position) throws InvalidPositionException, BoundaryViolationException {

		DNode<TIPO> v = checkPosition(position);
		DNode<TIPO> prev = v.getPrev();

		if (prev == header) throw new BoundaryViolationException("N�o � poss�vel avan�ar al�m do in�cio da lista");

		return prev;

	}
	
	public void addBefore(Position<TIPO> position, TIPO elemento) throws InvalidPositionException {

		DNode<TIPO> validaPosition = checkPosition(position);
		numElementos++;
		DNode<TIPO> newNode = new DNode<TIPO>(validaPosition.getPrev(), validaPosition, elemento);

		validaPosition.getPrev().setNext(newNode);
		validaPosition.setPrev(newNode);
	}
	
	public void addFirst(TIPO elemento) {

		numElementos++;
		DNode<TIPO> newNode = new DNode<TIPO>(header, header.getNext(), elemento);

		header.getNext().setPrev(newNode);
		header.setNext(newNode);
	}
	
	public TIPO remove(Position<TIPO> position) throws InvalidPositionException {

		DNode<TIPO> validaPosition = checkPosition(position);

		numElementos--;

		DNode<TIPO> validaPositionPrev = validaPosition.getPrev();
		DNode<TIPO> validaPositionNext = validaPosition.getNext();
		validaPositionPrev.setNext(validaPositionNext);
		validaPositionNext.setPrev(validaPositionPrev);
		TIPO elementoValidado = validaPosition.elemento();		

		validaPosition.setNext(null);
		validaPosition.setPrev(null);

		return elementoValidado;
	}
	
	public TIPO set(Position<TIPO> position, TIPO elemento) throws InvalidPositionException {

		DNode<TIPO> validaPosition = checkPosition(position);
		TIPO elementoTemp = validaPosition.elemento();
		validaPosition.setElement(elemento);

		return elementoTemp;
	}
	
	public Position<TIPO> last() {
		
		if (isEmpty()) throw new EmptyListException("A lista est� vazia!");

		return trailer.getPrev();
	}
	
	public Position<TIPO> next(Position<TIPO> position) throws InvalidPositionException, BoundaryViolationException {

		DNode<TIPO> validaPosition = checkPosition(position);
		DNode<TIPO> next = validaPosition.getNext();

		if (next == trailer) throw new BoundaryViolationException("N�o � poss�vel avan�ar al�m da finaliza��o da lista");

		return next;
	}
	
	public void addLast(TIPO elemento) {

		numElementos++;

		DNode<TIPO> newNode = new DNode<TIPO>(trailer.getPrev(), trailer, elemento);
		trailer.getPrev().setNext(newNode);
		trailer.setPrev(newNode);
	}
	
	public void addAfter(Position<TIPO> position, TIPO elemento) throws InvalidPositionException {

		DNode<TIPO> validaPosition = checkPosition(position);

		numElementos++;

		DNode<TIPO> newNode = new DNode<TIPO>(validaPosition, validaPosition.getNext(), elemento);
		validaPosition.getNext().setPrev(newNode);
		validaPosition.setNext(newNode);
	}
	
	//exercicio 11
	//m�todo n�o recursivo para inverter uma lista de nodos.
	public void reverseList(PositionList<TIPO> lista) {
		
		NodeStack<TIPO> pilha = new NodeStack<TIPO>();
		
		for(TIPO i: lista) {pilha.push(i);}
		
		DNode<TIPO> inicio = header.getNext();
		
		while (inicio != trailer) {inicio.setElement(pilha.pop()); inicio = inicio.getNext();}
	}
	
	//exercicio 12
	/*makeFirst(p), que move o elemento na posi��o p para a
	primeira posi��o, mantendo a ordem relativa dos demais elementos inalterada.*/
	public void makeFirst(Position<TIPO> position) {
		
		DNode<TIPO> validaPosition = checkPosition(position);

		DNode<TIPO> validaPositionPrev = validaPosition.getPrev();
		DNode<TIPO> validaPositionNext = validaPosition.getNext();
		validaPositionPrev.setNext(validaPositionNext); 
		validaPositionNext.setPrev(validaPositionPrev); 
		
		validaPosition.setNext(header.getNext()); 
		validaPosition.setPrev(header); 
		
		header.getNext().setPrev(validaPosition); 
		header.setNext(validaPosition); 		
	}
	
	public static <TIPO> String toString(PositionList<TIPO> l) {

		String elementosLista = "";

		for (TIPO i: l) { elementosLista += ", " + i; }

		elementosLista = (elementosLista.length() == 0 ? elementosLista : elementosLista.substring(2));

		return "[" + elementosLista + "]";
	}
	
	public Iterator<TIPO> iterator() { return new ElementIterator<TIPO>(this); }

	public String toString() { return toString(this); }
}
