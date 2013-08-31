package cyper.pde.util;

import java.io.File;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkingSetManager;

import cyper.openinexplorer.Activator;

public class WorkspaceUtil {
    /***
     * Gets the selected project or or null if none selected
     * 
     * @return
     */
    public static IProject getSelectedProject() {
        return getSelectedProject(WorkspaceUtil.getWorkbenchWindow());
    }

    /***
     * Gets the selected project or null if none selected
     * 
     * @param window
     *            The workbench windows from where to get the current selection
     * @return
     */
    public static IProject getSelectedProject(IWorkbenchWindow window) {
        IStructuredSelection selection = getSelectedStructuredSelection(window);
        if (selection == null || !(selection.getFirstElement() instanceof IProject))
            return null;

        return (IProject) selection.getFirstElement();
    }

    /***
     * Gets the project from the workspace that has the specified name, or null
     * if none existing
     * 
     * @return
     */
    public static IProject getProject(String name) {
        IProject proj = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
        if (proj.exists())
            return proj;
        return null;
    }

    /***
     * Gets the selected folder or null if none selected
     * 
     * @return
     */
    public static IFolder getSelectedFolder() {
        return getSelectedFolder(WorkspaceUtil.getWorkbenchWindow());
    }

    /***
     * Gets the selected project or null if none selected
     * 
     * @param window
     *            The workbench window from where to get the current selection
     * @return
     */
    public static IFolder getSelectedFolder(IWorkbenchWindow window) {
        IStructuredSelection selection = getSelectedStructuredSelection(window);
        if (selection == null || !(selection.getFirstElement() instanceof IFolder))
            return null;

        return (IFolder) selection.getFirstElement();
    }

    /***
     * Gets the selected file or null if none selected
     * 
     * @return
     */
    public static IFile getSelectedFile() {
        return getSelectedFile(WorkspaceUtil.getWorkbenchWindow());
    }

    /***
     * Gets the file selected or null if none selected
     * 
     * @param window
     *            The workbench window from where to get the current selection
     * @return
     */
    public static IFile getSelectedFile(IWorkbenchWindow window) {
        IStructuredSelection selection = getSelectedStructuredSelection(window);
        if (selection == null || !(selection.getFirstElement() instanceof IFile))
            return null;
        return (IFile) selection.getFirstElement();
    }

    /***
     * Gets the selected StructuredSelection or null if none selected
     * 
     * @return
     */
    public static IStructuredSelection getSelectedStructuredSelection() {
        return getSelectedStructuredSelection(WorkspaceUtil.getWorkbenchWindow());
    }

    /***
     * Gets the selected StructuredSelection or null if none selected
     * 
     * @param window
     *            The workbench windows from where to get the current selection
     * @return
     */
    public static IStructuredSelection getSelectedStructuredSelection(final IWorkbenchWindow window) {
        if (window == null)
            return null;
        MyRunnable<IStructuredSelection> runnable = new MyRunnable<IStructuredSelection>() {
            @Override
            public void run() {
                try {
                    runnableObject_ = null;
                    if (!(window.getSelectionService().getSelection() instanceof IStructuredSelection))
                        return;
                    runnableObject_ = (IStructuredSelection) window.getSelectionService().getSelection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Display.getDefault().syncExec(runnable);
        return runnable.runnableObject_;
    }

    /***
     * Gets the selected container (folder/project) or null if none selected
     * 
     * @return
     */
    public static IContainer getSelectedContainer() {
        IStructuredSelection selection = getSelectedStructuredSelection();
        if (selection == null
                || !(selection.getFirstElement() instanceof IFolder || selection.getFirstElement() instanceof IProject))
            return null;

        return (IContainer) selection.getFirstElement();
    }

    public static IPath getWorkspaceLocation() {
        return ResourcesPlugin.getWorkspace().getRoot().getLocation();
    }

    /**
     * Gets the selected node absolute os path or null if none selected.
     * 
     * @return
     */
    public static String getSelectedNodePath() {

        IStructuredSelection selection = getSelectedStructuredSelection(getWorkbenchWindow());
        if (selection == null) {
            return null;
        }

        Object obj = selection.getFirstElement();

        if (obj instanceof JarPackageFragmentRoot) {
            System.out.println("JarPackageFragmentRoot");
            JarPackageFragmentRoot ir = (JarPackageFragmentRoot) obj;
            File file = ir.getPath().toFile();
            String filePath = file.getAbsolutePath();
            return filePath;
        }

        if (obj instanceof IJavaElement) {
            System.out.println("IJavaElement");
            IJavaElement ir = (IJavaElement) obj;
            obj = ir.getResource();
        }

        if (obj instanceof IResource) {
            System.out.println("IResource");
            IResource res = (IResource) obj;
            IPath path = res.getFullPath();
            String str = path.toOSString();

            if (str.startsWith("\\")) {
                str = getWorkspaceLocation().toOSString().concat(str);
            }

            return str;
        }
        return null;
    }

    /***
     * Gets the selected resource(file/folder/project) or null if none selected
     * 
     * @return
     */
    public static IResource getSelectedResource() {
        IResource res = getSelectedFile();
        if (res != null)
            return res;

        res = getSelectedFolder();
        if (res != null)
            return res;

        res = getSelectedProject();
        if (res != null)
            return res;
        return null;
    }

    /***
     * Returns the first WorkbenchWindow available. This is not always the same
     * with ActiveWorkbecnWindow
     * 
     * @return
     */
    public static IWorkbenchWindow getWorkbenchWindow() {
        if (Activator.getDefault().getWorkbench().getActiveWorkbenchWindow() != null)
            return Activator.getDefault().getWorkbench().getActiveWorkbenchWindow();
        if (Activator.getDefault().getWorkbench().getWorkbenchWindowCount() == 0)
            return null;
        return Activator.getDefault().getWorkbench().getWorkbenchWindows()[0];
    }

    /***
     * Returns the current working set manager
     * 
     * @return
     */
    public static IWorkingSetManager getWorkingSetManager() {
        return getWorkbenchWindow().getWorkbench().getWorkingSetManager();
    }

    static class MyRunnable<T> implements Runnable {
        protected T runnableObject_;

        public MyRunnable(T t) {
            this.runnableObject_ = t;
        }

        public MyRunnable() {

        }

        public void run() {
        }
    }

}
