package InstallOnDevices;
import java.util.ArrayList;

public class InstallMultipleDevices {
	private static String apkPackageValue, apkLocationPath;

	public static void main(String[] args) {
		try {
			if(args.length<3){
				throw new Exception("Mandatory Arguments Should not be less than 4");
			}
			if(args[0].toString().equalsIgnoreCase("install")){
				MobileResourceUtil.setAdbLocation(args[2].toString());
				apkLocationPath = args[1].toString();
			}else if(args[0].toString().equalsIgnoreCase("uninstall")){
				MobileResourceUtil.setAdbLocation(args[2].toString());
				apkPackageValue = args[1].toString();
			}
			Process _proc = MobileResourceUtil.adbCommandsRunInCMD("adb devices -l");
			ArrayList<String> _strList = MobileResourceUtil.getadbCommandsOutputCMD(_proc);
			ArrayList<String> _ListDevices = new ArrayList<String>();
			for(String _str : _strList){
				try {
					if(_str.endsWith("device")){
						String serialNumber = _str.split("device")[0].trim();
						_ListDevices.add(serialNumber);
						System.out.println(serialNumber);
					}else if (_str.endsWith("device:m0")){
						String serialNumber = _str.split("device product")[0].trim();
						_ListDevices.add(serialNumber);
						System.out.println(serialNumber);
					}
				} catch (NullPointerException nu) {
					if(_str == null){
						break;
					}
				}catch(Exception ex){
					System.out.println("Exception caught while finding the devices");
					System.out.println(ex.getMessage());
				}
			}
			MobileResourceUtil.destroyCMDProcess(_proc);
			
			//install/UnInstall the apk file in all the listed devices.
			if(args[0].toString().equalsIgnoreCase("install")){
				if(_ListDevices.size()>0){
					for(int i= 0; i<_ListDevices.size();i++){
						String Command = "adb -s " + _ListDevices.get(i) + " install " + apkLocationPath;
						Process _procObj = MobileResourceUtil.adbCommandsRunInCMD(Command);
						ArrayList<String> _strInstalSucc = MobileResourceUtil.getadbCommandsOutputCMD(_procObj);
						for (String string : _strInstalSucc) {
							try {
								if(string.contains("Failure")){
									System.err.println(string);
									System.err.println("Apk file instalation is failed in Device: "+_ListDevices.get(i)+"");
								}else if(string.contains("Success")){
									System.out.println(string);
									System.out.println("Apk file instalation is Success in Device: "+_ListDevices.get(i)+"");
								}
							}catch (NullPointerException nul) {
								break;
							}catch (Exception e) {
								System.out.println(e.getMessage());
							}
						}
						MobileResourceUtil.destroyCMDProcess(_procObj);
					}
					
				}else{
					System.err.println("list of devices are not found");
				}
			}else if(args[0].toString().equalsIgnoreCase("uninstall")){
				if(_ListDevices.size()>0){
					for(int i= 0; i<_ListDevices.size();i++){
						String Command = "adb -s " + _ListDevices.get(i) + " uninstall " + apkPackageValue;
						Process _procObj = MobileResourceUtil.adbCommandsRunInCMD(Command);
						ArrayList<String> _strInstalSucc = MobileResourceUtil.getadbCommandsOutputCMD(_procObj);
						for (String string : _strInstalSucc) {
							try {
								if(string.contains("Failure")){
									System.err.println(string);
									System.err.println("Apk file Uninstalation is failed in Device: "+_ListDevices.get(i)+"");
								}else if(string.contains("Success")){
									System.out.println(string);
									System.out.println("Apk file Uninstalation is Success in Device: "+_ListDevices.get(i)+"");
								}
							}catch (NullPointerException nul) {
								break;
							}catch (Exception e) {
								System.out.println(e.getMessage());
							}
						}
						MobileResourceUtil.destroyCMDProcess(_procObj);
					} 
				}else{
					System.err.println("list of devices are not found");
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
