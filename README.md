Jerrylab - A free Java alternative to Tomlab 
--------------------------------------------

Jerrylab is a small, incomplete Java alternative to Tomlab. It contains classes for
handling LPs, ILPs and MILPs conveniently. It is especially designed for metabolic
network analysis.

A Linux-like system and the [eclipse](https://eclipse.org/) environment are recommended!

Available networks
------------------

Example networks are not included but are available at

- [BioModels](https://www.ebi.ac.uk/biomodels-main/)

- [BiGG](http://bigg.ucsd.edu/)

See Example*.java files where Jerrylab expects the networks.


Requirements
------------

- JSATBox for constrain handling

- CRNToolkit for network handling

- An ILP-solver, e.g. [SCIP](http://scip.zib.de/), which is free. See Example*.java files where Jerrylab expects the solver.