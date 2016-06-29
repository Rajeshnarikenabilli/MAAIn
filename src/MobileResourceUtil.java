package InstallOnDevices;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class MobileResourceUtil {
	private static String location;
		
	/**
	 * Function to run adb commands in command prompt
	 * @author Siri Kumar Puttagunta
	 * @param String command to be executed
	 * @return Process object for the command to be executed*/
 	public static Process adbCommandsRunInCMD(String command) {
		ProcessBuilder _builder = null;
		Process _proc = null;
		String _commandCmd = "";
		String ADBToolPath = "", ADBParentPath = "";
		String WinOsCommandString = "", OsCommandString = "";
		try {
			ADBToolPath = MobileResourceUtil.getAdbLocation();
			File _toolPath = new File(ADBToolPath);
			ADBParentPath = _toolPath.getParent() + "\\";
			_commandCmd = "\""+command+"\"";
			WinOsCommandString  = "cd /d \""+ADBParentPath+"\" & cmd.exe /c " + _commandCmd;
			OsCommandString = "cd /d \""+ADBParentPath+"\" " + _commandCmd.toString();
			if (SeleResourceUtil.getOSName().toLowerCase().indexOf("win") >= 0) {
				_builder = new ProcessBuilder("cmd.exe","/c",WinOsCommandString);
			} else {
				_builder = new ProcessBuilder(OsCommandString);
			}
			_proc = _builder.start();
			MobileResourceUtil.pause(500);
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		return _proc;
	}

	/**
	 * Function to return output from command prompt
	 * @author Siri Kumar Puttagunta
	 * @param Process - object
	 * @return ArrayList containing entire output from command prompt*/
	public static ArrayList<String> getadbCommandsOutputCMD(Process proc) {
		ArrayList<String> _output = new ArrayList<String>();
		boolean readInputlineFound = false;
		boolean readErrorlineFound = false;
		Scanner _sc = null;
		String lineRead = "";
		try {
			_sc = new Scanner(new InputStreamReader(proc.getInputStream()));
			while(_sc.hasNextLine()){
				readInputlineFound = true;
				if((lineRead = _sc.nextLine()) != null){
					_output.add(lineRead);
					System.out.println(lineRead); 
				}else{
					_output.add(null);
					System.err.println("Command execution of Process output from readline value as null");
				}
			}
			if(!readInputlineFound){
				_sc = new Scanner(new InputStreamReader(proc.getErrorStream()));
				while(_sc.hasNextLine()){
					readErrorlineFound = true;
					if((lineRead = _sc.nextLine()) != null){
						_output.add(lineRead);
						System.out.println(lineRead); 
					}else{
						_output.add(null);
						System.err.println("Command execution of Process output from readline value as null");
					}
				}
			}
			if(!readErrorlineFound){
				_output.add(null);
				//System.err.println("While reading a Process from Command execution given as no output its null");
			}
			_sc.close();			
		}catch (NullPointerException ne) {
			System.err.println(ne.getMessage());
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		return _output;
	}
    	
	/**
	 * Function to provide some static pause in milli-seconds
	 * @author Siri Kumar Puttagunta
	 * @param long static pause time in milli-seconds*/
	public static boolean pause(long timeInMilliSeconds) {
		
		boolean pauseTime = false;
		try {
			Thread.sleep(timeInMilliSeconds);
			pauseTime = true;
		} catch (InterruptedException ie) {
			System.err.println(ie.getMessage());
		}
		return pauseTime;
	}

    public static void setAdbLocation(String location){
    	File app = new File(location);
    	MobileResourceUtil.location = app.getAbsolutePath();
    }
    
    public static String getAdbLocation(){
    	return MobileResourceUtil.location;
    }
    
    /**
     * Function to destroy the Process object created by CommonFunctions.runCMD method
     * @author Siri Kumar Puttagunta
     * @param Process - object*/
    public static void destroyCMDProcess(Process proc) {
    	try {
    		if (proc != null) {
    			proc.destroy();
    		}
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    	}
    }
}
