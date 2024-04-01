package com.example.simplicook.persistence.hsqldb;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.simplicook.application.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Helper class to manage database file operations, such as copying the database
 * from the application's assets to the device's internal storage, and formatting
 * date objects for SQL.
 */
public class DBHelper {

    /**
     * Copies the database from the application's assets folder to the device's internal storage.
     * This method ensures that the database is accessible by the application from a private directory.
     * @param context The application context used to access resources and assets.
     */
    public static void copyDatabaseToDevice(Context context) {
        final String DB_PATH = "db";

        String[] assetNames;
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = context.getAssets();

        try {
            // List all assets in the 'db' folder.
            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < Objects.requireNonNull(assetNames).length; i++) {
                // Prefix the asset name with the 'db' folder path.
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            // Copy each asset in the 'db' folder to the application's private directory.
            copyAssetsToDirectory(context, assetNames, dataDirectory);

            // Update the application's database path to point to the new location in internal storage.
            Main.setDBPathName(dataDirectory.toString() + "/" + Main.getDBPathName());

        } catch (final IOException ioe) {
            // Log any IO exceptions that occur during the copy operation.
            System.out.println("Unable to access application data: " + ioe.getMessage());
        }
    }

    /**
     * Copies specified assets to a target directory.
     * @param context The application context, used to access the assets.
     * @param assets An array of asset paths to copy.
     * @param directory The target directory where assets should be copied.
     * @throws IOException If an I/O error occurs during copying.
     */
    private static void copyAssetsToDirectory(Context context, String[] assets, File directory) throws IOException {
        AssetManager assetManager = context.getAssets();

        for (String asset : assets) {
            // Split the asset path and build the target copy path in the directory.
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            // Buffer for reading asset contents.
            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            // Only copy the asset if it doesn't already exist in the target directory.
            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                // Read from the asset and write to the file in the target directory.
                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }

    }

    /**
     * Formats a Date object into a string suitable for SQL queries.
     * @param date The Date object to format.
     * @return A string representation of the date in "yyyy-MM-dd hh:mm:ss" format.
     */
    public static String getSQLDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }

}
