import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.Date;

public class ServerChat extends JFrame implements Runnable {

	//Declare and initialize variables
JTextArea jtaArea;

int thread=0;

Socket client;

DataInputStream dis = null;

DataOutputStream dos = null;

/** Creates a new instance of ServerLoan */

public ServerChat() {

jtaArea=new JTextArea();

JScrollPane pane=new JScrollPane(jtaArea);

add(pane,BorderLayout.CENTER);

setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

setSize(500,300);

setTitle("Server Loan");

setVisible(true);

//Try block
try{
	   //Create instance
ServerSocket ss=new ServerSocket(8000);
//Append
jtaArea.append("Server Loan started at "+ new Date() +"\n");
//Loop
while(true){
    //Server socket accept
client = ss.accept();
//Increment thread
thread++;

//Display
jtaArea.append("starting thread for client "+thread+" at "+ new Date().toString() +"\n");
jtaArea.append("Client "+ thread +"'s host name is "+client.getInetAddress().getHostName()+"\n");
jtaArea.append("Client "+ thread +"'s IP Address is "+client.getInetAddress().getHostAddress()+"\n");

//Create thread instance
Thread t=new Thread(this);

//Start thread
t.start();

}

//Catch block
} catch(IOException ex){

System.err.println(ex);

}

}

//Method run()
public void run(){

try{

    //Create instances
dis=new DataInputStream(client.getInputStream());

dos=new DataOutputStream(client.getOutputStream());

//Loop
while(true){

double interest= dis.readDouble();

double years=dis.readDouble();

double amount=dis.readDouble();

//Add this
jtaArea.append("Annual Interest Rate: "+interest + "\n");

jtaArea.append("Number Of Years: "+years+ "\n");

jtaArea.append("Loan Amount "+amount+ "\n");

//Compute
interest=interest/1200;

double monthly_payment=( interest + (interest/(Math.pow(1+interest,years*12)-1))) * amount ;
double months = years*12;
double total_payment = monthly_payment * months;

//Display
jtaArea.append("monthly payment: "+monthly_payment+ "\n");
jtaArea.append("total payment: "+total_payment+ "\n");

dos.writeDouble(monthly_payment);

}

//Catch block
} catch(IOException ex){

System.err.println(ex);

}

}

//Main method
public static void main(String args[]){
	
	//Create new instance
	ServerChat server=new ServerChat();

}

}