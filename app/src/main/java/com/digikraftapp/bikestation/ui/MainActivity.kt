package com.digikraftapp.bikestation.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.digikraftapp.bikestation.databinding.ActivityMainBinding
import com.digikraftapp.bikestation.viewmodel.StationViewModel
import com.digikraftapp.bikestation.adapters.StationAdapter
import com.digikraftapp.bikestation.model.Feature
import com.digikraftapp.bikestation.util.Const
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //View binding
    private lateinit var binding: ActivityMainBinding

    //View model
    private val mainViewModel: StationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = "Bike Station"

        setUpUI()
        loadingIndication()
        observerStationList()

    }

    private fun setUpUI() {
        binding.jobsRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = StationAdapter(StationAdapter.OnClickListener{
                station -> pageNavigation(station)
            })
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayout.SHOW_DIVIDER_NONE))
        }
    }

    private fun loadingIndication() {
        mainViewModel.isLoading.observe(this) { isLoading: Boolean? ->
            if (isLoading == true) {
                binding.progressCircular.visibility = View.VISIBLE
            }else{
                binding.progressCircular.visibility = View.INVISIBLE
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observerStationList() {
        mainViewModel.getData().observe(this) { jobList ->
            jobList?.let {
                binding.jobsRecyclerview.apply {
                    with(adapter as StationAdapter) {
                        stationList = it
                        notifyDataSetChanged()
                    }
                }
            }
        }



    }

    private fun pageNavigation(station: Feature) {
        val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(Const.STATION, station)

        startActivity(intent)
    }


}