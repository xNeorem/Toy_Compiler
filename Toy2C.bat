rd main
clang-format --style=google -i %1
clang -pthread -lm -o main.exe %1
.\main