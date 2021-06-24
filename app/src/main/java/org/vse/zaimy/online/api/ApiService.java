package org.vse.zaimy.online.api;

import org.vse.zaimy.online.pojo.actual_backend.ActualBackendResponse;
import org.vse.zaimy.online.pojo.date.DateResponse;
import org.vse.zaimy.online.pojo.db.DbResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET(".")
    Observable<Response<ActualBackendResponse>> getActualBackend(
            @Query("p1") String p1,
            @Query("p2") String p2,
            @Query("p3") String p3,
            @Query("p4") String p4,
            @Query("p5") String p5,
            @Query("p6") String p6,
            @Query("p7") String p7,
            @Query("p8") String p8,
            @Query("p9") String p9,
            @Query("p10") String p0
    );

    @GET("{actualbackend}/date.json")
    Observable<Response<DateResponse>> getActualDate(
            @Path(value = "actualbackend", encoded = true)  String actualbackend);

    @GET("{actualbackend}/db.json")
    Observable<Response<DbResponse>> getActualDB(
            @Path(value = "actualbackend", encoded = true)  String actualbackend);

}
