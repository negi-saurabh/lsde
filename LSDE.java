package projectlsde;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LSDE {
	public static void main(String[] args) throws FileNotFoundException,IOException, ParseException {


		File dir = new File("C:\\Users\\Saurabh\\Desktop\\Callback sync and async\\test\\JSONs\\");

		for (File file : dir.listFiles()) {

			JSONParser parser = new JSONParser();
			JSONObject pagesObject = (JSONObject)parser.parse(new FileReader(file));

			JSONArray jsonArray= (JSONArray) pagesObject.get("data");
			for (Object o : jsonArray) {
				JSONObject jsonObject  = (JSONObject) o;


				if(!jsonObject.keySet().contains("authors"))
					continue;
				if(!jsonObject.keySet().contains("title"))
					continue;
				if(!jsonObject.keySet().contains("year"))
					continue;
				if(!jsonObject.keySet().contains("cited-by"))
					continue;
				if(!jsonObject.keySet().contains("publisher"))
					continue;

				String authorsName = (String) jsonObject.get("authors");
				if (authorsName == null)
					continue;

				String[] authors = authorsName.split("]");
				for (int i=0 ; i< authors.length;i++) {
					createJSON(jsonObject,authors[i].substring(1, authors[i].length()));
				}
			}
		}
	}
	private static void appendToList(JSONObject jsonObj, JSONObject toBeAppended) {

		JSONArray arr = (JSONArray) jsonObj.get("features");        
		arr.add(toBeAppended);
	}

	public static void createJSON(JSONObject jsonObject, String authorsName) throws IOException, ParseException {
		try {
			JSONObject obj = new JSONObject();
			obj.put("Publication Name",  (String) jsonObject.get("title"));
			obj.put("year",  (long) jsonObject.get("year"));
			obj.put("cited-by",  (long) jsonObject.get("cited-by"));
			obj.put("publisher",  (String) jsonObject.get("publisher"));

			char initial = authorsName.charAt(0);
			String filename = initial+"."+"json";
			String directory = "C:\\Users\\Saurabh\\Desktop\\Callback sync and async\\test\\Files\\";
			directory = (directory + filename).toLowerCase();
			File f = new File(directory);
			
			if(f.exists()){
				FileReader reader = new java.io.FileReader(directory);
				JSONParser parser = new JSONParser();
				Object p = parser.parse(reader);
				if (p instanceof JSONObject) {
					System.out.println("JSONobject");
					JSONObject jo = (JSONObject) p;
					JSONArray jsonArray = new JSONArray();
					JSONObject callLog =  (JSONObject)jo;
					for (Object key : jo.keySet())  {

						if(key.equals(authorsName)) {
							jsonArray= (JSONArray) jo.get(authorsName);
							callLog = (JSONObject)jo;
						}
					}
					
					jsonArray.add(obj);
					callLog.put(authorsName,jsonArray);
					
					try (FileWriter file = new FileWriter(directory)) {
						file.write(callLog.toJSONString());
						System.out.println("Successfully Copied JSON Object to File...");
						System.out.println("\nJSON Object: " + callLog);

					}
				}
				else {
					try (FileWriter file = new FileWriter(directory)) {
						JSONArray ja = (JSONArray) p;
						System.out.println("JSONArray");
						JSONObject callLog = new JSONObject();
						ja.add(callLog);
						callLog.put(authorsName,ja);
						file.write(callLog.toJSONString());
						System.out.println("Successfully Copied JSON Object to File...");
						System.out.println("\nJSON Object: " + callLog);
					}
				}
			}
			else {
				// try-with-resources statement based on post comment below :)
				f.createNewFile();
				try (FileWriter file = new FileWriter(directory)) {
					JSONArray ja = new JSONArray();
					JSONObject callLog = new JSONObject();
					ja.add(obj);
					callLog.put(authorsName,ja);
					/*JSONObject last = new JSONObject();
					last.put("", callLog);*/
					file.write(callLog.toJSONString());
					System.out.println("Successfully Copied JSON Object to File...");
					System.out.println("\nJSON Object: " + callLog);
				} 
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}