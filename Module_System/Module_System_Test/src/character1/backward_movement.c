##module character1

#include <stdio.h>

##private
void backwardMovementC1() {
    printf("Character1's backward movement!\n");
}
##end_private

##public
void callBackwardMovementC1() {
    backwardMovementC1();
}
##end_public