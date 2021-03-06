import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.os.Environment;
import android.util.Log;

public class SDLog {
	public static boolean saveLogToSD = true;
	public static String saveLogPath = Environment.getExternalStorageDirectory() + "/SD_LOG";

        //Log Type (Just For Printing at LogCat)
	public enum LogType {
		E, W, D, V, I
	}

	public static void log(String tag, String logText, LogType logType) {

		if (logText == null) {
			return;
		}

		switch (logType) {
		case E: {
			Log.e(tag, logText);
			break;
		}
		case W: {
			Log.w(tag, logText);
			break;
		}
		case D: {
			Log.d(tag, logText);
			break;
		}
		case I: {
			Log.i(tag, logText);
			break;
		}
		case V:
		default: {
			Log.v(tag, logText);
			break;
		}
		}

		if (saveLogToSD) {
		  // Create a Log file using current date
			Date now = new Date();
			SimpleDateFormat fileFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			File logFileDir = new File(saveLogPath);

			File logFile = new File(logFileDir.getAbsolutePath() + "/" + fileFormat.format(now) + ".txt");

			if (!logFileDir.exists()) {
				try {
					if (logFileDir.mkdirs()) {
						System.out.println("Directory created");
					} else {
						System.out.println("Directory is not created");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (!logFile.exists()) {
				try {
					if (logFile.createNewFile()) {
						System.out.println("File created");
					} else {
						System.out.println("File is not created");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
				BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
				StringBuilder LogString = new StringBuilder();
				LogString.append(format.format(now));
				LogString.append("    ");
				LogString.append("[");
				LogString.append(tag);
				LogString.append("]");
				LogString.append(" => ");
				LogString.append(logText);
				buf.append(LogString.toString());
				buf.newLine();
				buf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
