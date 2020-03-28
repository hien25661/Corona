package app.free.corona.virus.services;

import java.util.ArrayList;

import app.free.corona.virus.services.responses.NewsListReponse;
import app.free.corona.virus.services.responses.corona.CountryInforResponse;
import app.free.corona.virus.services.responses.corona.OverviewResponse;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoronaApi {
    @GET("all")
    Observable<OverviewResponse> getOverview();

    @GET("countries")
    Observable<ArrayList<CountryInforResponse>> getCountriesInfor(@Query("sort") String sort);
}
