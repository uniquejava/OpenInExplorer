package cyper.pde.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorUtil {

	/**
	 * 
	 * 取得堆栈的详细信息.
	 * 
	 * @param e
	 * 
	 * @return
	 * 
	 */
	public static String getError(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	public static String getError(Throwable e, int maxLength) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String str = sw.toString();
		if (str != null && str.length() > maxLength) {
			str = str.substring(0, maxLength);
		}
		return str;
	}
}