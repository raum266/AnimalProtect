package me.damo1995.AnimalProtect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.plugin.PluginDescriptionFile;

public class UpdateChecker {
    /* 
     * @Author: DreTaX | SaveIt Update Checker File, rewritten from scratch. 
     */
  
    protected AnimalProtect plugin; 
    protected String updateVersion; 
    protected int curVer; 
    protected int updateVer; 
  
    public UpdateChecker(AnimalProtect instance) 
    { 
        this.plugin = instance; 
    } 
  
    public Boolean isLatest() { 
        System.out.println("[AnimalProtect]: Checking for updates. Please wait."); 
        try { 
            updateVer = 0; 
            curVer = 0; 
            // Opening link 
            URLConnection yc = new URL("http://animalprotect.tk/version/version.html").openConnection(); 
            // We will read the line 
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream())); 
            // Getting your plugins version 
            PluginDescriptionFile pdf = this.plugin.getDescription(); 
            String version = pdf.getVersion(); 
            // Replacing dots from the line, so we don't get errors 
            this.updateVersion = in.readLine().replace(".", "");
            // Parsing 
            updateVer = Integer.parseInt(this.updateVersion); 
            curVer = Integer.parseInt(version.replace(".", "")); 
            // If the updateversion is bigger then current 
            if (updateVer > curVer) { 
                System.out.println("A new version of AnimalProtect is available: " + this.updateVersion); 
                System.out.println("Your current version is:  " + version); 
                System.out.println("Get it From: dev.bukkit.org/server-mods/animalprotect"); 
                // Return false because we have a new update 
                return false; 
            } 
            System.out.println("[AnimalProtect]: No Updates Found..."); 
            // Close connection 
            in.close(); 
            // Return true because we have the latest 
            return true; 
        } 
        catch (Exception e) { 
            // Error 
            e.printStackTrace(); 
            System.out.println("[AnimalProtect]: Error Occured while checking, please open ticket if it fails again!"); 
        }return true; 
    } 
  
    public String getUpdateVersion() { 
        return this.updateVersion; 
    } 
      
}
