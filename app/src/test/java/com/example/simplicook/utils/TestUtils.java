package com.example.simplicook.utils;

import com.example.simplicook.application.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TestUtils {

    private static final File DB_SRC = new File("src/main/assets/db/HSQL_DB.script");

    public static File copyDB() throws IOException {
        final File target = File.createTempFile("temp-db", ".script");

        // Delete the existing file if it exists
        if (target.exists()) {
            target.delete();
        }

        Files.copy(DB_SRC.toPath(), target.toPath());
        Main.setDBPathName(target.getAbsolutePath().replace(".script", ""));
        return target;
    }
}
