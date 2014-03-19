package edu.uci.ics.BoardGameServer.Distribution;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.simple.JSONObject;

public class Login {

	Login(){

	}

	public boolean validate(String username, String password){
		try {

			File file = new File("PlayerDatabase.txt");
			Scanner in = new Scanner(file);

			while(in.hasNext())
			{
				String fileUsername = in.next();
				String filePassword = in.next();
				String fileEmail = in.next();

				if(username.equals(fileUsername))
				{
					if(password.equals(filePassword))
					{
						in.close();
						return true;
					}
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}


	public String getEmail(String username, String password){
		try {

			File file = new File("PlayerDatabase.txt");
			Scanner in = new Scanner(file);

			while(in.hasNext())
			{
				String fileUsername = in.next();
				String filePassword = in.next();
				String fileEmail = in.next();

				if(username.equals(fileUsername))
				{
					if(password.equals(filePassword))
					{
						in.close();
						return fileEmail;
					}
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//should never get to this point
		return null;
	}

}
