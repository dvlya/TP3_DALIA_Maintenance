# biblio-api

API REST de gestion d'une petite bibliothèque (Spring Boot 3.5, Java 25, architecture MVC).

## Démarrer

```bash
./mvnw spring-boot:run
```

- Swagger UI : http://localhost:8080/swagger-ui.html
- Les données sont gardées **en mémoire** (perdues à l'arrêt).

## Structure

| Package | Rôle |
|---|---|
| `model` | Le modèle : `Livre` |
| `repository` | Accès aux données (en mémoire) |
| `service` | Logique métier |
| `controller` | Contrôleur REST (+ `LivreRequest`, corps JSON des requêtes) |
| `exception` | Exceptions et traduction en codes HTTP |

## Endpoints

| Méthode | URL | Description |
|---|---|---|
| GET | `/api/livres` | Lister les livres |
| GET | `/api/livres/{id}` | Obtenir un livre |
| POST | `/api/livres` | Ajouter un livre |
| PUT | `/api/livres/{id}` | Modifier un livre |
| DELETE | `/api/livres/{id}` | Supprimer un livre |
| POST | `/api/livres/{id}/emprunter` | Emprunter un livre |
| POST | `/api/livres/{id}/retourner` | Retourner un livre |
| GET | `/api/livres/{id}/frais-retard?joursRetard=N` | Frais de retard en $ |

---

Questions d"examen A1
1.
-Modèle : la classe qui représente les données d’un livre
   - Contrôleur :reçoit les requêtes HTTP et appelle le service
   - Logique métier : dans livreService et contient les règles comme l’emprunt et le calcul des frais

2. GestionnaireErreurs

   Il transforme les exceptions en réponses HTTP
   - Livre introuvable : 404
   - Double emprunt : 409


3. stockage des donnee

    ils sont sotckee en mémoire dans LivreRepository
   Au redémarrage, les modifications sont perdues et les trois livres de départ sont recréés,donc pas de persistance.

4. le trajet de `POST /api/livres/1/emprunter` 

   - LivreController.emprunter(1) reçoit la requête
   - Il appelle `LivreService.emprunter(1)`
   - Le service appelle `trouver(1)` puis `repositoryfindById(1)`
   - Il vérifie que le livre est disponible
   - Le contrôleur retourne le livre en JSON avec le code 200
   - Si le livre est introuvable : 404
   -  déjà emprunté : 409

   
