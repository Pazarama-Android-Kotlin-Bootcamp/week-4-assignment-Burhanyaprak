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
import com.burhanyaprak.data.model.Daily
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

    //lazy keyword allows the property to take up memory when it is used - it helps to load screen faster
    //lazy keyword allows the property to take up memory when it is used - it helps to load screen faster
    // val weatherAdapter = WeatherAdapter()
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
        getEverything()
    }

    private fun getEverything() {
        ApiClient.getApiService().getEverythingNews()
            .enqueue(object : Callback<WeatherModel> {
                @RequiresApi(Build.VERSION_CODES.O)
                @SuppressLint("SimpleDateFormat", "SetTextI18n")
                override fun onResponse(
                    call: Call<WeatherModel>,
                    response: Response<WeatherModel>
                ) {
                    Log.d("deneme", "response: " + response.body().toString())
                    if (response.isSuccessful) {
                        val everything = response.body()
                        everything?.let {
                            //Log.d("deneme1", "Timezone: " + it.timezone)
                            binding.textViewCityName.text = it.timezone
                            binding.textViewTemp.text =
                                ((it.current.temp.roundToInt().toString()) + " " + "\u2103")
                            it.current.weather.forEach { weather ->
                                val iconUrl =
                                    "https://openweathermap.org/img/wn/${weather.icon}@2x.png"
                                Glide.with(requireActivity()).load(iconUrl)
                                    .into(binding.imageViewWeather)
                            }
                            var localDate: LocalDate = LocalDate.now()
                            var i = 0
                            it.daily.forEach { daily ->
                                localDate = localDate.plusDays(1)
                                if (i == 0) {
                                    binding.textViewWeatherNameFirstNextDay.text =
                                        localDate.dayOfWeek.toString()
                                    binding.texviewDayDegreeFirstNextDay.text =
                                        daily.temp.day.roundToInt().toString()
                                    binding.texviewNightDegreeFirstNextDay.text =
                                        daily.temp.night.roundToInt().toString()
                                    val iconUrl =
                                        "https://openweathermap.org/img/wn/${daily.weather.first().icon}@2x.png"
                                    Glide.with(requireActivity()).load(iconUrl)
                                        .into(binding.imageViewFirstNextDay)
                                } else if (i == 1) {
                                    binding.textViewWeatherNameSecondNextDay.text =
                                        localDate.dayOfWeek.toString()
                                    binding.texviewDaySecondNextDay.text =
                                        daily.temp.day.roundToInt().toString()
                                    binding.texviewNightSecondNextDay.text =
                                        daily.temp.night.roundToInt().toString()
                                    val iconUrl2 =
                                        "https://openweathermap.org/img/wn/${daily.weather.first().icon}@2x.png"
                                    Glide.with(requireActivity()).load(iconUrl2)
                                        .into(binding.imageViewSecondNextDay)
                                } else if (i == 2) {
                                    binding.textViewWeatherThirdNextDay.text =
                                        localDate.dayOfWeek.toString()
                                    binding.texviewDayThirdNextDay.text =
                                        daily.temp.day.roundToInt().toString()
                                    binding.texviewNightThirdNextDay.text =
                                        daily.temp.night.roundToInt().toString()
                                    val iconUrl3 =
                                        "https://openweathermap.org/img/wn/${daily.weather.first().icon}@2x.png"
                                    Glide.with(requireActivity()).load(iconUrl3)
                                        .into(binding.imageViewThirdNextDay)
                                }
                                i++
                            }

                        }
                    }
                }

                override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                    Log.d("deneme1", "failure $t")
                }
            })
    }

    fun getDaytring(dayNumber: Int): List<String> {
        return when (dayNumber) {
            1 -> listOf("Sunday")
            2 -> listOf("Monday")
            3 -> listOf("Tuesday")
            4 -> listOf("Wednesday")
            5 -> listOf("Thursday")
            6 -> listOf("Friday")
            7 -> listOf("Saturday")

            else -> listOf("-1", "-1", "-1")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}