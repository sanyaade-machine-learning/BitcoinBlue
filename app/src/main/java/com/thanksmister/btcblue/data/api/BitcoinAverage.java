/*
 * <!--
 *   ~ Copyright (c) 2017. ThanksMister LLC
 *   ~
 *   ~ Licensed under the Apache License, Version 2.0 (the "License");
 *   ~ you may not use this file except in compliance with the License. 
 *   ~ You may obtain a copy of the License at
 *   ~
 *   ~ http://www.apache.org/licenses/LICENSE-2.0
 *   ~
 *   ~ Unless required by applicable law or agreed to in writing, software distributed 
 *   ~ under the License is distributed on an "AS IS" BASIS, 
 *   ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 *   ~ See the License for the specific language governing permissions and 
 *   ~ limitations under the License.
 *   -->
 */

package com.thanksmister.btcblue.data.api;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import rx.Observable;

public interface BitcoinAverage {
    @GET("/constants/time")
    Observable<Response> serverTime();

    @GET("/exchanges/{exchange_name}")
    Observable<Response> perExchangeData(@Header("X-signature") String signature,
                                         @Path("exchange_name") String exchange_name);
}