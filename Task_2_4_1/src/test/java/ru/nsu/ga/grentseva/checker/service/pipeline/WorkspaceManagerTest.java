package ru.nsu.ga.grentseva.checker.service.pipeline;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class WorkspaceManagerTest {

    @TempDir
    Path tempDir;

    @Test
    void getWorkspaceCreatesDirectory() throws Exception {
        WorkspaceManager manager = new WorkspaceManager();
        File workspace = manager.getWorkspace();

        assertTrue(workspace.exists());
        assertTrue(workspace.isDirectory());
    }

    @Test
    void getWorkspaceReturnsSameDirectory() throws Exception {
        WorkspaceManager manager = new WorkspaceManager();
        File first = manager.getWorkspace();
        File second = manager.getWorkspace();

        assertEquals(first.getAbsolutePath(), second.getAbsolutePath());
    }

    @Test
    void workspaceAlreadyExists() throws Exception {
        WorkspaceManager manager = new WorkspaceManager();

        File first = manager.getWorkspace();
        File second = manager.getWorkspace();

        assertTrue(first.exists());
        assertEquals(first.getAbsolutePath(), second.getAbsolutePath());
    }
}