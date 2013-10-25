import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class URLList {
	
	private Preferences prefs = Preferences.userRoot().node("BatotoSaveList");
	
	public void saveList(ArrayList<String> fileList){
		
		try {
			prefs.clear();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		
		int i = -1, size = fileList.size();
		while (++i < size){
			prefs.put("url"+i, fileList.get(i));
		}
		
	}
	
	public ArrayList<String> loadList(){
		
		ArrayList<String> urls = new ArrayList<String>();
		int i = 0;
		String temp;
		
		while((temp = prefs.get("url"+(i++), "-1")) != "-1"){
			urls.add(temp);
		}
		
		return urls;
	}
	
}
