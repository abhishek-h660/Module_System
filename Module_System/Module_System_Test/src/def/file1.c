##module def

#include <stdio.h>

##private
void printHello() {
    printf("Hello");
}
##end_private

##public
void sayHello() {
    printHello();
}
##end_public