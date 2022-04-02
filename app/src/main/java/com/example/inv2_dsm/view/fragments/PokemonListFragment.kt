package com.example.inv2_dsm.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inv2_dsm.R
import com.example.inv2_dsm.databinding.FragmentPokemonListBinding
import com.example.inv2_dsm.model.PokemonDataModel
import com.example.inv2_dsm.view.adapter.ItemAdapter
import com.example.inv2_dsm.viewmodels.RecyclerPokemonViewModel

class PokemonListFragment : Fragment(), ClickListener {
    lateinit var viewModel: RecyclerPokemonViewModel
    lateinit var binding: FragmentPokemonListBinding
    private var mAdapter: ItemAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            activity?.let {
                ViewModelProvider(it).get(RecyclerPokemonViewModel::class.java)
            }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokemon_list, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Inicializa recycler view
        mAdapter = ItemAdapter(this)
        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        binding.recyclerview.adapter = mAdapter

        //Observador de la lista
        viewModel.listState.observe(viewLifecycleOwner) {
            mAdapter?.setItems(list = it)
            binding.progress.isInvisible = true
        }

        viewModel.progressState.observe(viewLifecycleOwner) { show->
            binding.progress.isVisible = show
        }

        viewModel.fetchPokemonData()
    }

    override fun itemSelect(data: PokemonDataModel){
        viewModel.setItemSelection(data)

        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(android.R.id.content, PokemonDetailFragment.newInstance())
            ?.addToBackStack(null)
            ?.commit()
    }
}

interface ClickListener {
    fun itemSelect(data: PokemonDataModel)
}