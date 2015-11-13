package rasppiproject;

import com.pi4j.io.gpio.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;

/**
 *
 * @author Jeremy
 */

public class RaspPiProject {

    public static void main(String[] args) throws MalformedURLException, IOException 
    {
        boolean setlightsOn = true;
        boolean lightsOn = true;
        GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalMultipurpose mypin = gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_07, PinMode.DIGITAL_INPUT);

        // Sets it as an Output pin.
        mypin.setMode(PinMode.DIGITAL_OUTPUT);

        while(true)
        {
            //Handles Php
            URL url = new URL("http://csce.uark.edu/~ttchou/Senior_Design/SLLights/SL_main.php");
            URLConnection connection = url.openConnection();
            connection.connect();
            
            
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine= in.readLine();
            System.out.println("InLine: " + inputLine);
            //System.out.println(inputLine);
            in.close();
            
            if(inputLine.equals("1"))
            {
                System.out.println("In low 1");
                setlightsOn = false;
                if(!(mypin.isLow()))
                {
                    System.out.println("In low 2");
                    mypin.low();
                }
            }
            if(inputLine.equals("0"))
            {
                System.out.println("In high1 ");
                if(!(mypin.isHigh()))
                {
                    System.out.println("In high2 ");
                    mypin.high();
                }
            }
        }
    
    }
}
