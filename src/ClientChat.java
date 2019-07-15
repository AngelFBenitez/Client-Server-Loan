import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

// class
public class ClientChat extends javax.swing.JApplet {

	// declare and initialize variables
private JTextField jtfAnnualInterest =new JTextField(22);

private JTextField jtfNumberOfYears =new JTextField(22);

private JTextField jtfLoanAmount =new JTextField(22);

private JTextArea jtaText=new JTextArea(5,20);

private JButton jtbSubmit =new JButton("Submit");


//create a new socket
Socket socket;

/** Creates a new instance of Client */

// method init()
public void init(){

	// create new instance
JPanel p1=new JPanel();

//Set layout
p1.setLayout(new GridLayout(3,1));

//Add labels
p1.add(new JLabel("Anuual Interest Rate"));
p1.add(new JLabel("Number OF Years"));
p1.add(new JLabel("Loan Amount"));
JPanel p2=new JPanel();

p2.setLayout(new GridLayout(3,1));

// Set alignments
jtfAnnualInterest.setHorizontalAlignment(JTextField.RIGHT);
jtfNumberOfYears.setHorizontalAlignment(JTextField.RIGHT);
jtfLoanAmount.setHorizontalAlignment(JTextField.RIGHT);

//add
p2.add(jtfAnnualInterest);
p2.add(jtfNumberOfYears);
p2.add(jtfLoanAmount);
JPanel left=new JPanel(new BorderLayout());

left.add(p1,BorderLayout.WEST);

left.add(p2,BorderLayout.CENTER);

JScrollPane scrollPane=new JScrollPane(jtaText);

add(left,BorderLayout.CENTER);

add(jtbSubmit,BorderLayout.EAST);

add(scrollPane,BorderLayout.SOUTH);

jtbSubmit.addActionListener(new ButtonListener());

try{

socket=new Socket("localhost",8000);} catch(IOException ex) {

System.err.println(ex);

}

}
// Class ButtonListener()
private class ButtonListener implements ActionListener{
public void actionPerformed(ActionEvent ae){
	
//try 
try{
double interest = Double.parseDouble(jtfAnnualInterest.getText());

double years=Double.parseDouble(jtfNumberOfYears.getText());

double amount =Double.parseDouble(jtfLoanAmount.getText());

// create instances
DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
DataInputStream dis=new DataInputStream(socket.getInputStream());

// write
dos.writeDouble(interest);
dos.writeDouble(years);
dos.writeDouble(amount);

// instance
double monthly_payment= dis.readDouble();
double months = years*12;
double total_payment = monthly_payment * months;

// append
jtaText.append(" Annual Interest Rate: "+interest+ "\n");
jtaText.append(" Number Of Years: "+years + "\n");
jtaText.append(" Loan Amount "+amount + "\n");
jtaText.append(" Monthly payment: "+monthly_payment+" \n");
jtaText.append(" Total Payment: " + total_payment + "\n");
} 
// catch
catch(IOException ex){
// print
System.err.println(ex);

}

}

}
// main, driver
public static void main(String args[]){

JFrame frame=new JFrame("Client Loan");

ClientChat applet=new ClientChat();

frame.add(applet,BorderLayout.CENTER);
// function call.
applet.init();

applet.start();

frame.pack();

frame.setVisible(true);

}

}