package functions;

import java.util.Scanner;

public class DisplayMenu {

    Scanner scanObj = new Scanner(System.in);

    public int menu() {
        System.out.println("WELCOME TO BEST SHOPPING CENTER STORAGE MANAGEMENT SYSTEM");
        System.out.println("*********************************************************");
        System.out.println("1. Get Storage Information");
        System.out.println("2. Insert Storage Information");
        System.out.println("3. Update Storage Information");
        System.out.println("4. Create Storage Record");
        System.out.println("5. Delete Storage Record");
        System.out.println("6. Logout");
        System.out.println("*********************************************************");
        System.out.println("Please Select Number (1-6) for your operation: ");
        int selection = scanObj.nextInt();
        return selection;
    }
}
