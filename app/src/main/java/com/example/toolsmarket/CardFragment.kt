package com.example.toolsmarket

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toolsmarket.adapters.CardsListAdapter
import com.example.toolsmarket.databinding.FragmentCardsBinding
import com.example.toolsmarket.models.LoadingResult
import com.example.toolsmarket.models.Card
import com.example.toolsmarket.repository.Mock
import com.example.toolsmarket.viewModels.CardsFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [CardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: CardsFragmentViewModel
    private var _binding: FragmentCardsBinding? = null
    private val cardsAdapter = CardsListAdapter()


    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardsBinding.inflate(inflater, container, false)
        init()
        viewModel.init()
        return binding.root
    }

    private fun init() {

        (activity?.application as ApplicationCore).component.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CardsFragmentViewModel::class.java)

        binding.cards.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        binding.cards.adapter = cardsAdapter

        val observer = Observer<LoadingResult<List<Card>?>> { newValue ->
            when (newValue) {
                is LoadingResult.Success -> cardsAdapter.submitList(newValue.value)
                is LoadingResult.Failure -> cardsAdapter.submitList(listOf(newValue.message?.let { x ->
                    newValue.moreInfo?.let { y ->
                        Card.ErrorCard(
                            x, y
                        )
                    }
                }))
                else -> cardsAdapter.submitList(Mock().getData())
            }

        }
        viewModel.liveData.observe(viewLifecycleOwner, observer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}