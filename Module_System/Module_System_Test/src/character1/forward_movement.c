##module character1

#include <stdio.h>

##private
void forwardMovementC1() {
    printf("character1's forward movement\n");
}
##end_private

##public 
void callForwardMovementC1() {
    forwardMovementC1();
}
##end_public