package dominio;

import java.io.*;
import java.util.*;

public class Tablero {
    private static final int DIMENSION = 30;
    private int[][] estadoActual;
    private int[][] estadoSiguiente;
    private String archivo;
    private ArrayList Tablero = new ArrayList();
    private String nombreFichero = "matriz.txt";
    private String contenidoArchivo;

    public Tablero() {
        this.estadoActual = new int[DIMENSION][DIMENSION];
        this.estadoSiguiente = new int[DIMENSION][DIMENSION];
    }

    public void leerEstadoActual(String archivo) throws FileNotFoundException, IOException
    {
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);

        while ((contenidoArchivo = br.readLine()) != null)
        {
            System.out.println(contenidoArchivo);
        }
        
    }

        
    

    public void generarEstadoActualPorMontecarlo() {
        Random random = new Random();
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                estadoActual[i][j] = random.nextDouble() < 0.5 ? 1 : 0;
            }
        }

        calcularEstadoSiguiente();
    }

    public void transitarAlEstadoSiguiente() {
        int[][] temp = estadoActual;
        estadoActual = estadoSiguiente;
        estadoSiguiente = temp;

        calcularEstadoSiguiente();
    }

    private void calcularEstadoSiguiente() {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                int vecinasVivas = contarVecinasVivas(i, j);
                if (estadoActual[i][j] == 1) {
                    estadoSiguiente[i][j] = (vecinasVivas == 2 || vecinasVivas == 3) ? 1 : 0;
                } else {
                    estadoSiguiente[i][j] = (vecinasVivas == 3) ? 1 : 0;
                }
            }
        }
    }

    private int contarVecinasVivas(int fila, int columna) {
        int vecinasVivas = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int filaVecina = (fila + i + DIMENSION) % DIMENSION;
                int columnaVecina = (columna + j + DIMENSION) % DIMENSION;
                vecinasVivas += estadoActual[filaVecina][columnaVecina];
            }
        }
        return vecinasVivas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                sb.append(estadoActual[i][j] == 1 ? "*" : " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
