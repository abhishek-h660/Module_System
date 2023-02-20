FROM openjdk:11

WORKDIR /abhishek/

COPY . /abhishek/

RUN javac Configure.java
RUN javac compiler.java

CMD [ "java", "compiler", "/abhishek/Module_System_Test"]