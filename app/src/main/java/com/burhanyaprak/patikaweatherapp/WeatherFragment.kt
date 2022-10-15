package com.burhanyaprak.patikaweatherapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.burhanyaprak.data.api.ApiClient
import com.burhanyaprak.data.model.DailyWeather
import com.burhanyaprak.data.model.WeatherModel
import com.burhanyaprak.patikaweatherapp.databinding.FragmentWeatherBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import kotlin.math.roundToInt

class WeatherFragment : Fragment() {
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    private val TAG: String = "WeatherFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWeatherBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getWeather()
    }

    private fun getWeather() {
        ApiClient.getApiService().getEverythingNews()
            .enqueue(object : Callback<WeatherModel> {
                @RequiresApi(Build.VERSION_CODES.O)
                @SuppressLint("SimpleDateFormat", "SetTextI18n")
                override fun onResponse(
                    call: Call<WeatherModel>,
                    response: Response<WeatherModel>
                ) {
                    if (response.isSuccessful) {
                        val weatherList = ArrayList<DailyWeather>()
                        val everything = response.body()
                        everything?.let {
                            binding.textViewCityName.text = it.timezone
                            binding.textViewTemp.text =
                                ((it.current.temp.roundToInt().toString()) + "\u00B0")
                            it.current.weather.forEach { weather ->
                                val iconUrl =
                                    "https://openweathermap.org/img/wn/${weather.icon}@2x.png"
                                Glide.with(requireActivity()).load(iconUrl)
                                    .into(binding.imageViewWeather)
                            }
                            var localDate: LocalDate = LocalDate.now()
                            it.daily.forEach { daily ->
                                localDate = localDate.plusDays(1)
                                val iconUrl =
                                    "https://openweathermap.org/img/wn/${daily.weather.first().icon}@2x.png"
                                val dayOfWeek = localDate.dayOfWeek.toString()
                                val tempWeatherDailyModel = DailyWeather(
                                    dayOfWeek,
                                    iconUrl,
                                    daily.temp.day,
                                    daily.temp.night
                                )
                                weatherList.add(tempWeatherDailyModel)
                            }
                            binding.recyclerViewWeatherForNextDays.adapter =
                                WeatherAdapter().apply {
                                    submitList(weatherList)
                                }
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                    Log.d(TAG, "failure: $t")
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}