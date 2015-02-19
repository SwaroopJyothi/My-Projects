/* Simple SHell program: g++ shell.cpp -o myshell */
/* include c header files */

#include <stdlib.h>
#include <unistd.h> // for function fork()
#include <sys/wait.h> // for function wait()
#include <stdio.h>
#include <stdlib.h>
#include <cstring>
#include <crypt.h>
// include c++ header files
#include <string>
#include <iostream>
#include <fstream>
#define N 15
#define LOGOUTCODE 5

using namespace std;

int his=0;
int index1,index2;
string history[50];
string readString(ifstream&);
void build_command(string *comm,char *command);
void user_login();
void type_prompt();
int read_command(string *comm,string *parameter, int background,char *command);
void exec_command(int opcode,string *parameter,char *command);

char sample[30][30];


int main()
{

 int i=0,opcode=0;
  int status, background=0;
  pid_t pid;
  string parameter[10];

  char command[20]={'\0'};
  string comm[5000];
  build_command(comm,command);       /* read in the commands into a table or hash table */
 user_login();          /* Authenticate the user */
 while (i < N ) {        /* repeat maximum N times */


        type_prompt( );    /* display prompt */

//cin.get();

   opcode = read_command(comm,parameter, background,command);  /* input from terminal */

                his=his+1;

  if (opcode>0) { /* valid command */


	if(parameter[0]=="&")
		background=1;
 
         pid=fork();
            if (pid>0) { /* Parent code */

	if(!background)	
	{	
	wait(&status);
	//	cout<<endl<<parameter[0];

	//		if(parameter[0]=="mydf&")

	//			cout<<"background running  "<<parameter[0];
//                wait(&status);
                cout << "Parent ID= " <<getpid() << "\n";
        if (status == 0x0500) exit(0); /* status must be a hex number */

	}

	else

	cout<<"this is background "<<parameter[0]<<endl; 

           }

                else if(pid==0) { /* Child code */
                cout << "Child: returned PID= " << getpid() << "\n";

                        cout<<endl<<"command is present"<<endl;


        exec_command (opcode,parameter,command);      /* execute command */

                exit(0);
            } /* end of child code */
   
}
        else {

 cout << "Invalid command, try again\n"; }
   
i++;
  }
  return 1;
}

void build_command(string *comm,char *command)
{
  cout << "Build command function: 1\n";


int x,y,i,j,k;
int arr[20];
int sum;

  int array_size = 1024; // define the size of character array
//      char * array = new char[array_size][array_size];
char sam[20][20]={'\0'};
char array[1020]={'\0'};




        int position = 0;

        ifstream fin("sam.txt");

          if(fin.is_open())

{
                while(!fin.eof() && position < array_size)
                {
                        fin.get(array[position]); //reading one character from file to array
                        position++;

        }

}

for(i=0,k=0;i<position;i++)
{
if(array[i]!='\0')
{

if(array[i]==';' )

        { j++;k=0;}


else
{
        sample[j][k]=array[i];
k++;}
//cout<<sam[j][k];
}

}
//cout<<sample[0]<<endl<<sample[1]<<endl<<sample[2]<<endl;

for( x=0;x<j;x++)
{
for( y=0;y<k;y++)
//cout<<sample[x][y];
cout<<"";
}


for(i=0;i<x;i++)
{sum=0;
for(j=0;j<15;j++)
{

sum=sum+(int)sample[i][j];
//cout<<sample[i][j]<<"  " <<sum;

}
arr[i]=sum;

}

for(i=0;i<x;i++)

{

 comm[arr[i]]=sample[i];

}

}


void user_login()
{

  char user[80];
  char pass[80];
 string cry_pt[10];
  string line_in;
  string::size_type pos;
  ifstream fp_in;
  int count = 0;
  int x,ind;
  string user_pass[100][2];

  fp_in.open("users.txt"); //Open user file
  if(fp_in == NULL) {
  cout << "Could not open user file, exiting." << endl;
    //return -1;
 }
  while(!fp_in.eof()) { //While there is something to read
    line_in=readString(fp_in);  //Read a line from the file
    if(line_in.length() > 0) { //If the line is non-empty
      pos = line_in.find("\t",0); //Find the tab deliminter
      if( pos != string::npos) { //If a tab is found..
        user_pass[count][0] = line_in.substr(0,pos); //Get username
        user_pass[count][1] = line_in.substr(pos+1); //Get password
        //cout << "User: " << user_pass[count][0] << " Pass: " << user_pass[count][1] << endl;
        count++;
      }
    }
  }
  fp_in.close(); //Done with the user file
        if(!count > 0) {
        cout << "Userlist is empty, exiting" << endl;
   // return -2;
 }


for(int i=0;i<count;i++)
{
string pass1=user_pass[i][1];
const char *_pass=pass1.c_str();
string crypted_pass=crypt(_pass,"sampletest");
cry_pt[i]=crypted_pass;
//cout<<user_pass[i][2]<<endl;
}
  int logged_in=0;
  while(!logged_in) { //Force user to login. Give a quit option if you'd like
    cout << "Enter user name: ";
    cin >> user;
    //cout << "Enter password: ";
    //cin >> pass;
        char *pass=getpass("enter password");
        //for(int i=0;i<5;i++)
        //cout<<endl<<user_pass[i][0]<<"   "<<user_pass[i][1]<<"  "<<cry_pt[i];
        int found = 0;
    for(x=0;x<=5;x++) { //For each user/password pair..
      if(user_pass[x][0].compare(user) == 0) { //Matched the username
        found = 1;
        const char *_pass1=pass;
     string crypt1=crypt(_pass1,"sampletest");
        if(cry_pt[x].compare(crypt1)==0) { //Matched user and pass
          found = 1;
          logged_in = 1;

                index1=x;
        } else {
          cout << "Invalid password!" << endl;
        }
      }
    }
    if(!found) {
      cout << "Invalid username!" << endl;
    }
  }
                index2=x;
  //Once we're done with that loop, they logged in successfully.
  cout << "Logged in successfully!" << endl;
//      return 0;

}


