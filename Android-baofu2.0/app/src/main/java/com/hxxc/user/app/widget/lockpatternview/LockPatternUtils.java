package com.hxxc.user.app.widget.lockpatternview;

import android.content.Context;
import android.os.FileObserver;
import android.util.Log;

import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.SPUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 图案解锁加密、解密工具类
 *
 * @author way
 */
public class LockPatternUtils {
    private static final String TAG = "LockPatternUtils";
    private String LOCK_PATTERN_FILE = "gesture.key_" + SPUtils.geTinstance().getUid();
    public static boolean RELOADING = false;
    /**
     * The minimum number of dots in a valid pattern.
     */
    public static final int MIN_LOCK_PATTERN_SIZE = 4;
    /**
     * The maximum number of incorrect attempts before the user is prevented
     * from trying again for {@link #FAILED_ATTEMPT_TIMEOUT_MS}.
     */
    public static final int FAILED_ATTEMPTS_BEFORE_TIMEOUT = 5;
    /**
     * The minimum number of dots the user must include in a wrong pattern
     * attempt for it to be counted against the counts that affect
     * {@link #FAILED_ATTEMPTS_BEFORE_TIMEOUT} and
     * {@link #}
     */
    public static final int MIN_PATTERN_REGISTER_FAIL = MIN_LOCK_PATTERN_SIZE;
    /**
     * How long the user is prevented from trying again after entering the wrong
     * pattern too many times.
     */
    public static final long FAILED_ATTEMPT_TIMEOUT_MS = 30000L;

    private static File sLockPatternFilename;
    private static final AtomicBoolean sHaveNonZeroPatternFile = new AtomicBoolean(
            false);
    private static FileObserver sPasswordObserver;
    private static String dataSystemDirectory;

    private static class LockPatternFileObserver extends FileObserver {
        public LockPatternFileObserver(String path, int mask) {
            super(path, mask);
        }

        @Override
        public void onEvent(int event, String path) {
            Log.d(TAG, "file path" + path);
            if (("gesture.key_" + SPUtils.geTinstance().getUid()).equals(path)) {
                Log.d(TAG, "lock pattern file changed");
                sHaveNonZeroPatternFile.set(new File(dataSystemDirectory, ("gesture.key_" + SPUtils.geTinstance().getUid())).length() > 0);
            }
        }
    }

    public LockPatternUtils(Context context) {
//		if (sLockPatternFilename == null ) {
//			RELOADING = true;
        dataSystemDirectory = context.getFilesDir().getAbsolutePath();

        sLockPatternFilename = new File(dataSystemDirectory, LOCK_PATTERN_FILE);
        sHaveNonZeroPatternFile.set(sLockPatternFilename.length() > 0);
        int fileObserverMask = FileObserver.CLOSE_WRITE
                | FileObserver.DELETE | FileObserver.MOVED_TO
                | FileObserver.CREATE;
        sPasswordObserver = new LockPatternFileObserver(
                dataSystemDirectory, fileObserverMask);
        sPasswordObserver.startWatching();
//		}
    }

    /**
     * Check to see if the user has stored a lock pattern.
     *
     * @return Whether a saved pattern exists.
     */
    public boolean savedPatternExists() {
        return sHaveNonZeroPatternFile.get();
    }

    public void clearLock() {
        saveLockPattern(null);

    }

