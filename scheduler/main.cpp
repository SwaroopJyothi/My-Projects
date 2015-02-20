#include <signal.h>
#include <sys/types.h>
#include <time.h>
#include <unistd.h>
#include <sys/wait.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include "iostream"
#include "fstream"
#include "queue"
#include <fcntl.h>
#include "header.h"
#include "generator.cpp"
#include "scheduler.cpp"

using namespace std;

void *jobGenerator(void *threadarg);

struct Thread_queues ThreadData1,ThreadData2;
pthread_t threads[4];
void setJobqueues();


int main()
{
        signal(SIGINT, SIG_IGN);
        setJobqueues();

        pthread_mutex_init(&Taskqueue,NULL);
        pthread_mutex_init(&Serverqueue,NULL);
        pthread_mutex_init(&PowerUserqueue,NULL);
        pthread_mutex_init(&Userqueue,NULL);
        pthread_mutex_init(&disp,NULL);

       	pthread_create(&threads[0], NULL,jobGenerator,(void *) &ThreadData1);       
       	pthread_create(&threads[1], NULL,jobScheduler,(void *) &ThreadData2);

        pthread_join(threads[0],NULL);
        pthread_join(threads[1],NULL);

        pthread_mutex_destroy(&Taskqueue);
        pthread_mutex_destroy(&Serverqueue);
        pthread_mutex_destroy(&Userqueue);
        pthread_mutex_destroy(&disp);
        
        pthread_exit(NULL);

        return 1;            
 }

void setJobqueues()
{
      
        ThreadData1.thread_id=0; 
        memcpy(ThreadData1.Jobqueue,job,4);
        memcpy(ThreadData1.Quantum,quant,4);
        ThreadData2.thread_id=1;  
        memcpy(ThreadData2.Jobqueue,job,4);
        memcpy(ThreadData2.Quantum,quant,4);
} 
