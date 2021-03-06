package com.fmt.cheaptrip.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.fmt.cheaptrip.R;
import com.fmt.cheaptrip.entities.User;
import com.fmt.cheaptrip.utils.login.DefaultLoginUtils;
import com.fmt.cheaptrip.utils.login.FacebookLoginUtils;
import com.fmt.cheaptrip.utils.login.GplusLoginUtils;
import com.fmt.cheaptrip.utils.login.LoginUtils;

/**
 * Created by santostc on 26-05-2016.
 */
public class UserAccountManager {

    /**
     * This method checks if the user is logged in any of these login types<br>
     *
     * @param context
     * @return true if is logged
     */
    public static boolean isLogged(Context context) {

        if (FacebookLoginUtils.isLogged(context)) {
            return true;
        } else if (GplusLoginUtils.isLogged(context)) {
            return true;

        } else if (DefaultLoginUtils.isLogged(context)) {
            return true;
        }
        return false;
    }

    /**
     * @param context
     * @return
     */
    public static String getCurrentUserId(Context context) {
        return LoginUtils.getCurrentUserId(context);
    }

    public static Bitmap getCurrentUserProfileImage(Context context) {

        Bitmap userProfilePicture = null;

        if (DefaultLoginUtils.isLogged(context)) {
            //
        } else if (GplusLoginUtils.isLogged(context)) {
            userProfilePicture = GplusLoginUtils.getUserPic(context);
        } else if (FacebookLoginUtils.isLogged(context)) {
            userProfilePicture = FacebookLoginUtils.getCurrentUserProfile(context);
        }
        if (userProfilePicture == null) {
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.developer);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            userProfilePicture = Bitmap.createScaledBitmap(bitmap, 160, 160, false);
        }

        return userProfilePicture;
    }

    public static String getCurrentUserName(Context context) {
        String userName = "";

        if (DefaultLoginUtils.isLogged(context)) {
            userName = DefaultLoginUtils.getUserName(context);
        } else if (GplusLoginUtils.isLogged(context)) {
            userName = GplusLoginUtils.getUserName(context);
        } else if (FacebookLoginUtils.isLogged(context)) {
            userName = FacebookLoginUtils.getUserName(context);
        }

        return userName;
    }

    public static String getCurrentUserEmail(Context context) {
        return LoginUtils.getCurrentUserEmail(context);
    }

    public static void clearAllData(Context context) {
        LoginUtils.removeCurrentUserEmail(context);
        logout(context);
    }

    public static void logout(Context context) {

        if (DefaultLoginUtils.isLogged(context)) {
            DefaultLoginUtils.revoke(context);
        } else if (GplusLoginUtils.isLogged(context)) {
            GplusLoginUtils.revoke(context);
        } else if (FacebookLoginUtils.isLogged(context)) {
            FacebookLoginUtils.logout(context);

        }
    }

}
