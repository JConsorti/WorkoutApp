//My First java workout app

package WorkoutApp;

import java.time.*;
import java.time.format.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExerciseLoggingApp
{

// Creates file object and sets pointer
	
        ClassLoader loader = Workout.class.getClassLoader();
        String filePath = loader.getResource("WorkoutApp/").toString();
	File workoutFile = new File(filePath.substring(6) + "Log/Workout Log.txt");
	
// Gets current day/time information
  	private LocalDateTime dateTime = LocalDateTime.now();	
// Set format to month/day/year 	
  	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd yyyy hh:mm");
	private String todaysDate = dateTime.format(formatter);

// Declare all variables to be used
	private Scanner keyboard = new Scanner(System.in);
	private int weight;
	private boolean isFirstExercise;
	private List<String> workoutList = new ArrayList<>();
	private List<String> workoutLog = new ArrayList<>();
	private int totalWeightLifted;
	private List<WorkoutTracker> tracker = new ArrayList<>();
		
// Adds a list of standard exercises to workoutList
	public void createListOfWorkouts()
	{
		workoutList.add("Squats");
		workoutList.add("Bench Press");
		workoutList.add("Overhead Press");
		workoutList.add("Pull ups");
		workoutList.add("Front Squats");
		workoutList.add("Curls");
		workoutList.add("Shoulder Press");
		workoutList.add("Tricep Extensions");
		workoutList.add("Kettle Bell Swings");
		workoutList.add("Other");
	}

// returns boolean of true if user is planning on doing another exercise otherwise returns false.
	public boolean isStillLifting()
	{
		boolean continueLifting;
		System.out.println("Are you done lifting for the day? (y/n)");	
		char result = keyboard.next().charAt(0);
		return continueLifting = !(result=='y' || result == 'Y');
	}

// returns boolean of true if user is planning on lifting "today" otherwise returns false
//	public boolean isWorkingOut()
//	{
//		System.out.println("Are you working out today? (y/n)");
//		char result = keyboard.next().charAt(0);
//		return workingOut = !(result=='y' || result == 'Y');
//	}

//called to display all workouts that are contained in the workoutList
	public void showWorkoutList()
	{
		int i=1;
		for(String workout : workoutList)
		{
			System.out.println(i++ + ". " + workout);
		}
		System.out.println("0. To Quit");
	}


//Gives user a list of exercise to choose from, based on user input it returns the exercise with a motivational message.
	public String askWhatExercise()
	{
		System.out.println("Choose an exercise from the list below or press 0 to end your workout.");
		showWorkoutList();
		try
		{
		int exerciseChoice = keyboard.nextInt();
			if(exerciseChoice == 0)
			{
				System.out.println("We'll start again tomorrow!");
				System.exit(0);
			}
		String exerciseDone = workoutList.get(exerciseChoice-1);
		switch(exerciseChoice)
		{
			case 1:
				System.out.println("Cool, let's do some " + exerciseDone + ".");
				return exerciseDone ;
			case 2:
				System.out.println("Okay aswesome I love " + exerciseDone + "!");
				return exerciseDone;
			case 3:
				System.out.println(exerciseDone + " will get you jacked!");
				return exerciseDone;
			case 4:
				System.out.println(exerciseDone + " sound good!");
				return exerciseDone;
			case 5:
				System.out.println("Let's get some " + exerciseDone + " going!");
				return exerciseDone;
			case 6:
				System.out.println("Can't go wrong with " + exerciseDone + "!");
				return exerciseDone;
			case 7:
				System.out.println("Everyone loves " + exerciseDone + "!");
				return exerciseDone;
			case 8:
				System.out.println("Just a few " + exerciseDone + " will have you swole!");
				return exerciseDone;
			case 9:
				System.out.println("Make sure to have good form on these " + exerciseDone + "!");
				return exerciseDone;
			case 10:
				System.out.println("What exercise would you like to do?");
				exerciseDone = keyboard.next();
				return exerciseDone;
			default:
				System.out.println("What was that now?");
				return "";
		}
		}
		catch (NumberFormatException e)
		{
			System.out.println("Please enter a numeric value");
			askWhatExercise();
		}
		return "What";
	}

	public int askForNumSets()
	{
		System.out.println("How many sets?");
		int setsDone = keyboard.nextInt();
		return setsDone;
	}
	
	public int askForNumReps()
	{
		System.out.println("How many reps?");
		int repsDone = keyboard.nextInt();
		return repsDone;
	}

	public int askForWeightUsed()
	{
		int weightLifted = 0;
		System.out.println("At what weight?");
		try
		{
			weightLifted = keyboard.nextInt();
		}
		catch(NumberFormatException e)
		{
			System.out.println("Needs to be a number");
			keyboard.next();
			askForWeightUsed();
		}
		weight = weightLifted;
		return weightLifted;
	}

	public void workout()
	{
		createListOfWorkouts();
		do
		{
			tracker.add(new WorkoutTracker(askWhatExercise(), askForNumSets(), askForNumReps(), askForWeightUsed()));
			totalWeightLifted += weight;
		}while(isStillLifting());
		logWorkout(tracker);
	}

	public void printResults()
	{
		System.out.println(todaysDate + "\n");
		for(WorkoutTracker stat : tracker)
		{
			System.out.println(stat);
		}
		System.out.println("\nTotal weight lifted today: " + totalWeightLifted + " pounds!");
	}


	public void createFile()
	{
		boolean b = false;
		if(!workoutFile.exists())
		{
			try
			{
				workoutFile.getParentFile().mkdirs();
				b = workoutFile.createNewFile();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("File already exists, adding to workoutLog.");
		}
		if(b)
		{
			System.out.println("File successfullly created!");
		}
	}
	
	public void logWorkout(List<WorkoutTracker> tracker)
	{
		createFile();
		try(FileWriter fw = new FileWriter(workoutFile, true))
		{
			try (BufferedWriter writer = new BufferedWriter(fw))
			{
				writer.write(todaysDate);
				writer.write(System.lineSeparator());
				for(WorkoutTracker stat : tracker)
				{
					writer.write(stat.toString());
					writer.write(System.lineSeparator());
				}
				writer.write(System.lineSeparator());
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		catch(Exception e)
		{
				System.out.println(e);
		}
	}
}