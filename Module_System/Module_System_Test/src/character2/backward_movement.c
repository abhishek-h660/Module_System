##module character2

#include <stdio.h>

##private
void backwardMovementC2() {
    printf("Character2's backward movement!\n");
}
##end_private

##public
void callBackwardMovementC2() {
    backwardMovementC2();
}
##end_public