typedef struct Queue 
{ 
	int size, front, back, capacity;
	int* array; 
} Queue; 

  
// Queue is empty if front and back are the same
int isEmpty(Queue* queue) {  
	if (queue->size == 0) {
		return 0; // true if not 0
	} else{
		return 1;
	}
} 
  
// Function to add an item to the queue.   
// It changes back and size 
void enqueue(Queue* queue, int item) 
{ 	
	if (queue->size == queue->capacity) {
		return;
	}
	queue->array[queue->back] = item; 
	queue->back = (queue->back + 1)%queue->capacity; 
	queue->size = queue->size + 1; 
} 
  
// Function to remove an item from queue.  
// It changes front and size 
int dequeue(Queue* queue) 
{ 
	if (queue->size == 0) {
		return -1;
	}
	int item = queue->array[queue->front]; 
	queue->front = (queue->front + 1)%queue->capacity; 
	queue->size = queue->size - 1; 
	return item; 
} 
  
// Function to get front of queue 
int front(Queue* queue) 
{ 
	if (isEmpty(queue) == 0) 
		return -1; 
	return queue->array[queue->front]; 
} 
  
// Function to get back of queue 
int back(Queue* queue) 
{ 
	if (isEmpty(queue) == 0) 
		return -1; 
	return queue->array[queue->back]; 
} 
