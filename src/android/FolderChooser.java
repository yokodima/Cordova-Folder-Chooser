package com.example.cordova.folderChooser;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.lang.Exception;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.provider.DocumentsContract;
import android.provider.DocumentsContract.Document;
import android.content.Context;

public class FolderChooser extends CordovaPlugin {
    private static final String ACTION_SAVE_FILE_TO_USB = "saveFileToUSB";

    private CallbackContext callback;

    private void chooseFile (CallbackContext callbackContext, String accept) {
        String uri = "";
        Context context = this.cordova.getActivity().getApplicationContext();

        for (File f : context.getExternalFilesDirs("")) {
            if (Environment.isExternalStorageRemovable(f)) {
                uri = f.getAbsolutePath();
            }
        }

        JSONObject result = new JSONObject();
        result.put("uri", uri);

        callbackContext.success(result);
    }

    @Override
    public boolean execute (
            String action,
            JSONArray args,
            CallbackContext callbackContext
    ) {
        try {
            if (action.equals(FolderChooser.ACTION_OPEN)) {
                this.chooseFile(callbackContext, args.getString(0));
                return true;
            }
        }
        catch (JSONException err) {
            this.callback.error("Execute failed: " + err.toString());
        }

        return false;
    }
}