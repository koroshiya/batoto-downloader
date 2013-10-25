import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;


public class URLParser {
	
	private final static int BUFFER = 4096;
	private final static boolean FAILSAFE = true;

	public static void directDownload(String oldPath, String home){
		
		File newDir = null;
		
		try{
			newDir = new File(home + File.separator + "comic");
		}catch(Exception ex){
			System.out.println(ex.toString());
			return;
		}
		String workDir = home;
		if (newDir.exists() || newDir.mkdir()){
			workDir = newDir.getAbsolutePath();
			System.out.println(workDir);
		}

		try{
			String fmtImg = "http://thepunchlineismachismo.com/comics/" + oldPath;
			System.out.println(fmtImg);
			URL filePath = findExtension(fmtImg, 0);
			System.out.println(filePath);
			Download(filePath, workDir);
		}catch(Exception ex){
			return;
		}	
	}

	public static boolean downloadFromURL(String oldPath, String home){
		if (!oldPath.endsWith("/") && !oldPath.endsWith("/1")){oldPath = oldPath + "/1";}
		if (!(oldPath.startsWith("http://") || oldPath.startsWith("https://"))){
			return false;
		}
		/**
		*TODO: Use regex for "///d{1,*}$/"
		*TODO: Lock newDir
		**/
		URL url = null;
		File newDir = null;
		try{
			url = new URL(URLDecoder.decode(oldPath, "UTF-16"));
			newDir = new File(home + File.separator + LastFolderInPath(url.toString()));
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		
		String workDir = home;
		if (newDir.exists() || newDir.mkdir()){
			workDir = newDir.getAbsolutePath();
			System.out.println(workDir);
		}
		
		String regex;
		boolean boolContinue = true;
		int i = 1;
		if (FAILSAFE){
			do{
				try{
					regex = findFormat(new URL(AbsoluteFolder(url.toString()) + i), false);
					Download(new URL(URLDecoder.decode(regex, "UTF-8")), workDir);
				}catch(Exception e){
					boolContinue = false;
				}
				i++;
			}while(boolContinue);
		}else{
			try{
				regex = findFormat(url, true);
			}catch (Exception ex){
				ex.printStackTrace();
				return false;
			}
			do{
				try{
					String form = FormatNumber(i, (regex.startsWith("http://img") 
												|| regex.startsWith("http://eu")) ? 6 : 2);
					
					String fmtImg = (regex + form);
					URL filePath = findExtension(fmtImg, i);
					Download(filePath, workDir);
					System.out.println("Downloaded file: " + i);
				}catch(Exception ex){
					ex.printStackTrace();
					boolContinue = false;
				}
				i++;
			}while(boolContinue);
		}
		return i != 1;
	}

	private static URL findExtension(String path, int i) throws Exception{
	
		URL url;
		String[] extensions = {".png", ".jpg", ".gif"};	

		for (String s : extensions){
			url = new URL(URLDecoder.decode(path + s, "UTF-8"));
			if (testURL(url)){return url;}
		}//form
		String form = FormatNumber(i + 1, 2);
		for (String s : extensions){
			url = new URL(URLDecoder.decode(path + "-" + form + s, "UTF-8"));
			if (testURL(url)){return url;}
		}

		return null;	
	}

	private static boolean testURL(URL url) throws Exception{
		
		int code = 0;
		try{
			HttpURLConnection huc = (HttpURLConnection) url.openConnection(); 
			huc.setDoOutput(true);
			huc.setRequestMethod("GET"); 
			huc.connect();
			code = huc.getResponseCode();
		}catch (Exception ex){
			System.out.println("testURL" + ex.toString());
		}
		return code == 404 ? false : true;		
		
	}

	private static String findFormat(URL url, boolean dir) throws Exception{
		
		url = new URL(URLDecoder.decode(url.toString(), "UTF-16"));
		URLConnection spoof = url.openConnection();
		InputStream inStream = (spoof.getInputStream());
		InputStreamReader in = new InputStreamReader(inStream);
        
        String encoding = spoof.getContentEncoding();
        InputStream gin;
		encoding = encoding == null ? "UTF-8" : encoding;
		if ("gzip".equalsIgnoreCase(encoding)) {
			gin = new GZIPInputStream(inStream);
		}else{
			String body = IOUtils.toString(inStream, encoding);
			gin = IOUtils.toInputStream(body);
		}
		
		BufferedReader bin = new BufferedReader(new InputStreamReader(gin));
		
        String inputLine;
        while ((inputLine = bin.readLine()) != null){
			//System.out.println(inputLine);
            if ((inputLine.contains("http://img.batoto.net/comics/2")) || 
				(inputLine.contains("http://eu.batoto.net/comics/2"))){
				int start = inputLine.indexOf("http://");
				inputLine = inputLine.substring(start);
				int end = inputLine.indexOf('"');
				inputLine = inputLine.substring(0, end);
				return dir ? AbsoluteFolder(inputLine) + "img" : inputLine;
			}else if (inputLine.contains("http://arc.batoto.net/comics/2")){
				int start = inputLine.indexOf("http://");
				inputLine = inputLine.substring(start);
				int end = inputLine.indexOf('"');
				inputLine = inputLine.substring(0, end);

				return dir ? AbsoluteFolder(inputLine) : inputLine;
			}
		}
        in.close();
		return null;
	}
	
	private static void Download(URL url, String workDir) throws IOException{
		System.out.println("url: "+url);
		BufferedInputStream in = new BufferedInputStream(url.openStream());
		FileOutputStream outStream = new FileOutputStream(workDir + File.separator + LastFileInPath(url.toString()));
		BufferedOutputStream out = new BufferedOutputStream(outStream);
		
	    byte data[] = new byte[BUFFER];
	    int count = in.read(data, 0, BUFFER);
	    do{
	        out.write(data, 0, count);
	        count = in.read(data, 0, BUFFER);
	    }while (count != -1);
	    
	    out.close();
	    outStream.close();
	    in.close();
	}
	
	private static String LastFileInPath(String path){
		int start = path.lastIndexOf('/');
		return path.substring(start + 1);
	}
	
	private static String LastFolderInPath(String path){
		int start = path.lastIndexOf('/');
		String newPath = path.substring(0, start);
		start = newPath.lastIndexOf('/');
		return newPath.substring(start + 1);
	}

	private static String AbsoluteFolder(String path){
		int start = path.lastIndexOf('/');
		return path.substring(0, start + 1);
	}
	
	private static String FormatNumber(int i, int places){
		StringBuffer str = new StringBuffer("");
		int precede = 0;
		
		if (i < 10){precede = 1;}
		else if (i < 100){precede = 2;}
		else if (i < 1000){precede = 3;}
		
		for (int a = 0; a < places - precede; a++){
			str.append("0");
		}
		str.append(i);
		
		return str.toString();
	}
	
}
