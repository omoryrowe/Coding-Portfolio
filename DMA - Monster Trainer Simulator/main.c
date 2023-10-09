#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "leak_detector_c.h"

typedef struct monster {
  char *name;
  char *element;
  int population;
} monster;

typedef struct region {
  char *name;
  int monster_cnt;
  int total_population;
  monster **monsters;
} region;

typedef struct itinerary {
  int region_cnt;
  region **regions;
  int captures;
} itinerary;

typedef struct trainer {
  char *name;
  itinerary *visits;
} trainer;

monster *makeMonster(char *name, char *element, int population) {
  monster *tempMonster = (monster *)malloc(sizeof(monster));
  tempMonster->name = malloc(51 * sizeof(char));
  strcpy(tempMonster->name, name);
  tempMonster->element = malloc(51 * sizeof(char));
  strcpy(tempMonster->element, element);
  tempMonster->population = population;
  return tempMonster;
}

monster **readMonsters(int *monsterCount) {
  char *mName = malloc(51 * sizeof(char));
  char *mElement = malloc(51 * sizeof(char));
  int mPopulation;
  monster **tempMonsterArray = (monster **)malloc(*monsterCount * sizeof(monster *));
  for (int i = 0; i < *monsterCount; i++) {
    scanf("%s", mName);
    scanf("%s", mElement);
    scanf("%d", &mPopulation);
    tempMonsterArray[i] = makeMonster(mName, mElement, mPopulation);
  }
  free(mName);
  free(mElement);
  return tempMonsterArray;
}

region **readRegions(int *countRegions, monster **monsterList, int monsterCount) {
  region **regionList = malloc(*countRegions * sizeof(region *));
  char *nameMatch = malloc(51 * sizeof(char));
  for (int i = 0; i < *countRegions; i++) {
    regionList[i] = malloc(sizeof(region));
    // fills up region names with memory and values
    regionList[i]->name = malloc(51 * sizeof(char));
    scanf("%s", regionList[i]->name);
    // fills up region monster count with memory and values
    scanf("%d", &regionList[i]->monster_cnt);
    // fill up region monsters with memory and values
    regionList[i]->monsters = malloc(regionList[i]->monster_cnt * sizeof(monster *));

    for (int j = 0; j < regionList[i]->monster_cnt; j++) {
      // fills up the names of the region monsters
      scanf("%s", nameMatch);
      // searches and stores the population and monsters from monsterList if the
      // monster inputted exists
      for (int k = 0; k < monsterCount; k++) {
        if ((strcmp(nameMatch, monsterList[k]->name)) == 0) {
          regionList[i]->monsters[j] = monsterList[k];
          regionList[i]->total_population += monsterList[k]->population;
        }
      }
    }
  }
  free(nameMatch);
  return regionList;
}

trainer *loadTrainers(int *trainerCount, region **regionList, int countRegions) {
  trainer *trainerList = malloc(*trainerCount * sizeof(trainer));
  char *nameMatch = malloc(51 * sizeof(char));
  for (int i = 0; i < *trainerCount; i++) {
    trainerList[i].name = malloc(51 * sizeof(char));
    trainerList[i].visits = malloc(sizeof(itinerary));
    
    // fills up the names of trainers
    scanf("%s", trainerList[i].name);
    ////fills up the iteniraries
    // fills up the captures
    scanf("%d", &trainerList[i].visits->captures);
    // fills up the region count
    scanf("%d", &trainerList[i].visits->region_cnt);
    trainerList[i].visits->regions = malloc(trainerList[i].visits->region_cnt * sizeof(region *));
    // fills up regions
    for (int j = 0; j < trainerList[i].visits->region_cnt; j++) {
      scanf("%s", nameMatch);
      for (int k = 0; k < countRegions; k++) {
        if ((strcmp(nameMatch, regionList[k]->name)) == 0) {
          trainerList[i].visits->regions[j] = regionList[k];
        }
      }
    }
  }
  free(nameMatch);
  return trainerList;
}

void processInputs(monster **monsterList, int monsterCount, region **regionList, int regionCount, trainer *trainerList, int trainerCount) {
  for (int i = 0; i < trainerCount; i++) { // trainers
    printf("%s\n", trainerList[i].name);
    for (int j = 0; j < trainerList[i].visits->region_cnt; j++) { // regions
      printf("%s\n", trainerList[i].visits->regions[j]->name);
      for (int k = 0; k < trainerList[i].visits->regions[j]->monster_cnt; k++) { // monsters
        double finalNum = (((double)trainerList[i].visits->regions[j]->monsters[k]->population / (double)trainerList[i].visits->regions[j]->total_population) * trainerList[i].visits->captures);
        if (fmod(finalNum, 1.0) >= .50) {
          finalNum = ceil(finalNum);
        } else {
          finalNum = floor(finalNum);
        }
        if(finalNum != 0){
          printf("%.0f-%s\n", finalNum, trainerList[i].visits->regions[j]->monsters[k]->name);
        }else{
          continue;
        }
      }
    }
    printf("\n");
  }
}

void releaseMemory(monster** monsterList, int monsterCount, region** regionList, int regionCount, trainer* trainerList, int trainerCount ){
  for(int i = 0; i < monsterCount; i++){
    free(monsterList[i]->name);
    free(monsterList[i]->element);
    free(monsterList[i]);
  }
  free(monsterList);
  
  for(int j = 0; j < regionCount; j++){
    free(regionList[j]->name);
    free(regionList[j]->monsters);
    free(regionList[j]);
  }
  free(regionList);
  
  for(int k = 0; k < trainerCount; k++){
    free(trainerList[k].name);
    free(trainerList[k].visits->regions);
    free(trainerList[k].visits);
  }
  free(trainerList);
}

int main() {
  atexit(report_mem_leak);
  int mCount;
  int rCount;
  int tCount;

  scanf("%d", &mCount);
  monster **monsterList = readMonsters(&mCount);

  scanf("%d", &rCount);
  region **regionList = readRegions(&rCount, monsterList, mCount);

  scanf("%d", &tCount);
  trainer *trainerList = loadTrainers(&tCount, regionList, rCount);

  // processes inputs
  processInputs(monsterList, mCount, regionList, rCount, trainerList, tCount);

  //frees memory
  releaseMemory(monsterList, mCount, regionList, rCount, trainerList, tCount);

  return 0;
}