#module abc

#private
void math() {
    printf("This is private math method");
}
#end_private

#public 
void callMath() {
    math();
}
#end_public