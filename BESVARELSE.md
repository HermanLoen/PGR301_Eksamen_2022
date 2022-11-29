### Oppgave 3 

* Gå til **Settings/Branches** og se etter seksjonen "Branch Protection Rules".
* Velg _"Add"_
* Velg _"main"_ Som branch i branch name pattern
* Velg _"require a pull request before merging"_
* velg _"require approval"_, sett antall til 1
* Velg _"Require status check to pass before merging"_
* I søkefeltet skriv inn teksten _"build"_ som skal la deg velge "GitHub Actions".
* Lagre endringene ved å trykke på "_Save changes_"
* Nå kan vi ikke Merge en pull request inn i Main uten at status sjekken er i orden. Det betyr at vår Workflow har kjørt OK.
* Ingen i teamet kan heller "snike seg unna" denne sjekken ved å comitte kode rett på main branch.