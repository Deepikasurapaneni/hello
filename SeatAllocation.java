import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class SeatAllocation {
    private static final int COLLEGES = 20;
    private static final int BRANCHES = 5;
    private static final int SEATS = 60;
    private static final int PREFERENCES = 2;
    private static final int MAX_LEN = 50;

    private static class Student {
        int rank;
        String name;
        int[] preferences;

        Student(int rank, String name, int[] preferences) {
            this.rank = rank;
            this.name = name;
            this.preferences = preferences;
        }
    }

    private static void allocateSeats(Student[] students, int[][] collegeAllotment) {
        int allotted;
        for (int i = 0; i < students.length; i++) {
            allotted = 0;
            for (int j = 0; j < PREFERENCES && allotted < SEATS; j++) {
                for (int k = 0; k < COLLEGES * BRANCHES && allotted < SEATS; k++) {
                    int collegeIndex = k / BRANCHES;
                    int branchIndex = k % BRANCHES;
                    if (collegeAllotment[collegeIndex][branchIndex] == -1 && Arrays.equals(students[i].preferences, new int[] {collegeIndex, branchIndex})) {
                        collegeAllotment[collegeIndex][branchIndex] = students[i].rank;
                        allotted++;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Student[] students;
        int[][] collegeAllotment = new int[COLLEGES][BRANCHES];
        int n = 0, rank, collegePreference, branchPreference;
        String name;
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("input1.csv"));

            String line = reader.readLine();
            while (line != null) {
                String[] values = line.split(",");
                if (values.length != 4) {
                    System.err.println("Invalid input: " + line);
                    System.exit(1);
                }

                rank = Integer.parseInt(values[0]);
                name = values[1];
                collegePreference = Integer.parseInt(values[2]) - 1;  // adjust to zero-based index
                branchPreference = Integer.parseInt(values[3]) - 1;  // adjust to zero-based index
                students[n] = new Student(rank, name, new int[] {collegePreference, branchPreference});
                n++;

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error opening file: " + e.getMessage());
            System.exit(1);
        }

        students = new Student[n];

        for (int i = 0; i < COLLEGES; i++) {
            for (int j = 0; j < BRANCHES; j++) {
                collegeAllotment[i][j] = -1;
            }
        }

        allocateSeats(students, collegeAllotment);

        System.out.println("Allocation of Seats:");
        for (int i = 0; i < COLLEGES; i++) {
            for (int j = 0; j < BRANCHES; j++) {
                System.out.printf("College %d Branch %d: %d\n", i + 1, j + 1, collegeAllotment[i][j]);
            }
        }
    }
}