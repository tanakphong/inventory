package com.wesoft.inventory.Class;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

import jcifs.smb.SmbFile;

/**
 * Created by USER275 on 10/6/2017.
 */

public class Utils {


    public static String getIPAddress() {
        ArrayList<String> tmpNetwork = new ArrayList<>();
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        inetAddress.getAddress();
                        if (!inetAddress.getHostAddress().toString().startsWith("fe80")) {
                            tmpNetwork.add(inetAddress.getHostAddress().toString());
                        }
                    }
                }
            }
            return tmpNetwork.get(0);
        } catch (SocketException ex) {
            Log.i("dlg", "getIPAddress : " + ex.getMessage());
            return null;
        }
    }

    public static String GetAppPath(Context context){
        String extStoreage = Environment.getExternalStorageDirectory().getAbsolutePath();
        String packageName = context.getPackageName();
        return  extStoreage + "/Android/data/" + packageName;
    }

    public static void CreateDirectory(File fileOrFolder){
        if (!fileOrFolder.exists()) {
            fileOrFolder.mkdirs();
        }
    }

    public static void RemoveDirectory(File fileOrFolder){
        if (fileOrFolder.isDirectory())
        {
            String[] children = fileOrFolder.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(fileOrFolder, children[i]).delete();
            }
        }
        fileOrFolder.delete();
    }

    public static boolean DownloadFileFormSMB(SmbFile newfile, File oldfile){
        try{
            if(newfile.length() != oldfile.length()) {
                InputStream is = newfile.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                //We create an array of bytes
                byte[] data = new byte[250];
                int current = 0;

                while ((current = bis.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, current);
                }

                FileOutputStream fos = new FileOutputStream(oldfile);
                fos.write(buffer.toByteArray());
                fos.flush();
                fos.close();
                buffer.flush();
                buffer.close();
                bis.close();
                return true;
            }else{
                return false;
            }
        }catch (Exception aE){
            aE.printStackTrace();
            return false;
        }
    }

    public static String readTextFilePath(String path,String delimiter, int loop) {
        try {

            File myFile = new File(path);
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader bufferedreader = new BufferedReader(
                    new InputStreamReader(fIn));

            String line;
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = bufferedreader.readLine()) != null) {
                for (int i = 0; i < loop; i++) {
                    stringBuilder.append(line);
                    stringBuilder.append('\t');
                }
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
