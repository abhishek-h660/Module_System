##module abc

#include <stdio.h>

##private
void math(int x, int y) {
    printf("Got x and y");
}
##end_private

##public 
void callMath() {
    math(6, 5);
}
##end_public