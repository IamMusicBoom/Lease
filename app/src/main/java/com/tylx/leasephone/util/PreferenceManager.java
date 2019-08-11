/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tylx.leasephone.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;



public class PreferenceManager {
    /**
     * name of preference
     */
    public static final String PREFERENCE_NAME = "saveInfo";
    private static SharedPreferences mSharedPreferences;
    private static PreferenceManager mPreferencemManager;
    private static SharedPreferences.Editor editor;


    @SuppressLint("CommitPrefEdits")
    private PreferenceManager(Context cxt) {
        mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public static synchronized void init(Context cxt) {
        if (mPreferencemManager == null) {
            mPreferencemManager = new PreferenceManager(cxt);
        }
    }

    /**
     * get instance of PreferenceManager
     *
     * @param
     * @return
     */
    public synchronized static PreferenceManager getInstance() {
        if (mPreferencemManager == null) {
            throw new RuntimeException("please init first!");
        }

        return mPreferencemManager;
    }


    public void setqq(String qq) {
        editor.putString("qq", qq);
        editor.apply();
    }

    public String getqq() {
        return mSharedPreferences.getString("qq", "");
    }

    public void setUnreadmsg(int num) {
        editor.putInt("Unreadmsg", num);
        editor.apply();
    }

    public void addUnreadmsg() {
        int n = getUnreadmsg();
        n++;
        editor.putInt("Unreadmsg", n);
        editor.apply();
    }

    public int getUnreadmsg() {
        return mSharedPreferences.getInt("Unreadmsg", 0);
    }
}
