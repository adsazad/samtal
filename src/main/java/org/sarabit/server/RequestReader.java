/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sarabit.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author arashdeep
 */
public class RequestReader {

    String requestStr;

    public RequestReader(InputStream inputStream) throws IOException {
        InputStreamReader inRequestStream = new InputStreamReader(inputStream);
        BufferedReader inRequestReader = new BufferedReader(inRequestStream);
        this.requestStr = inRequestReader.readLine();
        while (inRequestReader != null) {
            System.out.println(this.requestStr);
            this.requestStr = inRequestReader.readLine();
        }
    }
}
