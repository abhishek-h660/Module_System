#include <stdio.h>
#include "./character1.h"
#include "./character2.h"
#include "./test1.h"
int main()
{
    callForwardMovementC1();
    
    callBackwardMovementC1();
    callForwardMovementC2();
    callBackwardMovementC2();
    callForwardMovementC3();
    callDependency();
    return 0;
}
