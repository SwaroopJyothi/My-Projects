#include "header.h"

void *jobGenerator(void *threadarg)
{
	struct Thread_queues *temp;
	int job_no,quantum=2,q,i;
       

	temp= (struct Thread_queues *)threadarg;
	
	memcpy(job,temp->Jobqueue,3);
	memcpy(quant,temp->Quantum,3);
	memcpy(rnd,temp->Round,3);
	
	cout<<"\n\n\t\tGENERATION OF JOBS STARTED NOW\n\n";
	srand( time(NULL) );
	for(i=1;i<=25;i++)
	{
		job_no=rand()%100+1;
		printf("\n\t JOB NO: %d Generated ",job_no);
		if(job_no>=1 && job_no<=10)
		{
			pthread_mutex_lock(&Taskqueue);
			if(job[0].size()==MAX_JOB_QUEUE)
			{
				cout<<"\n\t\tTask Queue IS FULL "<<job_no<<endl<<endl;			                                
			}
            else {
		          job[0].push(job_no); }

		          pthread_mutex_unlock(&Taskqueue);
		}

		else if(job_no>=11 && job_no<=30)
		{
			pthread_mutex_lock(&Serverqueue);
			if(job[1].size()==MAX_JOB_QUEUE)
			{
				cout<<"\n\t\tServer Queue IS FULL "<<job_no<<endl<<endl;
				                                
			}
             else {
		        
			q=((random()%3)+1);

            job[1].push(job_no);
			int temp1=0;
			rnd[1].push(temp1);
			quant[1].push(q);}
			pthread_mutex_unlock(&Serverqueue);
		}
                else if(job_no>=31 && job_no<=50)
                {
                        pthread_mutex_lock(&PowerUserqueue);
                        if(job[3].size()==MAX_JOB_QUEUE)
                        {
                                cout<<"\n\t\tPower User Queue IS FULL "<<job_no<<endl<<endl;


                        }
                        else{

                        job[3].push(job_no);
                        quant[3].push(quantum);
                       }
                        pthread_mutex_unlock(&PowerUserqueue);
                }
   		else if(job_no>=51 && job_no<=100)
                {
			pthread_mutex_lock(&Userqueue);	
			if(job[2].size()==MAX_JOB_QUEUE)
			{
				cout<<"\n\t\tUser Queue IS FULL "<<job_no<<endl<<endl;
				
		                     
                	}
                        else{
                        
			job[2].push(job_no);
                        quant[2].push(quantum);
                       }
       			pthread_mutex_unlock(&Userqueue);
        }
    pthread_mutex_lock(&disp);

        display(job[0],job[1],job[2],job[3]);
 pthread_mutex_unlock(&disp);
	
sleep(3);	
	}
	cout<<"\n\n\t\tJobs Created Successfully"<<endl<<endl;
	pthread_exit(NULL);
}