string readString(ifstream& reader) {
  char buffer[256];
  reader.getline(buffer,256);
  string str1 = buffer;
  return str1;
}


void type_prompt()
{
        cout << "MSH>> ";

//cin.get();
}
int read_command(string *comm,string *parameter, int background,char *command)
{

        //cin.get();
        char command1[500]={'\0'};
        char *temp={'\0'};
        char ch;
        char temp2[100]={'\0'};
        string temp1="";
//      cout<<endl<<"read command";
        int opcode=0,size;
        int i,j,k,sum=0;
//      cin.get();
        cout<<"";
        if(his==0)
        cin.get();
        cin.getline(command1,200);
                history[his]=command1;

        for(i=0;i<20;i++)
        parameter[i]="";

        i=0;
    temp = strtok( command1, " " );
    while( temp != NULL  )
    {
        if(i==0)
        {
        strcpy(command, temp);
        //cout<< temp<<endl;
        parameter[i]=command;


        }       i++;
        strcpy(temp2,temp);
parameter[i]=temp2;

      temp= strtok( NULL, " " );
 }


        for(i=99;i>=0;i--)
        if(command[i]=='\0')
        size=i;
//cout<<endl<<command;
for(j=0;j<size;j++)
{
sum=sum+(int)command[j];

}
//cout<<sum<<endl<<comm[sum];
if(comm[sum]==parameter[0])
{
sum=0;
//cout<<"success"<<endl<<parameter[0];
opcode=2;

}



if(parameter[0].compare("mylogout")==0) opcode = LOGOUTCODE;

        return opcode;
}



void exec_command(int opcode, string *parameter,char *command)
{
              cout << "Child: Execute command function: 1\n"<<endl;


//      cout<<endl<<"this is in exec "<<his<<history[his];
        string execute;

        if(opcode == LOGOUTCODE)
        exit(LOGOUTCODE);


        if (parameter[0]=="myedit")
  {

        if(parameter[1]=="myedit" && parameter[2]!="")


        execute="pico "+parameter[2];
        system(execute.c_str());


        if(parameter[2]!="" && parameter[3]!="")
        execute="pico "+parameter[2]+" "+parameter[3];
        system(execute.c_str());

        }

         if(parameter[0]== "mypwd")

        {

 char user[80];
  char pass[80];
  string line_in;
  string::size_type pos;
  ifstream fp_in;
std::ofstream myfile;
 int count = 0;
  string user_pass[100][4];

  fp_in.open("users.txt"); //Open user file
  if(fp_in == NULL) {
    cout << "Could not open user file, exiting." << endl;

  }
  while(!fp_in.eof()) { //While there is something to read
    line_in=readString(fp_in);  //Read a line from the file
    if(line_in.length() > 0) { //If the line is non-empty
      pos = line_in.find("\t",0); //Find the tab deliminter
      if( pos != string::npos) { //If a tab is found..
        user_pass[count][0] = line_in.substr(0,pos); //Get username
        user_pass[count][1] = line_in.substr(pos+1); //Get password
               count++;
      }
    }
  }
string u_pass;
cout<<"change password"<<endl;
 char *u_pass1=getpass("Enter new password");

  myfile.open ("users.txt");
for(int i=0;i<index2;i++)
{
if(i==index1)
user_pass[i][1]=u_pass1;
myfile<<user_pass[i][0]<<"      "<<user_pass[i][1]<<endl;
        }

}

if( parameter[2]=="|")

{
system(parameter[0].c_str());
system(parameter[3].c_str());


}

         if (parameter[0]=="myps")

        {


     if(parameter[1]=="myps" && parameter[2]!="")
                {
                cout<<endl<<parameter[1]<<endl<<parameter[2]<<endl<<endl;
                system("ps -ef | grep jmeruga");

                }
         if(parameter[1]=="myps" && parameter[2]=="")

        system("ps -ef");

                }



        if(parameter[0]=="myhistory")

 {
//      cout<<"thw value of his "<<his;
        for(int i=0;i<his;i++)
        cout<<endl<<history[i];

        }


        if(parameter[0]=="mysearch")

        {

if(parameter[1]=="mysearch" && parameter[2]!="" && parameter[3]!= "")
execute="grep -n "+parameter[2]+" "+parameter[3];
system(execute.c_str());

        }


        if(parameter[0]=="mydf")

        {

        if(parameter[1]=="mydf" && parameter[2]=="")
                system("df");
        if(parameter[1]=="mydf" && parameter[2]!="")

                {
                        execute="df -k | grep "+parameter[2];
                        system(execute.c_str());

                }
        }


        if(parameter[0]=="mycopy"){


        if(parameter[1]=="mycopy" && parameter[2]!="")

        {
        execute = "cp "+parameter[2]+" "+parameter[3];
        system(execute.c_str());
 }

        if(parameter[1]=="mycopy" && parameter[2]!="" && parameter[3]!="" && parameter[4]!="")
        {
        execute="cat "+parameter[2]+" "+parameter[3]+" >> "+parameter[4];
        system(execute.c_str());

        }


        }

}

