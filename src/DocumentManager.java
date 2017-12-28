import java.io.File;
import java.util.List;
import java.util.ArrayList;


class DocumentManager
{
	public static void main(String[] args)
	{
		if(args.length < 1) return;

		File[] files = new File(args[0]).listFiles();
		showFiles(files);

		List<File> foundFiles = new ArrayList<File>();
		findFile(args[0], args[1], foundFiles);

		for(File file : foundFiles)
			System.out.println(file.getPath());
	}


	/*-----------------------------------
	Show all files in a directory
	-----------------------------------*/
	public static void showFiles(File[] files)
	{
		for(File file : files)
		{
			if(file.isDirectory())
			{
				System.out.println("Directory: " + file.getName());
				showFiles(file.listFiles());
			}
			else
			{
				System.out.println("File: " + file.getName());
			}
		}
	}


	/*-----------------------------------
	Find files in a directory
	-----------------------------------*/
	public static void findFile(String dirName, String fileName, List<File> foundFiles)
	{
		File[] files = new File(dirName).listFiles();

		for(File file : files)
		{
			if(file.isDirectory()) 
				findFile(file, fileName, foundFiles);

			else if(file.getName().equals(fileName)) 
				foundFiles.add(file);
		}
	}


	public static void findFile(File dir, String fileName, List<File> foundFiles)
	{
		File[] files = dir.listFiles();

		for(File file : files)
		{
			if(file.isDirectory())
				findFile(file, fileName, foundFiles);

			else if(file.getName().equals(fileName))
				foundFiles.add(file);
		}
	}
}