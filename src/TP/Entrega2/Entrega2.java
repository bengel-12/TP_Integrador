package TP.Entrega2;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Programa {

    public static void main(String[] args) {

        String archivoPartidos = args[0];
        String archivoResultados = args[1];

        Map<String, Integer> puntajes = new HashMap<>();

        try (CSVReader readerPartidos = new CSVReader(new FileReader(archivoPartidos));
             CSVReader readerResultados = new CSVReader(new FileReader(archivoResultados))) {

            String[] lineaPartidos;
            String[] lineaResultados;

            while ((lineaPartidos = readerPartidos.readNext()) != null && (lineaResultados = readerResultados.readNext()) != null) {

                // Verificamos que la linea de resultados sea valida
                if (lineaResultados.length != 4) {
                    System.out.println("La línea de resultados no tiene el número correcto de campos");
                    continue;
                }

                String persona = lineaResultados[0];
                String equipoLocal = lineaPartidos[0];
                String equipoVisitante = lineaPartidos[1];
                int golesLocal = 0;
                int golesVisitante = 0;

                try {
                    golesLocal = Integer.parseInt(lineaResultados[1]);
                    golesVisitante = Integer.parseInt(lineaResultados[2]);
                } catch (NumberFormatException e) {
                    System.out.println("La cantidad de goles no es un número entero");
                    continue;
                }

                Partido partido = new Partido(equipoLocal, equipoVisitante);
                Resultado resultado = new Resultado(equipoLocal, equipoVisitante, golesLocal, golesVisitante);

                int puntaje = 0;

                // Buscamos el partido correspondiente en el archivo de partidos
                for (String[] linea : readerPartidos) {
                    Partido partidoActual = new Partido(linea[0], linea[1]);
                    if (partidoActual.equals(partido)) {
                        // Encontramos el partido correspondiente y buscar el resultado
                        for (String[] linea2 : readerResultados) {
                            Resultado resultadoActual = new Resultado(linea[0], linea[1], Integer.parseInt(linea2[1]), Integer.parseInt(linea2[2]));
                            if (resultadoActual.equals(resultado)) {
                                // Encontramos el resultado correspondiente y verificar si es empate
                                if (resultadoActual.getGolesLocal() == resultadoActual.getGolesVisitante()) {
                                    puntaje++;
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
}
               
