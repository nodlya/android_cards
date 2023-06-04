package com.example.toolsmarket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toolsmarket.adapters.CardsListAdapter
import com.example.toolsmarket.databinding.FragmentCardsBinding
import com.example.toolsmarket.models.LoadingResult
import com.example.toolsmarket.models.Card
import com.example.toolsmarket.networks.ApiNetworkSource
import com.example.toolsmarket.networks.INetworkSource
import com.example.toolsmarket.repository.Mock
import com.example.toolsmarket.viewModels.CardsFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [CardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardFragment : Fragment() {

    private var _binding: FragmentCardsBinding? = null
    private val cardsAdapter = CardsListAdapter()
    private val viewModel = CardsFragmentViewModel()
    val liveData = MutableLiveData<LoadingResult<List<Card>?>>()
    private val network: INetworkSource = ApiNetworkSource()

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardsBinding.inflate(inflater, container, false)
        init()


        lifecycleScope.launch {
            val response = withContext(Dispatchers.IO) {
                network.getCards()
            }
            if (response.isSuccessful) {
                val responses = response.body()
                val cards = responses?.let { Card.getCards(it) }
                val result = LoadingResult.Success(cards)
                liveData.postValue(result)
            } else {
                liveData.postValue(LoadingResult.Failure("internet error", "check your internet connection"))
            }
        }


        return binding.root
    }

    private fun init() {
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
        liveData.observe(viewLifecycleOwner, observer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}