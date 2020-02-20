import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static String word = new String();

    public static String loop(int num) {
        String a = new String();
        for (int i = 0; i < num; ++i) {
            a += String.valueOf(i);
        }

        return a;
    }

    private static void swap(char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private static void permutations(char[] arr, int loc, int len, ArrayList<String> result) {
        if (loc == len) {
            result.add(new String(arr));
            return;
        }

        // Pick the element to put at arr[loc]
        permutations(arr, loc + 1, len, result);
        for (int i = loc + 1; i < len; i++) {
            // Swap the current arr[loc] to position i
            swap(arr, loc, i);
            permutations(arr, loc + 1, len, result);
            // Restore the status of arr to perform the next pick
            swap(arr, loc, i);
        }
    }

    public static ArrayList<String> permutations(String str) {
        ArrayList<String> result = new ArrayList<String>();
        if (str.length() == 0) { return result; }
        permutations(str.toCharArray(), 0, str.length(), result);
        return result;
    }

    public static String charTostr(char[][] c) {
        String s = "";
        for (int i=0; i < c.length; i++) {
            for (int j=0; j < c[i].length; j++) {
                s += c[i][j];
            }
        }
        return s;
    }

    public static String dencrypt(String str, int order[]){
        System.out.println("cipher text : "+str);
        String lowerStr = str.toLowerCase();
        int divNum = 0;
        if(lowerStr.contains("x")){
            divNum=lowerStr.indexOf("x");
        }
        int rowNum = lowerStr.length()/(divNum+1); //rownum = 5 ;;; divNum+1 = 25
        char[][] c = new char[rowNum][divNum+1];
        int start[] = new int[rowNum+1];//{0*(divNum+1),1*(divNum+1),2*(divNum+1),3*(divNum+1),4*(divNum+1),5*(divNum+1)};
        for(int i=0;i<rowNum+1;i++){
            start[i] = i*(divNum+1);
        }
        for(int i=0;i<rowNum;i++){
            int end = divNum+1;
            c[i] = lowerStr.substring(start[i],start[i+1]).toCharArray();
        }
    /*    for(int i=0;i<rowNum;i++){
            for(int j=0;j<(divNum+1);j++){
                System.out.print(c[i][j]);
            }
            System.out.println();
        }*/
        char[][] trans = new char[divNum+1][rowNum];
        for(int i=0;i<rowNum;i++){
            for(int j=0;j<(divNum+1);j++){
                trans[j][i] = c[i][j];
            }
        }
        char[][] plain = new char[divNum+1][rowNum];
        System.out.print("key length : "+rowNum +"\nordering of the key : "+ (int)(order[0]+1));
        for(int i=1;i<rowNum;i++){
            System.out.print(" , "+(int)(order[i]+1));
        }
    //    System.out.println("\ntransposition matrix : ");
        for(int j=0;j<(divNum+1);j++){
            for(int i=0;i<rowNum;i++){
      //          System.out.print(trans[j][i]);
                plain[j][i] = trans[j][order[i]];
             /*   System.out.print(trans[j][4]);
                System.out.print(trans[j][2]);
                System.out.print(trans[j][3]);
                System.out.print(trans[j][0]);
                System.out.print(trans[j][1]);*/
            }
         //   System.out.println();
        }
    /*    System.out.print("\nplain text matrix : \n");
        for(int j=0;j<(divNum+1);j++){
            for(int i=0;i<rowNum;i++){
                System.out.print(plain[j][i]);
            }
            System.out.println();
        }*/
        String match = charTostr(plain);
        String textword[] = word.split(",");
        if(match.contains(textword[0].replaceAll(" ","")) && match.contains(textword[1].replaceAll(" ","")) && match.contains(textword[2].replaceAll(" ","")) && match.contains(textword[3].replaceAll(" ",""))){
            System.out.print("\nplain text : ");
            System.out.println(match);
        }
        System.out.print("\nplain text without padding : ");
        System.out.print(charTostr(plain).replaceAll("x",""));
        String string = charTostr(plain).replaceAll("x","");
        return string;
    }

    public static String encrypt(String str) {
        int key=5;
        System.out.print("\nplain text : "+str+"\n");
        String padding = new String();
        //System.out.print(str.length());
        if(str.length()%key != 0){
           // System.out.print("sting to key : "+str.length()%key);
            for(int i=0;i<(key-(str.length()%key));i++){
                padding += "x";
            }
            str = str+padding;
            System.out.print("\nafter padding : "+str+"\n");
        }
        char[][] mat = new char[str.length()/key][key];
        int off = 0;
    /*    System.out.print("the matrix : \n");
        for(int i=0;i<(str.length()/key);i++){
            for(int j=0;j<key;j++){
                System.out.print(str.charAt(off++));
            }
            System.out.println();
        }*/
        int keyorder[] = {4,2,3,0,1};
        int offset = 0;
        for(int i=0;i<(str.length()/key);i++){
            for(int j=0;j<key;j++){
                mat[i][keyorder[j]] = str.charAt(offset++);
            }
        }
    //    System.out.print("\nthe matrix after column organizing : \n");
        char[][] trans = new char[key][str.length()/key];
        for(int i=0;i<(str.length()/key);i++){
              for(int j=0;j<key;j++){
                 trans[j][i]=mat[i][j];
      //          System.out.print(mat[i][j]);
             }
        //     System.out.println();
        }
     //   System.out.print("\ntranspose matrix : \n");
        String enStr = new String();
        for(int i=0;i<key;i++){
            for(int j=0;j<(str.length()/key);j++){
                enStr +=trans[i][j];
       //         System.out.print(trans[i][j]);
            }
         //   System.out.println();
        }
        System.out.print("\nencrypt text : "+enStr.toUpperCase()+"\n");
        return enStr.toUpperCase();
    }

    public static int stringCompare(String str1, String str2)
    {

        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);

        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int)str1.charAt(i);
            int str2_ch = (int)str2.charAt(i);

            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }
        if (l1 != l2) {
            return l1 - l2;
        }
        else {
            return 0;
        }
    }

    public static void main(String[] args) {
        PrintStream fileOut;
        File file;
        Scanner sc;
        String line[] = new String[3];
       // sc = new Scanner();
        {
            try {
                file = new File("transposition-95.txt");
                sc = new Scanner(file);
                int i = 0;
                while (sc.hasNextLine()) {
                    line[i] = sc.nextLine();
                    //System.out.println(line[i]+" "+i);
                    i++;
                }
                fileOut = new PrintStream("output.txt");
                System.setOut(fileOut);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        //String str = "RMLLGTRIWDATRTROLRBFADIVXEYCERTRVNTTINHSRLTITNIOEXHAIMIDMWDOEUMNAPWTOGRAIMXERLENAOFALGNOGIPIAMAPRSOXTDWONAOOEHHELIEUTSBNEZVNS";
        String str = line[0];
        word = line[2];
        System.out.print("Decryption\n");
        int order[] = {4,2,3,0,1};
        ArrayList<String> result = permutations(loop(5));
        int[] result1;
        for(int i=0;i<result.size();i++){
            result1 = Arrays.stream(result.get(i).split("")).mapToInt(Integer::parseInt).toArray();
           // for(int j=0;j<order.length;j++){
                 if(order == result1){
                    System.out.print(result1+"asce\n\n");
             //    }
            }
        }
        String dtext = dencrypt(str,order);
        System.out.print("\n\n\nEncryption\n");
        String etext = encrypt(dtext);
        int unmatched=stringCompare(str,etext);
        System.out.println("\nComparing " + etext + "\nand :" + str + "\n" /*+unmatched+str.length()*/);
        int accuracy = ((str.length()-unmatched)*100)/str.length();
        System.out.print("accuracy : "+ accuracy +"%");
    }

}
