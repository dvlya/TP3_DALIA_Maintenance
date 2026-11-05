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

> Questions d’examen A3 — Tests Swagger

| # | Appel | Résultat attendu | Signification du code HTTP |
|---|---|---|---|
| 1 | `GET /api/livres` | 200, liste de trois livres au démarrage |liste affichee|
| 2 | `POST /api/livres` avec un livre valide | 201: livre créé avec un identifiant | livre créée | 
| 3 | `POST /api/livres` avec un titre vide | 400: Le titre est obligatoire  | données invalides |
| 4 | `POST /api/livres/1/emprunter` deux fois |409:  le livre est déjà emprunté  |  409 : conflit car deja emprunter|
| 5 | `GET /api/livres/2/frais-retard?joursRetard=100` | 200, frais de 20 $ | calcul réussi |


## Partie E — Livraison et test dans Codespaces

### Image publiée

L’image est publiée sur GHCR avec le tag Git de la version :

`ghcr.io/dvlya/biblio-api:v1.0.0`

### Commandes utilisées

```bash
docker login ghcr.io -u dvlya
docker pull ghcr.io/dvlya/biblio-api:v1.0.0
docker run -d --name biblio-release -p 8080:8080 ghcr.io/dvlya/biblio-api:v1.0.0
curl -i http://localhost:8080/api/livres
```

### Résultat

La requête retourne les trois livres initiaux au format JSON.

Swagger est accessible depuis l’adresse du port 8080 redirigé
par Codespaces
### Version initiale

La version `1.0.0` correspond à la première livraison stable de l’API.
Le tag Git `v1.0.0` déclenche les tests, la publication de l’image
sur GHCR et la création de la Release avec le JAR.

### Liens

- [GitHub Actions](https://github.com/dvlya/TP3_DALIA_Maintenance/actions)
- [Pull Requests](https://github.com/dvlya/TP3_DALIA_Maintenance/pulls)
- [Releases](https://github.com/dvlya/TP3_DALIA_Maintenance/releases)
- [Image GHCR](https://github.com/users/dvlya/packages/container/package/biblio-api)

