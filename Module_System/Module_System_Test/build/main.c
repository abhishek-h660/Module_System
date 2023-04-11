#include <stdio.h>
#include "./abc.h"
#include "./def.h"
#include "./test1.h"
int main()
{
    callMath();
    printf("\n");
    sayHello();
    printf("\n");
    callDependency();
    printf("\n");
    return 0;
}
