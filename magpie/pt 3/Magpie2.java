public class Magpie2
{
	/** Get a default greeting @return a greeting*/
	public String getGreeting()
	{
		return "Hello, let's talk.";
	}
	
	/** getResponse() method
	 * ===========================================
	 * 	Gives a response to a user statement
	 *  @param statement (the user statement)
	 * 	@return a response based on the rules given */
	public String getResponse(String statement)
	{
		String response = "";

		/** Exercise_01:
		 * ==================================================
		 * 	Code that asks the user "Say something, please."
		 * 	if you enter nothing, or if you accidentally hit
		 * 	enter. Think to yourself: "What is the length of
		 * 	an empty String?" */
		if(statement.length() == 0)
		{
			response = "Say something, please.";
		}

		/** To be completed in Exercise_02:
		 * 	Modify the following code to use the findKeyword
		 * 	Method (details in "Exercise_02" below. */
		else if (findKeyword(statement, "no", 0) >= 0)
		{
			response = "Why so negative?";
		}

		else if (findKeyword(statement, "mother", 0) >= 0
				|| findKeyword(statement, "father", 0) >= 0
				|| findKeyword(statement, "sister", 0) >= 0
				|| findKeyword(statement, "brother", 0) >= 0)
		{
			response = "Tell me more about your family.";
		}

		/** Exercise_03(Final)
		 * ==================================================
		 * Create additional code (another else if) that
		 * responds "Tell me more about your pet" if the
		 * user mentions the word cat, dog, fish, or turtle
		 * in their statement.
		 *
		 * Create addtional code (another else if) that
		 * responds "He sounds like a pretty dank teacher"
		 * if you mention "Robinette" in your statement */

		else if(findKeyword(statement, "dog", 0) >= 0
				|| findKeyword(statement, "cat", 0) >= 0
				|| findKeyword(statement, "fish", 0) >= 0
				|| findKeyword(statement, "turtle", 0) >= 0)
		{
			response = "Tell me more about your pet";
		}
		
		else if(findKeyword(statement, "Robinette", 0) >= 0)
		{
			response = "He sounds like a pretty dank teacher";
		}
		
		else if(findKeyword(statement, "I want to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}
		
		else if(findKeyword(statement, "I like you", 0) >= 0)
		{
			response = "Why do you like me?";
		}
		
		else if(findKeyword(statement, "me", 0) >= 0)
		{
			//look for a two word (you <something> me) pattern
			int psn = findKeyword(statement, "you", 0);
			
			if(psn >= 0 && findKeyword(statement, "me", psn) >= 0)
			{
				response = transformYouMeStatement(statement);
			}
			
			else
			{
				response = getRandomResponse();
			}
		}
		
		else
		{
			//look for a two word (you <something> me) pattern
			int psn = findKeyword(statement, "I", 0);
			
			if(psn >= 0 && findKeyword(statement, "you", psn) >= 0)
			{
				response = transformIYouStatement(statement);
			}
			
			else
			{
				response = getRandomResponse();
			}
		}
		return response;
	}

	private String transformIWantToStatement(String statement)
	{
		statement.trim().toLowerCase();
		String lastchar = statement.substring(statement.length() - 1);
		
		if(lastchar.compareTo(".") == 0)
		{
			statement = statement.substring(0, statement.length());
		}
		
		int psn = findKeyword(statement, "I want to", 0);
		String restOfStatement = statement.substring(psn+9, statement.length()-1);
		
		return "What would it mean to" + restOfStatement;
	}
	
	private String transformYouMeStatement(String statement)
	{
		statement.trim().toLowerCase();
		String lastchar = statement.substring(statement.length() - 1);
		
		if(lastchar.compareTo(".") == 0)
		{
			statement = statement.substring(0, statement.length());
		}
		
		int psnOfYou = findKeyword(statement, "you");
		int psnOfMe = findKeyword(statement, "me", psnOfYou + 3);
		String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe);
		
		return "What makes you think that I" + restOfStatement + "you?";
	}
	
	private String transformIYouStatement(String statement)
	{
		statement.trim().toLowerCase();
		String lastchar = statement.substring(statement.length() - 1);
		
		if(lastchar.compareTo(".") == 0)
		{
			statement = statement.substring(0, statement.length());
		}
		
		int psnOfI = findKeyword(statement, "I");
		int psnOfYou = findKeyword(statement, "you", psnOfI + 2);
		String restOfStatement = statement.substring(psnOfI + 2, psnOfYou);
		
		return "Why do you " + restOfStatement + "me?";
	}
	
	/** Ex_02: The findKeyword() Method...
	 * ========================================================= */
	private int findKeyword(String statement, String goal, int startPos)
	{
		/* New String variable phrase = a more searchable version of statement.
		 	-Use a combination of trim() and toLowerCase() modify statement.

		   New int variable psn = the location of goal in phrase after
		   startPos

			-->Refinement: Make sure we find goal by itself, and not part
			of another word ("no" vs no in "know"). if you find an occurrence
			of goal, make sure before and after aren't letters.

			As long as psn >= 0...
				Check if psn > 0 - there is no need to check for before at the
				beginning of the word
					set before = the slot in phrase before psn */

		String phrase = statement.trim().toLowerCase();
		goal = goal.toLowerCase();

		int psn = phrase.indexOf(goal, startPos);

		while (psn >= 0)
		{

			String before = " ", after = " ";
			if (psn > 0)
			{
				before = phrase.substring(psn - 1, psn);
			}
			if (psn + goal.length() < phrase.length())
			{
				after = phrase.substring(psn + goal.length(),
										psn + goal.length() + 1);
			}

			if (((before.compareTo("a") < 0) || (before.compareTo("z") > 0))
					&& ((after.compareTo("a") < 0) || (after.compareTo("z") > 0)))
			{
				return psn;
			}
			psn = phrase.indexOf(goal, psn + 1);
		}

		return -1;

	}

	/** Override - this method is used if there are only 2 parameters...*/
	private int findKeyword(String statement, String goal)
	{
		//set startPos to 0 if not specified
		return findKeyword(statement, goal, 0);
	}

	/** getRandomResponse() method
	 * =============================================================*/
	/** Pick a default response to use if nothing else fits.
	 * 	@return a non-committal string*/
	private String getRandomResponse()
	{
		final int NUMBER_OF_RESPONSES = 4;
		double r = Math.random();
		int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
		String response = "";
		
		if (whichResponse == 0)
			response = "Interesting, tell me more.";
		else if (whichResponse == 1)
			response = "Hmmm.";
		else if (whichResponse == 2)
			response = "Do you really think so?";
		else if (whichResponse == 3)
			response = "You don't say.";

		return response;
	}
}
