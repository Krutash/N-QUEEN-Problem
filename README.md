# N-QUEEN-Problem
This is implementation of N Queen Problem in Java. It incorporates Domain Pruning. Console based input.

After initializing and defining all the data structures and variables, we call “backtrack” method
which starts back tracking with first queen and first column. Before assigning a row position we
perform constraint propagation by calling “ForwardCheck” method. We do this forward check to
eliminate positions which are already under attack by other queens. This method uses
“ThreatArray” to check whether position is already under attack or not. Every time we perform a
forward check, if the position is not under attack by any previously assigned queen, we perform
follwing things:
a. We update “ThreatArray” by calling “markPlace” method and passing -1 as an argument.
b. We call “isSolutionPossible” method which iterates over next columns to check whether
any assignment of queen in future becomes impossible. On failure we revert the changes
done on “ThreatArray” by calling “markPlace” method again and passing 1 as an
argument.
9
c. Forward check only assigns the position to current queen if there is at least one position
available for each of the future queen.
d. On successful assignment we backtrack again on next queen and next column.
