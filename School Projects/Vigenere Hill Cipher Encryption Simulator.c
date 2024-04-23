/*=============================================================================
| Encrypting a plaintext file using the Vigenere cipher
|
| Author: Omory Rowe
| Language: C
|
| To Compile: gcc -o pa01 pa01.c
|
| To Execute: C -> ./pa01 kX.txt pX.txt
| where kX.txt is the keytext file
| and pX.txt is plaintext file
|
| Note: All input files are simple 8 bit ASCII input
|
|
+=============================================================================*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

int** multiply(int** a, int** b, int x, int y);

int main(int argc, char **argv) { 
  int i;
 
  char* fname = argv[ 1 ]; 
  FILE *file = fopen( fname, "r" ); 

  //main program
  //stores matrix
  int matrixNum;
  int j;
  fscanf(file, "%d", &matrixNum);
  int** key = malloc(matrixNum * sizeof(int*));
  for(i = 0; i < matrixNum; i++)
    key[i] = malloc(matrixNum * sizeof(int));
  printf("\nKey matrix:");
  for(i = 0; i < matrixNum; i++){
    printf("\n");
    for(j = 0; j < matrixNum; j++){
      fscanf(file, "%d", &key[i][j]);
      if(key[i][j] / 100 >= 1){
        printf(" %d", key[i][j]);
      }else if(key[i][j] / 10 >= 1){
        printf("  %d", key[i][j]);
      }else{
        printf("   %d", key[i][j]);
      }
    }
  }
  fclose( file );
  
  //stores plaintext
  char* fname2 = argv[ 2 ];
  FILE *file2 = fopen( fname2, "r" );
  char* temp = malloc(10000 * sizeof(char));
  char c;
  while(1){
    c = fgetc(file2);
    strcat(temp, &c);
    if(feof(file2))
      break;
  }
  char* plaintext = malloc(100000 * sizeof(char));
  int index = 0;
  int charCount;
  for(i = 0; i < 10000; i++){
    if(temp[i] >= 97 && temp[i] <= 122){
      plaintext[index] = temp[i];
      index++;
    }else if(temp[i] >= 65 && temp[i] <= 90){
      plaintext[index] = temp[i] + 32;
      index++;
    }else if(temp[i] == 0){
      break;
    }
  }
  charCount = strlen(plaintext);
  while((charCount % matrixNum) != 0){
    plaintext[index] = 'x';
    index++;
    charCount++;
  }
  int pindex = 0;
  printf("\n\nPlaintext:");
  for(i = 0; i < strlen(plaintext); i++){
    if((pindex % 80) == 0)
      printf("\n");
    if(plaintext[pindex] >= 97 && plaintext[pindex] <= 122)
      printf("%c", plaintext[pindex]);
    pindex++;
  }
  fclose( file2 );
  
  //matrix multiplication
  char* ciphertext = malloc(100000 * sizeof(char));
  int** tempMatrix;
  int** M = malloc(matrixNum * sizeof(int*));
  for(i = 0; i < matrixNum; i++)
    M[i] = malloc(sizeof(int));
  int k;
  int mCount = 0;
  char tempChar;
  
  for(i = 0; i < (charCount / matrixNum); i++){
    for(k = 0; k < matrixNum; k++){
      M[k][0] = plaintext[mCount] - 97;
      mCount++;
      //printf("Char index: %d\n", M[k][0]);
    }
    tempMatrix = multiply(key, M, matrixNum, matrixNum);
    for(j = 0; j < matrixNum; j++){
      tempChar = tempMatrix[j][0] + 97;
      strcat(ciphertext, &tempChar);
    }
  }

  //prints ciphertext
  int cindex = 0;
  printf("\n\nCiphertext:");
  for(i = 0; i < strlen(ciphertext); i++){
    if((cindex) % 160 == 0)
      printf("\n");
    if(ciphertext[cindex] >= 97 && ciphertext[cindex] <= 122)
      printf("%c", ciphertext[cindex]);
    cindex++;
  }
  printf("\n"); //remove if need be
  free(temp);
  free(plaintext);
  free(ciphertext);
  for(i = 0; i < matrixNum; i++){
    free(M[i]);
    free(key[i]);
    free(tempMatrix[i]);
  }
  free(M);
  free(key);
  free(tempMatrix);
  return 0; 
} 

int** multiply(int** a, int** b, int x, int y){
  if(x != y){
    return 0;
  }else{
    int** c = malloc(y * sizeof(int*));
    for(int z = 0; z < y; z++)
      c[z] = malloc(sizeof(int));
    for(int i = 0; i < x; i++){
      for(int j = 0; j < 1; j++){
        c[i][j] = 0;
        for(int k = 0; k < x; k++){
          //printf("a[i][k]: %d\n", a[i][k]);
          //printf("b[k][j]: %d\n", b[k][j]);
          c[i][j] += a[i][k] * b[k][j];
          //printf("After muliplication: %d\n", c[i][j]);
        }
        c[i][j] %= 26;
        //printf("Final Number: %d\n\n", c[i][j]);
      }
    }
    return c;
  }
}

/*=============================================================================
| I Omory Rowe 5123450 affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/
