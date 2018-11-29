package base.tools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import properties.clsProperties;
import utils.clsGetARSPath;

public class clsPost {
    public static final String  F_CONFIGFILENAME = "system.properties";

    public static void sendInflux(String module, String measurement, Double value) {
        sendInflux( module,  measurement,  Double.toString(value)); 
    }
    public static void sendInflux(String module, String measurement, String value) {
        String oPath = clsGetARSPath.getConfigPath();        
        clsProperties oProp = clsProperties.readProperties(oPath, F_CONFIGFILENAME);
        String url = oProp.getProperty("influxdb","");  //"http://localhost:8086/write?db=sima";
        String empty = new String();
        if (value.equals(empty)) return;
        
        URL obj;
        try {
            obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // add request header
            con.setRequestMethod("POST");
            // con.setRequestProperty("User-Agent", "Mozilla/5.0");
            // con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = measurement + ",module=" + module + " value=" + value;

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } catch (IOException e) {
            // TODO (brandy) - Auto-generated catch block
            e.printStackTrace();
        }

    }

}
