# Book management

A simple Rset API project for book management based on Spring Boot

# CI Integration

    - Adding our first workflow
All GitHub repositories come with Actions enabled by default. Browse to your repository, and you’ll see a tab labeled Actions. All you need to do is tell your repository to make use of Actions in your development process.

Actions run workflows, which are usually associated with specific stages in your development process, for example, a workflow that runs when a pull request opens. Inside workflows are jobs—the individual steps in a workflow. These workflows, and the jobs inside them, are defined in YAML-formatted files inside your repository in a directory called .github/workflows. In this project the workflow is triggered when a push or pull request is applied to master branch.  

    - Setup java and build with maven 
Let’s create a workflow to build the project when we open a new pull request or push to master branch. We create a YAML file to hold this workflow.
```yaml
    name: Java CI with Maven

    on:
      push:
        branches: [ "master" ]
      pull_request:
        branches: [ "master" ]
    jobs:
      build:
        runs-on: ubuntu-latest
    
        steps:
        - name: Checkout repository 
          uses: actions/checkout@v3
        - name: Set up JDK 11
          uses: actions/setup-java@v3
          with:
            java-version: '11'
            distribution: 'temurin'
            cache: maven
        - name: Build with Maven
          run: mvn -B package --file pom.xml
```

We can see our workflow has a descriptive name: Java CI with Maven. We next want to define when our workflow runs. We do this in the `on` block. We’ve specified two conditions, both qualified with a specific branch: master.

- Push : action will trigger if someone pushes to the master branch

- Pull request : action will trigger if someone opens a pull request from the master branch.

Below the `on` block is the jobs block, this contains the jobs to be executed by this workflow. We’ve got one job defined: build. Each job runs on a specific platform. We can currently choose from Linux, Windows, and macOS. In our case, we’re running our job on a container using Ubuntu Linux.

Every job has steps. These are the tasks the job will execute. If one step fails, then generally, the whole job will fail.

We’ve named each step we’re taking. The first step, called Checkout repository, checks out the current repository. In any job, you’ll usually need to do this as the first step, or at least one of the first steps. It makes use of a pre-packaged action: [actions/checkout@v2](https://github.com/actions/checkout). Prepackaged actions are provided by GitHub or community members and usually perform tasks that might otherwise use multiple steps or represent repetitive configuration. Here our actions/checkout basically performs a local git clone of the repository. You can usually pass options to the pre-packaged action to specify how it’ll execute that task.

Similarly, our second step, called Set up JDK 11, runs another prepackaged action: [actions/setup-java@v3](https://github.com/actions/setup-java). This action takes care of installing java inside the container running our job. We can see one of the action arguments here too. The with block tells Actions what java version to install and maven is also needed .

Our last step is the main part of our job. This step runs mvn build command to build the artifact. 