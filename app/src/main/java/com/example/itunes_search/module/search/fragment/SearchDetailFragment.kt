package com.example.itunes_search.module.search.fragment

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.itunes_search.R
import com.example.itunes_search.base.BaseParcelable
import com.example.itunes_search.databinding.FragmentSearchDetailBinding
import com.example.itunes_search.model.ResultModel

class SearchDetailFragment : Fragment() {
    lateinit var binding: FragmentSearchDetailBinding
    private lateinit var searchDetail: ResultModel
    private var mediaPlayer = MediaPlayer()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_detail,
            container,
            false
        )
        searchDetail = arguments?.getParcelable<BaseParcelable>("data")?.value as ResultModel
        setupView()
        return binding.root
    }

    private fun setupView() {
        binding.resultModel = searchDetail

        binding.ivStart.setOnClickListener {
            searchDetail.previewUrl.let {
                val uri = Uri.parse(searchDetail.previewUrl)
                try {
                    mediaPlayer.setDataSource(requireContext(), uri)
                    mediaPlayer.setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    mediaPlayer.prepareAsync()
                    mediaPlayer.setOnPreparedListener { mp ->
                        mp.start()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        binding.ivStop.setOnClickListener {
            mediaPlayer.stop()
            mediaPlayer.reset()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }
}