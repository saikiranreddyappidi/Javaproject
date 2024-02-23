package LabPrograms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());
        String[] s = (in.readLine()).split(" ");
        int[] speed = new int[n];
        int[] temp = new int[n];
        int max = 0;
        for (int i = 0; i < n; i++) {
            temp[i] = 1;
            speed[i] = Integer.parseInt(s[i]);
            for (int j = 0; j < i; j++) {

                if (speed[i] <= speed[j])
                    temp[i] = Math.max(temp[i], temp[j] + 1);

            }

            if (temp[i] > max)
                max = temp[i];

        }

        ArrayList<Integer> al = new ArrayList<>();
        int flag = n - 1;
        for (int j = max; j > 0; j--) {

            for (int k = flag; k >= 0; k--) {
                if (temp[k] == j) {
                    flag = k;
                    al.add(k);
                    break;
                }

            }

        }
        int happy = max;

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (al.contains(i))
                continue;
            if (speed[i] <= min) {
                min = speed[i];
                happy++;
            }
        }
        System.out.print(happy);
    }
}