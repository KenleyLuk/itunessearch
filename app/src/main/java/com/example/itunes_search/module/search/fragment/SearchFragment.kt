package com.example.itunes_search.module.search.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itunes_search.R
import com.example.itunes_search.base.BaseParcelable
import com.example.itunes_search.databinding.FragmentSearchBinding
import com.example.itunes_search.helper.itemDecoration.SpaceTopItemDecoration
import com.example.itunes_search.injection.component.ViewModelFactory
import com.example.itunes_search.model.ResultModel
import com.example.itunes_search.module.main.MainActivity
import com.example.itunes_search.module.search.adapter.SearchListAdapter
import com.example.itunes_search.module.search.viewModel.SearchListViewModel

class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    lateinit var viewModel: SearchListViewModel
    private var searchAdapter: SearchListAdapter = SearchListAdapter { result: ResultModel -> onItemClick(result) }
    private var resultModelList = listOf<ResultModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory()).get(SearchListViewModel::class.java)
        observer()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        setupView()
        return binding.root
    }

    private fun setupView() {
        binding.viewModel = viewModel
        binding.recyclerViewSearch.apply {
            binding.recyclerViewSearch.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.recyclerViewSearch.adapter = searchAdapter
            addItemDecoration(SpaceTopItemDecoration(context, 12))
        }

        resultModelList.let {
            if (it.isNotEmpty()){
                binding.coverPageSearch.visibility = View.GONE
            }else {
                binding.coverPageSearch.visibility = View.VISIBLE
            }
        }

        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    Log.d("onQueryTextChange", "empty")
                    binding.coverPageSearch.visibility = View.VISIBLE
                } else {
                    Log.d("onQueryTextChange", newText)
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d("onQueryTextSubmit", "Submit")
                loadApi(query)
                return false
            }

        })
    }

    private fun loadApi(queryText: String) {
        viewModel.callSearchAPI(queryText)
    }

    private fun observer() {
        viewModel.resultList.observe(this, Observer {
            it.let {
                if (it.isNotEmpty()) {
                    binding.coverPageSearch.visibility = View.GONE
                    resultModelList = it
                    searchAdapter.updateData(it)
                } else {
                    binding.coverPageSearch.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun onItemClick(resultModel: ResultModel) {
        val mainActivity = requireActivity() as MainActivity
        val bundle = Bundle()
        bundle.putParcelable("data", BaseParcelable(resultModel))
        mainActivity.fragmentTransaction(SearchDetailFragment(), bundle, true)
    }
}