---
layout: page
title: Wang Jun Hao's Project Portfolio Page
---

## Project: MediBook

MediBook is a **desktop medical records software** targeting **doctors and administrative staffs** in clinics or hospitals to 
help **manage patient details.** It is **optimized for use via a Command Line Interface** (CLI) while 
still having the benefits of a Graphical User Interface (GUI), implemented using JavaFX.

Main highlights of MediBook include:
* Ability to keep track of patients’ administrative and medical details
* Ability to store medical consultation notes for each patient
* Displaying of health metric charts such as `BMI`
* Login accounts for doctors for a more streamlined experience (e.g. auto-fill name and medical IC)

<br/>

**Given below are my contributions to the project.**

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=wang-jun-hao)

* **New Feature**: Added the ability to create/edit/delete medical notes for each patient
  * What it does: Allows users to create, edit and delete medical notes belonging to a patient, when logged in as a doctor
  and viewing a patient profile page. Autofills doctor's and patient's information.
  * Justification: This is a fundamental feature of a medical records software as it allows doctors to store text notes 
  following a consultation with a patient. By only allowing these commands when logged in and 
  viewing a patient profile page, the commands themselves can be streamlined as information about the doctor and patient 
  are automatically retrieved.
  * Highlights: This enhancement required the implementation of many underlying classes, such as `Doctor` and
  `MedicalNoteList`, and established a connection between these classes and `Patient`. It required an in-depth design analysis 
  to reduce coupling and avoid cyclic dependency. The autofilling feature required the implementation of `ModelContext` 
  to retrieve these information.

* **New Feature**: Doctor verification when editing or deleting medical notes
  * What it does: Restricts editing and deleting of medical notes to the doctor who authored them.
  * Justification: Medical notes are important sensitive information that affect treatment decisions. The integrity of
  medical notes should be protected. This feature prevents tampering of medical notes by other doctors.
  * Highlights: This feature interacts with login and model context features to check for account match.
  
* **New Feature**: Display of medical notes in reverse chronological order
  * What it does: Sorts medical notes in reverse chronological order and display them in patient profile page

* **New Feature**: Added support for `BMI` for patients
  * What it does: Auto computation and storing of a patient's `BMI` from `Height` and `Weight` fields if present.

* **Enhancements to existing features**:
  * Updated help command window
  
* **Documentation**:
  * User Guide:
    * Added documentation for the features `addnote`, `editnote` and `deletenote`
    * Introduction of UG    
    * Re-ordered features so that related features are back-to-back
    * Added elaboration on `login` feature
  * Developer Guide:
    * Added implementation details of `addnote`, `editnote` and `deletenote` features.

* **Project management**:
  * Managed releases `v1.2.1` - `v1.4` (3 releases) on GitHub
  * Created and wrapped up milestones `v1.1` - `v1.4` on GitHub
  * Created branches on team repo for integration

* **Contributions to team-based tasks**
  * Liaised with tutor on project matters
  * Created platform for team notes
  * Release and milestones management
  * Managed build number, javaFx version and enabled assertions in gradle

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#12](), [\#32](), [\#19](), [\#42]()
  * Reported bugs and suggestions for other teams in the class (examples: [1](), [2](), [3]())
