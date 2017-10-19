You know what to do. Just open an Issue or a Pull Request - I will review it.

# Our branching model
The master branch is meant to be always the latest release with only tagged states. 

The dev branch contains the state of development. 

All features should be developed an in a "feature/-description-" branch that can be merged
into the dev branch. The same is with hotfixes: "hotfixes/-description_" with the difference that
hotfix branches can also merged directly into master.
