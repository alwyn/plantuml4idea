package org.plantuml.idea.toolwindow.listener;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.ToolWindowType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.plantuml.idea.lang.PlantUmlFileType;
import org.plantuml.idea.toolwindow.PlantUmlToolWindowFactory;

public class PlantUmlToolWindowOpener implements FileEditorManagerListener {

    @Override
    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
    }

    @Override
    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
    }

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
        VirtualFile newFile = event.getNewFile();
        VirtualFile oldFile = event.getOldFile();

        ToolWindow window = getToolWindow(event);
        if (window != null && window.getType() == ToolWindowType.DOCKED) {
            if (isPlantUml(newFile)) {
                if (!window.isVisible()) {
                    window.show(null);
                }
            } else if (isPlantUml(oldFile)) {
                if (window.isVisible()) {
                    window.hide(null);
                }
            }
        }
    }

    @Nullable
    private ToolWindow getToolWindow(FileEditorManagerEvent event) {
        Project project = event.getManager().getProject();
        return ToolWindowManager.getInstance(project).getToolWindow(PlantUmlToolWindowFactory.ID);
    }

    private boolean isPlantUml(VirtualFile file) {
        return file != null && file.getFileType() == PlantUmlFileType.PLANTUML_FILE_TYPE;
    }
}
