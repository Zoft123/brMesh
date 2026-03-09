package com.brgd.brblmesh.GeneralClass;

import android.content.Context;
import android.os.Environment;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import j$.util.Objects;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class LogcatHelper {
    private static LogcatHelper INSTANCE;
    private static String PATH_LOGCAT;
    private LogDumper mLogDumper = null;
    private final int mPId;

    public static LogcatHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LogcatHelper(context);
        }
        return INSTANCE;
    }

    private LogcatHelper(Context context) {
        PATH_LOGCAT = getFilePath(context);
        this.mPId = Process.myPid();
    }

    private String getFilePath(Context context) {
        String str;
        if (Environment.getExternalStorageState().equals("mounted")) {
            str = ((File) Objects.requireNonNull(context.getExternalFilesDir(null))).getAbsolutePath() + File.separator + "log";
        } else {
            str = null;
        }
        if (str == null) {
            str = context.getFilesDir().getAbsolutePath() + File.separator + "log";
        }
        File file = new File(str);
        if (!file.exists() ? file.mkdirs() : true) {
            return str;
        }
        return null;
    }

    public void start() {
        if (TextUtils.isEmpty(PATH_LOGCAT)) {
            return;
        }
        if (this.mLogDumper == null) {
            this.mLogDumper = new LogDumper(String.valueOf(this.mPId), PATH_LOGCAT);
        }
        this.mLogDumper.start();
    }

    public void stop() {
        LogDumper logDumper = this.mLogDumper;
        if (logDumper != null) {
            logDumper.stopLogs();
            this.mLogDumper = null;
        }
    }

    private class LogDumper extends Thread {
        private final String cmds;
        private final String dir;
        private Process logcatProc;
        private final String mPID;
        private boolean mRunning = true;

        public LogDumper(String str, String str2) {
            this.mPID = str;
            this.dir = str2;
            this.cmds = "logcat *:e *:d | grep \"(" + str + ")\"";
        }

        public void stopLogs() {
            this.mRunning = false;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            String line;
            try {
                try {
                    this.logcatProc = Runtime.getRuntime().exec(this.cmds);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.logcatProc.getInputStream()), 1024);
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(new File(this.dir, "log_" + LogcatHelper.this.getFileName() + ".log"));
                        while (this.mRunning && (line = bufferedReader.readLine()) != null) {
                            try {
                                if (line.contains(this.mPID)) {
                                    fileOutputStream.write((line + "\n").getBytes());
                                }
                            } finally {
                            }
                        }
                        fileOutputStream.close();
                        bufferedReader.close();
                        Process process = this.logcatProc;
                        if (process != null) {
                            process.destroy();
                            this.logcatProc = null;
                        }
                    } catch (Throwable th) {
                        try {
                            bufferedReader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (IOException e) {
                    Log.d("printStackTrace", "printStackTrace" + e);
                    Process process2 = this.logcatProc;
                    if (process2 != null) {
                        process2.destroy();
                        this.logcatProc = null;
                    }
                }
            } catch (Throwable th3) {
                Process process3 = this.logcatProc;
                if (process3 != null) {
                    process3.destroy();
                    this.logcatProc = null;
                }
                throw th3;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getFileName() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
    }
}
