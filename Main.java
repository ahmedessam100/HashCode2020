import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static int B;
    private static int L;
    private static int D;
    private static int [] books;
    private static List<Library> libraries;
    private static Queue<Library> signedUp;
    private static Set<Book> scanned;


    public static void main(String[] args) {
        String[] inputFiles = new String[]{"in_a.txt","in_b.txt","in_c.txt","in_d.txt","in_e.txt","in_f.txt"};
//        String[] inputFiles = new String[]{"in_f.txt"};
        for (String inputFile : inputFiles) {
            books = null;
            libraries = null;
            signedUp = new ArrayDeque<>();
            scanned = new HashSet<>();
            readInput(inputFile);
            solve();
            saveOutput(inputFile.replace("in","out"));
        }

    }



    private static void readInput(String file){
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(file));
            B = scanner.nextInt();
            L = scanner.nextInt();
            D = scanner.nextInt();


            books = new int[B];
            for (int i = 0; i < B; i++) {
                books[i] = scanner.nextInt();
            }

            libraries = new ArrayList<>(L);
            for (int i = 0; i < L; i++) {
                int b = scanner.nextInt();
                int signUpProcessDays = scanner.nextInt();
                int shipCapacityPerDay = scanner.nextInt();

                Library library = new Library(i, signUpProcessDays,shipCapacityPerDay);
                Book[] libBooks = new Book[b];
                for (int j = 0; j < b; j++) {
                    int bookId= scanner.nextInt();
                    libBooks[j] = new Book(bookId,books[bookId]);
                }
                library.addBooks(libBooks);

                libraries.add(library);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


    private static void solve(){
        Library signingUpLibrary = null;

        for (int i = 0; i < D; i++) {
            if (signingUpLibrary == null || signingUpLibrary.signUpProcessDays == 0){
                if (signingUpLibrary != null) {
                    libraries.remove(signingUpLibrary);
                    signedUp.add(signingUpLibrary);
                }
                signingUpLibrary = getOptimalLibrary(i);
            }

            if (signingUpLibrary != null)
                signingUpLibrary.signUpProcessDays--;

            for (Library signedUpLib : signedUp) {
                signedUpLib.scan(scanned);
            }

        }


    }

    private static Library getOptimalLibrary(int currentDay){
        float maxRatio = 0;
        Library chosenLibrary = null;
        for (Library library: libraries) {
            float ratio = library.getRatioInDays(D - currentDay - library.signUpProcessDays);

            if (ratio > maxRatio){
                maxRatio = ratio;
                chosenLibrary = library;
            }
        }

        return chosenLibrary;
    }


    private static void saveOutput(String file) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(signedUp.size() + "\n");

            for (Library signedUpLib :signedUp) {
                writer.write(signedUpLib.id + " " + signedUpLib.scannedBooks.size() + "\n");
                writer.write(Arrays.stream(signedUpLib.scannedBooks.toArray())
                        .map(String::valueOf)
                        .collect(Collectors.joining(" ")) + "\n");
            }


            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
