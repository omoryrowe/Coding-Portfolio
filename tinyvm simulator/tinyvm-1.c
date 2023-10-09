// Omory Rowe Programming Project
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MEMORY_SIZE 128
#define DATA_MEMORY_SIZE 10

typedef struct {
  int op;
  int addr;
} InstructionRegister;

int PC;       // Program Counter
int A;        // Accumulator
int negCheck; // Makes sure INPUT > 0

int size = 13;
InstructionRegister IR;   // Instruction Register
int IM[13];               // Instruction Memory
int DM[DATA_MEMORY_SIZE]; // Data Memory

// File Line Counter
int getFileNum(FILE *file) {
  char codeTemp[10];
  int count = 0;
  if (file == NULL) {
    printf("ASM code file is not opening...");
    exit(0);
  }

  // caluclate the number of line
  while (fgets(codeTemp, 10, file) != NULL)
    count++; // increment count

  fclose(file);
  return count; // return count
}

void fetch() {
  IR.op = IM[PC];
  IR.addr = IM[PC + 1];
  PC += 2;
}

int DMindex = 0;

void execute(FILE *out) {

  switch (IR.op) {
  case 1: // LOAD
    fprintf(out, "\n /* Loading from address [%d]... */ \n", IR.addr);
    A = IR.addr;

    break;
  case 2: // ADD

    fprintf(out,
            "\n /ADDing accumulator and value obtained from address [%d]/ \n",
            IR.addr);
    A += IR.addr;

    break;
  case 3: // STORE

    fprintf(out, "\n /* STORE-ing accumulator to memory location %d */ \n",
            DMindex);
    DM[DMindex] = A;
    DMindex++;

    break;
  case 4: // SUB

    fprintf(
        out,
        "\n /SUBtracting accumulator and value obtained from address [%d]/ \n",
        DMindex);
    A -= DM[DMindex];

    break;
  case 5: // IN

    printf("\nEnter an INPUT value: ");
    while (1) {
      scanf("%d", &negCheck);
      if (negCheck > 0) {
        A = negCheck;
        break;
      } else {
        printf("\n/Negative values aren't allowed. Enter a positive value:/\n");
      }
    }

    break;
  case 6: // OUT

    fprintf(out, "\n /*SCREEN OUTPUT: %d */ \n", A);

    break;
  case 7: // HALT

    fprintf(out, "\n Program complete.\n");
    exit(0);

    break;
  case 8: // JMP

    fprintf(out, "\n Execute JMP \n");
    PC = IR.addr;

    break;
  case 9: // SKIPZ

    fprintf(out, "\n /Skipping the next instruction/ \n");
    if (A == 0) // if it is skip next instruction
      PC += 2;

    break;
  case 10: // SKIPG

    fprintf(out, "\n /Skipping the next instruction/ \n");
    if (A > 0)
      PC += 2;

    break;
  case 11: // SKIPL

    fprintf(out, "\n /Skipping the next instruction/ \n");
    if (A < 0)
      PC += 2;

    break;
  default:
    // Invalid opcode
    break;
  }
}

int main(int argc, char *argv[]) {
  size = getFileNum(fopen(argv[1], "r"));
  FILE *out;
  out = fopen("out.txt", "w");
  // Check the command-line arguments
  // if (argc != 2) {
  //   printf("Usage: %s input_file\n", argv[0]);
  //   return 1;
  // }

  // Reads the file
  FILE *file = fopen(argv[1], "r");
  char fileArr[10];
  int i = 0;
  if (file == NULL) {
    printf("ASM code file is not opening...");
    exit(0);
  }

  while (fgets(fileArr, sizeof(size), file) != NULL) {

    if ((fileArr == "\n" || fileArr != " ")) {
      if (isdigit(fileArr[0]) && (int)fileArr[2] != 0) {
        IM[i] = atoi(&fileArr[0]);
        IM[i + 1] = atoi(&fileArr[2]);
        i += 2; // increment
      }
    }
  }

  fclose(file);

  // Initialize the Program Counter
  PC = 0;

  // Run the fetch-execute cycle until the HLT instruction is encountered
  int executionVariable = 1;
  while (executionVariable) {
    fetch();
    execute(out);

    // Check for HLT instruction to halt the program
    if (IR.op == 7) {
      executionVariable = 0;
    }

    // Print the current state after each instruction execution
    fprintf(out, "PC=%d | A=%d | DM=[", PC, A);
    for (int i = 0; i < DATA_MEMORY_SIZE; i++) {
      fprintf(out, "%d", DM[i]);
      if (i < DATA_MEMORY_SIZE - 1) {
        fprintf(out, ", ");
      }
    }
    fprintf(out, "]\n");
  }

  fclose(out);

  return 0;
}