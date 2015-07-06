/*
 * Copyright (c) 2015 ThanksMister LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed 
 * under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.thanksmister.btcblue.events;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

/**
 * A custom Bus to post events on the Main Thread only.
 */
@Singleton
public class ApplicationBus extends Bus
{
    private final Handler mainThread = new Handler(Looper.getMainLooper());

    public ApplicationBus() {
        super();
    }

    @Override
    public void post(final Object event) {

        if (event == null) {
            return;
        }

        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    post(event);
                }
            });
        }
    }
}
