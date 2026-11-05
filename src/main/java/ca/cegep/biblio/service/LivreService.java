package ca.cegep.biblio.service;
import ca.cegep.biblio.model.Categorie;
import ca.cegep.biblio.exception.LivreIntrouvableException;
import ca.cegep.biblio.model.Livre;
import ca.cegep.biblio.repository.LivreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/** Logique métier de la bibliothèque. */
@Service
public class LivreService {
    // private static final double FRAIS_JEUNESSE = 0.10;
    // private static final double FRAIS_SCIENCE = 0.50;   
    // private static final double FRAIS_AUTRE = 0.25;
    // private static final double PLAFOND_JEUNESSE = 5;
    // private static final double PLAFOND_SCIENCE = 20;
    // private static final double PLAFOND_AUTRE = 10;
    private static final int ANNEE_PUBLICATION_MIN = 1450;
    private static final int ANNEE_PUBLICATION_MAX = 2100;

    private final LivreRepository repository;

    public LivreService(LivreRepository repository) {
        this.repository = repository;
    }

    public List<Livre> lister() {
        return repository.findAll();
    }

    public Livre trouver(Long id) {
        return repository.findById(id).orElseThrow(() -> new LivreIntrouvableException(id));
    }
    private void validerLivre(String titre, String auteur, int anneePublication) {
    if (titre == null || titre.isBlank()) {
        throw new IllegalArgumentException("Le titre est obligatoire");
    }
    if (auteur == null || auteur.isBlank()) {
        throw new IllegalArgumentException("L'auteur est obligatoire");
    }
    if (anneePublication < ANNEE_PUBLICATION_MIN
            || anneePublication > ANNEE_PUBLICATION_MAX) {
        throw new IllegalArgumentException(
                "L'année de publication est invalide");
    }
}
public Livre creer(String titre, String auteur, String isbn, int anneePublication, String categorie) {
    validerLivre(titre, auteur, anneePublication);

    Livre livre = new Livre(
            null, titre, auteur, isbn, anneePublication, categorie);

    return repository.save(livre);

} public Livre modifier(Long id, String titre, String auteur, String isbn,int anneePublication, String categorie) {
    Livre livre = trouver(id);
    validerLivre(titre, auteur, anneePublication);

    livre.setTitre(titre);
    livre.setAuteur(auteur);
    livre.setIsbn(isbn);
    livre.setAnneePublication(anneePublication);
    livre.setCategorie(categorie);

    return repository.save(livre);
    }
    public void supprimer(Long id) {
        trouver(id);
        repository.deleteById(id);
    }

    public Livre emprunter(Long id) {
        Livre livre = trouver(id);
        if (!livre.isDisponible()) {
            throw new IllegalStateException("Ce livre est déjà emprunté");
        }
        livre.setDisponible(false);
        return repository.save(livre);
    }

    public Livre retourner(Long id) {
        Livre livre = trouver(id);
        if (livre.isDisponible()) {
            throw new IllegalStateException("Ce livre n'est pas emprunté");
        }
        livre.setDisponible(true);
        return repository.save(livre);
    }

    public double calculerFraisRetard(String categorie, int joursRetard) {
        if (joursRetard <= 0) {
            return 0;
        }

        return Categorie.depuis(categorie).calculerFrais(joursRetard);
    }
}
