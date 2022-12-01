import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class PlayerSelector{

public static void main(String[]args) throws FileNotFoundException{

if ( args.length < 2 ){

help();
return;

}
try {

String inputFilePath = args[0];
String outputFilePath = args[1];

String[] playerList = readFile(inputFilePath);


//DEBUG ----------------------------------------------------



int average = computeAverageScore(playerList);

int[] scoreList = getScores(playerList);

parallelSelectionSort(playerList, scoreList);

String[] selectPlayerList = selectPlayers(playerList, average);

savePlayerList(selectPlayerList, outputFilePath);

System.out.println("Wrote"+ outputFilePath);
} catch (FileNotFoundException e ){

  System.out.println(e.getMessage());
}
}


public static void help(){

  System.out.println("Usage: PlayerSelector Input_File Output_File");
}

public static String[] readFile(String inputFilePath) throws FileNotFoundException{
  int nPlayers = getNumberOfPlayers(inputFilePath);
String[] playerList = readPlayers(inputFilePath, nPlayers);

return playerList;

}

public static int[] getScores(String[] playerList){
int[] scoreList = new int [playerList.length];
for(int i=0; i<playerList.length; i++ ){
int score = getScoreFromRecord(playerList[i]);
scoreList[i] = score;

}

return scoreList;

}
public static String[] selectPlayers(String[] playerList, int average){
String[] selected = new String[playerList.length];
 int j  = 0;
 for ( int i = 0; i< playerList.length ; i ++ ){

   int score = getScoreFromRecord(playerList[i]);
   if ( score > average){

     selected[j] = playerList[i];
     i ++;
   }

 }

 String[] selectedPlayerList = new String[j];

 for ( int i = 0; i < selectedPlayerList.length; i++){

selectedPlayerList[i] = selected[i];


 }

return selectedPlayerList;
}

public static String[] selectPlayers(String[] playerList,int[] scoreList, int average){

int foundIndex = binarySearch(scoreList, average);
int nElements;
int beginIndex;
if ( foundIndex >= 0 ){

 nElements = scoreList.length - foundIndex - 1  ;
 beginIndex = foundIndex + 1;

} else {

int insertionPoint =  ( - foundIndex) - 1;
 nElements =  scoreList.length - insertionPoint;
 beginIndex = insertionPoint;


}

String[] selected = new String[nElements];
int j = 0;
for (int i = beginIndex; i< playerList.length; i++){

selected[j] = playerList[i];
j++;


}

return selected;
}

public static int binarySearch(int[] arr, int key ){

  int low = 0 ;
  int high = arr.length -1 ;

  while (low <= high ){

int mid = (high - low ) / 2 + low ;
int midValue = arr[mid] ;
if (key < midValue){
high = mid -1 ;

} else if (key > midValue){

low = mid + 1 ;


} else {


return mid;


}


  }

 return - ( low +1 );

}
public static int getScoreFromRecord (String playerRecord){

  String[] parts = playerRecord.split(",");
  // printArray(parts, "parts");
  int score = Integer.parseInt(parts[2]);
  return score ;


}

public static int computeAverageScore(String[] playerList){

//  int[] scoreList = new int[playerList.length];
int[]   scoreList = getScores(playerList);
int averageScore = computeAverage(scoreList);


return averageScore;


}



public static int computeAverage(int[] arr){

  int sum = 0 ;
  for (int i= 0; i<arr.length; i++ ){

    sum += arr[i];

  }

  double dAverage = (double)sum/(double)arr.length;
  int average = (int)Math.round(dAverage);


return average;

}

public static void savePlayerList(String[] selectedPlayerList, String outputFilePath)throws FileNotFoundException{

PrintWriter out = null;
try {

File outFile = new File(outputFilePath);
out = new PrintWriter(outFile);
for (int i = 0 ; i< selectedPlayerList.length; i++){

  out.println(selectedPlayerList[i]);
}

} finally {

if ( out != null ) out.close();

}




  return;

}


public static int getNumberOfPlayers(String inputFilePath) throws FileNotFoundException{
Scanner scanner = null;
int nRecords = 0 ;
  try {
    File inFile = new File(inputFilePath);
    scanner = new Scanner(inFile);
    while (scanner.hasNextLine()){

      scanner.nextLine();
      nRecords++;

    }
  } finally {
    if ( scanner != null) scanner.close();

  }
return 0;
}

public static String[] readPlayers(String inputFilePath, int nPlayers) throws FileNotFoundException{

String[] recordList = new String[nPlayers];

Scanner scanner = null;

try {
File inFile = new File(inputFilePath);

scanner = new Scanner(inFile);
int i = 0;
while (scanner.hasNextLine()){


recordList[i] = scanner.nextLine();
  i++;

  if ( i == recordList.length) break;
}

} finally {

if (scanner != null) scanner.close();


}

return null;
}

public static void parallelSelectionSort(String[] playerList, int [] scoreList){
for (int i =0 ; i < scoreList.length; i++){
  int indexSmallest = findSmallest(scoreList, i);
  swapElement(playerList, scoreList, i , indexSmallest);
}


}

public static int findSmallest(int[] scoreList, int begin){

int indexSmallest = begin;

for ( int i = 1 ; i < scoreList.length; i++){
int smallest = scoreList[indexSmallest];

if (scoreList[i] < smallest) indexSmallest = i;



}

return indexSmallest;

}


public static void swapElement(String[] playerList, int[] scoreList, int i, int j ){

int scoreI = scoreList[i];
String playerI = playerList[i];

scoreList[i] = scoreList[j];
playerList[i] = playerList[j];

scoreList[j] = scoreI;
playerList[j] = playerI;



}
/*
public static void printArray(String[] arr){

for (int i=0;  i <arr.length; i++){

System.out.printf("arr[%d] = %s\n", msg, i, arr);

}
}

public static void printArray(String[] arr, String msg){

for (int i=0;  i <arr.length; i++){

System.out.printf("%s[%d] = %s\n", msg, i, arr[i]);


}


}

public static void printArray(int[] arr, String msg){

for (int i=0;  i <arr.length; i++){

System.out.printf("%s[%d] = %d\n", msg, i, arr[i]);

}
}
*/


}
