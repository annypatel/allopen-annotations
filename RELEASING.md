Releasing
========

 1. Change the version in `gradle.properties` to a non-SNAPSHOT version.
 2. Update the `README.md` with the new version.
 3. `git commit -m "Prepare for release X.Y.Z"` (where X.Y.Z is the new version)
 4. `git tag -a X.Y.X -m "Version X.Y.Z"` (where X.Y.Z is the new version)
 5. `./gradlew clean publishMavenPublicationToReleaseRepository`
 6. Update the `gradle.properties` to the next SNAPSHOT version.
 7. `git commit -m "Prepare next development version"`
 8. `git push && git push --tags`
 9. Visit [Sonatype Nexus](https://oss.sonatype.org/) and promote the artifact.
