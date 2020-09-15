import java.io.*;
import java.util.Arrays;
import java.util.Scanner;


public class csvwork {

    public static boolean tryParseInt(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the 1st file name: ");
        String firstName = scan.nextLine();
        File file1 = new File(firstName);
        while (!file1.exists()) {
            System.out.println("Incorrect data. Try again: ");
            firstName = scan.nextLine();
            file1 = new File(firstName);
        }

        System.out.println("Enter the 2nd file name: ");
        String secondName = scan.nextLine();
        File file2 = new File(secondName);

        System.out.println("Do you want to enter a delimiter? Enter 1 if yes, 0 if no: ");
        String want = scan.nextLine();
        while (!tryParseInt(want)) {
            System.out.println("Incorrect data. Try again: ");
            want = scan.nextLine();
        }
        int wantDel = Integer.parseInt(want);

        char delimiter;
        while (wantDel != 1 && wantDel != 0) {
            System.out.println("Incorrect data. Try again: ");
            wantDel = scan.nextInt();
        }
        if (wantDel == 1) {
            System.out.println("Enter the delimiter: ");
            String delim = scan.next();
            delimiter = delim.charAt(0);                            //эл-т строки с 0-индексом
        }
        else {
            delimiter = ',';                                        //по умолчанию ,
        }

        System.out.println("Enter a unifying symbol: ");
        String unSymbol = scan.next();
        scan.close();
        FileReader fr = new FileReader(file1);
        Scanner fsc = new Scanner(fr);
        FileWriter fw = new FileWriter(file2);

        String nextStr;
        int wordLen;
        String currLine;
        Scanner strSc;
        char[] strChar;
        String lenStr;

        while (fsc.hasNextLine()) {
            currLine = fsc.nextLine();
            strSc = new Scanner(currLine);
            while (strSc.hasNext()) {
                nextStr = strSc.nextLine();
                strChar = nextStr.toCharArray();
                wordLen = 0;
                for (int i = 0; i < nextStr.length(); i++) {
                    if (strChar[i] == '"') {
                        i++;
                        while ((i < nextStr.length())) {
                            if (i != nextStr.length() - 1 && strChar[i] == '"' && strChar[i + 1] == delimiter || i == nextStr.length() - 1 && strChar[i] == '"') {
                                break;
                            }
                            else {
                                System.out.print(strChar[i]);                         //слова выводятся в консоль
                                i++;
                                wordLen++;
                            }
                        }
                        lenStr = Integer.toString(wordLen);
                        fw.write(lenStr);
                        i++;
                        wordLen = 0;
                        if (i == nextStr.length() - 1 && strChar[i] == delimiter) {
                            continue;
                        }
                        else if (i < nextStr.length()) {
                            fw.write(unSymbol);
                            System.out.print(" ");
                        }
                    }
                    else {
                        while ((i < nextStr.length()) && (strChar[i] != delimiter)) {
                            System.out.print(strChar[i]);
                            i++;
                            wordLen++;
                        }
                        lenStr = Integer.toString(wordLen);
                        fw.write(lenStr);
                        if (i == nextStr.length() - 1) {                             //&& strChar[i] == delimiter
                            continue;
                        }
                        else if (i < nextStr.length()) {
                            fw.write(unSymbol);
                            System.out.print(" ");
                        }
                        wordLen = 0;
                    }
                }
                fw.write("\n");
                System.out.print("\n");
            }
            strSc.close();
        }
        fr.close();
        fsc.close();
        fw.close();
    }
}


