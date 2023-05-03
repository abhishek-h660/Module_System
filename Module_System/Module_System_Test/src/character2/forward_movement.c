##module character2

#include <stdio.h>

##private
void forwardMovementC2() {
    printf("character2's forward movement\n");
}
##end_private

##public 
void callForwardMovementC2() {
    forwardMovementC2();
}
##end_public