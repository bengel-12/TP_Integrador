package TP.Entrega1;

public class Resultado {
    private String equipoLocal;
    private String equipoVisitante;
    private int golesLocal;
    private int golesVisitante;
    //El constructor Resultado crea un nuevo resultado con el equipo local, equipo visitante,
    //goles del equipo local y goles del equipo visitante.
    public Resultado(String equipoLocal, String equipoVisitante, int golesLocal, int golesVisitante) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
    }
    //Los metodos devuelven el equipo local, equipo visitante, goles del equipo local y goles
    //del equipo visitante, respectivamente.
    public String getEquipoLocal() {
        return equipoLocal;
    }

    public String getEquipoVisitante() {
        return equipoVisitante;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }
}