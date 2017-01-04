package cyper.openinexplorer.handlers;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import cyper.pde.util.ErrorUtil;
import cyper.pde.util.Log;
import cyper.pde.util.WorkspaceUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class OpenInExplorerHandler extends AbstractHandler {
	public static enum OS {
		WINDOWS, LINUX, MACOS
	}

	/**
	 * The constructor.
	 */
	public OpenInExplorerHandler() {
	}

	private OS getOsName() {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("windows") != -1) {
			return OS.WINDOWS;
		} else if (os.indexOf("mac") != -1) {
			return OS.MACOS;
		} else {
			return OS.LINUX;
		}
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			Object obj = ssel.getFirstElement();
			if (obj == null) {
				MessageDialog.openWarning(null, "Cyper!", "Please select something in the package explorer first!");
				return null;
			}
		} else {
			return null;
		}

		try {
			String path = WorkspaceUtil.getSelectedNodePath();

			if (path != null) {
				OS osName = getOsName();
				Log.info(osName.toString());
				Log.info(path);

				Process proc = null;
				if (osName.equals(OS.LINUX)) {
					String[] b = new String[] { "/bin/sh", "-c", "nautilus " + path };
					proc = Runtime.getRuntime().exec(b);
				} else if (osName.equals(OS.WINDOWS)) {
					proc = Runtime.getRuntime().exec(new String[] { "cmd", "/c", "explorer /select," + path });
				} else {
					proc = Runtime.getRuntime().exec(new String[] { "/usr/bin/open", "-R", path });
				}

				BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

				BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

				// read the output from the command
				String s = null;
				while ((s = stdInput.readLine()) != null) {
					Log.info(s);
				}

				// read any errors from the attempted command
				while ((s = stdError.readLine()) != null) {
					Log.info(s);
				}

			} else {
				MessageDialog.openWarning(null, "Cyper!", "No path for this node, cannot open it.");
			}
		} catch (Throwable e) {
			e.printStackTrace();
			MessageDialog.openError(null, "Cyper!", "Error occurred: " + ErrorUtil.getError(e));
		}
		return null;
	}
}
