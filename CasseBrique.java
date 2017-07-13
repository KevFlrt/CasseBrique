/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kevin
 */

public class CasseBrique {
    public static void main(String[] arrstring) {
        try {
            CasseBFrame casseBFrame = new CasseBFrame();
            while (true) {
                casseBFrame.go();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}