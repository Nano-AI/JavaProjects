import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.*;

public class FileProcessing {
    // create a trio class
    public static class Trio<T> {
        // store first second and third values
        T first;
        T second;
        T third;
        // constructor
        public Trio(T first, T second, T third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }
    }

    // function that reads a file from a given path
    // bool line:
    //      true : return line by line
    //      false: return char by char
    public static ArrayList<String> readFile(String path, boolean line) {
        // create scanner
        Scanner s = null;
        // try to read file, print error if not
        try {
            s = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            System.out.println("File at path '" + path + "' not found.");
            return null;
        }
        // create array list
        ArrayList<String> o = new ArrayList<String>();
        // read each line
        if (line) {
            while (s.hasNextLine()) {
                o.add(s.nextLine());
            }
        } else {
            while (s.hasNext()) {
                o.add(s.next());
            }
        }
        // close scanner
        s.close();
        return o;
    }

    // method overload for readFile
    public static ArrayList<String> readFile(String path) {
        return readFile(path, false);
    }

    // function that compares the lines in a file
    public static ArrayList<Trio<String>> compareLines(String f1, String f2) {
        // read lines from both given files
        ArrayList<String> i1 = readFile(f1, true);
        ArrayList<String> i2 = readFile(f2, true);
        // create an arraylist with three values:
        // line one, line two, and line number
        ArrayList<Trio<String>> out = new ArrayList<>();
        // iterate through least number of lines from noth files
        for (int i = 0; i < Math.min(i1.size(), i2.size()); i++) {
            // boolean to store if they're same
            boolean same = true;
            // both lines
            String s1 = i1.get(i);
            String s2 = i2.get(i);
            // check if sizes of both lines are not the same
            // otherwise check the string itself
            if (s1.length() != s2.length()) {
                same = false;
            } else {
                // iterate through each line
                for (int j = 0; j < Math.min(s1.length(), s2.length()); j++) {
                    // check if char at each location of the string
                    if (s1.charAt(j) != s2.charAt(j)) {
                        // they're not the same; break
                        same = false;
                        break;
                    }
                }
            }
            // if they're not the same
            if (!same) {
                // add both strings and the line location to the array
                out.add(new Trio<>(s1, s2, Integer.toString(i + 1)));
            }
        }
        // returh the array
        return out;
    }

    // count the number of chars/words in a file
    private static HashMap<String, Integer> countText(String f1, boolean ch) {
        // store hashmap of String and int that keeps count of chars/strings
        HashMap<String, Integer> counts = new HashMap<>();
        // read the file
        ArrayList<String> file = readFile(f1);
        // iterate through the string
        for (String s : file) {
            // if counting chars
            if (ch) {
                // iterate through each char in the line
                for (int i = 0; i < s.length(); i++) {
                    // get the char at the location & lower case it
                    String a = Character.toString(s.charAt(i)).toLowerCase();
                    // check if it already exists in the hashmap
                    if (!counts.containsKey(a)) {
                        // add it with value of one
                        counts.put(a, 1);
                    } else {
                        // increment it
                        counts.put(a, counts.get(a) + 1);
                    }
                }
            } else {
                // if counting words
                // take the word, make it lower case, and remove all things that aren't letters a-z
                String a = s.toLowerCase().replaceAll("[^a-zA-Z ]", "");
                // check if it exists in the hashmap
                if (!counts.containsKey(a)) {
                    // add it with initial value of one
                    counts.put(a, 1);
                } else {
                    // increment it
                    counts.put(a, counts.get(a) + 1);
                }
            }
        }
        // return value
        return counts;
    }

    // counts the words in a file
    public static HashMap<String, Integer> countWords(String f1) {
        return countText(f1, false);
    }

    // counts the chars in a file
    public static HashMap<String, Integer> countChars(String f1) {
        return countText(f1, true);
    }

    // sorts a hash map by the values, not the keys
    public static LinkedHashMap sortedHashMap(HashMap<String, Integer> v) {
        // create an output hashmap
        LinkedHashMap<String, Integer> sorted = new LinkedHashMap<>();
        // create a list of values
        ArrayList<Integer> list = new ArrayList<>();
        // iterate through each value and add them to the output map
        for (Map.Entry<String, Integer> entry : v.entrySet()) {
            list.add(entry.getValue());
        }
        // sort the list from greatest to least
        Collections.sort(list, (o1, o2) -> o2 - o1);
        // iterate through each number in the list
        for (Integer num : list) {
            // iterate through each entry in the set
            for (Map.Entry<String, Integer> entry : v.entrySet()) {
                // if they're equal
                if (entry.getValue().equals(num)) {
                    // add the them to the new hashmap
                    sorted.put(entry.getKey(), num);
                }
            }
        }
        // returh the hashmap
        return sorted;
    }

    // main function
    public static void main(String[] args) {
        // input scanner
        Scanner s = new Scanner(System.in);

        // ask first + last name
        System.out.print("Enter a first file name: ");
        String f1 = s.nextLine();

        System.out.print("Enter a second file name: ");
        String f2 = s.nextLine();

        // close scanner
        s.close();

        // run coded functions to test them
        ArrayList<Trio<String>> t = compareLines(f1, f2);
        for (Trio<String> a : t) {
            System.out.printf("Line %s:\n" +
                    "> %s\n" +
                    "< %s\n", a.third, a.first, a.second);
        }

        LinkedHashMap charCount = sortedHashMap(countChars(f1));
        LinkedHashMap wordCount = sortedHashMap(countWords(f1));

        LinkedHashMap char1Count = sortedHashMap(countChars(f2));
        LinkedHashMap word1Count = sortedHashMap(countWords(f2));

        System.out.println("File 1 Data: ");
        System.out.println(charCount);
        System.out.println(wordCount);

        System.out.println("File 2 Data:");
        System.out.println(char1Count);
        System.out.println(word1Count);
    }
}
