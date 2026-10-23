# Java Even/Odd — CI/CD with GitHub Actions and Docker Hub

A small Java 17 command-line app that classifies an integer as even or odd. Maven and JUnit 5 test the logic. GitHub Actions runs tests on pushes and pull requests to `main`; after tests pass, pushes/tags build and publish a Docker image to Docker Hub.

## Project structure

```text
java-even-odd-ci/
├── .github/workflows/ci-cd.yml
├── src/main/java/com/example/EvenOdd.java
├── src/test/java/com/example/EvenOddTest.java
├── pom.xml
├── Dockerfile
└── .dockerignore
```

## Run locally

Requirements: JDK 17+, Maven 3.9+, Docker (for container steps).

```bash
mvn clean verify
mvn -q package
java -jar target/even-odd-1.0.0.jar 8
java -jar target/even-odd-1.0.0.jar 7
```

Expected output:
```text
8 is Even
7 is Odd
```

The CLI rejects missing or non-integer arguments with a usage/error message and exit code 2.

## JUnit coverage

The test suite checks positive even/odd values, zero, negative even/odd values, and `Integer.MIN_VALUE` / `Integer.MAX_VALUE`.

Run only tests with:
```bash
mvn test
```

## Build and run the Docker image locally

The Dockerfile is multi-stage. The build stage runs `mvn clean verify`, so a Docker image cannot be built if JUnit tests fail.

```bash
docker build -t java-even-odd:local .
docker run --rm java-even-odd:local 8
docker run --rm java-even-odd:local 7
```

## Publish to Docker Hub with GitHub Actions

1. Push this project to a GitHub repository and make sure the default branch is named `main`.
2. Create a Docker Hub access token in Docker Hub account settings (use a read/write token).
3. In GitHub, open **Settings → Secrets and variables → Actions → New repository secret** and add:
   - `DOCKERHUB_USERNAME` — your Docker Hub username.
   - `DOCKERHUB_TOKEN` — the Docker Hub access token (not your account password).
4. Commit and push to `main`. The workflow runs tests first; only if they pass does the Docker job build and push `docker.io/<your-dockerhub-username>/java-even-odd:latest` and a SHA-tagged image.
5. Creating and pushing a Git tag such as `v1.0.0` also publishes a version-tagged image.

Pull and run after a successful workflow:
```bash
docker pull YOUR_DOCKERHUB_USERNAME/java-even-odd:latest
docker run --rm YOUR_DOCKERHUB_USERNAME/java-even-odd:latest 42
```

Replace `YOUR_DOCKERHUB_USERNAME` with your actual Docker Hub username.

## Pipeline flow

```text
Push / Pull Request
        |
        v
Checkout + Set up Java 17
        |
        v
Maven clean verify (JUnit 5)
        |
        +---- test failure ---> stop; do not publish
        |
        v
On push/tag only: Docker Buildx + Docker Hub login
        |
        v
Build image (Dockerfile reruns tests)
        |
        v
Push latest / version / SHA tag to Docker Hub
```

## Notes

- Pull requests run tests but do not push an image.
- Docker publishing requires the two GitHub Actions secrets above.
- This project is configured to publish the repository `java-even-odd` under your Docker Hub username. Change that image name in `.github/workflows/ci-cd.yml` if desired.
