package TP.Entrega1;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        // Introducimos las rutas de los archivos
        if (args.length < 2) {
            System.out.println("Debe proporcionar las rutas a los archivos de partidos y resultados");
            return;
        }
        
        // Se lee el archivo de partidos
        Partido[] partidos = new Partido[2];
        try (CSVReader reader = new CSVReader(new FileReader(args[0]))) {
            String[] nextLine;
            int i = 0;
            while ((nextLine = reader.readNext()) != null && i < 2) {
                partidos[i] = new Partido(nextLine[0], nextLine[1]);
                i++;
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo de partidos: " + e.getMessage());
            return;
        }
        
        // Leer el archivo de resultados
        Resultado[] resultados = new Resultado[2];
        try (CSVReader reader = new CSVReader(new FileReader(args[1]))) {
            String[] nextLine;
            int i = 0;
            while ((nextLine = reader.readNext()) != null && i < 2) {
                resultados[i] = new Resultado(nextLine[0], nextLine[1], Integer.parseInt(nextLine[2]), Integer.parseInt(nextLine[3]));
                i++;
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo de resultados: " + e.getMessage());
            return;
        }
        
        // Calcular el puntaje
        int puntaje = 0;
        for (Resultado resultado : resultados) {
            for (Partido partido : partidos) {
                if (resultado.getEquipoLocal().equals(partido.getEquipoLocal()) && resultado.getEquipoVisitante().equals(partido.getEquipoVisitante())) {
                    if (resultado.getGolesLocal() == resultado.getGolesVisitante()) {
                        puntaje += 1;
                    }
                }
            }
        }
        
        // Imprimir el puntaje
        System.out.println("El puntaje de la persona es: " + puntaje);
    }
}