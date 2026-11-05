package ca.cegep.biblio.service;


import ca.cegep.biblio.model.Livre;
import ca.cegep.biblio.repository.LivreRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

/**
 * TP partie C : à vous d'implémenter les 3 tests ci-dessous.
 * Retirez @Disabled de chaque test une fois son implémentation écrite.
 * Respectez le modèle Arrange / Act / Assert.
 */
@ExtendWith(MockitoExtension.class)
class LivreServiceTest {

    @Mock
    private LivreRepository repository;

    @InjectMocks
    private LivreService service;

    // ---------- 1 test simple (sans mock) ----------

    @Test
    void calculerFraisRetard_longRetard_plafonneLeMontant() {
        // À tester : pour une catégorie donnée, un très grand nombre de jours de retard
        // ne dépasse jamais le plafond de cette catégorie.
        // Indice : la méthode ne dépend pas du repository.

        //Arrange
        double resultat = service.calculerFraisRetard("SCIENCE", 100);
        double validationFrais = 20.0;
        assertEquals(validationFrais, resultat);  }

    // ---------- 1 test sur une exception (assertThrows) ----------
        // À tester : emprunter un livre dont disponible == false lance une IllegalStateException.
        // Indice : le repository (mock) doit retourner ce livre pour son id.


    // ---------- 1 test avec mock (when / thenReturn / verify) ----------

    @Test
    void emprunter_livreDejaEmprunte_lanceIllegalStateException() {
    // Arrange
    Long id = 1L;
    Livre livre = new Livre();
    livre.setDisponible(false);

    when(repository.findById(id)).thenReturn(Optional.of(livre));
    //act
    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> service.emprunter(id));
    
    assertEquals("Ce livre est déjà emprunté",exception.getMessage());
    
    verify(repository.save());}
        


    // ---------- 1 test avec mock (when / thenReturn / verify) ----------

    @Test
    void emprunter_livreDisponible_marqueIndisponibleEtSauvegarde() {
    // À tester : emprunter un livre disponible le rend indisponible
    // et appelle repository.save(...) exactement une fois.
    // Définissez le comportement du mock avec when(...).thenReturn(...).
    // Arrange
    Long id = 1L;
    Livre livre = new Livre();
    livre.setDisponible(true);

    when(repository.findById(id)).thenReturn(java.util.Optional.of(livre));
    when(repository.save(livre)).thenReturn(livre);
     // Act
    Livre resultat = service.emprunter(id);
     // Assert
    assertFalse(resultat.isDisponible());
    verify(repository).save(livre);
      
      

      



  
    }}


