package com.burhanyaprak.patikaweatherapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.burhanyaprak.data.model.DailyWeather
import com.burhanyaprak.patikaweatherapp.databinding.CustomListWeatherBinding
import kotlin.math.roundToInt

class WeatherAdapter: ListAdapter<DailyWeather, WeatherAdapter.WeatherViewHolder>(WeatherDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val itemBinding = CustomListWeatherBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        return WeatherViewHolder(itemBinding)
    }
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class WeatherViewHolder(
        itemBinding: CustomListWeatherBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        private val weatherDayDegree = itemBinding.weatherDayDegree
        private val weatherNightDegree = itemBinding.weatherNightDegree
        private val weatherDayName = itemBinding.weatherDayName
        private val weatherImage = itemBinding.imageViewWeather
        @SuppressLint("SetTextI18n")
        fun bind(dailyWeather: DailyWeather) {
            weatherDayName.text = dailyWeather.dayName
            weatherDayDegree.text = "${dailyWeather.dayDegree.roundToInt()}\u00B0"
            weatherNightDegree.text = "${dailyWeather.nightDegree.roundToInt()}\u00B0"
            Glide.with(itemView.context).load(dailyWeather.imageUrl)
                .into(weatherImage)
        }
    }

    class WeatherDiffUtil : DiffUtil.ItemCallback<DailyWeather>() {
        override fun areItemsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean {
            return oldItem.dayName == newItem.dayName
        }

        override fun areContentsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean {
            return oldItem == newItem
        }

    }
}