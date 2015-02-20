#include "header.h"
#include<signal.h>
#include<sys/types.h>

#define ALARM 1000000
#define SLEEP 30

static bool NOT_PRESSED = true ;
void INThandler(int signum)
{
    //do nothing
    NOT_PRESSED = false ;
  
}

void operate(int n)
{
	signal(SIGINT,operate);
	forward=0;
	jobnum=job[2].front();
	job[2].pop();
	if(jobnum!=0){
    pthread_mutex_lock(&disp);

 cout<<"\n\t EXECUTING JOB NO: "<<jobnum<<" FROM User Queue"<<endl<<endl;
 pthread_mutex_unlock(&disp);

   }	
}


void executeJob(queue<int> job[],queue<int> quant[],int n)
{
    void operate(int);
    pthread_t tid ; 
 
 if(n==0)
    {
	    pthread_mutex_lock(&Taskqueue);
	    jobnum=job[0].front();
	    job[0].pop();
          pthread_mutex_lock(&disp);
	
		  cout<<"\n\t EXECUTING JOB NO: "<<jobnum<<" FROM TASK_QUEUE"<<endl<<endl;	
	      pthread_mutex_unlock(&disp);

                   pthread_mutex_unlock(&Taskqueue);
    }
	else if(n==1)
	{
			
		pthread_mutex_lock(&Serverqueue);
		jobnum=job[1].front();
		job[1].pop();
        qntum=quant[1].front();
        quant[1].pop();
		int round=rnd[1].front();
		rnd[1].pop();
	 pthread_mutex_lock(&disp);

	 cout<<"\n\t EXECUTING JOB NO: "<<jobnum<<" FROM SERVER_QUEUE"<<endl<<endl;		
     pthread_mutex_unlock(&disp);
	 cout<<"\n\t QUANTUM FOR JOB NO: "<<jobnum<<" is "<<qntum;
	cout<<"\n\t THIS IS ROUND  "<<++round<<" of JOB "<<jobnum; 
	qntum--;	
	if(qntum>0)
				{
			job[1].push(jobnum);
			quant[1].push(qntum);
			rnd[1].push(round);
					}
               else{
 pthread_mutex_lock(&disp);
  pthread_mutex_unlock(&disp);
 
                 }
		pthread_mutex_unlock(&Serverqueue);
	}
    else if(n==2)
    {
	    pthread_mutex_lock(&Userqueue);
	    jobnum=job[2].front();
	    job[2].pop();
            qntum=quant[2].front();
            quant[2].pop();          
             pthread_mutex_lock(&disp);

			 cout<<"\n\t EXECUTING JOB NO: "<<jobnum<<" FROM USER_QUEUE"<<endl<<endl;
             pthread_mutex_unlock(&disp);

             qntum--;
                if(qntum>0)
                {
                        job[2].push(jobnum);
                        quant[2].push(qntum);
						sleep(3);
                }
               else{   
 pthread_mutex_lock(&disp);
           	 
 pthread_mutex_unlock(&disp);
  
}
            pthread_mutex_unlock(&Userqueue);
    }


     if(n==3)
      {
            pthread_mutex_lock(&PowerUserqueue);
            jobnum=job[3].front();
            job[3].pop();
          pthread_mutex_lock(&disp);

                  cout<<"\n\t EXECUTING JOB NO: "<<jobnum<<" FROM POWER_USER_QUEUE"<<endl<<endl;
               signal(SIGINT,INThandler);
              while (NOT_PRESSED) ;
              NOT_PRESSED = true ;
          
             
              pthread_mutex_unlock(&disp);
              
                   pthread_mutex_unlock(&PowerUserqueue);
               tid = pthread_self();
               cout << tid ;
       }



}
           
               
int selectJob(queue <int> job[])  
{
        int Job1;
        Job1=job[0].front();
        if(Job1==0)
        {
                Job1=job[1].front();
                                if(Job1==0)
				{
					Job1=job[3].front();
                                        if(Job1==0)
                                        {
                                                Job1 = job[2].front();
			        		if(Job1==0)  
					        	return 5;
					        else 
					        	return 2;
                                        }
                                      
                                        else 
                                                return 3 ;
				}
			        else
					return 1;
	}
        else
			return 0;
        
}

void *jobScheduler(void *threadarg)
{
     int n,i=0;
     int selectJob(queue<int> n[]);

     void executeJob(queue<int> [],queue<int> [],int);
     struct Thread_queues *Temp;
	 	
     Temp = (struct Thread_queues *)threadarg;
     memcpy(job,Temp->Jobqueue,4);
     memcpy(quant,Temp->Quantum,4);
     cout<<"\n\n\t\tJOB SCHEDULER STARTED\n\n"<<endl<<endl;
     Label:
     while(job[0].front()!=0 || job[1].front()!=0 || job[2].front()!=0 || job[3].front()!=0 )
     {
        while(i<N)
        {      
                n=selectJob(job); i++;
                if(n==5)  break;
                else      executeJob(job,quant,n);
 pthread_mutex_lock(&disp);
        	
display(job[0],job[1],job[2],job[3]);
 pthread_mutex_unlock(&disp);
		   
                sleep(4); 
        }
   }
   sleep(12);
   if(job[0].front()!=0 || job[1].front()!=0 || job[2].front()!=0 || job[3].front()!=0)
   goto Label;
   cout<<"\n\n\n\t\t\tALL JOBS ARE EXECUTED SUCCESSFULLY"<<endl<<endl;
   pthread_exit(NULL); 
}

