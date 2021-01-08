#! /bin/bash

rm main
clang-format --style=google -i $1
clang -pthread -lm -o main $1
open -a Terminal.app ./main