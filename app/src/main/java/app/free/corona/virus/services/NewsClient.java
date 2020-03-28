package app.free.corona.virus.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsClient {
    private static NewsApi newsApi;
    private static CoronaApi coronaApi;

    /**
     * @return NewsApi
     */
    public static NewsApi getNewsService() {
        if (newsApi == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                    .connectTimeout(30, TimeUnit.MINUTES)
                    .readTimeout(30, TimeUnit.MINUTES)
                    .writeTimeout(30, TimeUnit.MINUTES)
                    .build();
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://newsapi.org/v2/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            newsApi = retrofit.create(NewsApi.class);
        }
        return newsApi;
    }

    /**
     * @return NewsApi
     */
    public static CoronaApi getCoronaService() {
        if (coronaApi == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                    .connectTimeout(30, TimeUnit.MINUTES)
                    .readTimeout(30, TimeUnit.MINUTES)
                    .writeTimeout(30, TimeUnit.MINUTES)
                    .build();
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://corona.lmao.ninja/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            coronaApi = retrofit.create(CoronaApi.class);
        }
        return coronaApi;
    }

}
