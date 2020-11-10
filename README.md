# postit


## TODO
* [x] onglet 1 -> note du sprint 1 à 5
* [ ] enchanges rating sprint avec le serveur
* [x] onglet 2 -> 5 catégories
* [x] onglet 3 -> actions
* [ ] enchanges actions avec le serveur
* [x] onglet 4 -> note de l'utilité de la rétro
* [ ] enchanges rating retro avec le serveur
* [x] bouton save (en bas à droite comme +)
* [x] bouton 'coeur' sur postit qui ne sont pas les miens + count
* [x] passer automatiquement à la ligne + aligner à gauche
* [ ] surbrillance selection de l'admin
* [ ] editable/supp par l'admin
* [ ] save que pour l'admin
* [x] user même largeur
* [x] couleurs plus fluo :D + ordonnée
* [x] couleur fond liste user
* [x] se reconnecter au click sur synchro
* [x] utiliser List plutôt que Set (qui fait de l'ordre alpha)
* [x] police du pre
* [x] card add/remove animation 
* [ ] card edit animation -> https://stackoverflow.com/questions/48652941/vue-click-animation-without-settimeout
* [x] précisions dans les notifs (valide ou erreur) 
* [x] color user 'me'

#Quarkus

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./gradlew quarkusDev
```

## Packaging and running the application

The application can be packaged using `./gradlew quarkusBuild`.
It produces the `postit-1.0-SNAPSHOT-runner.jar` file in the `build` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/lib` directory.

The application is now runnable using `java -jar build/postit-1.0-SNAPSHOT-runner.jar`.

If you want to build an _über-jar_, just add the `--uber-jar` option to the command line:
```
./gradlew quarkusBuild --uber-jar
```

## Creating a native executable

You can create a native executable using: `./gradlew build -Dquarkus.package.type=native`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./build/postit-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/gradle-tooling#building-a-native-executable.