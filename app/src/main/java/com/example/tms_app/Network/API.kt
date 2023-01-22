package com.example.watchme.Network
import com.example.errorhandling.exception.ResultCallAdapterFactory
import com.example.tms_app.Network.Request.ApprovedRequestToken
import com.example.tms_app.Network.Request.MarkMovieBody
import com.example.tms_app.Network.Request.SessionId
import com.example.tms_app.Network.Request.ValidateRTokenLoginBody
import com.example.tms_app.Network.Response.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import kotlin.jvm.internal.Intrinsics.Kotlin


private const val BASE_URL = "https://api.themoviedb.org/3/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(ResultCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface Api {
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") api_key: String, @Query("language") language:String?,@Query("page") page:Int?, @Query("region") region:String?): kotlin.Result<GetMoviesResponse>

    @GET("authentication/token/new?")
    suspend fun getUnApprovedRequestToken(@Query("api_key") api_key:String): kotlin.Result<GetUnApprovalRequestToken>

    @POST("authentication/token/validate_with_login?")
    suspend fun validateRequestTokenWithLogin(@Query("api_key") api_key:String, @Body body: ValidateRTokenLoginBody): kotlin.Result<ValidateRTokenLoginResponse>

    @POST("authentication/session/new?")
    suspend fun createSessionKey(@Query("api_key") api_key: String, @Body approvedRequestToken: ApprovedRequestToken): kotlin.Result<CreateSessionIdResponse>

    @GET("account?")
    suspend fun getUserInfoFromServer(@Query("api_key") api_key: String, @Query("session_id") session_id: String): kotlin.Result<getUserInfoFromServerResponse>

    @GET("account/{account_id}/favorite/movies?")
    suspend fun getFavouriteMovies(@Query("api_key") api_key: String, @Query("session_id") session_id: String, @Query("page") page: Int = 1): kotlin.Result<FavouriteMoviesResult>

    @POST("account/{account_id}/favorite?")
    suspend fun markMovie(@Query("api_key") api_key: String, @Query("session_id") session_id: String, @Body markMovieBody : MarkMovieBody): kotlin.Result<MarkMovieResponse>

    @GET("tv/top_rated?")
    suspend fun getTopRatedSeries(@Query("api_key") api_key: String, @Query("language") language:String,@Query("page") page:Int): kotlin.Result<GetTopRatedTVResponse>

    @GET("account/{account_id}/favorite/tv")
    suspend fun getFavouriteTV(@Query("api_key") api_key: String, @Query("session_id") session_id: String, @Query("page") page: Int = 1): kotlin.Result<GetFavouriteTVResult>

    @GET("search/movie?")
    suspend fun searchForMovie(@Query("api_key") api_key: String, @Query("query") query: String, @Query("page") page: Int = 1): kotlin.Result<GetMoviesResponse>

    @GET("search/tv?")
    suspend fun searchForTv(@Query("api_key") api_key: String, @Query("query") query: String, @Query("page") page: Int = 1): kotlin.Result<GetTopRatedTVResponse>

    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    suspend fun logout(@Query("api_key") api_key: String, @Body session_id: SessionId):kotlin.Result<LogoutResponse>

    @GET("movie/{movie_id}?")
    suspend fun getMovieDetails( @Path("movie_id") movie_id: Int, @Query("api_key") api_key: String,):kotlin.Result<MovieDetails>

    /* @POST("discount")
     suspend fun postRequest(@Body discounts: DiscountsBodyModel): Result<DiscountPostResponse>*/

}

object Retrofit{
    val retrofitService: Api by lazy {
        retrofit.create(Api::class.java)
    }
}