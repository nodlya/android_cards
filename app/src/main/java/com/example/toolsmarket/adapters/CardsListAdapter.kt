package com.example.toolsmarket.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.toolsmarket.R
import com.example.toolsmarket.models.Card
import com.example.toolsmarket.databinding.DefaultCardBinding
import com.example.toolsmarket.databinding.CardWithoutImageBinding
import com.example.toolsmarket.databinding.RoundCardBinding
import com.example.toolsmarket.databinding.ErrorCardBinding
import com.squareup.picasso.Picasso

class CardsListAdapter : ListAdapter<Card, RecyclerView.ViewHolder>(MyDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val binding = DefaultCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                DefaultCardWithBackgroundHolder(binding)
            }
            R.layout.default_card -> {
                val binding = DefaultCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                DefaultCardHolder(binding)
            }
            R.layout.card_without_image -> {
                val binding = CardWithoutImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                CardWithoutImageHolder(binding)
            }
            R.layout.round_card -> {
                val binding = RoundCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                RoundCardHolder(binding)
            }
            R.layout.error_card -> {
                val binding = ErrorCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                ErrorCardHolder(binding)
            }
            else -> throw IllegalStateException("Unknown view type $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Card.DefaultCardBackground -> 0
            is Card.DefaultCard -> R.layout.default_card
            is Card.CardWithoutImage -> R.layout.card_without_image
            is Card.RoundCard -> R.layout.round_card
            is Card.ErrorCard -> R.layout.error_card
            else -> Int.MAX_VALUE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> (holder as DefaultCardWithBackgroundHolder).bind(getItem(position) as Card.DefaultCardBackground)
            R.layout.default_card -> (holder as DefaultCardHolder).bind(getItem(position) as Card.DefaultCard)
            R.layout.card_without_image -> (holder as CardWithoutImageHolder).bind(getItem(position) as Card.CardWithoutImage)
            R.layout.round_card -> (holder as RoundCardHolder).bind(getItem(position) as Card.RoundCard)
            R.layout.error_card -> (holder as ErrorCardHolder).bind(getItem(position) as Card.ErrorCard)
            else -> throw IllegalStateException("Unknown item view type ${holder.itemViewType}")
        }
    }

    inner class DefaultCardHolder(private val binding: DefaultCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card.DefaultCard) = with(binding) {
            try {
                Picasso.get().load(card.img)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.broken_image)
                    .into(cardImage)
            } catch (ex: Exception) {
                Log.e("Error", ex.message.toString())
                ex.printStackTrace()
            }
            cardHeader.text = card.title
            cardInfo.text = card.subtitle
        }
    }

    inner class DefaultCardWithBackgroundHolder(private val binding: DefaultCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card.DefaultCardBackground) = with(binding) {
            try {
                Picasso.get().load(card.img)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.broken_image)
                    .into(cardImage)
            } catch (ex: Exception) {
                Log.e("Error", ex.message.toString())
                ex.printStackTrace()
            }
            cardHeader.text = card.title
            cardInfo.text = card.subtitle
            try {
                val color = Color.parseColor(card.hasBag)
                textInfo.setBackgroundColor(color)
            } catch (ex: Exception) {
                Log.e("Error", ex.message.toString())
                ex.printStackTrace()
            }
        }
    }

    inner class CardWithoutImageHolder(private val binding: CardWithoutImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card.CardWithoutImage) = with(binding) {
            cardHeader.text = card.title
            cardSubhead.text = card.subtitle
        }
    }

    inner class ErrorCardHolder(private val binding: ErrorCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card.ErrorCard) = with(binding) {
            cardHeader.text = card.title
            cardSubhead.text = card.subtitle
        }
    }
    inner class RoundCardHolder(private val binding: RoundCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card.RoundCard) = with(binding) {
            cardHeader.text = card.title
            cardSubhead.text = card.subtitle
            if (card.isCircle) {
                try {
                    Picasso.get().load(card.img)
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.broken_image)
                        .into(roundImage)
                } catch (ex: Exception) {
                    Log.e("Error", ex.message.toString())
                    ex.printStackTrace()
                }
            }
        }
    }

    class MyDiffCallback : DiffUtil.ItemCallback<Card>() {

        override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
            return oldItem == newItem
        }
    }
}