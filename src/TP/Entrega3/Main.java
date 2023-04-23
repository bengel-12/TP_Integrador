package TP.Entrega3;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;

public class Main {

    private static final String ROUNDS_FILE_PATH = "rounds.csv";
    private static final String RESULTS_FILE_PATH = "results.csv";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydatabase_pronosticos";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "benja";
    private static int pointsPerCorrectResult = 1;
    private static int bonusPointsForRound = 2;
    private static int bonusPointsForPhase = 5;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: java Main <ruta_archivo_redondos> <ruta_archivo_resultados>");
            return;
        }
        String roundsFilePath = args[0];
        String resultsFilePath = args[1];

        // leemos las rondas
        List<Round> rounds = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(roundsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int ronda = Integer.parseInt(parts[0]);
                List<Match> matches = new ArrayList<>();
                for (int i = 1; i < parts.length; i += 2) {
                    matches.add(new Match(parts[i], parts[i + 1]));
                }
                rounds.add(new Round(ronda, matches));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // leemos los resultados de la base de datos
        List<Result> results = new ArrayList<>();
        if (resultsFilePath.endsWith(".csv")) {
            
            try (BufferedReader br = new BufferedReader(new FileReader(resultsFilePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length != 4) {
                        System.out.println("Invalid result: " + line);
                        continue;
                    }
                    int ronda = Integer.parseInt(parts[0]);
                    String persona = parts[1];
                    String equipo_local = parts[2];
                    String equipo_visitante = parts[3];
                    int goles_local = 0;
                    int goles_visitante = 0;
                    try {
                        goles_local = Integer.parseInt(parts[4]);
                        goles_visitante = Integer.parseInt(parts[5]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid result: " + line);
                        continue;
                    }
                    results.add(new Result(ronda, persona, equipo_local, equipo_visitante, goles_local, goles_visitante));
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } else {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT id, ronda,partido, persona, equipo_local, equipo_visitante, goles_local, goles_visitante FROM resultado")) {
                while (rs.next()) {
                	int id = rs.getInt("id");
                    int ronda = rs.getInt("ronda");
                    String persona = rs.getString("persona");
                    String equipo_local = rs.getString("equipo_local");
                    String equipo_visitante = rs.getString("equipo_visitante");
                    int goles_local = rs.getInt("goles_local");
                    int goles_visitante = rs.getInt("goles_visitante");
                    resultado.add(new Resultado(ronda, persona, equipo_local, equipo_visitante, goles_local, goles_visitante));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
