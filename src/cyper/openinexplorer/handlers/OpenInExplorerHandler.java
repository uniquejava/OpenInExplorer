package cyper.openinexplorer.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import cyper.pde.util.WorkspaceUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class OpenInExplorerHandler extends AbstractHandler {
    public final static boolean isLinux = System.getProperty("os.name").toLowerCase().indexOf("windows") == -1;
    public final static boolean isWindows = !isLinux;

    /**
     * The constructor.
     */
    public OpenInExplorerHandler() {
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

                if (isLinux) {
                    String[] b = new String[] { "/bin/sh", "-c", "nautilus " + path };
                    Runtime.getRuntime().exec(b);
                } else {
                    Runtime.getRuntime().exec(new String[] { "cmd", "/c", "explorer /select," + path });
                }

            } else {
                MessageDialog.openWarning(null, "Cyper!", "No path for this node, cannot open it.");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            MessageDialog.openError(null, "Cyper!", "Error occurred: " + e.getMessage());
        }
        return null;
    }
}
