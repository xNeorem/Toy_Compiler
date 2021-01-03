#! /bin/bash

clang -pthread -lm -o main $1
./main