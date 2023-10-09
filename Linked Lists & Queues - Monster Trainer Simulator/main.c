/* This program is written by: Omory Rowe */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "leak_detector_c.h"
#define EMPTY -1

typedef struct customer {
  char *name;
  int numServices;
  int timeEntering;
  int lineNumber;
} customer;

typedef struct node {
  struct customer *customers;
  struct node *next;
} node;

typedef struct queue {
  struct node *front;
  struct node *back;
} queue;

int enqueue(queue *qPtr, node *cust);
customer *dequeue(queue *qPtr);
int empty(queue *qPtr);
int peek(queue *qPtr);
void init(struct queue *qPtr);
void displayQueue(struct queue *MyQueuePtr);

int enqueue(queue *qPtr, node *cust) {
  node *temp = malloc(sizeof(node));

  if (temp != NULL) {
    temp->customers = cust->customers;
    temp->next = NULL;

    if (qPtr->back != NULL)
      qPtr->back->next = temp;

    qPtr->back = temp;

    if (qPtr->front == NULL)
      qPtr->front = temp;
    
    return 1;
  } else {
    return 0;
  }
}

customer *dequeue(queue *qPtr) {
  customer *s;
 
  if (qPtr->front == NULL)
    return NULL;

  s = qPtr->front->customers;
  node *temp = qPtr->front;

  qPtr->front = qPtr->front->next;

  free(temp);

  if (qPtr->front == NULL)
    qPtr->back = NULL;

  return s;
}

int isEmpty(queue* q) {
    return q->front == NULL;
}

int peek(queue *qPtr) {
  if (qPtr->front != NULL) {
    return qPtr->front->customers->lineNumber; // cannot return the whole customer, so this returns the line number instead
  } else {
    return EMPTY;
  }
}

void init(struct queue *qPtr) {
  qPtr->front = NULL;
  qPtr->back = NULL;
}

void displayQueue(struct queue *MyQueuePtr) {
  struct node *t = MyQueuePtr->front;
  printf("\n%s %d", t->customers->name, t->customers->timeEntering);
}

node *makeCustomer(char *cName, int cServices, int cTimeEnt, int cLN) {
  //creates a temporary node, then fills up all the data from the parameters passed to the function into a temporary customer and stores it in the node to be returned
  node *tempNode = malloc(sizeof(node));
  customer *tempCustomer = malloc(sizeof(customer));
  tempCustomer->name = malloc(51 * sizeof(char));
  strcpy(tempCustomer->name, cName);
  tempCustomer->numServices = cServices;
  tempCustomer->timeEntering = cTimeEnt;
  tempCustomer->lineNumber = cLN;
  tempNode->customers = tempCustomer;
  tempNode->next = NULL;
  return tempNode;
}

queue *fillCustomers(int *customerCount) {
  //creates a temporary array and node. Takes data from input and stores it into the temporary Customer using "makeCustomer". Afterwards, the the node is added to the array using "enqueue"
  char *custName = malloc(51 * sizeof(char));
  int custServices;
  int custTimeEnt;
  int custLN;
  queue *tempCustomerList = malloc(15 * sizeof(queue));

  node *tempCustomer;
  node *freeCustomer;

  for (int j = 0; j < 15; j++) {
    init(&tempCustomerList[j]);
  }

  for (int i = 0; i < *customerCount; i++) {
    scanf("%s", custName);
    scanf("%d", &custServices);
    scanf("%d", &custTimeEnt);
    scanf("%d", &custLN);
    tempCustomer = makeCustomer(custName, custServices, custTimeEnt, custLN);
    enqueue(&tempCustomerList[custLN - 1], tempCustomer);
    freeCustomer = tempCustomer;
    free(freeCustomer);
  }
  //frees the customer Name that was previously allocated
  free(custName);
  
  return tempCustomerList;
}

int getNextLine(queue *q, int ct) {
    int cL = 0;
    //loops through the array for the first person in the array and stores it into cL for comparison later
    for (int j = 0; j < 15; j++){
        if(q[j].front != NULL){
            cL = j;
            break;
        }
    }
    int smallestTime = q[cL].front->customers->timeEntering;
    int smallestServices = q[cL].front->customers->numServices;
    int temp = 0;
    if (ct == 0){   //find the index of the queue with the smallest Time
            for (int i = 0; i < 15; i++){
                if (q[i].front != NULL){
                    temp = q[i].front->customers->timeEntering;
                    if (smallestTime > temp){
                        smallestTime = temp;
                        cL = i;
                    }
                }
            }
        return cL;
    } else { //the smallest time is less than  the CT;
         //find the index of the queue with smallest number of services
            for (int i = 0; i < 15; i++){
                if (q[i].front != NULL){
                  temp = q[i].front->customers->numServices;
                  if (smallestServices > temp){
                       smallestServices = temp;
                       cL = i;
                  }
                }
            }
        return cL;
    }
}

void processInputs(queue *q, int custC) {
  int currentTime = 0;
  int cln;
  int addedTime;
  customer* tempDQ;
  //loops through each customer, gets the next person in line, then adds their processing time to "currentTime" and prints after they checkout
  for(int i = 0; i < custC; i++) {
    isEmpty(q);//test
    cln = getNextLine(q, currentTime);
    if(q[cln].front->customers->timeEntering > currentTime)
      currentTime = q[cln].front->customers->timeEntering;
    currentTime += (80 * q[cln].front->customers->numServices) + 50;
    printf("\n%s checks out at %d from line %d.", q[cln].front->customers->name, currentTime, peek(&q[cln]));
    //removes customer from the line once they checkout
    tempDQ = dequeue(&q[cln]);
    free(tempDQ->name);
    free(tempDQ);
  }
}

int main(void) {
  atexit(report_mem_leak);
  int caseCount;
  int custCount;
  scanf("%d", &caseCount);
  for (int i = 0; i < caseCount; i++) {
    scanf("%d", &custCount);
    queue *customerQueueList = fillCustomers(&custCount);
    processInputs(customerQueueList, custCount);
    free(customerQueueList);
  }
  return 0;
}
