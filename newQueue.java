import java.io.*;
import java.util.Scanner;

public class newQueue {

  public static newClient head = null;
  public static newClient tail = null;
  public static newClient clienteleHead = null;
  public static newClient currentClientele = null;
  public static newClient clienteleTail = null;

  public static Scanner s = new Scanner(System.in);

  public static void main(String[] args) throws FileNotFoundException {

    String path = "clients.csv";
    String dataExport = "clients2.csv";

    clientImport(path);


    char choice = menu(s);

    while (choice != 'q'){

      switch (choice){

        case 'a':
        case 'A':
          newClient createdClient = new newClient();

          System.out.print("Account Number: ");
          createdClient.accountNumber = s.nextLine();
          System.out.print("Client First Name: ");
          createdClient.fname = s.nextLine();
          System.out.print("Client Last Name: ");
          createdClient.lname = s.nextLine();
          System.out.print("Account Balance: ");
          createdClient.accountBalance = s.nextLine();
          add(createdClient);



          break;
        case 'p':
        case 'P':
          newClient p = peek();
          if (p != null){
            System.out.println("\nNext in line is: " + p.fname + " " + p.lname);
          } else {
            System.out.println("No one in queue!");
          }

          break;
        case 's':
        case 'S':



          newClient serving = peek();

          if (serving != null) {

            while (currentClientele != null) {

              if (currentClientele.accountNumber.equals(serving.accountNumber)) {

                if (currentClientele.fname.equals(serving.fname) && currentClientele.lname.equals(serving.lname)) {
                  System.out.print(serving.fname + " " + serving.lname + "\n" + serving.accountNumber + "\n\n------------\n(D)eposit\n(W)ithdraw\n(C)heck Balance\nMake Choice: ");
                  char choice2 = servingMenu(s);


                  switch (choice2) {
                    case 'd':
                    case 'D':
                      deposit(s, serving);


                      break;
                    case 'w':
                    case 'W':
                      withdraw(s, serving);

                      break;
                    case 'c':
                    case 'C':
                      System.out.println("Your current balance is: " + serving.accountBalance);

                      break;
                    default:
                      break;

                  }
                  poll();
                }
                break;
              } else {
                currentClientele = currentClientele.next;

              }

            }
          }
          break;

      }

      choice = menu(s);

    }

    export(dataExport);


  }


  public static void clientImport(String path) {

    int clientNum = 0;




    try {

      BufferedReader reader = new BufferedReader(new FileReader(path));
      String data = "";

      while ((data = reader.readLine()) != null) {

        String[] values = data.split(",");
        clientNum++;

        newClient clientele = new newClient();

        clientele.accountNumber = values[0];
        clientele.fname = values[1];
        clientele.lname = values[2];
        clientele.accountBalance = values[3];


        if (clienteleHead == null) {
          clienteleHead = clientele;
          currentClientele = clientele;
          clienteleTail = clientele;
        } else {
          clienteleTail.next = clientele;
          clienteleTail = clienteleTail.next;
        }
      }

      reader.close();
    }
    catch (IOException e) {
      System.out.println("Error in file: " + path);
      e.printStackTrace();
    }

    System.out.println(clientNum + " clients are in the queue!");
  }

  public static char menu(Scanner s){

    System.out.print("\nNordea Bank\n**********\n(A)dd to Queue\n(P)eek for the first in line\n(S)erve Next\n(Q)uit\nMake Choice: ");
    String choice1 = s.nextLine();

    return choice1.charAt(0);
  }

  public static newClient peek(){
    return head;
  }


  public static void add(newClient newPerson){
    if (peek() == null){
      head = newPerson;
      tail = newPerson;
    } else {
      tail.next = newPerson;
      tail = newPerson;
    }
  }

 public static newClient poll () {
    newClient served = head;
    head = head.next;

    return served;
  }

  public static char servingMenu(Scanner s){

    String choice2 = s.nextLine();

    return choice2.charAt(0);
  }


  public static void deposit(Scanner s, newClient d){

    double actBalance1 = Double.parseDouble(d.accountBalance);
    System.out.println("How much do you want to deposit?");
    double depositAmount = Double.parseDouble(s.nextLine());

    if (depositAmount > 0){

      actBalance1 += depositAmount;
      d.accountBalance = String.valueOf(actBalance1);
      currentClientele.accountBalance = d.accountBalance;

      System.out.println("You've successfully deposited " + depositAmount + " and you're balance is now: " + d.accountBalance);
    } else {
      System.out.println("Invalid");
    }
  }

  public static void withdraw(Scanner s, newClient w){

    double actBalance2 = Double.parseDouble(w.accountBalance);

      System.out.println("How much do you want to withdraw?");
      double withdrawAmount = Double.parseDouble(s.nextLine());

      if (actBalance2 >= withdrawAmount) {

        actBalance2 -= withdrawAmount;

        w.accountBalance = String.valueOf(actBalance2);
        currentClientele.accountBalance = w.accountBalance;

        System.out.println("You've successfully withdrawn " + withdrawAmount + " ,and your current balance is: " + w.accountBalance);

      } else {
        System.out.println("Not enough money!");
      }

    }

  public static void export(String dataExport) {

    newClient exportClient = clienteleHead;
    int exportCount = 0;

    try {
      FileWriter fileWriter = new FileWriter(dataExport, false);

      while (exportClient != null) {
        String dataLine = String.format("%s, %s, %s, %s\n", exportClient.accountNumber, exportClient.fname, exportClient.lname, exportClient.accountBalance);

        fileWriter.write(dataLine);

        exportClient = exportClient.next;

        exportCount++;
      }

      System.out.println(exportCount + " clients done");
      fileWriter.close();


    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }



}


