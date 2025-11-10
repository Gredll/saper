import java.util.Scanner;

public class Test {

    int getSize() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите размер поля: ");
        return scanner.nextInt();
    }
    public int size = getSize();
    public char[][] createField() {
        int[][] field = new int[size][size];
        char[][] Cfield = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = (int) (Math.random() * 100) ;
                if (field[i][j]<30){
                    Cfield[i][j] = (char) (3+'0');
                }else {
                    Cfield[i][j] = (char) (2+'0');
                }
            }

        }
        return Cfield;
    }
    public char[][] DrawField() {
        char[][] Dfield = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Dfield[i][j] = '?';
            }
        }
        return Dfield;
    }
}