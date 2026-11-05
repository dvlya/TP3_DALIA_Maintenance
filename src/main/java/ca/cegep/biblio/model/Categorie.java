package ca.cegep.biblio.model;

public enum Categorie {
    JEUNESSE(0.10, 5.0),
    SCIENCE(0.50, 20.0),
    AUTRE(0.25, 10.0);
     private final double fraisParJour;
    private final double plafond;
   

    Categorie(double fraisParJour, double plafond) {
        this.fraisParJour = fraisParJour;
        this.plafond = plafond;}

    public double calculerFrais(int joursRetard) {
        if(joursRetard <= 0) {
        return 0;}
        return Math.min(joursRetard * fraisParJour, plafond);}

    public static Categorie depuis(String categorie) {
        return switch (categorie) {
            case "JEUNESSE" -> JEUNESSE;
            case "SCIENCE" -> SCIENCE;
            default -> AUTRE;};
    }
}
