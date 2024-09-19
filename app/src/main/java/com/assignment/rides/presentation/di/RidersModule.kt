package com.assignment.rides.presentation.di

import com.assignment.rides.BuildConfig
import com.assignment.rides.data.repositoryImpl.VehicleRepositoryImpl
import com.assignment.rides.data.retrofit.RetrofitInterface
import com.assignment.rides.domain.repository.VehicleRepository
import com.assignment.rides.domain.usecase.FetchVehicleUseCase
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Charles Raj I on 18/09/24
 * @project Rides
 * @author Charles Raj
 */

@Module
@InstallIn(SingletonComponent::class)
class RidersModule {


    @Provides
    @Singleton
    fun provideRetrofitClient() : Retrofit {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(loggingInterceptor)
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.writeTimeout(60, TimeUnit.SECONDS)

        val gson = GsonBuilder()
            .create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInterface(retrofit: Retrofit) : RetrofitInterface {
        return retrofit.create(RetrofitInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideFetchVehicleRepository(retrofitInterface: RetrofitInterface) : VehicleRepository {
        return VehicleRepositoryImpl(retrofitInterface)
    }

    @Provides
    @Singleton
    fun provideFetchVehicleUseCase(vehicleRepository: VehicleRepository) : FetchVehicleUseCase {
        return FetchVehicleUseCase(vehicleRepository)
    }

}