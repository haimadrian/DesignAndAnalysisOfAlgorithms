package algorithm;

/**
 * @author Haim Adrian
 * @since 29-Jun-20
 */
public class MatrixChainMultiplication implements AlgorithmIfc {
    /** Cost for each multiplication. Final cost is at [0,n-1] */
    private int[][] m;

    /** Where we separate left part from right part in order to put parentheses that result in lowest cost */
    private int[][] s;

    @Override
    public void execute() {
        int matrixCount = readInt("How many matrices? (for example: 4 in case there are A,B,C,D)");
        int[] matricesDimension = new int[matrixCount + 1];
        System.out.println("Enter the vector representing the matrices dimension. (length=" + (matrixCount + 1) + ")");
        System.out.println("(for example: 5 2 10 5 3, those are the dimensions from the presentation. Result will be: 160)");
        System.out.println("Note that the numbers should be entered one by another and not in a single line..");
        for (int i = 0; i < matrixCount + 1; i++) {
            matricesDimension[i] = readInt("");
        }

        matrixChainOrder(matricesDimension);

        System.out.println("\nTotal cost can be found at [0," + (matrixCount-1) + "]:  " + m[0][matrixCount-1] + "    - O(n^3)");
        System.out.println(matrixToString(m));
        System.out.println("\nHere is the matrix that tells where to put the parentheses");
        System.out.println(matrixToString(s));

        System.out.println("\nAnd here is how it looks like");
        printParentheses(s, 0, matrixCount - 1);
        System.out.println();
    }

    // Matrix A(i) has dimension matricesDimension[i] x matricesDimension[i+1] for i = 0..n-1
    private void matrixChainOrder(int[] matricesDimension) {
        int matrixCount = matricesDimension.length - 1;
        m = new int[matrixCount][matrixCount] ;
        s = new int[matrixCount][matrixCount];

        // The cost (minimum number of scalar multiplications) is zero when multiplying one matrix.
        // Hence we fill in the main diagonal ([i][i]) with zeroes, and the rest are set to -1 so we will ignore
        // the bottom triangle when printing a matrix.
        for (int i = 0; i < matrixCount ; i++) {
            for (int j = 0; j < matrixCount; j++) {
                m[i][j] = i == j ? 0 : -1;
                s[i][j] = i == j ? 0 : -1;
            }
        }

        // L is used to count the times we calculate diagonals in the matrix m.
        // This is the amount of diagonals that we run on until we get to [0,n-1]
        // Example of scan: [0,1], [1,2], [2,3] -> [0,2], [1,3] -> [0,3]   (in case n = 4 matrices)
        // Subsequence lengths
        for (int l = 1; l < matrixCount; l++) {
            // Run over rows until we get to n-l, to perform a diagonal iteration.
            for (int i = 0; i < matrixCount - l; i++) {
                int j = i + l;
                m[i][j] = Integer.MAX_VALUE;

                // Try any option that we have.
                // For example when i=0,j=3, for ABCD, 0=(A)(BCD),   1=(AB)(CD),   2=(ABC)(D)
                // when i=1,j=3, for BCD, 1=(B)(CD),   2=(BC)(D)
                // when i=1,j=2, for BC, 1=(B)(C)
                // and so on
                for (int k = i; k < j; k++) {
                    int cost = m[i][k] + m[k+1][j] + matricesDimension[i]*matricesDimension[k+1]*matricesDimension[j+1];
                    if (cost < m[i][j]) {
                        m[i][j] = cost;

                        // Index of the subsequence split that achieved minimal cost (Human index starting from 1)
                        s[i][j] = k+1;
                    }
                }
            }
        }
    }

    private String matrixToString(int[][] matrix) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {
            if (sb.length() > 0) {
                sb.append(System.lineSeparator());
            }

            sb.append("[");
            for (int j = 0; j < matrix[i].length; j++) {
                String number = String.format("%5d", Integer.valueOf(matrix[i][j]));
                sb.append(matrix[i][j] != -1 ? number : String.format("%5s", " ")).append(", ");
            }
            sb.delete(sb.length()-2, sb.length());
            sb.append("]");
        }

        return sb.toString();
    }

    private void printParentheses(int[][] s, int i, int j) {
        if (i == j) {
            System.out.print("M" + (i+1));
            return;
        }

        int k = s[i][j];

        if (i != (k-1)) {
            System.out.print("(");
        }

        printParentheses(s, i, k-1);

        if (i != (k-1)) {
            System.out.print(")");
        }
        System.out.print("*");
        if (k != j) {
            System.out.print("(");
        }

        printParentheses(s, k, j);

        if (k != j) {
            System.out.print(")");
        }
    }
}

