/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine;

import java.io.IOException;

public class DrawMachineSimLoader {

    public static void main(String[] args) throws IOException {
        Application application = new DrawMachineSim();
        application.start();
    }

}
