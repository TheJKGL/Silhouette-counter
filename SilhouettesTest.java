package malakhov.searching_silhouettes;

import acm.graphics.GImage;

public class SilhouettesTest {

    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";

    public static void main(String[] args) throws InterruptedException {
        long startOfTheProgram = System.currentTimeMillis();
        check(new GImage("assets/testSilhouettes/1(5).png"),5);
        check(new GImage("assets/testSilhouettes/2(36).png"),36);
        check(new GImage("assets/testSilhouettes/3(5).png"),5);
        check(new GImage("assets/testSilhouettes/4(9).png"),9);
        check(new GImage("assets/testSilhouettes/5(5).png"),5);
        check(new GImage("assets/testSilhouettes/6(8).png"),8);
        check(new GImage("assets/testSilhouettes/7(10).png"),10);
        check(new GImage("assets/testSilhouettes/8(9).png"),9);
        check(new GImage("assets/testSilhouettes/9(10).png"),10);
        check(new GImage("assets/testSilhouettes/10(77).png"),77);
        check(new GImage("assets/testSilhouettes/11(4).jpg"),4);
        check(new GImage("assets/testSilhouettes/13(7).jpg"),7);
        check(new GImage("assets/testSilhouettes/14(20).jpg"),20);
        check(new GImage("assets/testSilhouettes/15(29).jpg"),29);
        check(new GImage("assets/testSilhouettes/16(13).jpg"),13);
        check(new GImage("assets/testSilhouettes/skater.jpg"),1);
        long endOfTheProgram = System.currentTimeMillis();
        long workTime = endOfTheProgram - startOfTheProgram;
        System.out.println("workTime: " + workTime + " milliseconds");
    }

    public static void check(GImage image, int expected) throws InterruptedException {
        Assignment13Part2 object = new Assignment13Part2();
        double result = object.findSilhouettes(image);
        if ( result == expected) {
            System.out.println(GREEN + "Test passed. Result: " + result + " Expected result: " + expected);
        } else {
            System.out.println(RED + "Test failed. Result: " + result + " Expected result: " + expected);
        }
    }
}
