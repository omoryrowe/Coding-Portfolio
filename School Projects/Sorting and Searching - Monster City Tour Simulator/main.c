/* This program is written by: Omory Rowe */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "leak_detector_c.h"

struct trie {
  int freq;
  int sum_freq;
  int cur_max_freq;

  struct trie *next[26];
};

struct trie *init();
void insert(struct trie *tree, char word[], int k, int count);
int search(struct trie *tree, char word[], int k);
void printTrie(struct trie *tree, char cur[]);
struct trie *del(struct trie *root, char key[], int depth);
int isEmpty(struct trie *root);
void query(struct trie *tree, char word[], int k);

void freeDictionary(struct trie *tree);

int main() {
  atexit(report_mem_leak);
  struct trie *myDictionary = init();
  int numLoops;
  int input;
  int count;
  char* word = malloc(2000000 * sizeof(char));

  scanf("%d", &numLoops);
  for (int x = 0; x < numLoops; x++) {
    scanf("%d", &input);
    if (input == 1) {
      scanf("%s", word);
      scanf("%d", &count);
      insert(myDictionary, word, 0, count);
    } else {
      scanf("%s", word);
      query(myDictionary, word, 0);
    }
  }

  word[0] = '\0';
  freeDictionary(myDictionary);
  free(word);
  return 0;
}

// DONE
struct trie *init() {

  // Create the struct, not a word.
  struct trie *myTree = malloc(sizeof(struct trie));
  myTree->freq = 0;
  myTree->sum_freq = 0;
  myTree->cur_max_freq = 0;

  // Set each pointer to NULLL.
  int i;
  for (i = 0; i < 26; i++)
    myTree->next[i] = NULL;

  // Return a pointer to the new root.
  return myTree;
}


void insert(struct trie *tree, char word[], int k, int count) { 

  int incomingIndex = word[k] - 'a';

  if (k == 0) {
    tree->sum_freq = tree->sum_freq + count;

    if (count > tree->cur_max_freq){
      tree->cur_max_freq = tree->cur_max_freq + count;
    }
  
  }

  if (tree->next[incomingIndex] == NULL) {
    tree->next[incomingIndex] = init();
    tree->next[incomingIndex]->sum_freq = tree->next[incomingIndex]->sum_freq + count;
    if (tree->next[incomingIndex]->cur_max_freq < tree->next[incomingIndex]->sum_freq) {
      tree->next[incomingIndex]->cur_max_freq = tree->next[incomingIndex]->sum_freq;
    }
  } else {

    if (tree->cur_max_freq > tree->next[incomingIndex]->cur_max_freq && k >= 1) {
      if(tree->next[incomingIndex]->cur_max_freq < count){
        tree->next[incomingIndex]->cur_max_freq = count;
      } 
      tree->next[incomingIndex]->sum_freq =tree->next[incomingIndex]->sum_freq + count;
    } else {
      if (tree->next[incomingIndex]->next[word[k+1] - 'a']) {
        tree->next[incomingIndex]->cur_max_freq = tree->next[incomingIndex]->cur_max_freq + count;
      } 
      tree->next[incomingIndex]->sum_freq =tree->next[incomingIndex]->sum_freq + count;
    }
  }

  if (k + 1 == strlen(word)) {

    tree->next[incomingIndex]->freq = tree->next[incomingIndex]->cur_max_freq;

    tree->next[incomingIndex]->cur_max_freq = 0;
    return;
  } else {
    insert(tree->next[incomingIndex], word, k + 1, count);
  }
}


void freeDictionary(struct trie *tree) {

  int i;
  for (i = 0; i < 26; i++)
    if (tree->next[i] != NULL)
      freeDictionary(tree->next[i]);

  free(tree);
}


void printTrie(struct trie *tree, char cur[]) {

  // Stop!
  if (tree == NULL)
    return;

  // Print this node, if it's a word.
  if (tree->freq > 0) {
    printf("Checking frequency: %s\n", cur);
  }

  // Safer if we store this.
  int len = strlen(cur);

  // Recursively print all words in each subtree,
  // in alphabetical order.
  int i;
  for (i = 0; i < 26; i++) {
    cur[len] = (char)('a' + i);
    cur[len + 1] = '\0';
    printTrie(tree->next[i], cur);
  }
}

void query(struct trie *tree, char word[], int k) {

  // If the next place doesn't exist, word is not a word.
  int incomingIndex = word[k] - 'a';

  if (tree->next[incomingIndex] == NULL) {
    printf("unknown word\n");
    return;
  }

  if (k + 1 == strlen(word)) {
    int temp = tree->next[incomingIndex]->cur_max_freq;
    int min;
    int ans;
    int count = 0;
    int index = 0;
    
    if (isEmpty(tree->next[incomingIndex])) {
      printf("unknown word\n");
      return;
    } else {
      for (int i = 0; i < 26; i++) {
        if (tree->next[incomingIndex]->next[i] != NULL) {
          ans = abs(temp - tree->next[incomingIndex]->next[i]->sum_freq);
          if (ans == 0) {
            printf("%c", i + 97);
          }
          
        }
        
      }
      printf("\n");
    }
  } else {
    query(tree->next[incomingIndex], word, k + 1);
  }
}

// return 1 if full, 0 if empty
int isEmpty(struct trie *root) {
  for (int i = 0; i < 26; i++)
    if (root->next[i])
      return 0;
  return 1;
}