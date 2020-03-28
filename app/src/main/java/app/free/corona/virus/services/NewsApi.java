package app.free.corona.virus.services;

import app.free.corona.virus.services.responses.NewsListReponse;
import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("everything")
    Flowable<Response<NewsListReponse>> getNewsList(@Query("q") String q,
                                                    @Query("to") String to,
                                                    @Query("sortBy") String sortBy,
                                                    @Query("apiKey") String apiKey,
                                                    @Query("page") int page

    );
}
