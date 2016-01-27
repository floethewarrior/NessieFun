import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

public class Main {

	public static void main(String[] args)
	{
		String key = "41eeda952633709290497655fb8ba3a8";
		String url = "http://api.reimaginebanking.com";
		
		Request r1 = new Request();
		r1.setKey(key);
		r1.setUrl(url);
		r1.setResource("/atms");
		
		Request r2 = new Request();
		r2.setKey(key);
		r2.setUrl(url);
		r2.setResource("/customers");
		
		Request r3 = new Request();
		r3.setKey(key);
		r3.setUrl(url);
		r3.setResource("/accounts");

		ArrayList<String> accountIDs = null;
		
		try {
			String accounts = "";
			System.out.println(r1.sendGet());
			System.out.println(r2.sendGet());
			System.out.println(accounts = r3.sendGet());
			accountIDs = extractAccountIDs(accounts);
			for(String id : accountIDs)
			{
				System.out.println(id);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Request r4 = new Request();
		r4.setKey(key);
		r4.setUrl(url);
		r4.setResource("/accounts/" + accountIDs.get(0) +"/bills");
		
		Request r5 = new Request();
		r5.setKey(key);
		r5.setUrl(url);
		r5.setResource("/accounts/" + accountIDs.get(0) +"/deposits");
		
		JSONObject obj = new JSONObject();

		
		try {
			obj.put("medium", "balance");
			obj.put("transaction_date", "2016-01-27");
			obj.put("status", "pending");
			obj.put("amount", 1337);
			obj.put("description", "An excelent deposit");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		r5.setPayload(obj);
		
		try {
			System.out.println(r4.sendGet());
			System.out.println(r5.sendPost());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static ArrayList<String> extractAccountIDs(String customers)
	{
		Pattern p = Pattern.compile("([0-9]){5}([a-z]){1}([0-9]){2}([a-z]){2}([0-9]){1}([a-z]){2}([0-9]){2}([a-z]){1}([0-9]){8}");
		Matcher m = p.matcher(customers);
		ArrayList<String> accountIDs = new ArrayList<String>();
		while(m.find())
		{
			accountIDs.add(m.group());
		}
		
		return accountIDs;
	}
	
}
