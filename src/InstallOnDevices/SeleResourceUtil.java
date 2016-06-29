package InstallOnDevices;
public class SeleResourceUtil {
	
	private static String _osName = System.getProperty("os.name");
	private static final String OS_ARCH = System.getProperty("os.arch");
	private static final String OS_VERSION = System.getProperty("os.version");
	private static final String JAVA_VERSION = System.getProperty("java.version");
				
	/**
     * Function to retrieve OS Name for execution machine
	 * @author Rajesh Narikenabilli
	 * @return String name of the OS*/
	public static String getOSName() {
		return _osName;
	}
	  
    /**
	 * Function to replace last occurrence of any substring
	 * @param String - string, String substring, String replacement
	 * @return String after the replacement operation*/
	public static String replaceLast(String string, String substring,
			String replacement) {
		int lastIndex = string.lastIndexOf(substring);
		if (lastIndex == -1) {
			return string;
		} else {
			return string.substring(0, lastIndex) + replacement
					+ string.substring(lastIndex + substring.length());
		}
	}

	/**
	 * Function to check the architecture of the system (64/32 bit)
	 * @author Rajesh Narikenabilli
	 * @return true if 64 bit else false*/
	public static boolean is64Bit() {
		String arch = System.getenv("PROCESSOR_ARCHITECTURE");
		if (arch.endsWith("64")) {
			return true;
		} else {
			return false;
		}
	}
    
	/**
	 * Function to give architecture of the system (64/32 bit)
	 * @author Rajesh Narikenabilli
	 * @return String of 64 bit else 32 bit*/
	public static String osArch(){
		return OS_ARCH;
	}
	
	/**
	 * Function to give JAVA Version installed on the machine.
	 * @author Rajesh Narikenabilli
	 * @return String of java Version*/
	public static String javaVersion(){
		return JAVA_VERSION;
	}
    
	/**
	 * Function to give OS Version installed on the machine.
	 * @author Rajesh Narikenabilli
	 * @return String of java Version*/
	public static String osVersion(){
		return OS_VERSION;
	}
}
