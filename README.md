# Advent of Code Helpers 🎄

Kotlin helper methods and extension functions for
solving [Advent of Code](https://adventofcode.com/) problems.

# Usage

This is deployed as a GitHub Package you can use in your own project. To do that, add the repo to your `build.gradle`:

```
repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/davidmerrick/advent-of-code-utils")
        credentials {
            username = System.getenv("GITHUB_ACTOR")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}
```

Add a dependency for it:
```
dependencies {

    implementation("io.github.davidmerrick.aoc:advent-of-code-utils:0.1.0-SNAPSHOT")
...
```

Next, generate a GitHub token that has `packages:read` scope.

At the time of this writing, you can do that by going to GitHub -> Account -> Settings -> Developer Settings.
[This link](https://github.com/settings/tokens) should also work.

In your project's environment variables, set `GITHUB_ACTOR` to your GitHub username, and `GITHUB_TOKEN` to the token you just generated.

You should now be able to use methods from this project. Happy coding.

# Versioning

This project uses [reckon](https://github.com/ajoberstar/reckon) for versioning.
By default, any new commits will result in minor version bumps.
To do a major version bump, prefix your commit message with `major:`.
