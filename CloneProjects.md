## Background ##

On xdocreport we choosed Git as SCM (Source Control Management).

Git is a DVCS (Distributed Version Control System) which means each developer has a clone of the repo on his local machine.

At start, developer must "clone" the shared repo making a local repo.

Commits occurs on local repo.

If a developer wants to share Its commits, he has to "push" Its commits to a remote repo (most of the time the repo he previously cloned).

If a developer wants to grad other people's commit,he has to :
  * Fetch other commits from remote repo : It will copy other people commits onto local repo.
  * Pull : It will merge local branch with other's people commits in the developer workspace.

DVCS has more different concepts compared to more traditionals CVCS  (such as CVS and SVN), but It has many advantages when It comes to deal with branches and sophisticated commit workflow.

## Clone and Import a project - Step by step ##
  * under eclipse follow this steps.
    * File -> Import -> Git -> Projects from Git

![http://wiki.xdocreport.googlecode.com/git/screenshots/ImportFromGit1.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ImportFromGit1.png)
  * Click on the "clone" button
![http://wiki.xdocreport.googlecode.com/git/screenshots/ImportFromGit2.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ImportFromGit2.png)
  * In the first entry field paste:
  * ` http://code.google.com/p/xdocreport/ ` if you want to grab the code as "anonymous"
  * **or** ` https://<yourgmailaccount>@code.google.com/p/xdocreport/ ` as a contributor.

Full instruction can be found here : http://code.google.com/p/xdocreport/source/checkout

![http://wiki.xdocreport.googlecode.com/git/screenshots/ImportFromGit3.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ImportFromGit3.png)

![http://wiki.xdocreport.googlecode.com/git/screenshots/ImportFromGit4.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ImportFromGit4.png)

![http://wiki.xdocreport.googlecode.com/git/screenshots/ImportFromGit5.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ImportFromGit5.png)

![http://wiki.xdocreport.googlecode.com/git/screenshots/ImportFromGit6.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ImportFromGit6.png)

![http://wiki.xdocreport.googlecode.com/git/screenshots/ImportProjectFromGit1.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ImportProjectFromGit1.png)

![http://wiki.xdocreport.googlecode.com/git/screenshots/ImportProjectFromGit2.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ImportProjectFromGit2.png)