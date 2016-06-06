/*
 * Modified from example here:
 * http://stackoverflow.com/questions/11341931/how-to-create-a-csv-on-android
 */

package com.thanksmister.btcblue.data;

import android.os.Environment;

import com.thanksmister.btcblue.data.api.model.Exchange;
import com.thanksmister.btcblue.utils.Conversions;
import com.thanksmister.btcblue.utils.Dates;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class WriterService
{
    @Inject
    public WriterService()
    {
    }

    public Observable<File> writeReceiptFileObservable(final String title, final Exchange exchange,
                                                       final String btcValue, final String arsValue, final String usdValue)
    {

        return Observable.create(new Observable.OnSubscribe<File>()
        {
            @Override
            public void call(Subscriber<? super File> subscriber)
            {
                File folder = new File(Environment.getExternalStorageDirectory() + "/BitcoinBlue");
                if (!folder.exists()) {
                    boolean var = folder.mkdir();
                }

                String timestamp = Dates.parseFileTimeStamp();
                final String filename = (title.isEmpty()) ? timestamp : (title + "_" + timestamp) + ".csv";
                File outFile = new File(folder, filename);

                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));

                    bw.append("NAME");
                    bw.append(',');

                    bw.append("DATE");
                    bw.append(',');

                    bw.append("EXCHANGE");
                    bw.append(',');

                    bw.append("ARS/USDB");
                    bw.append(',');

                    bw.append("ARS/USD");
                    bw.append(',');

                    bw.append("ARS AMOUNT");
                    bw.append(',');

                    bw.append("USD AMOUNT");
                    bw.append(',');

                    bw.append("BTC AMOUNT");
                    bw.append(',');
                    
                    bw.newLine();

                    bw.append(title);
                    bw.append(',');

                    bw.append(timestamp);
                    bw.append(',');

                    bw.append(exchange.getDisplay_name());
                    bw.append(',');

                    bw.append(exchange.getBlueFormatted());
                    bw.append(',');

                    bw.append(exchange.getOfficialFormatted());
                    bw.append(',');

                    bw.append(Conversions.formatCurrencyAmount(arsValue));
                    bw.append(',');

                    bw.append(usdValue);
                    bw.append(',');

                    bw.append(btcValue);
                    // bw.append(',');

                    bw.newLine();
                    bw.close();

                    subscriber.onNext(outFile);
                    subscriber.onCompleted();

                } catch (Exception e) {

                    String err = (e.getMessage() == null) ? "Failed to write file." : e.getMessage();
                    subscriber.onError(new Throwable(err));
                }
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}