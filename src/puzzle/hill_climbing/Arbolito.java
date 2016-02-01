package puzzle.hill_climbing;

import java.util.*;

public class Arbolito {
	
	Nodos inicio;
	int total;
	ArrayList<int[][]> visitados;
	Queue<Pasos> solucion;
	Queue<Pasos> solucion2;
	int[][] estadoFinal;
	int[][] estadoParcial={{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
	public static final int[] posicion1={0, 2};
	public static final int[] posicion2={1, 2};
	public static final int[] posicion3={2, 1};
	public static final int[] posicion4={0, 0};
	public static final int[] posicion5={1, 0};
	public static final int[] posicionop={2, 1};
	
	public Arbolito(int[][] inicial, int[][] fin){
		visitados=new ArrayList<int[][]>();
		this.estadoFinal=fin;
		this.estadoParcial[0][2]=this.estadoFinal[0][0];
		this.estadoParcial[1][2]=this.estadoFinal[0][1];
		this.estadoParcial[2][2]=this.estadoFinal[0][2];
		this.estadoParcial[2][0]=this.estadoFinal[1][0];
		this.estadoParcial[1][0]=this.estadoFinal[1][1];
		this.estadoParcial[0][0]=this.estadoFinal[1][2];
		this.estadoParcial[1][1]=this.estadoFinal[2][0];
		this.estadoParcial[0][1]=this.estadoFinal[2][1];
		this.estadoParcial[2][1]=this.estadoFinal[2][2];
		this.total=1;
		solucion=new LinkedList<Pasos>();
		solucion2=new LinkedList<Pasos>();
		if(Arrays.deepEquals(inicial, estadoFinal)){
			this.inicio=new Nodos(inicio, estadoFinal, 0, null, 0, 0, 0, 0, 0, 0, 0, 0);
			visitados.add(inicio.matriz);
			System.out.println("la solucion es el principio!");
			inicio.matrizToString();
		}else{
			this.inicio=new Nodos(null, inicial, 0, null, 0, 0, 0, 0, 0, 0, 0, 0);
			visitados.add(inicio.matriz);
			Stack<Movimientos> subMatrices=this.inicio.movimientos();
			while(!subMatrices.empty()){
				add(subMatrices.pop(), inicio, this.estadoParcial[0][2],
						posicion1[0], posicion1[1], 0, 0, 0, 0, 0);
			}
		}
	}
	
	public void add(Movimientos mov, Nodos padre, int numh, int posx, int posy,
			int correcto1, int correcto2, int correcto3, int correcto4, int correcto5){
		if(!compararVisitados(mov.matriz)){
			Nodos hijo = new Nodos(padre, mov.matriz, mov.numero, mov.movimiento,
					numh, posx, posy, correcto1, correcto2, correcto3, correcto4,
					correcto5);
			padre.hijos.add(hijo);
			visitados.add(mov.matriz);
			total++;
		}
	}
	
	public boolean compararVisitados(int[][] temp){
		for(int i=0; i<visitados.size(); i++){
			if(Arrays.deepEquals(visitados.get(i), temp)){
				return true;
			}
		}
		return false;
	}
	
	public boolean checarMatriz(Nodos nodo, int posy, int posx, int num){
		Nodos estadoPadre=nodo;
		Nodos estadoActual=nodo;
		for(int i=0; i<estadoPadre.hijos.size(); i++){
			estadoActual=estadoPadre.hijos.get(i);
			if(estadoActual.matriz[posy][posx]==num){
				return true;
			}
		}
		return false;
	}
	
	public Nodos regresaCorrecto(Nodos nodo, int posy, int posx, int num){
		Nodos estadoPadre=nodo;
		Nodos estadoActual=nodo;
		for(int i=0; i<estadoPadre.hijos.size(); i++){
			estadoActual=estadoPadre.hijos.get(i);
			if(estadoActual.matriz[posy][posx]==num){
				solucion.add(new Pasos(estadoActual.numero, estadoActual.movimiento));
				estadoActual.matrizToString();
				return estadoActual;
			}
		}
		return null;
	}

	
	public int crearhijos(Nodos nodo, int numh, int posx, int posy,	int correcto1,
			int correcto2, int correcto3, int correcto4, int correcto5){
		Nodos estadoPadre=nodo;
		Nodos estadoActual=nodo;
		int n = 0;
		int h = 1000;
		System.out.println("nodos hijos: \n");
		for(int i=0; i<estadoPadre.hijos.size(); i++){
			estadoActual=estadoPadre.hijos.get(i);
			if(h>estadoActual.heuristica){
				n=i;
				h=estadoActual.heuristica;
			}
		}
		estadoActual=estadoPadre.hijos.get(n);
		estadoActual.matrizToString();
		solucion.add(new Pasos(estadoActual.numero, estadoActual.movimiento));
		Stack<Movimientos> subMatrices=estadoActual.movimientos();
		while(!subMatrices.empty()){
			add(subMatrices.pop(), estadoActual, numh, posx, posy, correcto1,
					correcto2, correcto3, correcto4,correcto5);
		}
		return n;
	}
	
	public Nodos proceso(Nodos nodo){
		int n=0;
		int m=0;
		Movimientos mov;
		for (int y=0; y<3; y++){
			for(int x=0; x<3; x++){
				if(nodo.matriz[y][x]==0){
					n=y;
					m=x;
					break;
				}
			}
		}
		while(n!=0){
			mov=nodo.moverArriba(n, m, nodo.matriz);
			solucion.add(new Pasos(mov.numero, mov.movimiento));
			add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
			nodo=nodo.hijos.get(0);
			nodo.matrizToString();
			n--;
		}
		while (m<1){
			mov=nodo.moverDer(n, m, nodo.matriz);
			solucion.add(new Pasos(mov.numero, mov.movimiento));
			add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
			nodo=nodo.hijos.get(0);
			nodo.matrizToString();
			m++;
		}
		mov=nodo.moverDer(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		m++;
		mov=nodo.moverAbajo(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		n++;
		mov=nodo.moverAbajo(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		n++;
		mov=nodo.moverIzq(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		m--;
		mov=nodo.moverArriba(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		n--;
		mov=nodo.moverDer(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		m++;
		mov=nodo.moverArriba(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		n--;
		mov=nodo.moverIzq(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		m--;
		return nodo;
	}
	
	public Nodos proceso2(Nodos nodo){
		int n=0;
		int m=0;
		Movimientos mov;
		System.out.println("AQUI ESTA EL NODO DEL PROCESO 2\n");
		nodo.matrizToString();
		for (int y=0; y<3; y++){
			for(int x=0; x<3; x++){
				if(nodo.matriz[y][x]==0){
					n=y;
					m=x;
					break;
				}
			}
		}
		while(n!=0){
			visitados.clear();
			mov=nodo.moverArriba(n, m, nodo.matriz);
			solucion.add(new Pasos(mov.numero, mov.movimiento));
			add(mov, nodo, 0, 0, 0, 0, 0, 0, 0, 0);
			nodo=nodo.hijos.get(0);
			nodo.matrizToString();
			n--;
		}
		if(!(nodo.matriz[0][1]==0)){
			mov=nodo.moverAbajo(n, m, nodo.matriz);
			solucion.add(new Pasos(mov.numero, mov.movimiento));
			add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
			nodo=nodo.hijos.get(0);
			nodo.matrizToString();
			n++;
			mov=nodo.moverAbajo(n, m, nodo.matriz);
			solucion.add(new Pasos(mov.numero, mov.movimiento));
			add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
			nodo=nodo.hijos.get(0);
			nodo.matrizToString();
			n++;	
			mov=nodo.moverDer(n, m, nodo.matriz);
			solucion.add(new Pasos(mov.numero, mov.movimiento));
			add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
			nodo=nodo.hijos.get(0);
			nodo.matrizToString();
			m++;
			mov=nodo.moverArriba(n, m, nodo.matriz);
			solucion.add(new Pasos(mov.numero, mov.movimiento));
			add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
			nodo=nodo.hijos.get(0);
			nodo.matrizToString();
			n--;
			mov=nodo.moverArriba(n, m, nodo.matriz);
			solucion.add(new Pasos(mov.numero, mov.movimiento));
			add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
			nodo=nodo.hijos.get(0);
			nodo.matrizToString();
			n--;
		}
		mov=nodo.moverIzq(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		m--;
		mov=nodo.moverAbajo(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		n++;
		return nodo;
	}
	
	public boolean checarFinal(Nodos nodo){
		Nodos estadoPadre=nodo;
		Nodos estadoActual=nodo;
		for(int i=0; i<estadoPadre.hijos.size(); i++){
			estadoActual=estadoPadre.hijos.get(i);
			if(Arrays.deepEquals(estadoActual.matriz, estadoParcial)){
				return true;
			}
		}
		return false;
	}
	
	public int crearhijosFinal(Nodos nodo, int correcto1, int correcto2,
			int correcto3, int correcto4, int correcto5){
		Nodos estadoPadre=nodo;
		Nodos estadoActual=nodo;
		int n = 0;
		int h = 1000;
		for(int i=0; i<estadoPadre.hijos.size(); i++){
			estadoActual=estadoPadre.hijos.get(i);
			if(h>estadoActual.heuristicafinal){
				n=i;
				h=estadoActual.heuristicafinal;
			}
		}
		estadoActual=estadoPadre.hijos.get(n);
		estadoActual.matrizToString();
		solucion.add(new Pasos(estadoActual.numero, estadoActual.movimiento));
		Stack<Movimientos> subMatrices=estadoActual.movimientos();
		while(!subMatrices.empty()){
			add(subMatrices.pop(), estadoActual, 0, 0 ,0, correcto1, correcto2,
					correcto3, correcto4, correcto5);
		}
		return n;
	}
	
	public Nodos procesoFinal(Nodos nodo){
		int n=0;
		int m=0;
		Movimientos mov;
		for (int y=0; y<3; y++){
			for(int x=0; x<3; x++){
				if(nodo.matriz[y][x]==0){
					n=y;
					m=x;
					break;
				}
			}
		}
		while(n!=0){
			mov=nodo.moverArriba(n, m, nodo.matriz);
			solucion.add(new Pasos(mov.numero, mov.movimiento));
			add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
			nodo=nodo.hijos.get(0);
			nodo.matrizToString();
			n--;
		}	
		mov=nodo.moverDer(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		m++;
		mov=nodo.moverAbajo(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		n++;
		mov=nodo.moverAbajo(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		n++;
		mov=nodo.moverIzq(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		m--;
		mov=nodo.moverIzq(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		m--;
		mov=nodo.moverArriba(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		n--;
		mov=nodo.moverArriba(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		n--;
		mov=nodo.moverDer(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		m++;
		mov=nodo.moverDer(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		m++;
		mov=nodo.moverAbajo(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		n++;
		mov=nodo.moverIzq(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		m--;
		mov=nodo.moverIzq(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		m--;
		mov=nodo.moverAbajo(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		n++;
		mov=nodo.moverDer(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		m++;
		mov=nodo.moverDer(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		m++;
		mov=nodo.moverArriba(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		n--;
		mov=nodo.moverIzq(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		m--;
		mov=nodo.moverIzq(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		m--;
		mov=nodo.moverAbajo(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		n++;
		mov=nodo.moverDer(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		m++;
		mov=nodo.moverDer(n, m, nodo.matriz);
		solucion.add(new Pasos(mov.numero, mov.movimiento));
		add(mov, nodo, 0,0,0, 0, 0, 0, 0, 0);
		nodo=nodo.hijos.get(0);
		nodo.matrizToString();
		m++;
		return nodo;
	}
	
	public Queue<Pasos> resolver(Nodos nodo){
		int i=0;
		nodo.matrizToString();
		while(!checarMatriz(nodo, posicion1[0], posicion1[1], this.estadoParcial[0][2])){
			i=crearhijos(nodo, this.estadoParcial[0][2], posicion1[0], posicion1[1],
					0, 0, 0, 0, 0);
			nodo=nodo.hijos.get(i);
		}
		visitados.clear();
		nodo=regresaCorrecto(nodo, posicion1[0], posicion1[1], this.estadoParcial[0][2]);
		Stack<Movimientos> subMatrices=nodo.movimientos();
		while(!subMatrices.empty()){
			add(subMatrices.pop(), nodo, this.estadoParcial[1][2], posicion2[0],
					posicion2[1], this.estadoParcial[0][2], 0, 0, 0, 0);
		}
		while(!checarMatriz(nodo, posicion2[0], posicion2[1], this.estadoParcial[1][2])){
			i=crearhijos(nodo, this.estadoParcial[1][2], posicion2[0],
					posicion2[1], this.estadoParcial[0][2], 0, 0, 0, 0);
			nodo=nodo.hijos.get(i);
		}
		visitados.clear();
		nodo=regresaCorrecto(nodo, posicion2[0], posicion2[1], this.estadoParcial[1][2]);
		subMatrices=nodo.movimientos();
		while(!subMatrices.empty()){
			add(subMatrices.pop(), nodo, this.estadoParcial[2][2], posicion3[0],
					posicion3[1], this.estadoParcial[0][2], this.estadoParcial[1][2],
					0, 0, 0);
		}
		while(!checarMatriz(nodo, posicion3[0], posicion3[1], this.estadoParcial[2][2])){
			i=crearhijos(nodo, this.estadoParcial[2][2], posicion3[0],
					posicion3[1], this.estadoParcial[0][2], this.estadoParcial[1][2],
					0, 0, 0);
			nodo=nodo.hijos.get(i);
		}
		visitados.clear();
		nodo=regresaCorrecto(nodo, posicion3[0], posicion3[1], this.estadoParcial[2][2]);
		nodo=proceso(nodo);
		if(!(nodo.matriz[posicion4[0]][posicion4[1]]==this.estadoParcial[0][1])){
			subMatrices=nodo.movimientos();
			while(!subMatrices.empty()){
				add(subMatrices.pop(), nodo, this.estadoParcial[0][1], posicion4[0],
						posicion4[1], this.estadoParcial[0][2], this.estadoParcial[1][2],
						this.estadoParcial[2][2], 0, 0);
			}
			//System.out.print("llegue aqui\n");
			while(!checarMatriz(nodo, posicion4[0], posicion4[1], this.estadoParcial[0][1])){
				i=crearhijos(nodo, this.estadoParcial[0][1], posicion4[0],
						posicion4[1], this.estadoParcial[0][2], this.estadoParcial[1][2],
						this.estadoParcial[2][2], 0, 0);
				nodo=nodo.hijos.get(i);
			}
			visitados.clear();
			nodo=regresaCorrecto(nodo, posicion4[0], posicion4[1], this.estadoParcial[0][1]);
		}
		if(nodo.matriz[0][1]==this.estadoParcial[0][0]||nodo.matriz[1][1]==this.estadoParcial[0][0]){
			
			subMatrices=nodo.movimientos();
			while(!subMatrices.empty()){
				add(subMatrices.pop(), nodo, this.estadoParcial[0][0], posicionop[0],
						posicionop[1], this.estadoParcial[0][2], this.estadoParcial[1][2],
						this.estadoParcial[2][2], 0, 0);
			}
			while(!checarMatriz(nodo, posicionop[0], posicionop[1], this.estadoParcial[0][0])){
				i=crearhijos(nodo, this.estadoParcial[0][0], posicionop[0],
						posicionop[1], this.estadoParcial[0][2], this.estadoParcial[1][2],
						this.estadoParcial[2][2], 0, 0);
				nodo=nodo.hijos.get(i);
			}
			nodo=regresaCorrecto(nodo, posicionop[0], posicionop[1], this.estadoParcial[0][0]);
			subMatrices=nodo.movimientos();
			while(!subMatrices.empty()){
				add(subMatrices.pop(), nodo, this.estadoParcial[0][1], posicion4[0],
						posicion4[1], this.estadoParcial[0][2], this.estadoParcial[1][2],
						this.estadoParcial[2][2], 0, 0);
			}
			while(!checarMatriz(nodo, posicion4[0], posicion4[1], this.estadoParcial[0][1])){
				i=crearhijos(nodo, this.estadoParcial[0][1], posicion4[0],
						posicion4[1], this.estadoParcial[0][2], this.estadoParcial[1][2],
						this.estadoParcial[2][2], 0, 0);
				nodo=nodo.hijos.get(i);
			}
			nodo=regresaCorrecto(nodo, posicion4[0], posicion4[1], this.estadoParcial[0][1]);
		}
		if(!(nodo.matriz[posicion5[0]][posicion5[1]]==this.estadoParcial[0][0])){
			subMatrices=nodo.movimientos();
			while(!subMatrices.empty()){
				add(subMatrices.pop(), nodo, this.estadoParcial[0][0], posicion5[0],
						posicion5[1], this.estadoParcial[0][2], this.estadoParcial[1][2],
						this.estadoParcial[2][2], this.estadoParcial[0][1], 0);
			}
			visitados.clear();
			while(!checarMatriz(nodo, posicion5[0], posicion5[1], this.estadoParcial[0][0])){
				i=crearhijos(nodo, this.estadoParcial[0][0], posicion5[0],
						posicion5[1], this.estadoParcial[0][2], this.estadoParcial[1][2],
						this.estadoParcial[2][2], this.estadoParcial[0][1], 0);
				nodo=nodo.hijos.get(i);
			}
			nodo=regresaCorrecto(nodo, posicion5[0], posicion5[1], this.estadoParcial[0][0]);
		}
		
		nodo=proceso2(nodo);
		subMatrices=nodo.movimientos();
		while(!subMatrices.empty()){
			add(subMatrices.pop(), nodo, 0, 0, 0, this.estadoParcial[0][2],
					this.estadoParcial[1][2], this.estadoParcial[2][2],
					this.estadoParcial[0][1], this.estadoParcial[0][0]);
		}
		while(!checarFinal(nodo)){
			i=crearhijosFinal(nodo, this.estadoParcial[0][2],
					this.estadoParcial[1][2], this.estadoParcial[2][2],
					this.estadoParcial[0][1], this.estadoParcial[0][0]);
			nodo=nodo.hijos.get(i);
		}
		nodo=regresaCorrecto(nodo, posicionop[0], posicionop[1], this.estadoParcial[2][1]);
		visitados.clear();
		nodo=procesoFinal(nodo);
		System.out.print("la solucion!!!!\n");
		System.out.print("se crearon "+total+" nodos en memoria\n");
		System.out.println("se pasaron por "+solucion.size()+" nodos en los pasos\n\n");
		visitados.clear();
		return solucion;
	}
	
	public void add(Movimientos mov, Nodos padre){
		if(!compararVisitados(mov.matriz)){
			Nodos hijo = new Nodos(padre, mov.matriz, mov.numero, mov.movimiento,
					0, 0, 0, 0, 0, 0, 0, 0);
			padre.hijos.add(hijo);
			visitados.add(mov.matriz);
			total++;
		}
	}
	
	public Queue<Pasos> resolver2(Nodos nodo){
		Nodos estadoPadre=nodo;
		Nodos estadoActual=nodo;
		Stack<Movimientos> subMatrices=new Stack<Movimientos>();
		while(!Arrays.deepEquals(estadoPadre.matriz, estadoFinal)){
			int n = 0;
			int h = 1000;
			for(int i=0; i<estadoPadre.hijos.size(); i++){
				estadoActual=estadoPadre.hijos.get(i);
				if(h>estadoActual.heuristica2){
					n=i;
					h=estadoActual.heuristica2;
				}
			}
			try {
				estadoActual=estadoPadre.hijos.get(n);
			} catch (Exception e) {
				System.out.print("la solucion no pudo ser encontrada!!!!\n");
				System.out.print("se crearon "+total+" nodos en memoria\n");
				System.out.println("se pasaron por "+solucion2.size()+" nodos en los pasos\n\n");
				return solucion2;
			}
			estadoActual.matrizToString();
			solucion2.add(new Pasos(estadoActual.numero, estadoActual.movimiento));
			subMatrices=estadoActual.movimientos();
			while(!subMatrices.empty()){
				add(subMatrices.pop(), estadoActual);
			}
			estadoPadre=estadoActual;
		}
		System.out.print("la solucion!!!!\n");
		System.out.print("se crearon "+total+" nodos en memoria\n");
		System.out.println("se pasaron por "+solucion2.size()+" nodos en los pasos\n\n");
		visitados.clear();
		return solucion2;
	}

	/**
	 * @return the inicio
	 */
	public Nodos getInicio() {
		return inicio;
	}

	
	
}
