package puzzle.hill_climbing;

import java.util.ArrayList;
import java.util.Stack;

public class Nodos {
	
	public Nodos padre;
	public ArrayList<Nodos> hijos;
	public int[][] matriz;
	public int numero;
	public String movimiento;
	public int heuristica;
	public int heuristicafinal;
	public int heuristica2;
	public static final String derecha="right";
	public static final String izquierda="left";
	public static final String arriba="up";
	public static final String abajo="down";
	
	public Nodos(Nodos padre, int[][] matriz, int numero, String movimiento, int numh,
			int posy, int posx, int correcto1, int correcto2, int correcto3, 
			int correcto4, int correcto5){
		this.padre=padre;
		this.hijos= new ArrayList<Nodos>();
		this.matriz=matriz;
		this.numero=numero;
		this.movimiento=movimiento;
		this.heuristica=calcularHeuristica(numh, correcto1, correcto2, correcto3,
				correcto4, posy, posx);
		this.heuristicafinal=calcularHeuristicaFinal(correcto1, correcto2, correcto3,
				correcto4, correcto5);
		this.heuristica2=calcularHeuristicaMerol();
	}

	public Stack<Movimientos> movimientos(){
		Stack<Movimientos> posibles=new Stack<Movimientos>();
		int n=0;
		int m=0;
		for (int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				if(matriz[i][j]==0){
					n=i;
					m=j;
					break;
				}
			}
		}
		switch (n) {
			case 0:
				posibles.push(moverAbajo(n, m, matriz));
				break;
			case 1:
				posibles.push(moverAbajo(n, m, matriz));
				posibles.push(moverArriba(n, m, matriz));
				break;
			default:
				posibles.push(moverArriba(n, m, matriz));
				break;
		}
		switch (m) {
			case 0:
				posibles.push(moverDer(n, m, matriz));
				break;
			case 1:
				posibles.push(moverDer(n, m, matriz));
				posibles.push(moverIzq(n, m, matriz));
				break;
			default:
				posibles.push(moverIzq(n, m, matriz));
				break;
		}
		return posibles;
	}
	
	public Movimientos moverArriba(int n, int m, int[][] matriz){
		int[][] temp={{0, 0, 0},{0, 0, 0},{0, 0, 0}};
		for (int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				temp[i][j]=matriz[i][j];
			}
		}
		temp[n][m]=temp[n-1][m];
		temp[n-1][m]=0;
		Movimientos nuevo=new Movimientos(temp, temp[n][m], abajo);
		return nuevo;
	}
	
	public Movimientos moverAbajo(int n, int m, int[][] matriz){
		int[][] temp={{0, 0, 0},{0, 0, 0},{0, 0, 0}};
		for (int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				temp[i][j]=matriz[i][j];
			}
		}
		temp[n][m]=temp[n+1][m];
		temp[n+1][m]=0;
		Movimientos nuevo=new Movimientos(temp, temp[n][m], arriba);
		return nuevo;
	}
	
	public Movimientos moverDer(int n, int m, int[][] matriz){
		int[][] temp={{0, 0, 0},{0, 0, 0},{0, 0, 0}};
		for (int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				temp[i][j]=matriz[i][j];
			}
		}
		temp[n][m]=temp[n][m+1];
		temp[n][m+1]=0;
		Movimientos nuevo=new Movimientos(temp, temp[n][m], izquierda);
		return nuevo;
	}
	
	public Movimientos moverIzq(int n, int m, int[][] matriz){
		int[][] temp={{0, 0, 0},{0, 0, 0},{0, 0, 0}};
		for (int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				temp[i][j]=matriz[i][j];
			}
		}
		temp[n][m]=temp[n][m-1];
		temp[n][m-1]=0;
		Movimientos nuevo=new Movimientos(temp, temp[n][m], derecha);
		return nuevo;
	}
	
	public int calcularHeuristica(int numeroh, int correcto1, int correcto2,
			int correcto3, int correcto4, int posy, int posx){
		if(this.numero==correcto1||this.numero==correcto2||this.numero==correcto3
				||this.numero==correcto4){
			return 1000;
		}
		int n=0;
		int m=0;
		int x = 0;
		int y = 0;
		int h;
		for (int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				if(matriz[i][j]==numeroh){
					n=i;
					m=j;
					break;
				}
			}
		}
		h=(Math.abs(n-posy)+Math.abs(m-posx))*100;
		for (int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				if(matriz[i][j]==0){
					x=i;
					y=j;
					break;
				}
			}
		}
		h+=(Math.abs(n-x)+Math.abs(m-y))*10;
		h+=(Math.abs(x-posy)+Math.abs(y-posx));
		return h;
	}
	public int calcularHeuristicaFinal(int correcto1, int correcto2,
			int correcto3, int correcto4, int correcto5){
		if(this.numero==correcto1||this.numero==correcto2||this.numero==correcto3
				||this.numero==correcto4||this.numero==correcto5){
			return 10000;
		}
		return 1;
	}
	
	public int calcularHeuristicaMerol(){
		int n=0;
		int m=0;
		int x = 0;
		int y = 0;
		int h = 0;
		int a=1;
		for(int posy=0; posy<3; posy++){
			for(int posx=0; posx<3; posx++){
				if(a==9){
					break;
				}
				for (int i=0; i<3; i++){
					for(int j=0; j<3; j++){
						if(matriz[i][j]==a){
							n=i;
							m=j;
							break;
						}
					}
				}
				h+=(Math.abs(n-posy)+Math.abs(m-posx))*100;
				for (int i=0; i<3; i++){
					for(int j=0; j<3; j++){
						if(matriz[i][j]==0){
							x=i;
							y=j;
							break;
						}
					}
				}
				h+=(Math.abs(n-x)+Math.abs(m-y))*10;
				h+=(Math.abs(x-posy)+Math.abs(y-posx));
			}
		}
		return h;
	}
	
	public void matrizToString(){
		for(int i=0; i<3; i++){
			System.out.print("[");
			for(int j=0; j<3; j++){
				System.out.print(" "+matriz[i][j]+" ");
			}
			System.out.print("]\n");
		}
		System.out.print("\n");
	}
	
}
