//My First java workout app

package WorkoutApp;

import java.time.*;
import java.time.format.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
//import java.nio.file.Files;
//import org.apache.commons.io.FileUtils;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExerciseLoggingApp
{

// Creates file object and sets pointer
	File filePath = new File("C:\\Users\\bodog\\Desktop\\OCA Study\\WorkoutApp\\WorkoutLog\\Workout Log.txt");
	
// Gets current day/time information
  	private LocalDate date = LocalDate.now();	
// Set format to month/day/year 	
  	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd yyyy");
	private String todaysDate = date.format(formatter);

// Declare all variables to be used
	private String exercise;
	private Scanner keyboard = new Scanner(System.in);
	private int sets;
	private int reps;
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
		workoutList.add("Thrusters");
	}

// returns boolean of true if user is planning on doing another exercise otherwise returns false.
	public boolean isStillLifting()
	{
		boolean continueLifting = false;
		System.out.println("Are you done lifting for the day? (y/n)");	
		char result = keyboard.next().charAt(0);
		return continueLifting = (result=='y' || result == 'Y') ? false : true;
	}

// returns boolean of true if user is planning on lifting "today" otherwise returns false
	public boolean isWorkingOut()
	{
		boolean workingOut = false;
		System.out.println("Are you working out today? (y/n)");
		char result = keyboard.next().charAt(0);
		return workingOut = (result=='y' || result == 'Y') ? true : false;
	}

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
	public String whatExercise()
	{
		System.out.println("Choose an exercise from the list below or press 0 to end your workout.");
		showWorkoutList();
		int exerciseChoice = keyboard.nextInt();
		String exerciseDone = workoutList.get(exerciseChoice-1);
		switch(exerciseChoice)
		{
			case 0:
				System.out.println("We'll start again tomorrow!");
				System.exit(0);
				break;
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
				System.out.println("Get low on these " + exerciseDone + "!");
				return exerciseDone;
			default:
				System.out.println("What was that now?");
				return "";
		}
		return "WHAT?";
	}

//	public List<String> addWorkout(List<String> workouts)
//	{
//		System.out.println("First lets 
//	}

	public int numSets()
	{
		System.out.println("How many sets?");
		int setsDone = keyboard.nextInt();
		return setsDone;
	}
	
	public int numReps()
	{
		System.out.println("How many reps?");
		int repsDone = keyboard.nextInt();
		return repsDone;
	}

	public int weightUsed()
	{
		int weightLifted = 0;
		System.out.println("At what weight?");
		try
		{
			weightLifted = keyboard.nextInt();
		}
		catch(Exception e)
		{
			System.out.println("Needs to be a number");
			keyboard.next();
			weightUsed();
		}
		return weightLifted;
	}

	public void workout()
	{
		createListOfWorkouts();
		if(isWorkingOut())
		{
		createFile();
			do
			{
				exercise = whatExercise();
				sets = numSets();	
				reps = numReps();
				weight = weightUsed();
				totalWeightLifted += weight;
				tracker.add(new WorkoutTracker(exercise, sets, reps, weight));
				//System.out.println(tracker.get(0));
			}while(isStillLifting());
//		logWorkout(tracker);
		}
		else
		{
			System.out.println("Okay maybe tomorrow.");
		}
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
		if(!filePath.exists())
		{
			try
			{
				b = filePath.createNewFile();
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
	
//	public void logWorkout(List<WorkoutTracker> tracker)
//	{
//		try(FileWriter fw = new FileWriter(filePath))
//		{
//		try (BufferedWriter writer = new BufferedWriter(fw))
//		{
//			for(WorkoutTracker stat : tracker)
//			{
//				writer.write(stat.toString());
//			}
//		}
//		}
//	}
}
