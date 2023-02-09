[Back home](../README.md)
### Setup Gradle build script

You should be able to include it with the following in your `build.gradle`:
```gradle
repositories {
    //Maven repo for Vampirism
    maven {
        url = "https://maxanier.de/maven2"
    }
    
    //Maven repo for Werewolves
    maven {
        url = "https://maven.paube.de/releases" // for full versions
    }
    maven {
        url = "https://maven.paube.de/snapshots" // for snapshot version
    }
}
dependencies {
    implementation "de.teamlapen.vampirism:Vampirism:${mc_version}-${vampirism_version}"
    implementation "de.teamlapen.werewolves:Werewolves:${mc_version}-${werewolves_version}"
}
```

#### Choose a version

`${mc_version}` gets replaced by the current Minecraft version. (i.e. `1.16.5`)
`${vampirism_version}` gets replaced by the version of Vampirism you want to use (i.e `1.8.0`)
`${werewolves_version}` gets replaced by the version of Werewolves you want to use (i.e `0.5.6`)

These properties can be set in a file named `gradle.properties`, placed in the same directory as your `build.gradle`
file. Example `gradle.properties`:

```
mc_version=11.16.5
vampirism_version=1.8.0
werewolves_version=0.5.6
```

#### Rerun Gradle setup commands

Please re-import the gradle project

## Setting up the development environment
If you would like to compile your own versions or even contribute to Werewolves's development you need to setup a dev environment.
The following example instructions will setup IntelliJ (Free community edition or Non-Free Ultimate edition). If you already have a setup or want to use another IDE, jump [here](#setting-up-werewolves-in-another-environment).

#### IntelliJ
1. Make sure you have the Java **JDK** (minimum Java 8) as well as the IntelliJ IDE installed.
2. If you want to contribute to the development (via pull requests), fork Werewolves on Github.
3. (Optionally) Install Git, so you can clone the repository and push changes.
4. Clone (`git clone https://github.com/TeamLapen/Werewolves`) or [download](https://github.com/TeamLapen/Werewolves/archive/14) Werewolves to a new "Werewolves" folder.
5. In IntelliJ use `New...` -> `New from Version Control` -> Fill out repo, directory and name
6. After cloning is done IntelliJ offers you to import a unlinked Gradle Project. Click this.
7. Run `genIntellijRuns` and edit the run config to use the correct module


That's it.

#### Eclipse or other IDEs
If you would like to setup Werewolves in another way or another IDE, you should pay regard to the following points.  
1. Make sure `src/main/java` ist marked as source folders and `src/main/resources` is marked as resource folders.  
2. Werewolves might have a few dependencies (e.g. Vampirism), which are specified in the gradle files and should be automatically downloaded and added when you run `ideaModule` or `eclipse`.  
3. Werewolves requires at least Java 8 

## Using JetBrains Runtime

If you want to use better hot-reloading take a look at https://forge.gemwire.uk/wiki/Hotswap

The `runClientJBR` and `runServerJBR` tasks are configured witht he jvm `AllowEnhancedClassRedefinition` flag set to true.