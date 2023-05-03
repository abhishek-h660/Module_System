##module character3

#include <stdio.h>

##private
void forwardMovementC3() {
    printf("character3's forward movement\n");
}
##end_private

##public 
void callForwardMovementC3() {
    forwardMovementC3();
}
##end_public