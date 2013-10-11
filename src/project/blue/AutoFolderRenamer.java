package project.blue;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

/**
 * 同階層にあるフォルダ名を正規化する
 */
public class AutoFolderRenamer {

	private static final Pattern OLD_COMIC_PATTERN = Pattern.compile("([^ ]+) - (.+) ([0-9]+)");
	/**
	 * メイン関数
	 * @param args 引数
	 */
	public static void main(String[] args) {
		File dir = new File(".");
		File[] files = dir.listFiles(
			new FileFilter() {
				@Override
				public boolean accept(File file) {
					if (isComicFolder(file))
						return true;
					else
						return false;
				}
			}
		);
		int count = 0;
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			//System.out.println((i + 1) + ":    " + file);
			boolean succcess = rename(file);
			if (succcess)
				count++;
	    }
		JOptionPane.showMessageDialog(null, count + "個のフォルダ名を変更しました。", "完了", JOptionPane.INFORMATION_MESSAGE);
	}

	public static boolean isComicFolder(File file) {
		if (file.isDirectory()) {
			 Matcher match = OLD_COMIC_PATTERN.matcher(file.getName());
			 if (match.find())
				 return true;
		}
		return false;
	}

	public static boolean rename(File file) {
		if (file.isDirectory()) {
			 Matcher match = OLD_COMIC_PATTERN.matcher(file.getName());
			 if (match.find()) {
				 String newName = null;
				 if (match.groupCount() >= 3) {
					 String author = match.group(1);
					 String title = match.group(2);
					 String num = match.group(3);
					 newName = "[" + author + "] " + title + " " + num;
				 } else if (match.groupCount() == 2) {
					 String author = match.group(1);
					 String title = match.group(2);
					 newName = "[" + author + "] " + title;
				 }
				 if (newName != null) {
					 //System.out.println(newName);
					 file.renameTo(new File(newName));
					 return true;
				 }
			 }
		}
		return false;
	}
}
