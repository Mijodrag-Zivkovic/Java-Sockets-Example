package http;

import java.util.ArrayList;

public class Citat {

    private String autor;
    private String citat;
    public static ArrayList<Citat> citati = new ArrayList<>();
    public static Citat citatDana = new Citat("", "");

    public Citat(String autor, String citat) {
        this.autor = autor;
        this.citat = citat;

    }

    public String getAutor() {
        return autor;
    }

    public String getCitat() {
        return citat;
    }

    public static void setCitatDana(Citat citatDana) {
        Citat.citatDana = citatDana;
    }
}
