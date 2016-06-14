package org.generations.logs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class LogsHandler {
    public static final String URL = "http://localhost:9200/logs/tng/";
    
    public static boolean push(String jsonString) throws Exception {
        URL obj = new URL(URL);
	HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setUseCaches(false);
        con.setRequestProperty( "Content-Type", "application/json" );
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod("POST");
        con.connect();
        OutputStream os = con.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write(jsonString);
        osw.flush();
        osw.close();
        
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader( con.getInputStream(),"utf-8"));
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        System.out.println(sb.toString());
        
        return true;
    }
}
