import java.util.Scanner;

/*public class Test {

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
}*/
public class main {

    //  открытие безопасных клеток
    private static void revealEmptyTiles(char[][] field, char[][] visible, int row, int col) {
        if (row < 0 || col < 0 || row >= field.length || col >= field[0].length) return;
        if (visible[row][col] != '?') return;
        if (field[row][col] == '3') return;

        int minecount = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int nr = row + dr;
                int nc = col + dc;
                if (nr >= 0 && nr < field.length && nc >= 0 && nc < field[0].length) {
                    if (field[nr][nc] == '3') minecount++;
                }
            }
        }

        visible[row][col] = (minecount == 0) ? '0' : (char) (minecount + '0');

        if (minecount == 0) {
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr != 0 || dc != 0) {
                        revealEmptyTiles(field, visible, row + dr, col + dc);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        //инициализация переменых
        Scanner scanner = new Scanner(System.in);
        Test test = new Test();
        boolean play = true, lchoose = false, validcheck = true, firstclicl = true;
        int horizontal, vertical, h, v, minecount = 0, mineam = 0, freettittle = 0;
        char[][] field = test.createField();
        char[][] visiblefield = test.DrawField();
        String choose = " ";

        // цикл отрисовки поля и подсчёта мин и пустых клеток
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(visiblefield[i][j] + " ");
                if (field[i][j] == '3') {
                    mineam++;
                } else {
                    freettittle++;
                }
            }
            System.out.println();
        }
        System.out.println("количество мин " + mineam);

        //игровой цикл
        while (play) {
            //ввод кординат клетки
            System.out.println("введите число по горизонтале");
            horizontal = scanner.nextInt();
            horizontal--;
            System.out.println("введите число по вертикале");
            vertical = scanner.nextInt();
            vertical--;
            scanner.nextLine();

            //проверка на первый выбор , клетка всегда должна быть ровна 2
            if (firstclicl) {
                firstclicl = false;

                // очистка мины с выбранной клетки и соседей
                for (int i1 = -1; i1 <= 1; i1++) {
                    for (int j1 = -1; j1 <= 1; j1++) {
                        int newV = vertical + i1;
                        int newH = horizontal + j1;
                        if (newV >= 0 && newV < field.length && newH >= 0 && newH < field[0].length) {
                            field[newV][newH] = '2'; // убрать мины вокруг и в самой клетке
                        }
                    }
                }

                // повторное открытие безопасной зоны
                revealEmptyTiles(field, visiblefield, vertical, horizontal);
                continue; // пропускаем ввод d/f — уже раскрыли
            }

            System.out.println("Вы хотите разминировать или поставить флаг ?   d/f");
            choose = scanner.nextLine();

            if (choose.equals("d")) {
                // проверка клетки
                switch (field[vertical][horizontal]) {
                    case '3':
                        play = false;
                        System.out.println("Вы проиграли");
                        visiblefield[vertical][horizontal] = '!';
                        break;
                    case '2':
                        //подсчёт мин вокруг клетки
                        v = vertical - 1;
                        h = horizontal - 1;
                        minecount = 0;
                        for (int i1 = 0; i1 < 3; i1++) {
                            for (int j1 = 0; j1 < 3; j1++) {
                                int newV = vertical + i1;
                                int newH = horizontal + j1;
                                if (newV >= 0 && newV < field.length && newH >= 0 && newH < field[0].length) {
                                    if (field[newV][newH] == '3') {
                                        minecount++;
                                    }
                                }
                            }
                        }
                        v = 0;
                        h = 0;
                        System.out.println("продолжай");
                        char cminecount = (char) (minecount + '0');
                        if (minecount != 0) {
                            visiblefield[vertical][horizontal] = cminecount;
                        } else {
                            visiblefield[vertical][horizontal] = '0';
                        }
                        freettittle--;
                        break;
                    case '*':
                        System.out.println("выбери другую клетку ");
                        break;
                    case 'F':
                        System.out.println("здесь мина");
                        break;
                }
            } else if ((choose.equals("f"))) {
                field[vertical][horizontal] = 'F';
                visiblefield[vertical][horizontal] = 'F';
                mineam--;
            } else {
                System.out.println("Вы ввели неправильное значение");
            }

            //отрисовка поля
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[i].length; j++) {
                    System.out.print(visiblefield[i][j] + " ");
                }
                System.out.println();
            }

            choose = " ";
            vertical = 0;
            horizontal = 0;

            //проверка на условие победы
            if (freettittle == 0 | mineam == 0) {
                play = false;
                System.out.println("Ты победил");
            }
        }
    }

}