    /**
     * Deserialize a pattern. 解密,用于保存状态
     *
     * @param string The pattern serialized with {@link #patternToString}
     * @return The pattern.
     */
    public static List<LockPatternView.Cell> stringToPattern(String string) {
        List<LockPatternView.Cell> result = new ArrayList<LockPatternView.Cell>();

        final byte[] bytes = string.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            result.add(LockPatternView.Cell.of(b / 3, b % 3));
        }
        return result;
    }

    /**
     * Serialize a pattern. 加密
     *
     * @param pattern The pattern.
     * @return The pattern in string form.
     */
    public static String patternToString(List<LockPatternView.Cell> pattern) {
        if (pattern == null) {
            return "";
        }
        final int patternSize = pattern.size();

        byte[] res = new byte[patternSize];
        for (int i = 0; i < patternSize; i++) {
            LockPatternView.Cell cell = pattern.get(i);
            res[i] = (byte) (cell.getRow() * 3 + cell.getColumn());
        }
        return new String(res);
    }

    /**
     * Save a lock pattern.
     *
     * @param pattern The new pattern to save.
     * @param
     */
    public void saveLockPattern(List<LockPatternView.Cell> pattern) {
        // Compute the hash
        final byte[] hash = LockPatternUtils.patternToHash(pattern);
        try {
            // Write the hash to file
            RandomAccessFile raf = new RandomAccessFile(new File(dataSystemDirectory, LOCK_PATTERN_FILE),
                    "rwd");
            // Truncate the file if pattern is null, to clear the lock
            if (pattern == null) {
                raf.setLength(0);
            } else {
                raf.write(hash, 0, hash.length);
            }
            raf.close();
        } catch (FileNotFoundException fnfe) {
            // Cant do much, unless we want to fail over to using the settings
            // provider
            Log.e(TAG, "Unable to save lock pattern to " + sLockPatternFilename);
        } catch (IOException ioe) {
            // Cant do much
            Log.e(TAG, "Unable to save lock pattern to " + sLockPatternFilename);
        }
    }

    public void saveStringLockPattern(String str) {
        // Compute the hash
        try {
            byte[] hash = str.getBytes();
            // Write the hash to file
            RandomAccessFile raf = new RandomAccessFile(new File(dataSystemDirectory, LOCK_PATTERN_FILE),
                    "rwd");
            // Truncate the file if pattern is null, to clear the lock
            if (hash == null) {
                raf.setLength(0);
            } else {
                raf.write(hash, 0, hash.length);
            }
            raf.close();
        } catch (FileNotFoundException fnfe) {
            // Cant do much, unless we want to fail over to using the settings
            // provider
            Log.e(TAG, "Unable to save lock pattern to " + sLockPatternFilename);
        } catch (IOException ioe) {
            // Cant do much
            Log.e(TAG, "Unable to save lock pattern to " + sLockPatternFilename);
        }
    }

    /*
     * Generate an SHA-1 hash for the pattern. Not the most secure, but it is at
     * least a second level of protection. First level is that the file is in a
     * location only readable by the system process.
     *
     * @param pattern the gesture pattern.
     *
     * @return the hash of the pattern in a byte array.
     */
    public static byte[] patternToHash(List<LockPatternView.Cell> pattern) {
        if (pattern == null) {
            return null;
        }
        final int patternSize = pattern.size();
        byte[] res = new byte[patternSize];
        for (int i = 0; i < patternSize; i++) {
            LockPatternView.Cell cell = pattern.get(i);
            res[i] = (byte) (cell.getRow() * 3 + cell.getColumn());
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(res);
            return hash;
        } catch (NoSuchAlgorithmException nsa) {
            return res;
        }
    }

    public static String patternToStrings(List<LockPatternView.Cell> pattern) {
        if (pattern == null) {
            return null;
        }
        final int patternSize = pattern.size();
//        String uid = SPUtils.geTinstance().getUid();
//        byte[] res = new byte[patternSize+uid.length()];
//        for (int i = 0; i < patternSize; i++) {
//            LockPatternView.Cell cell = pattern.get(i);
//            res[i] = (byte) (cell.getRow() * 3 + cell.getColumn());
//        }
//
//        for (int i = 0; i < uid.length(); i++) {
//            res[patternSize+i] = (byte)Integer.parseInt(uid.substring(i,i+1));
//        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < patternSize; i++) {
            LockPatternView.Cell cell = pattern.get(i);
            sb.append(cell.getRow() * 3 + cell.getColumn());
        }
        sb.append(SPUtils.geTinstance().getUid());
        byte[] res = sb.toString().getBytes();


        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(res);


//            return getString(hash);
            return CommonUtil.byteToHexString(hash);
        } catch (NoSuchAlgorithmException nsa) {
//            return getString(res);
            return CommonUtil.byteToHexString(res);
        }
    }

    public static String getString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(b[i]);
        }
        return sb.toString();
    }


    /**
     * Check to see if a pattern matches the saved pattern. If no pattern
     * exists, always returns true.
     *
     * @param pattern The pattern to check.
     * @return Whether the pattern matches the stored one.
     */
    public boolean checkPattern(List<LockPatternView.Cell> pattern) {
        try {
            // Read all the bytes from the file
            RandomAccessFile raf = new RandomAccessFile(new File(dataSystemDirectory, LOCK_PATTERN_FILE),
                    "r");
            final byte[] stored = new byte[(int) raf.length()];
            int got = raf.read(stored, 0, stored.length);
            raf.close();
            if (got <= 0) {
                return true;
            }
            // Compare the hash from the file with the entered pattern's hash
            return Arrays.equals(stored,
                    LockPatternUtils.patternToStrings(pattern).getBytes());
        } catch (FileNotFoundException fnfe) {
            return true;
        } catch (IOException ioe) {
            return true;
        }
    }

    /**
     * 判断是否设置过密码
     *
     * @param context
     * @return
     */
    public boolean isPatternSaved(Context context) {
        String userId = SPUtils.geTinstance().getUid();
        //TODO
        //不知是否正确
//		if (LOCK_PATTERN_FILE.equals("gesture.key")) {
//			LOCK_PATTERN_FILE = "gesture.key"+"_"+userId;
//		}  
        File file = new File(dataSystemDirectory, LOCK_PATTERN_FILE);
        try {
            // Read all the bytes from the file
            RandomAccessFile raf = new RandomAccessFile(file,
                    "r");
            final byte[] stored = new byte[(int) raf.length()];
            int got = raf.read(stored, 0, stored.length);
            raf.close();
            if (got <= 0) {
                return false;
            } else {
                return true;
            }
            // Compare the hash from the file with the entered pattern's hash
        } catch (FileNotFoundException fnfe) {
            return false;
        } catch (IOException ioe) {
            return false;
        }
    }
}
