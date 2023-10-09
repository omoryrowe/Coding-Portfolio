/*=============================================================================
| Calculating an 8, 16, or 32 bit
| checksum on an ASCII input file
|
| Author: Omory Rowe
| Language: C
|
| To Compile: gcc -o pa02 pa02.c
|
| To Execute: C -> ./pa02 inputFile.txt 8
| where inputFile.txt is an ASCII input file
| and the number 8 could also be 16 or 32
| which are the valid checksum sizes, all
| other values are rejected with an error message
| and program termination
|
| Note: All input files are simple 8 bit ASCII input
+=============================================================================*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

void getInputFileCheckSum(char *filename, int size);

void getCheckSum(int size, char *inputString, unsigned int long *check);

unsigned int long getBitMask(char *inputString, int checkSumSize);

int main(int argc, char **argv) {
  int i;
  int index = 0;
  int bitsize = 0;

  printf("\n");
  // stores text input
  char *fname = argv[1];
  FILE *file = fopen(fname, "r");

  char *temp = malloc(10000 * sizeof(char));
  char *input = malloc(10000 * sizeof(char));
  char c;
  int charCount = 0;

  while (1) {
    if (feof(file))
      break;
    c = fgetc(file);
    strcat(temp, &c);
  }

  for (i = 0; i < 10000; i++) {
    if (temp[i] >= 65 && temp[i] <= 122) {
      input[index] = temp[i];
      index++;
      charCount++;
    } else if (temp[i] == 0) {
      break;
    }
  }

  // printf("Input: %s\n", input);
  // printf("Character Count: %d\n", charCount);

  free(temp);
  free(input);
  fclose(file);

  // stores bitsize
  char *fname2 = argv[2];
  bitsize = atoi(fname2);
  if (bitsize == 8 || bitsize == 16 || bitsize == 32) {
    // printf("Bitsize = %d\n", bitsize);
  } else {
    fprintf(stderr, "Valid checksum sizes are 8, 16, or 32\n");
  }
  //compile
  getInputFileCheckSum(fname, bitsize);
}

void getInputFileCheckSum(char *filename, int size){
  FILE *input;
  
  int charVal = 0;
  int charCount = 0;

  int wordSize = size / 8 + 1;

  unsigned int long *checksum = calloc(1, sizeof(unsigned int long));

  char *charInput = malloc(wordSize * sizeof(char));

  int i = 0;
  int j = 0;
  int k = 0;

  input = fopen(filename, "r");

  if(input == NULL){
    printf("Unable to open file!\n");
    return;
  }
  
  while(charVal != EOF){
    if(wordSize - i == 1){
      charInput[i] = '\0';

      printf("%s", charInput);

      getCheckSum(size, charInput, checksum);

      j++;

      i = 0;

      if(j * (wordSize - 1) == 80)
        printf("\n");
    }else{
      charVal = fgetc(input);

      if(charVal == EOF){
        
        if(size == 16 || size == 32){
          
          if((wordSize - i) != 1 && (wordSize - i) != wordSize){
            k = 0;

            while((wordSize - i) != 1 && (wordSize - i) != wordSize){
              charInput[i] = 'X';
              i++;
              k++;
            }

            charInput[i] = '\0';
            printf("%s", charInput);

            getCheckSum(size, charInput, checksum);

            j++;

            charCount += k -1;

            i = 0;

            if(j * (wordSize - 1) == 80)
              printf("\n");
          }else{
            break;
          }
        }else{
          break;
        }
      }else{
        charInput[i] = charVal;
      }
      
      charCount++;
      i++;
        
    }
  }

  fclose(input);
  printf("\n");
  printf("%2d bit checksum is %8lx for all %4d chars\n", size, *checksum, charCount);
  free(checksum);
  free(charInput);
}

void getCheckSum(int size, char *inputString, unsigned int long *checksum){
  unsigned int long maskbit;

  maskbit = getBitMask(inputString, size);

  *checksum = maskbit + *checksum;
  *checksum = *checksum << (64 - size);
  *checksum = *checksum >> (64 - size);
}

unsigned int long getBitMask(char *inputString, int size){
  int i = 0;

  unsigned int long maskedBits;

  maskedBits = inputString[i];

  size -= 8;

  while(size != 0){
    maskedBits = (maskedBits << 8) + inputString[i + 1];
    size -= 8;
    i++;
  }

  return maskedBits;
}

/*=============================================================================
| I Omory Rowe (5123450) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+============================================================================*/
