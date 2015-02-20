#ifndef _GLOBALS_H_
#define _GLOBALS_H_
 
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <signal.h>
#include <ctype.h>
#include <string.h>
#include <pthread.h>
#include <time.h>
#include <queue>


#define MAX_JOB_QUEUE 4

using namespace std;


int jobnum, qntum, oper, forward=1, t=0, N=50;

pthread_mutex_t Taskqueue, Serverqueue, PowerUserqueue, Userqueue, disp;

struct Thread_queues
{
	   int  thread_id;
       queue <int> Jobqueue[4],Quantum[4],Round[4];
};


queue <int> job[4];
queue <int> quant[4];
queue <int> rnd[4];


void display(queue<int> &q1,queue<int> &q2,queue<int> &q3,queue<int> &q4)
{  
    int i,s1,s2,s3,s4,k1,k2,k3,k4;
   k1 = (int)q1.size();
   k2 = (int)q2.size();
   k3 = (int)q3.size();
   k4 = (int)q4.size();
   
   cout<<"\n"<<"                                     "<<"\n";
	cout<<endl<<"-----------------------------------------------"<<endl;
   cout<<" Task Queue (1-10) - ";
   if(k1 == 0) cout<< " No Jobs In Queue .";
   else
   for(i=1;i<=k1;i++)  
     {
       s1=q1.front(); 
       cout<<"    "<<s1<<"    ";
       q1.pop();  
       q1.push(s1);
     }   
      cout<<"\n"<<"                                   "<<"\n";   
 cout<<endl<<"-----------------------------------------------"<<endl;
 
  cout<<" Server Queue (11-30) - ";
  if(k2 == 0) cout<< " No Jobs In Queue .";
  else
  for(i=1;i<=k2;i++)  
     {
       s2=q2.front(); 
       cout<<"    "<<s2<<"    ";
       q2.pop();  
       q2.push(s2);
     }       
     cout<<"\n"<<"                                         "<<"\n";   
 cout<<endl<<"-----------------------------------------------"<<endl;

      cout<<" Power User Queue (31-50) - ";
     if(k4 == 0) cout<< " No Jobs In Queue .";
     else
     for(i=1;i<=k4;i++)
     {
       s4=q4.front();
       cout<<"     "<<s4<<"     ";
       q4.pop();
       q4.push(s4);
     }
     cout<<"\n"<<"                                          "<<"\n";     
 cout<<endl<<"-----------------------------------------------"<<endl;

     cout<<" User Queue (51-100) - ";     
     if(k3 == 0) cout<< " No Jobs In Queue .";
     else  
     for(i=1;i<=k3;i++) 
     {
       s3=q3.front();  
       cout<<"     "<<s3<<"     ";
       q3.pop();  
       q3.push(s3); 
     }    
      cout<<"\n"<<"                                          "<<"\n";

}
#endif
